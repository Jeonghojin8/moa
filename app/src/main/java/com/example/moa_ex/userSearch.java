package com.example.moa_ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class userSearch extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_search, stringRequest;


    ListView userList;
    search_adapter adapter;
    ArrayList<user_dataVO> list;

    Button btn_search;
    Button btn_add;
    Button btn_mypage;
    EditText et_Sname;


    ///////////////////////////////////////////////////////
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    ///////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersearch);


        ////////////////////////////////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String A_ID = sharedPreferences.getString(KEY_NAME, null);
        if (A_ID != null) {
            // textView.setText(ID);
            Log.d(A_ID, "onCreate: ");
        }
        /////////////////////////////////


        et_Sname = findViewById(R.id.et_Sname);

        btn_search = findViewById(R.id.btn_search);
        btn_add = findViewById(R.id.btn_add);
        btn_mypage = findViewById(R.id.btn_mypage);

        queue = Volley.newRequestQueue(userSearch.this);

        userList = findViewById(R.id.userList);

        list = new ArrayList<>();

        adapter = new search_adapter("add", userSearch.this, R.layout.search_layout2, list);

        userList.setAdapter(adapter);

        all_list();


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_sRequestPost();
            }

            public void search_sRequestPost() {

                int method = Request.Method.POST;
                String server_url = "http://172.30.1.59:3000/home/userSearch";

                list.clear();

                stringRequest_search = new StringRequest(
                        method,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("NodeConnActivity", "응답받은 데이터: " + response);

                                if (response == null) {
                                    Toast.makeText(getApplicationContext(), "찾으시는 어르신이 안계십니다", Toast.LENGTH_SHORT).show();
                                }

                                try {
                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject dept = array.getJSONObject(i);

                                        String S_id = dept.getString("s_id");
                                        Log.d(S_id, "onResponse: 아이디 검사중");


                                        String S_name = dept.getString("s_name");
                                        String S_birth = dept.getString("s_birth");
                                        String S_phone = dept.getString("s_phone");

                                        list.add(new user_dataVO(S_id, S_name, S_birth, S_phone));

                                    }

                                    adapter = new search_adapter("add", userSearch.this, R.layout.search_layout2, list);

                                    userList.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                adapter.notifyDataSetChanged();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley Error", "오류발생>>" + error.toString());
                            }
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        String search_name = et_Sname.getText().toString();

                        param.put("search_name", search_name);

                        return param;
                    }
                };

                queue.add(stringRequest_search);
            }

        });

        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userSearch.this, admin_mypage.class);
                startActivity(i);
                finish();
            }
        });
    }

        public void all_list() {

            int method = Request.Method.POST;
            String server_url = "http://172.30.1.42:3000/home/userAll";

            stringRequest = new StringRequest(
                    method,
                    server_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("NodeConnActivity", "응답받은 데이터(list): " + response);

                            try {
                                JSONArray array = new JSONArray(response);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject info = array.getJSONObject(i);

                                    String S_id = info.getString("s_id");
                                    Log.d(S_id, "onResponse: 아이디 검사중");


                                    String S_name = info.getString("s_name");
                                    String S_birth = info.getString("s_birth");
                                    String S_phone = info.getString("s_phone");

                                    list.add(new user_dataVO(S_id,S_name, S_birth, S_phone));

                                }

                                adapter = new search_adapter("add",userSearch.this, R.layout.search_layout2,list);

                                userList.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error", "오류발생>>" + error.toString());
                        }
                    }
            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();



                    return param;
                }
            };

            queue.add(stringRequest);
        }


}
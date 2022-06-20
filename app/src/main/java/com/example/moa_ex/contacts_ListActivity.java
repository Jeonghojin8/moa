package com.example.moa_ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class contacts_ListActivity extends AppCompatActivity {

    TextView tv_spinner;
    Spinner spinner;
    String[] items = { "선택해주세요","의료기관", "공공기관", "가족"};

    ListView listView;
    contacts_deptAdapter deptAdapter;
    ArrayList<contacts_dept_VO> list;

    Button btn_previous;
    Button btn_home;


    RequestQueue queue;
    StringRequest requestDept,requestHospital,requestProtectorList;

    ///////////////////////////////////////////////////////
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME2 = "mypref";
    private static final String KEY_NAME2 = "name";
    /////////////////////////////////////////////////////// - 1

//    SharedPreferences spf = getSharedPreferences("test", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list_view);

        btn_previous = findViewById(R.id.btn_previous);
        btn_home = findViewById(R.id.btn_home);


        ////////////////////////////////
//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME2,MODE_PRIVATE);
//        String S_ID = sharedPreferences.getString(KEY_NAME2,null);
//        if(S_ID != null){
//            // textView.setText(ID);
//            Log.d(S_ID, "onCreate: ");
//        }
        ///////////////////////////////// - 2

        SharedPreferences spf = getSharedPreferences("test",Context.MODE_PRIVATE);

        listView = findViewById(R.id.listview);
        list = new ArrayList<>();

        queue = Volley.newRequestQueue(contacts_ListActivity.this);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contacts_ListActivity.this,admin_mypage.class);
                startActivity(i);
                finish();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contacts_ListActivity.this,userSearch.class);
                startActivity(i);
                finish();
            }
        });

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 스피너 아이템 클릭시 값을 받아와줌
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tv_spinner = (TextView) view;
                tv_spinner.setText(items[position]);
                String text = spinner.getSelectedItem().toString();



                if(text == "공공기관"){
                    list.clear();
                    deptRequest();
                    queue.add(requestDept);


                }else if(text == "의료기관"){
                    list.clear();
                    hospitalRequest();
                    queue.add(requestHospital);


                }else if (text == "가족"){
                    list.clear();
                    protectorListRequest();
                    queue.add(requestProtectorList);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_spinner.setText("선택: ");
            }
        });

    }
    public void deptRequest(){
        int method = Request.Method.GET;
//       String server_url = "http://172.30.1.2:3000/home/list?name=dept";
        String server_url = "http://172.30.1.59:3000/home/dept"; // 서버주소

        requestDept = new StringRequest(

                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("기관정보", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("dept_list");

                            for(int i=0; i<array.length(); i++){
                                JSONObject dept = array.getJSONObject(i);

                                String dept_name = dept.getString("dept_name");
                                String dept_tel = dept.getString ("dept_tel");

                                list.add(new contacts_dept_VO(dept_name,dept_tel));

                            }

                            deptAdapter = new contacts_deptAdapter(
                                    contacts_ListActivity.this,
                                    R.layout.contacts_listitem,
                                    list);

                            listView.setAdapter(deptAdapter);
                            // 포스터 어댑터

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("오류발생", error.toString());
                    }
                }

        );

    }// deptRequest
    public void hospitalRequest(){
        int method = Request.Method.GET;
//       String server_url = "http://172.30.1.2:3000/home/list?name=dept";
        String server_url = "http://172.30.1.2:3000/home/hospital"; // 선경 서버주소

        requestHospital = new StringRequest(

                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("기관정보", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("dept_list");

                            for(int i=0; i<array.length(); i++){
                                JSONObject dept = array.getJSONObject(i);

                                String dept_name = dept.getString("dept_name");
                                String dept_tel = dept.getString ("dept_tel");

                                list.add(new contacts_dept_VO(dept_name,dept_tel));

                            }

                            deptAdapter = new contacts_deptAdapter(
                                    contacts_ListActivity.this,
                                    R.layout.contacts_listitem,
                                    list);

                            listView.setAdapter(deptAdapter);
                            // 포스터 어댑터

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("오류발생", error.toString());
                    }
                }

        );

    }// hospitalRequest
    public void protectorListRequest(){
        int method = Request.Method.POST;;
        String server_url = "http://172.30.1.42:3000/home/protectorList"; // 선경 서버주소

        requestProtectorList = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("가족정보", response);

                        try {
                            JSONArray array = new JSONArray(response);

                            for(int i=0; i<array.length(); i++){
                                JSONObject dept = array.getJSONObject(i);
                                String dept_name = dept.getString("s_protector");
                                String dept_tel = dept.getString ("p_phone");

                                list.add(new contacts_dept_VO(dept_name,dept_tel));

                            }

                            deptAdapter = new contacts_deptAdapter(
                                    contacts_ListActivity.this,
                                    R.layout.contacts_listitem,
                                    list);

                            listView.setAdapter(deptAdapter);
                            // 포스터 어댑터

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("오류발생", error.toString());
                    }
                }

        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

//                SharedPreferences spf = getSharedPreferences("test",Context.MODE_PRIVATE);

                Intent i = getIntent();
                String s_id = i.getStringExtra("tv_Sid");
                param.put("s_id",s_id);

                Log.d(s_id, "서버에 보내는 아이디");



                return param;
            }
        };

    } //protectorListRequest


}
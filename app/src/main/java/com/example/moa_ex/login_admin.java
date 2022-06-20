package com.example.moa_ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login_admin extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_login;

    EditText et_id, et_pw;
    Button btn_start, btn_join;

    /////////////////////////////////////////
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    ///////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        queue = Volley.newRequestQueue(login_admin.this);

        et_id = findViewById(R.id.et_Sname);
        et_pw = findViewById(R.id.et_pw);
        btn_start = findViewById(R.id.btn_start);
        btn_join = findViewById(R.id.btn_join);

        //////////////////////////////////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);
        ////////////////////////////////



        // 회원가입 버튼 클릭시
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login_admin.this, join_admin.class);
                startActivity(i);

            }
        });

        // 시작하기 버튼 클릭시
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_id.getText().toString().equals("")) {
                    Toast.makeText(login_admin.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {

                    ///////////////////////////////////////
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_NAME, et_id.getText().toString());
                    editor.apply();
                    /////////////////////////////////////////

                    if (name != null) {
                        // 데이터 사용시 호출
                        Intent intent = new Intent(login_admin.this, userSearch.class);
                        startActivity(intent);
                    }

                    // 요청 메소드
                    login_adminRequestPost();
                }
            }
        });


    }

    public void login_adminRequestPost(){
        int method = Request.Method.POST;
        String login_id = et_id.getText().toString();
        String login_pw = et_pw.getText().toString();
        String server_url = "http://172.30.1.59:3000/home/admin_login";

        stringRequest_login = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("","응답받은 데이터 >>"+response);
                        if (response.toString().equals("성공")){
                            Intent i = new Intent(login_admin.this, userSearch.class);
                            startActivity(i);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error","오류발생 >> "+error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("a_id",login_id);
                param.put("a_pw",login_pw);

                return param;
            }
        };

        queue.add(stringRequest_login);

    }


}
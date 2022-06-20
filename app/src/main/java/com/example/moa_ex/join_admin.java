package com.example.moa_ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class join_admin extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_join;

    EditText et_Aid, et_Anum, et_Aname, et_Apw, et_Atell;
    Button btn_joinA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_admin);

        queue = Volley.newRequestQueue(join_admin.this);

        et_Aid = findViewById(R.id.et_Aid);
        et_Anum = findViewById(R.id.et_Anum);
        et_Aname = findViewById(R.id.et_Aname);
        et_Apw = findViewById(R.id.et_Apw);
        et_Atell = findViewById(R.id.et_Atell);
        btn_joinA = findViewById(R.id.btn_joinA);

        // 회원가입 버튼 구현
        btn_joinA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 메소드 호출
                join_adminRequestPost();
            }
        });

    }

    public void join_adminRequestPost(){
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.59:3000/home/admin_join";

        stringRequest_join = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("NodeConnActivity2","응답받은 데이터: " +response);
                        if(response.toString().equals("성공")){
                            Intent i = new Intent(join_admin.this,join_success_a.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "관리자 가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                String Aid = et_Aid.getText().toString();
                String Apw = et_Apw.getText().toString();
                String Anum = et_Anum.getText().toString();
                String Aname = et_Aname.getText().toString();
                String Atell = et_Atell.getText().toString();

                param.put("a_id",Aid);
                param.put("a_pw",Apw);
                param.put("a_birth",Anum);
                param.put("a_name",Aname);
                param.put("a_phone",Atell);

                return param;
            }
        };

        queue.add(stringRequest_join);
    }

}


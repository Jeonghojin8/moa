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

public class join_senior_phone extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_join_phone;

    Button btn_next;
    EditText ed_Sphone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_senior_phone);

        ed_Sphone = findViewById(R.id.ed_Pphone);
        btn_next = findViewById(R.id.btn_next);
        queue = Volley.newRequestQueue(join_senior_phone.this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_Sphone.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                }else{
                    join_senior();
                }
            }
        });
    }

    public void join_senior(){
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.59:3000/home/user_join";

        stringRequest_join_phone = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("join_senior_phone", "응답받은 데이터" + response);
                        if (response.toString().equals("사용자회원가입 성공!")) {
                            Toast.makeText(join_senior_phone.this, "가입에 성공했습니다", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(join_senior_phone.this, join_success_s.class);
                            startActivity(i);
                            finish();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error","오류발생>>" +error.toString());
                        Toast.makeText(join_senior_phone.this, "가입에 실패했습니다", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                String S_PHONE = ed_Sphone.getText().toString();

                Intent i = new Intent(join_senior_phone.this, join_success_s.class);

                Intent i4 = getIntent();
                String S_NAME = i4.getStringExtra("S_NAME");
                Intent i5 = getIntent();
                String S_ID = i5.getStringExtra("S_ID");
                Intent i6 = getIntent();
                String S_BIRTH = i6.getStringExtra("S_BIRTH");

                i.putExtra("S_NAME", S_NAME);
                i.putExtra("S_ID", S_ID);
                i.putExtra("S_BIRTH", S_BIRTH);
                i.putExtra("S_PHONE", S_PHONE);

                param.put("S_NAME",S_NAME);
                param.put("S_ID",S_ID);
                param.put("S_BIRTH",S_BIRTH);
                param.put("S_PHONE",S_PHONE);

                return param;
            }
        };
        queue.add(stringRequest_join_phone);

    }
}
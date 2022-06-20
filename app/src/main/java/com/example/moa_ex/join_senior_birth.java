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

public class join_senior_birth extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_join_birth;

    Button btn_next;
    EditText ed_Sbirth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_senior_birth);

        ed_Sbirth = findViewById(R.id.ed_Pphone);
        btn_next = findViewById(R.id.btn_next);

        queue = Volley.newRequestQueue(join_senior_birth.this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ed_Sbirth.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();

                }else {

                    String S_BIRTH = ed_Sbirth.getText().toString();

                    Intent i = new Intent(join_senior_birth.this, join_senior_phone.class);

                    Intent i2 = getIntent();
                    String S_NAME = i2.getStringExtra("S_NAME");
                    Intent i3 = getIntent();
                    String S_ID = i3.getStringExtra("S_ID");

                    i.putExtra("S_NAME", S_NAME);
                    i.putExtra("S_ID", S_ID);
                    i.putExtra("S_BIRTH", S_BIRTH);

                    Log.i(S_NAME,"checking..");
                    Log.i(S_ID,"checking..");
                    Log.i(S_BIRTH,"checking..");

                    startActivity(i);
                    finish();
                }

            }
        });
        }
}
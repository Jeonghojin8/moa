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

public class join_senior_f_name extends AppCompatActivity {

    Button btn_next;
    EditText ed_Pname;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_senior_fname);

        queue = Volley.newRequestQueue(join_senior_f_name.this);

        ed_Pname = findViewById(R.id.ed_Pphone);
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ed_Pname.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "가족분의 성함을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {

                    String F_NAME = ed_Pname.getText().toString();

                    Intent i = new Intent(join_senior_f_name.this, join_senior_f_phone.class);
                    Intent i2 = getIntent();
                    String FAMILY = i2.getStringExtra("FAMILY");

                    i.putExtra("FAMILY",FAMILY);
                    i.putExtra("F_NAME", F_NAME);

                    Log.i(FAMILY,"checking5..");
                    Log.i(F_NAME,"checking5..");

                    startActivity(i);
                    finish();

                }
            }
        });
    }
}
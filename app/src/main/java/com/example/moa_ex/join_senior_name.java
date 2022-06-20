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

public class join_senior_name extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_join_name;

    Button btn_next;
    EditText ed_Sname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_senior_name);

        queue = Volley.newRequestQueue(join_senior_name.this);

        ed_Sname = findViewById(R.id.ed_Pphone);
        btn_next = findViewById(R.id.btn_next);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ed_Sname.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "성함을 입력해주세요", Toast.LENGTH_SHORT).show();

                }else{

                    String S_NAME = ed_Sname.getText().toString();

                    Intent i = new Intent(join_senior_name.this, join_senior_id.class);

                    i.putExtra("S_NAME",S_NAME);
                    Log.i(S_NAME, "onClick: ");

                    startActivity(i);
                    finish();

                }
            }
        });

    }

}



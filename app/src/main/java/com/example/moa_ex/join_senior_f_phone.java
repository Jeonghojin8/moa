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

public class join_senior_f_phone extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_join_fphone;

    Button btn_add;
    EditText ed_Pphone;

    ///////////////////////////////////////////////////////
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME2 = "mypref2";
    private static final String KEY_NAME2 = "name2";
    ///////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_senior_fphone);

        ed_Pphone = findViewById(R.id.ed_Pphone);
        btn_add = findViewById(R.id.btn_next);
        queue = Volley.newRequestQueue(join_senior_f_phone.this);




        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_Pphone.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "가족분 연락처를 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {

                }

                join_sRequestPost4();

            }
        });
    }
    public void join_sRequestPost4(){

        int method = Request.Method.POST;
        String server_url = "http://172.30.1.59:3000/home/protector_tel";

        stringRequest_join_fphone = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("NodeConnActivity","응답받은 데이터: " +response);

                        if(response.toString().equals("성공")){

//                            String F_PHONE = ed_Pphone.getText().toString();
//                            i.putExtra("F_PHONE", F_PHONE);
                            Intent i = new Intent(join_senior_f_phone.this, userMainActivity.class);

                            Toast.makeText(getApplicationContext(), "보호자 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();

                        }else{

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error","오류발생>>" +error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                /////////////////////////////////////////////
                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                String S_ID = sharedPreferences.getString(KEY_NAME2, null);
                if (S_ID != null) {
                    // textView.setText(ID);
                    Log.d(S_ID, "onCreate: ");
                }
                /////////////////////////////////////////////////

                String F_PHONE = ed_Pphone.getText().toString();
                Intent i = new Intent(join_senior_f_phone.this, login_senior.class);

                Intent i2 = getIntent();
                String FAMILY = i2.getStringExtra("FAMILY");
                Intent i3 = getIntent();
                String F_NAME = i3.getStringExtra("F_NAME");

                i.putExtra("FAMILY",FAMILY);
                i.putExtra("F_NAME", F_NAME);
                i.putExtra("F_PHONE", F_PHONE);

                param.put("FAMILY",FAMILY);
                param.put("P_NAME",F_NAME);
                param.put("F_PHONE",F_PHONE);
                param.put("S_ID",S_ID);

                Log.i(S_ID,"checking..");
                Log.i(FAMILY,"checking..");
                Log.i(F_NAME,"checking..");
                Log.i(F_PHONE,"checking..");

                return param;
            }
        };

        queue.add(stringRequest_join_fphone);
    }

}
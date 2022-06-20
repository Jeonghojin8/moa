package com.example.moa_ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

public class popup extends AppCompatActivity {

    RequestQueue queue;
    StringRequest R_matching;

    Button btn_yes, btn_no;

    ///////////////////////////////////////////////////////
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        btn_yes = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);
        queue = Volley.newRequestQueue(popup.this);

        ////////////////////////////////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String A_ID = sharedPreferences.getString(KEY_NAME, null);
        if (A_ID != null) {
            // textView.setText(ID);
            Log.d(A_ID, "onCreate: ");
        }
        /////////////////////////////////

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                matching_sRequestPost();
                  matching();
                Intent i2 = new Intent(popup.this, userSearch.class);
                startActivity(i2);

            }
        });
    }

    public void CancleEvent(View view){
        finish();
    }

    public void matching(){
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.59:3000/home/matching";

        R_matching = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("NodeConnActivity2","응답받은 데이터: " +response);

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

                Intent i = getIntent();
                String tv_Sid = i.getStringExtra("tv_Sid");
                Log.d(tv_Sid, "getParams: 팝업창에 연결된 s_id");

                param.put("s_id",tv_Sid);


                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String A_ID = sharedPreferences.getString(KEY_NAME, null);
                param.put("a_id",A_ID);
                Log.i(A_ID,"CHECK!!!!");


                return param;
            }
        };

        queue.add(R_matching);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        // 바깥레이어 클릭 시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        // 안드로이드 백버튼 막기
        return;
    }



}
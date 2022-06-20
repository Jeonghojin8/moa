package com.example.moa_ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
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

public class join_senior_family extends AppCompatActivity {

    RequestQueue queue;
    StringRequest stringRequest_join_family;

    ToggleButtonGroupTableLayout R_group;
    RadioButton r_btn1;
    RadioButton r_btn2;
    RadioButton r_btn3;
    RadioButton r_btn4;
    RadioButton r_btn5;
    RadioButton r_btn6;
    Button btn_next;

//    String FAMILY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_senior_family);

        queue = Volley.newRequestQueue(join_senior_family.this);


        R_group = (ToggleButtonGroupTableLayout) findViewById(R.id.R_group);

        r_btn1 = (RadioButton) findViewById(R.id.r_btn1);
        r_btn2 = (RadioButton) findViewById(R.id.r_btn2);
        r_btn3 = (RadioButton) findViewById(R.id.r_btn3);
        r_btn4 = (RadioButton) findViewById(R.id.r_btn4);
        r_btn5 = (RadioButton) findViewById(R.id.r_btn5);
        r_btn6 = (RadioButton) findViewById(R.id.r_btn6);

        btn_next = findViewById(R.id.btn_next);



        R_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID) {

                if (checkedID == R.id.r_btn1) {
                    Toast.makeText(join_senior_family.this, "배우자를 선택하셨습니다", Toast.LENGTH_SHORT).show();

                } else if (checkedID == R.id.r_btn2) {
                    Toast.makeText(join_senior_family.this, "형제/자매를 선택하셨습니다", Toast.LENGTH_SHORT).show();

                } else if (checkedID == R.id.r_btn3) {
                    Toast.makeText(join_senior_family.this, "자녀를 선택하셨습니다", Toast.LENGTH_SHORT).show();

                } else if (checkedID == R.id.r_btn4) {
                    Toast.makeText(join_senior_family.this, "부모를 선택하셨습니다", Toast.LENGTH_SHORT).show();

                } else if (checkedID == R.id.r_btn5) {
                    Toast.makeText(join_senior_family.this, "기타를 선택하셨습니다", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(join_senior_family.this, "'없다.'를 선택하셨습니다", Toast.LENGTH_SHORT).show();
                    finish();

                }


            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(join_senior_family.this, join_senior_f_name.class);

                int id = R_group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)findViewById(id);

                String FAMILY = rb.getText().toString();

                i.putExtra("FAMILY",FAMILY);

                Log.i(FAMILY,"checking..");

                startActivity(i);
                finish();

            }
        });


    }

}





package com.example.moa_ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class join_success_s extends AppCompatActivity {

    Button btn_gologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_success_s);

        btn_gologin = findViewById(R.id.btn_gologin);
    }


    public void onClickButton1(View v){
        Intent i = new Intent(this, login_senior.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // 액티비티 스택에 쌓이지 않음
        startActivity( i );
    }
}

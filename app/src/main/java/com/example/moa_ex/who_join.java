package com.example.moa_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class who_join extends AppCompatActivity {

    Button btn_Slogin, btn_Alogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_join);

        btn_Slogin = findViewById(R.id.btn_Slogjn);
        btn_Alogin = findViewById(R.id.btn_Alogin);
    }


    // 사용자 로그인 페이지로 이동
    public void onClickButton1(View v){
        Intent i = new Intent(this, login_senior.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // 액티비티 스택에 쌓이지 않음
        startActivity( i );
    }

    //관리자 로그인 페이지로 이동
    public void onClickButton2(View v){
        Intent i = new Intent(this, login_admin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // 액티비티 스택에 쌓이지 않음
        startActivity( i );
    }
}
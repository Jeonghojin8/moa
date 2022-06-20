package com.example.moa_ex;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class ListVIewHolder{

    TextView dept_name;
    TextView dept_tell;
    ImageView imgProfile;

    Button btn_alert;
    Button btn_call;
    Button btn_message;


    public ListVIewHolder(View itemView){
//            super(itemView);

        imgProfile = itemView.findViewById(R.id.imgProfile);
        dept_name = itemView.findViewById(R.id.tv_dept_name);
        dept_tell = itemView.findViewById(R.id.tv_dept_tell);

        btn_call = itemView.findViewById(R.id.btn_alert);
        btn_call = itemView.findViewById(R.id.btn_call);
        btn_message = itemView.findViewById(R.id.btn_message);


    }

    public TextView getDept_name(){
        return dept_name;
    }
    public TextView getDept_tell(){
        return dept_tell;
    }
    public ImageView getImgProfile() { return imgProfile;}

    public Button getBtn_alert(){return btn_alert;}
    public Button getBtn_call(){return btn_call;}
    public Button getBtn_message() {return btn_message;}

}


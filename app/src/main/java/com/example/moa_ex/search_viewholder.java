package com.example.moa_ex;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class search_viewholder {

    private ImageView img;
    private TextView tv_sid;
    private TextView tv_sname;
    private TextView tv_sbirth;
    private TextView tv_sphone;
    private Button btn_calender;
    private Button btn_alert;
    private Button btn_add;

    public search_viewholder(View itemView, String page){
        img = itemView.findViewById(R.id.img);
        tv_sid = itemView.findViewById(R.id.tv_sid);
        tv_sname = itemView.findViewById(R.id.tv_sname);
        tv_sbirth = itemView.findViewById(R.id.tv_sbirth);
        tv_sphone = itemView.findViewById(R.id.tv_sphone);

        if(page.equals("add")){ //어르신 추가화면
            btn_add = itemView.findViewById(R.id.btn_add);
        }else{ //관리하는 어르신 목록화면
            btn_calender = itemView.findViewById(R.id.btn_calender);
            btn_alert = itemView.findViewById(R.id.btn_alert);
        }

    }

    public ImageView getImg() {
        return img;
    }

    public TextView getTv_sid(){return tv_sid;}

    public TextView getTv_sname(){return tv_sname;}

    public TextView getTv_sbirth(){return tv_sbirth;}

    public TextView getTv_sphone(){return tv_sphone;}

    public Button getBtn_calender(){return btn_calender;}

    public Button getBtn_alert(){return btn_alert;}

    public Button getBtn_add() {
        return btn_add;
    }

}

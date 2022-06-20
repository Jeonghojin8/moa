package com.example.moa_ex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class search_adapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<user_dataVO> list;
    LayoutInflater inflater;
    search_viewholder holder; //View객체 초기화
    String page;


    public search_adapter(String page, Context context, int layout, ArrayList<user_dataVO> list) {
        this.page = page;
        this.context = context;
        this.layout = layout;
        this.list = list;
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public search_adapter(userSearch userSearch, int search_layout2, ArrayList<user_data> list) {
//    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            holder = new search_viewholder(view,page);
            view.setTag(holder); // 해당 뷰 객체 상태정보 저장 용도

        }else{
            holder = (search_viewholder) view.getTag();
        }

        user_dataVO vo = list.get(i);

//      holder.getImg().setImageResource(vo.getImgId());
        holder.getTv_sid().setText(vo.getS_id());
        holder.getTv_sname().setText(vo.getS_name());
        holder.getTv_sbirth().setText(String.valueOf(vo.getS_birth()));
        holder.getTv_sphone().setText(String.valueOf(vo.getS_phone()));

        //화면구분
        if(page.equals("add")){

            holder.getBtn_add().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,popup.class);

                    String tv_Sid = vo.getS_id();
                    i.putExtra("tv_Sid",tv_Sid);

                    Log.d(tv_Sid, "추가 버튼 눌렀을 때 선택된 아이디");

                    context.startActivity(i);
                };
            });
        }else{
            // 캘린더 버튼 클릭시 캘린더 페이지로
            holder.getBtn_calender().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,CalendarActivity.class);
                    String tv_Sid = vo.getS_id();
                    i.putExtra("tv_Sid",tv_Sid);
                    Log.d(tv_Sid, "추가 버튼 눌렀을 때 선택된 아이디");

                    context.startActivity(i);
                }
            });

            // 연락처 버튼 클릭시 연락처 페이지로
            holder.getBtn_alert().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,contacts_ListActivity.class);

                    String tv_Sid = vo.getS_id();
                    i.putExtra("tv_Sid",tv_Sid);

                    Log.d(tv_Sid, "추가 버튼 눌렀을 때 선택된 아이디");

                    context.startActivity(i);
                }
            });

        }

        return view;
    }
}
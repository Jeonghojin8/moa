package com.example.moa_ex;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class contacts_deptAdapter extends BaseAdapter {

    Context context; // 현재 정보 저장
    int layout; // list_view에 띄워질 실제 layout -> list.item.xml
    ArrayList<contacts_dept_VO> list;
    LayoutInflater inflater; // xml 파일을 view 혀앹로 변환 시키는 객체
    ListVIewHolder holder;

    // 어댑터 생성자 메소드
    public contacts_deptAdapter(Context context, int layout, ArrayList<contacts_dept_VO> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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


    // 겟뷰에서는 listview 안에 들어가는 item 내용을 초기화, 화면에 view가 보이도록 리턴
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = inflater.inflate(layout, viewGroup,false);

            contacts_dept_VO vo = list.get(i);
            holder = new ListVIewHolder(view);
            view.setTag(holder); // 해당 뷰 객체 상태정보 저장 용도

            // 실제 데이터 내용 각각의 view에 담아주기
            holder.dept_name.setText((vo.getDept_tel()));
            holder.dept_tell.setText(vo.getDept_name());

            // 콜버튼 클릭시
            holder.getBtn_call().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    contacts_dept_VO vo = list.get(i);
                    String tell = vo.getDept_tel();
                    Uri uri = Uri.parse("tel:"+ tell);

                    Intent i = new Intent(Intent.ACTION_DIAL,uri);
                    context.startActivity(i);

                    Log.d(tell, "call 버튼 눌렀을 때 선택된 번호");

                }
            });


        }else{
            holder= (ListVIewHolder) view.getTag();

        }
        return view;

    }



}

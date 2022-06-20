package com.example.moa_ex;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

//여러 요일들에 대한 배경을 설정하는 클래스
//활용도: 저장한 날짜 표현할 때
public class EventDecorator implements DayViewDecorator {


    private Drawable drawable;
    private int color;
    private Context context;
    private HashSet<CalendarDay> dates;
    private ArrayList<EmoCalendarVO> data;




    public EventDecorator(int color, Collection<CalendarDay> dates, ArrayList<EmoCalendarVO> data, @NonNull Context context, int resId) {
        drawable = context.getResources().getDrawable(resId);

        this.context = context;
        this.data = data;
        this.color = color;
        this.dates = new HashSet<>(dates);
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return dates.contains(day);
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {


            view.setSelectionDrawable(drawable); //배경이미지
//        view.addSpan(new DotSpan(5, color)); // 날자밑에 점
//        view.setBackgroundDrawable(drawable);
    }
}

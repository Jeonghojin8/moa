package com.example.moa_ex;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class CalendarActivity extends AppCompatActivity {
    RequestQueue queue;
    StringRequest StringRequest_add_date1;
    StringRequest StringRequest_add_date2;
    StringRequest StringRequest_add_date3;

//    String s_id ="4";

    Button btn_previous2;
    Button btn_home2;


    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView materialCalendarView;

//    //날짜데이터셋
//    String[] result = {"2022-06-01","2022-06-02","2022-06-03","2022-06-04"};

    ArrayList<EmoCalendarVO> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarmain);
        queue = Volley.newRequestQueue(CalendarActivity.this);

        btn_previous2 = findViewById(R.id.btn_previous2);
        btn_home2 = findViewById(R.id.btn_home2);

        materialCalendarView = (MaterialCalendarView)findViewById(R.id.materialCalendar);


        btn_previous2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalendarActivity.this,admin_mypage.class);
                startActivity(i);
                finish();
            }
        });

        btn_home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalendarActivity.this,userSearch.class);
                startActivity(i);
                finish();
            }
        });


        calendarInit();


        //AsyncTask 실행 -> 캘린더에 날짜 배경색 표현
        calendar_request();


        //날짜 클릭시 이벤트
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                String shot_Day = Year + "년" + Month + "월" + Day + "일";

                Log.i("shot_Day test", "선택된 날짜:"+shot_Day + "");

                //선택한 날짜의 배경색 제거
//                materialCalendarView.clearSelection();
                Toast.makeText(CalendarActivity.this, shot_Day , Toast.LENGTH_SHORT).show();
            }
        });

    }

    //달력 실행 시 초기화
    public void calendarInit() {
        //달력 시작/끝 셋팅
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2025, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        //월, 요일을 한글로 보이게 설정
        materialCalendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getStringArray(R.array.custom_months)));
        materialCalendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getStringArray(R.array.custom_weekdays)));

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        materialCalendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        // 월/연 -> 연/월이 보이는 방식 커스텀
        materialCalendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                Date inputText = day.getDate();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String getTime = sdf.format(inputText);

                String[] calendarHeaderElements = getTime.split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append("년")
                        .append(" ")
                        .append(calendarHeaderElements[1])
                        .append("월");
                return calendarHeaderBuilder.toString();
            }
        });


        //달력에 휴일 글자색 셋팅 관련 메소드
        //토요일(파란색), 일요일(빨간색), 당일(파란색
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new DayDecorator(CalendarActivity.this),
                oneDayDecorator);
    }

    //캘린더에 정해진 날짜들을 표현하는 기능
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

//        String[] Time_Result;
        ArrayList<EmoCalendarVO> data;

        ApiSimulator(ArrayList<EmoCalendarVO> data){
            this.data = data;
        }

        @NonNull
        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < data.size() ; i ++){

                String[] time = data.get(i).getDate().split("-");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                calendar.set(year,month-1,dayy);
                CalendarDay day = CalendarDay.from(calendar);

                dates.add(day);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) { return; }

            for (int i=0; i<data.size(); i++){

                materialCalendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(calendarDays.get(i)),
                        data,CalendarActivity.this, data.get(i).getEmoResId()));



            }
        }
    }


    //Date객체형태의 날짜정보 반환 기능
    public Date getDate(int year, int month, int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, date);
        return new Date(calendar.getTimeInMillis());
    }

    public void add_date(String date, String emotion, int img){
        data.add(new EmoCalendarVO(date,emotion,img));

    }



    // 대화데이터에서 중복없는 날짜를 출력하는 코드
    public void calendar_request(){
        int method = Request.Method.POST;
        String serval_url = "http://172.30.1.59:3000/home/calendar_request_date";

        StringRequest_add_date1 = new StringRequest(
                method,
                serval_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("가족정보", response);

                        try {
                            JSONArray array = new JSONArray(response);

                            for(int i=0; i<array.length(); i++){
                                JSONObject dept = array.getJSONObject(i);
                                String date = dept.getString("talk_date");
                                Log.d("tag","날짜 : "+date);
                                calendar_request_date(date);
                            }

                            new ApiSimulator(data).executeOnExecutor(Executors.newSingleThreadExecutor());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("err","응답실패 >>>"+error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                Intent i = getIntent();
                String s_id = i.getStringExtra("tv_Sid");
                Log.e(s_id, "getParams: " );

                param.put("s_id",s_id);
                return param;
            }
        };
        queue.add(StringRequest_add_date1);
    }

    // 감정캘린더에 감정이미지 리스트에 추가하고 이미지를 표시하는 코드
    // 매개변수 : date
    // 받는 값 : 대표 감정
    public void calendar_request_date(String date){
        int method2 = Request.Method.POST;
        String serval_url2 = "http://172.30.1.6:3000/home/calendar_request";

        StringRequest_add_date2 = new StringRequest(
                method2,
                serval_url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tag","각 날짜별 최대 감정 : "+date+" : "+response);
                            String emotion = response;
                            int img;
                            if (response.equals("기쁨")) {
                                img = R.drawable.smile;
                            } else if (response.equals("불안")) {
                                img = R.drawable.nervous;
                            } else if (response.equals("분노")) {
                                img = R.drawable.angry;
                            } else{
                                img = R.drawable.sad;
                            }
                            data.add(new EmoCalendarVO(date,response,img));

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
                String s_id = i.getStringExtra("tv_Sid");
                Log.e(s_id, "getParams: " );

                param.put("talk_date",date);
                param.put("s_id",s_id);
                return param;

//                param.put("s_id",s_id);
//                return param;
            }
        };
        queue.add(StringRequest_add_date2);

    };

    public void insert_calendar(String s_id,String talk_date, String emotion){
        int method3 = Request.Method.POST;
        String serval_url3 = "http://172.30.1.6:3000/home/insert_calendar";

        StringRequest_add_date3 = new StringRequest(
                method3,
                serval_url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                param.put("s_id",s_id);
                param.put("talk_date",talk_date);
                param.put("emotion",emotion);
                return param;
            }

        };
        queue.add(StringRequest_add_date3);
    }


}
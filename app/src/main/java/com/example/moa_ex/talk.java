package com.example.moa_ex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.annotation.RequiresApi;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.RequestBody;

public class talk extends AppCompatActivity implements TextToSpeech.OnInitListener {

    RequestQueue queue;
    StringRequest StringRequest_talk_data;
    TextView textView5;
    String str1;
    String str2;
    String str3;
    String str4;
    String t1;
    ImageButton btn_talkstart1, btn_talkstart2;
    Intent intent;
    SpeechRecognizer mRecognizer;
    final int PERMISSION = 1;
    ImageView emotions;
    String s_id ="3";
    String date = getTime();
    /////////////////
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME2 = "mypref2";
    private static final String KEY_NAME2 = "name2";
////////////////


    //Timer
    static int count = 0;
    TimerTask timerTask;
    Timer timer = new Timer();

    //TTS
    private TextToSpeech tts;

    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        //////////////////////////
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        String S_ID = sharedPreferences.getString(KEY_NAME2, null);
        TextView tv_s_id = findViewById(R.id.tv_s_id);
        if (S_ID != null) {
            tv_s_id.setText(S_ID);
            Log.d(S_ID, "onCreate: ");
        }
        //////////////////////////////

        emotions = findViewById(R.id.emotions);
//        if (android.os.Build.VERSION.SDK_INT > 9)
//        {
//            StrictMode.ThreadPolicy policy = new
//                    StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        //thread//
//        TestThread thread = new TestThread();
//        thread.start();
        //

        //tts
        tts = new TextToSpeech(this, this);
        //

        //tts//
        queue = Volley.newRequestQueue(talk.this);
        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);

        }

        textView5 = findViewById(R.id.textView5);
        btn_talkstart1 = findViewById(R.id.btn_talkstart1);
        btn_talkstart2 = findViewById(R.id.btn_talkstart2);


        // RecognizerIntent 생성
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);





        // 버튼 클릭 시 객체에 Context와 listener를 할당
        btn_talkstart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRecognizer = SpeechRecognizer.createSpeechRecognizer(talk.this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(intent); // 듣기 시작    //딜레이 후 시작할 코드 작성


//                new Handler().postDelayed(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//
////
////                        sendTalkMsg(str1);  //딜레이 후 시작할 코드 작성
//                    }
//                }, 2000);// ㅁ초 정도 딜레이를 준 후 시작

            }

        });

        btn_talkstart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(talk.this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(intent); // 듣기 시작
//                mRecognizer.stopListening();
//                mRecognizer.destroy();
//                mRecognizer.stopListening();
//                mRecognizer.destroy();
//                sendTalkMsg(str1);

//                TestThread thread = new TestThread(t1);
//                thread.start();


            }
        });

    }
    //
    class TestThread extends Thread{
        String t1;


        public TestThread(String t1){
            this.t1=t1;
        }
        public void run() {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("message", t1)
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://172.30.1.59:5000/review_model")
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                str2 = jsonObject.getString("msg");
                str3 = jsonObject.getString("msg_emo");

//                Log.v("response",response.body().string());
                Log.v("response",jsonObject.getString("msg"));
                Log.v("response",jsonObject.getString("msg_emo"));
                Log.v("thistime",getTime());


                Thread.sleep(500);

//                speakOut((String)jsonObject.getString("msg"));
//                new Handler().postDelayed(new Runnable()
//                            {
//                                @Override
//                                public void run()
//                                {
                if (str3.equals("기쁨") )
                {
                    emotions.setImageResource(R.drawable.smilingface);
                }
                else if(str3.equals("슬픔"))
                {
                    emotions.setImageResource(R.drawable.sadface);

                }
                else if (str3.equals("분노") )
                {
                    emotions.setImageResource(R.drawable.angryface);

                }else if (str3.equals("불안") )
                {
                    emotions.setImageResource(R.drawable.nervousface);

                }
//
//
//
////                                    speakOut((String)jsonObject.getString("msg"));   //딜레이 후 시작할 코드 작성
//                                }
//                            }, 250);// ㅁ초 정도 딜레이를 준 후 시작
                speakOut((String)jsonObject.getString("msg"));
                talkdata( s_id, str1,  str2, str3, date);
                // Do something with the response.
            } catch (IOException | JSONException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    //



    //tts//
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speakOut(String str2){

        tts.playSilentUtterance(1000, TextToSpeech.QUEUE_ADD, null);

        str1 = textView5.getText().toString();

        tts.setPitch((float)1.0); // 음성 톤 높이 지정
        tts.setSpeechRate((float)0.8); // 음성 속도 지정

        // 첫 번째 매개변수: 음성 출력을 할 텍스트
        // 두 번째 매개변수: 1. TextToSpeech.QUEUE_FLUSH - 진행중인 음성 출력을 끊고 이번 TTS의 음성 출력
        //                 2. TextToSpeech.QUEUE_ADD - 진행중인 음성 출력이 끝난 후에 이번 TTS의 음성 출력
        tts.speak(str2, TextToSpeech.QUEUE_FLUSH, null, "id1");
    }

    @Override
    public void onDestroy() {
        if(tts!=null){ // 사용한 TTS객체 제거
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) { // OnInitListener를 통해서 TTS 초기화
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.KOREA); // TTS언어 한국어로 설정

            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "This Language is not supported");
            }else{
//                speak_out.setEnabled(true);
                speakOut(str2);// onInit에 음성출력할 텍스트를 넣어줌
            }
        }else{
            Log.e("TTS", "Initialization Failed!");
        }
    }
//    tts//

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // 말하기 시작할 준비가되면 호출
            Toast.makeText(getApplicationContext(),"음성인식 시작",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // 입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            // 말하기를 중지하면 호출
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
//                        sendTalkMsg(str1);  //딜레이 후 시작할 코드 작성
                    TestThread thread = new TestThread(t1);
                    thread.start();
                }
            }, 500);// ㅁ초 정도 딜레이를 준 후 시작

        }

        @Override
        public void onError(int error) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러 발생 : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.v("말한 값", matches.get(0));
            for(int i = 0; i < matches.size() ; i++){
                textView5.setText(matches.get(i));
//                Log.v("matches", matches.get(0));
            }
            t1 = textView5.getText().toString();
            Log.v("t1",t1);

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    };
//    public void sendTalkMsg(String sendMsg) {
//
//
//        String flask_url = "http://172.30.1.44:5000/review_model";
//
//        StringRequest request = new StringRequest(Request.Method.POST, flask_url,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//                        //Flask서버의 return문에 작성한 결과값을 response변수를 통해서 접근
////                                Log.v("Flask응답값>> ", response);
//
//                        try {
//                            JSONObject object = new JSONObject(response);
//
//                            String str2 = object.getString("msg");
//                            String str3 = object.getString("msg_emo");
//                            Log.v("str2",str2);
//                            Log.v("Flask응답값>> ", object.getString("msg"));
//                            Log.v("Flask감정값>> ", object.getString("msg_emo"));
//                            new Handler().postDelayed(new Runnable()
//                            {
//                                @Override
//                                public void run()
//                                {
//                                   if (str3.equals("기쁨") )
//                               {
//                                emotions.setImageResource(R.drawable.smiling);
//                                    }
//                                   else if(str3.equals("슬픔"))
//                                   {
//                                       emotions.setImageResource(R.drawable.sad);
//
//                                   }
//                                   else if (str3.equals("분노") )
//                                   {
//                                       emotions.setImageResource(R.drawable.angry);
//
//                                   }else if (str3.equals("불안") )
//                                   {
//                                       emotions.setImageResource(R.drawable.nervous);
//
//                                }
//
//
//
//                                    speakOut(str2);   //딜레이 후 시작할 코드 작성
//                                }
//                            }, 250);// ㅁ초 정도 딜레이를 준 후 시작
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String,String> params = new HashMap<>();
//                String str1 = textView5.getText().toString();
//                Log.i("t1",str1);
//                //flask서버로 전달할 데이터를
//                params.put("message",str1);
//                Log.v("sendmsg",str1);
////               params.put("num1","오늘하루는 어떠셨나요?");
////                        params.put("num2","200");
//
//                return params;
//
//            }
//        };
//
//        queue.add(request);
//    }

    public String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }








    public void onClickStart(View v) {
        startTimerTask();

    }


    private void startTimerTask()
    {
        stopTimerTask();

        count = 0;

        //익명(이름없는) 객체(클래스) : 한번 쓰고 버리는 객체(이름 필요 없음)

        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                count++;

            }


        };
        //            타이머태스크  지연시간   간격시간
        timer.schedule(timerTask,3000 ,1000);
    }

    private void stopTimerTask()
    {
        if(timerTask != null)
        {

            timerTask.cancel();
            timerTask = null;
        }
    }

    public void talkdata(String s_id,String str1, String str2, String str3, String date){
        int method = com.android.volley.Request.Method.POST;
        String server_url = "http://172.30.1.59:3000/home/TalkData";

        StringRequest_talk_data = new StringRequest(
                method,
                server_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new com.android.volley.Response.ErrorListener() {
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
                param.put("request",str1);
                param.put("response",str2);
                param.put("emotion",str3);
                param.put("date",date);
                return param;
            }
        };
        queue.add(StringRequest_talk_data);


    }


}
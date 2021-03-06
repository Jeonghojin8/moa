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
        // ??????????????? 6.0?????? ???????????? ???????????? ????????? ??????
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);

        }

        textView5 = findViewById(R.id.textView5);
        btn_talkstart1 = findViewById(R.id.btn_talkstart1);
        btn_talkstart2 = findViewById(R.id.btn_talkstart2);


        // RecognizerIntent ??????
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // ????????? ???
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // ?????? ??????
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);





        // ?????? ?????? ??? ????????? Context??? listener??? ??????
        btn_talkstart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRecognizer = SpeechRecognizer.createSpeechRecognizer(talk.this); // ??? SpeechRecognizer ??? ????????? ????????? ?????????
                mRecognizer.setRecognitionListener(listener); // ????????? ??????
                mRecognizer.startListening(intent); // ?????? ??????    //????????? ??? ????????? ?????? ??????


//                new Handler().postDelayed(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//
////
////                        sendTalkMsg(str1);  //????????? ??? ????????? ?????? ??????
//                    }
//                }, 2000);// ?????? ?????? ???????????? ??? ??? ??????

            }

        });

        btn_talkstart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(talk.this); // ??? SpeechRecognizer ??? ????????? ????????? ?????????
                mRecognizer.setRecognitionListener(listener); // ????????? ??????
                mRecognizer.startListening(intent); // ?????? ??????
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
                if (str3.equals("??????") )
                {
                    emotions.setImageResource(R.drawable.smilingface);
                }
                else if(str3.equals("??????"))
                {
                    emotions.setImageResource(R.drawable.sadface);

                }
                else if (str3.equals("??????") )
                {
                    emotions.setImageResource(R.drawable.angryface);

                }else if (str3.equals("??????") )
                {
                    emotions.setImageResource(R.drawable.nervousface);

                }
//
//
//
////                                    speakOut((String)jsonObject.getString("msg"));   //????????? ??? ????????? ?????? ??????
//                                }
//                            }, 250);// ?????? ?????? ???????????? ??? ??? ??????
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

        tts.setPitch((float)1.0); // ?????? ??? ?????? ??????
        tts.setSpeechRate((float)0.8); // ?????? ?????? ??????

        // ??? ?????? ????????????: ?????? ????????? ??? ?????????
        // ??? ?????? ????????????: 1. TextToSpeech.QUEUE_FLUSH - ???????????? ?????? ????????? ?????? ?????? TTS??? ?????? ??????
        //                 2. TextToSpeech.QUEUE_ADD - ???????????? ?????? ????????? ?????? ?????? ?????? TTS??? ?????? ??????
        tts.speak(str2, TextToSpeech.QUEUE_FLUSH, null, "id1");
    }

    @Override
    public void onDestroy() {
        if(tts!=null){ // ????????? TTS?????? ??????
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) { // OnInitListener??? ????????? TTS ?????????
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.KOREA); // TTS?????? ???????????? ??????

            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "This Language is not supported");
            }else{
//                speak_out.setEnabled(true);
                speakOut(str2);// onInit??? ??????????????? ???????????? ?????????
            }
        }else{
            Log.e("TTS", "Initialization Failed!");
        }
    }
//    tts//

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // ????????? ????????? ??????????????? ??????
            Toast.makeText(getApplicationContext(),"???????????? ??????",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            // ????????? ???????????? ??? ??????
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // ???????????? ????????? ????????? ?????????
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // ?????? ???????????? ????????? ??? ????????? buffer??? ??????
        }

        @Override
        public void onEndOfSpeech() {
            // ???????????? ???????????? ??????
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
//                        sendTalkMsg(str1);  //????????? ??? ????????? ?????? ??????
                    TestThread thread = new TestThread(t1);
                    thread.start();
                }
            }, 500);// ?????? ?????? ???????????? ??? ??? ??????

        }

        @Override
        public void onError(int error) {
            // ???????????? ?????? ?????? ????????? ???????????? ??? ??????
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "??????????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "???????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "?????? ??? ??????";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER ??? ??????";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "????????? ?????????";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                default:
                    message = "??? ??? ?????? ?????????";
                    break;
            }

            Toast.makeText(getApplicationContext(), "?????? ?????? : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // ?????? ????????? ???????????? ??????
            // ?????? ?????? ArrayList??? ????????? ?????? textView??? ????????? ?????????
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.v("?????? ???", matches.get(0));
            for(int i = 0; i < matches.size() ; i++){
                textView5.setText(matches.get(i));
//                Log.v("matches", matches.get(0));
            }
            t1 = textView5.getText().toString();
            Log.v("t1",t1);

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // ?????? ?????? ????????? ????????? ??? ?????? ??? ??????
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // ?????? ???????????? ???????????? ?????? ??????
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
//                        //Flask????????? return?????? ????????? ???????????? response????????? ????????? ??????
////                                Log.v("Flask?????????>> ", response);
//
//                        try {
//                            JSONObject object = new JSONObject(response);
//
//                            String str2 = object.getString("msg");
//                            String str3 = object.getString("msg_emo");
//                            Log.v("str2",str2);
//                            Log.v("Flask?????????>> ", object.getString("msg"));
//                            Log.v("Flask?????????>> ", object.getString("msg_emo"));
//                            new Handler().postDelayed(new Runnable()
//                            {
//                                @Override
//                                public void run()
//                                {
//                                   if (str3.equals("??????") )
//                               {
//                                emotions.setImageResource(R.drawable.smiling);
//                                    }
//                                   else if(str3.equals("??????"))
//                                   {
//                                       emotions.setImageResource(R.drawable.sad);
//
//                                   }
//                                   else if (str3.equals("??????") )
//                                   {
//                                       emotions.setImageResource(R.drawable.angry);
//
//                                   }else if (str3.equals("??????") )
//                                   {
//                                       emotions.setImageResource(R.drawable.nervous);
//
//                                }
//
//
//
//                                    speakOut(str2);   //????????? ??? ????????? ?????? ??????
//                                }
//                            }, 250);// ?????? ?????? ???????????? ??? ??? ??????
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
//                //flask????????? ????????? ????????????
//                params.put("message",str1);
//                Log.v("sendmsg",str1);
////               params.put("num1","??????????????? ????????????????");
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

        //??????(????????????) ??????(?????????) : ?????? ?????? ????????? ??????(?????? ?????? ??????)

        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                count++;

            }


        };
        //            ??????????????????  ????????????   ????????????
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
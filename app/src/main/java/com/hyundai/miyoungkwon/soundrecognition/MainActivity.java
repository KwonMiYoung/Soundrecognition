package com.hyundai.miyoungkwon.soundrecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    SpeechRecognizer mRecognizer;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        Toast.makeText(this,"음성인식시작",Toast.LENGTH_SHORT).show();
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(RListener);
        mRecognizer.startListening(i);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRecognizer.destroy();
    }
    private RecognitionListener RListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            if(error==7){
                //not match
            }else if(error==6){
                //time out
            }else if(error==3){
                Toast.makeText(MainActivity.this,"에러발생 : 다른 녹음기능이 켜져있는지 확인하세요.",Toast.LENGTH_SHORT).show();
            }else if(error==1||error==2){
                Toast.makeText(MainActivity.this,"에러발생 : 인터넷이 연결되어있는지 확인하세요.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"에러발생 에러코드 : "+error,Toast.LENGTH_SHORT).show();
            }
            if(mRecognizer!=null){
                mRecognizer.destroy();
                mRecognizer.setRecognitionListener(RListener);
                mRecognizer.startListening(i);
            }else{
                mRecognizer.startListening(i);
            }

        }

        @Override
        public void onResults(Bundle results) {
            TextView resultView = (TextView)findViewById(R.id.textView);
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            resultView.setText("" + rs[0]);
            if(mRecognizer!=null){
                mRecognizer.destroy();
                mRecognizer.setRecognitionListener(RListener);
                mRecognizer.startListening(i);
            }else{
                mRecognizer.startListening(i);
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            TextView resultView = (TextView)findViewById(R.id.textView);
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = partialResults.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            if(rs[0].equals("이름")){
                resultView.setText("권미영입니다");
            }
            else if(rs[0].equals("나이")){
                resultView.setText("22살 입니다");
            }
            else if(rs[0].equals("날씨")){
                resultView.setText("비가 옵니다");
            }


            //resultView.setText("" +rs[0]);


        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };
}

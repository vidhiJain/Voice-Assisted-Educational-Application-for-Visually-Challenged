package org.arise.textToSpeech;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by phandaa on 10/12/15.
 */
public class TTSInitListener implements TextToSpeech.OnInitListener {


    private TextToSpeech tts;
    private boolean ready = false;
    private boolean allowed = false;
    private String text;
    private static TTSInitListener ins = null;

    public static TTSInitListener getInstance(Context cont){
        if(ins == null) {
            ins = new TTSInitListener(cont);
        }
        return  ins;
    }

    public static TTSInitListener getInstance(){
        return  ins;
    }

    private TTSInitListener(Context context){
        tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            tts.setLanguage(Locale.US);
            ready = true;
        }else{
            ready = false;
        }
    }

    public void setText(String text){
        this.text = text;
    }

    public void speakOut(){
        if(ready){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void snooze(){
        if(tts!=null){
            tts.stop();
        }
    }

}

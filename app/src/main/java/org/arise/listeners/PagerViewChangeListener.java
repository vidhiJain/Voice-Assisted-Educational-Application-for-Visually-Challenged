package org.arise.listeners;

import android.support.v4.view.ViewPager;

import org.arise.tabs.views.AllCoursesDetail;
import org.arise.tabs.views.CompletedCoursesDetail;
import org.arise.tabs.views.CurrentCoursesDetail;
import org.arise.textToSpeech.TTSInitListener;

/**
 * Created by phandaa on 10/18/15.
 */
public class PagerViewChangeListener implements ViewPager.OnPageChangeListener{

    private TTSInitListener tts;
    private String text;
    public PagerViewChangeListener(){
        tts = TTSInitListener.getInstance();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        text = "You are viewing section ";
        switch (position)
        {
            case 0:
                text = text+"All courses and on course     "+ AllCoursesDetail.adapter.getLastItemName();
                speak();
                break;
            case 1:
                text = text+"in-progress courses and on course     "+ CurrentCoursesDetail.adapter.getLastItemName();
                speak();
                break;
            case 2:
                text = text+"Completed courses and on course     "+ CompletedCoursesDetail.adapter.getLastItemName();
                speak();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void speak(){
        tts.setText(text);
        tts.speakOut();
    }
}

package org.arise.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.arise.enums.CourseStatus;
import org.arise.textToSpeech.TTSInitListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arise.arise.org.arise.CourseDetailsActivity;

/**
 * Created by Arpit Phanda on 3/11/2015.
 */
public class CourseListListener implements ListView.OnItemClickListener, ListView.OnItemLongClickListener{
    private Context context;
    private JSONArray lectureArray;
    private CourseStatus status;
    private TTSInitListener tts;

    public CourseListListener(Context activity, JSONArray courses, CourseStatus status) {
        this.context = activity;
        this.lectureArray = courses;
        this.status = status;
        tts = TTSInitListener.getInstance();
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        System.out.println("Single click captured");
        JSONObject specificCourse = null;
        boolean current = false;
        boolean completed = false;

        try {
             specificCourse = lectureArray.getJSONObject(position);
            if(status == CourseStatus.ALL)
            {
                current = specificCourse.getBoolean("current");
                completed = specificCourse.getBoolean("completed");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent courseDetailsDisplay = new Intent(context, CourseDetailsActivity.class);
        Bundle bundleForDisplay = new Bundle();
        bundleForDisplay.putString("Course",specificCourse.toString());
        if(status == CourseStatus.CURRENT)
        {
            bundleForDisplay.putBoolean("completed",false);
            bundleForDisplay.putBoolean("current",true);
        }
        else if(status == CourseStatus.COMPLETED)
        {
            bundleForDisplay.putBoolean("completed",true);
            bundleForDisplay.putBoolean("current",false);
        }
        else if(status == CourseStatus.ALL)
        {
            bundleForDisplay.putBoolean("completed",completed);
            bundleForDisplay.putBoolean("current",current);
        }

        courseDetailsDisplay.putExtras(bundleForDisplay);

        context.startActivity(courseDetailsDisplay);

    }

    @Override
    public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
        tts.snooze();
        String desc = "";
        try{
            desc = ((JSONObject)lectureArray.getJSONObject(position)).getString("course_description");
        }catch (JSONException e) {
            e.printStackTrace();
        }
        tts.setText(desc);
        tts.speakOut();

        return true;
    }
}

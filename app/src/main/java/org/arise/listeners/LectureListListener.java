package org.arise.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arise.arise.org.arise.PlayLectureActivity;

/**
 * Created by arpit on 15/3/15.
 */
public class LectureListListener implements ListView.OnItemClickListener{

    private Context context;
    private JSONArray lectureArray;
    private boolean completed;
    private boolean current;
    private int courseID;

    public LectureListListener(Context context, JSONArray lectures, Boolean completed, Boolean current,Integer courseID)
    {
        this.lectureArray = lectures;
        this.context = context;
        this.current = current;
        this.completed = completed;
        this.courseID = courseID;
    }


    @Override
    public void onItemClick(AdapterView adapterView, View view, int i, long l) {
        JSONObject lecture = null;
        String lectureName = "";
        String lectureUrl = "";
        int lectureID = 0;
        boolean lectureCompleted = false;

        try {
            lecture = lectureArray.getJSONObject(i);
            lectureName = lecture.getString("name");
            lectureUrl = lecture.getString("url");
            lectureID = lecture.getInt("id");
            if(current)
            {
                lectureCompleted = lecture.getBoolean("completed");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent playYoutubeVideo = new Intent(context, PlayLectureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",lectureName);
        bundle.putString("url",lectureUrl);
        bundle.putInt("courseID",courseID);
        bundle.putInt("lectureID",lectureID);

        if(current)
        {
            bundle.putBoolean("lecture_completed",lectureCompleted);
        }
        bundle.putBoolean("course_completed",completed);
        bundle.putBoolean("course_current",current);

        playYoutubeVideo.putExtras(bundle);

        context.startActivity(playYoutubeVideo);
    }
}

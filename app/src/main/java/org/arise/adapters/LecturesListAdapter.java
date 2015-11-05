package org.arise.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.arise.textToSpeech.TTSInitListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arise.arise.org.arise.CourseDetailsActivity;
import arise.arise.org.arise.R;

/**
 * Created by arpit on 15/3/15.
 */
public class LecturesListAdapter extends BaseAdapter{

    private JSONArray lectures;
    private Context context;
    private boolean completed;
    private boolean current;
    private int height;
    private int width;
    private TTSInitListener tts;
    private String text;

    public LecturesListAdapter(JSONArray lectureArray, Context context, Boolean completed, Boolean current, int height, int width) {
        this.lectures = lectureArray;
        this.context = context;
        this.completed = completed;
        this.current = current;
        this.height = height;
        this.width = width;

        tts = TTSInitListener.getInstance();
    }

    @Override
    public int getCount() {
        return lectures.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        System.out.println("Will return listItem");
        tts.snooze();
        View row;
        JSONObject lecture = null;
        String lectureName = "";
        boolean lectureComplete = false;

        try {
            lecture = lectures.getJSONObject(i);
            lectureName = lecture.getString("name");

            if(current)
            {
                lectureComplete = lecture.getBoolean("completed");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(view == null) {
            if(completed || current) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.lectures_list_item_indicator, null);
            }
            else
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.lectures_list_item, null);
            }
        }
        else
        {
            row = view;
        }

        TextView title = (TextView) row.findViewById(R.id.lecture_title);
        title.setText(lectureName);

        if(current)
        {
            TextView toFill = (TextView) row.findViewById(R.id.lecture_status);

            if(lectureComplete)
            {
                toFill.setText("COMPLETE");
                toFill.setBackgroundColor(Color.parseColor("#b2ff59"));
            }
            else if(!lectureComplete)
            {
                toFill.setText("IN PROGRESS");
                toFill.setBackgroundColor(Color.parseColor("#ff5252"));
            }
        }
        else if(completed)
        {
            TextView toFill = (TextView) row.findViewById(R.id.lecture_status);
            toFill.setText("COMPLETE");
            toFill.setBackgroundColor(Color.parseColor("#b2ff59"));
        }
        row.setLayoutParams(new ViewGroup.LayoutParams(this.width,this.height));

        text = "You are on Lecture   "+lectureName;
        tts.setText(text);
        tts.speakOut();

        return row;
    }
}

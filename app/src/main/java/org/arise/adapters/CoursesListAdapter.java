package org.arise.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.arise.enums.CourseStatus;
import org.arise.textToSpeech.TTSInitListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import arise.arise.org.arise.R;

/**
 * Created by Arpit Phanda on 3/11/2015.
 */
public class CoursesListAdapter extends BaseAdapter{

    private Context context;
    private JSONArray arrayJSON;
    private String item;
    private String itemDesc;
    private CourseStatus option;
    private int height,width;
    private int lastPosition;
    private String lastItemName;
    private TTSInitListener tts;

    public CoursesListAdapter(Context context, JSONArray courses, CourseStatus status, int height, int width) {
        arrayJSON = courses;
        this.context = context;
        option = status;
        this.height = height;
        this.width = width;
        tts = TTSInitListener.getInstance();
    }

    @Override
    public int getCount() {
        return arrayJSON.length();
    }

    public String getLastItemName(){
        return lastItemName;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tts.snooze();
        View row = null;
        JSONObject course;
        item = "";
        itemDesc = "";
        boolean completedBool = false;
        boolean currentBool = false;

        try {
            course = (JSONObject) arrayJSON.get(position);
            item = course.getString("course_name");
            itemDesc = course.getString("course_description");

            if(option == CourseStatus.ALL)
            {
                completedBool = course.getBoolean("completed");
                currentBool = course.getBoolean("current");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.course_list_item_all_courses, null);
            row.setLayoutParams(new ViewGroup.LayoutParams(this.width,this.height));
        }
        else
        {
            row = convertView;
        }

        TextView courseHeading = (TextView) row.findViewById(R.id.course_title);
        courseHeading.setText(item);
        TextView courseDescription = (TextView) row.findViewById(R.id.course_description);
        courseDescription.setText(itemDesc);

        TextView toFill = (TextView) row.findViewById(R.id.course_status);
        if(option == CourseStatus.ALL)
        {
            if(!currentBool && !completedBool)
            {
                toFill.setText("NEW");
                toFill.setBackgroundColor(Color.parseColor("#ff5252"));
            }
            else if(currentBool && !completedBool)
            {
                toFill.setText("IN PROGRESS");
                toFill.setBackgroundColor(Color.parseColor("#ffb300"));
            }
            else if(!currentBool && completedBool)
            {
                toFill.setText("COMPLETED");
                toFill.setBackgroundColor(Color.parseColor("#b2ff59"));
            }
        }
        else if(option==CourseStatus.COMPLETED)
        {
            toFill.setText("COMPLETED");
            toFill.setBackgroundColor(Color.parseColor("#b2ff59"));
        }
        else if(option == CourseStatus.CURRENT)
        {
            toFill.setText("IN PROGRESS");
            toFill.setBackgroundColor(Color.parseColor("#ffb300"));
        }
        lastPosition = position;
        lastItemName = item;

        tts.setText("You are on " + item);
        tts.speakOut();

        return row;
    }

}

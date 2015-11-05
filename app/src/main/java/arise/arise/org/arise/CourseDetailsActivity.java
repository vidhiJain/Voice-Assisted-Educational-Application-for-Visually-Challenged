package arise.arise.org.arise;

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.arise.CustomView.SingleScrollListView;
import org.arise.adapters.LecturesListAdapter;
import org.arise.fragments.NavigationDrawer;
import org.arise.listeners.LectureListListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CourseDetailsActivity extends BaseActivity {


    private JSONObject courseFromJSON;
    SingleScrollListView listLecture;
    String courseName = "";
    String courseDesc = "";
    Boolean completed;
    Boolean current;
    JSONArray lectureArray = null;
    int courseID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        View main_view = findViewById(R.id.course_details_id);
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        Bundle bundle = getIntent().getExtras();

        String course = bundle.getString("Course");
        completed = bundle.getBoolean("completed");
        current = bundle.getBoolean("current");

        try {
            courseFromJSON = new JSONObject(course);

            courseName = courseFromJSON.getString("course_name");
            courseID = courseFromJSON.getInt("courseID");
            courseDesc = courseFromJSON.getString("course_description");
            lectureArray = courseFromJSON.getJSONArray("lectures");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(courseName);

//        TextView courseHeading = (TextView) findViewById(R.id.course_title);
//        courseHeading.setText(courseName);

        TextView courseDescription = (TextView) findViewById(R.id.course_description_complete);
        courseDescription.setText(courseDesc);

        listLecture = (SingleScrollListView) findViewById(R.id.lecture_list);

        NavigationDrawer drawer = (NavigationDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawer.setUpDrawer(drawerLayout,toolbar, R.id.fragment_navigation_drawer, main_view);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        listLecture.setAdapter(new LecturesListAdapter(lectureArray, this, completed, current, listLecture.getHeight(),listLecture.getWidth()));
        listLecture.setOnItemClickListener(new LectureListListener(this, lectureArray, completed, current, courseID));
        listLecture.setSingleScroll(true);
    }
}

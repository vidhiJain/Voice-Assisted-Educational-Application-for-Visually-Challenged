package arise.arise.org.arise;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.arise.adapters.TabsPagerAdapter;
import org.arise.fragments.NavigationDrawer;
import org.arise.listeners.PagerViewChangeListener;
import org.arise.tab.SlidingTabLayout;
import org.arise.textToSpeech.TTSInitListener;


public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home_main);
            setEssentials();
    }

    private void setEssentials() {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        View main_view = findViewById(R.id.to_replace_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        viewPager.addOnPageChangeListener(new PagerViewChangeListener());
        viewPager.setOffscreenPageLimit(2);

        tabs.setCustomTabView(R.layout.custom_tab, R.id.tab_text);
        tabs.setDistributeEvenly(true);
        tabs.setBackgroundColor(Color.parseColor("#212121"));
        tabs.setSelectedIndicatorColors(Color.parseColor("#B71C1C"));
        tabs.setViewPager(viewPager);


        NavigationDrawer drawer = (NavigationDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawer.setUpDrawer(drawerLayout,toolbar, R.id.fragment_navigation_drawer, main_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

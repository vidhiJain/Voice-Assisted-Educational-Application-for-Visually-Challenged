package arise.arise.org.arise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.arise.adapters.TabsPagerAdapter;
import org.arise.fragments.NavigationDrawer;
import org.arise.tab.SlidingTabLayout;

/**
 * Created by Arpit Phanda on 3/10/2015.
 */
public class BaseActivity extends ActionBarActivity {

    private  Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullLayout = (DrawerLayout)getLayoutInflater().inflate(R.layout.activity_home,null);
        LinearLayout mainContent = (LinearLayout)fullLayout.findViewById(R.id.main_home);
//        DrawerLayout drawerLayout = (DrawerLayout) fullLayout.findViewById(R.id.drawer_layout);

//        NavigationDrawer drawer = (NavigationDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
//        drawer.setUpDrawer(drawerLayout,toolbar, R.id.fragment_navigation_drawer);
        getLayoutInflater().inflate(layoutResID, mainContent,true);
        super.setContentView(fullLayout);
    }
}

package org.arise.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.arise.adapters.NavigationListAdapter;
import org.arise.enums.SharedPreferenceEnum;
import org.arise.listeners.NavigationDrawerListener;

import arise.arise.org.arise.LoginActivity;
import arise.arise.org.arise.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment {

    private ActionBarDrawerToggle actionToggle;
    private DrawerLayout drawerLayout;
    private ListView listview;
    private String userKnowsDrawerExistKey = "user_knows";

    private boolean userAwareOfDrawer;
    private boolean fromSavedInstance;

    private View view;

    public NavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAwareOfDrawer = Boolean.valueOf(readFromPreferences(getActivity(),userKnowsDrawerExistKey,"false"));

        if(savedInstanceState!=null)
        {
            fromSavedInstance = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        listview = (ListView)layout.findViewById(R.id.list_options);
        listview.setAdapter(new NavigationListAdapter(getActivity()));
        listview.setOnItemClickListener(new NavigationDrawerListener(getActivity()));
        return layout;
    }


    public void setUpDrawer(DrawerLayout drawer, Toolbar toolbar, int fragment_navigation_drawer, final View main_view) {
        drawerLayout = drawer;
        actionToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout, toolbar,R.string.drawer_open,R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!userAwareOfDrawer)
                {
                    userAwareOfDrawer = true;
                    writeToSharedPreferences(getActivity(),userKnowsDrawerExistKey,"true");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                main_view.setTranslationX(slideOffset*drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
        };
        if(!userAwareOfDrawer && !fromSavedInstance)
        {
            view = getActivity().findViewById(fragment_navigation_drawer);
            drawerLayout.openDrawer(view);
        }

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionToggle.syncState();
            }
        });
        drawerLayout.setDrawerListener(actionToggle);

    }

    public static void writeToSharedPreferences(Context context, String preference, String preferenceValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preference,preferenceValue);
        editor.commit();
    }

    public static String readFromPreferences(Context context, String preference,String defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPref", Context.MODE_PRIVATE);
        return sharedPreferences.getString(preference,defaultValue);
    }
}

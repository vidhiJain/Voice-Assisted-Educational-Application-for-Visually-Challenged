package org.arise.listeners;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import arise.arise.org.arise.AboutArise;
import arise.arise.org.arise.AboutUser;
import arise.arise.org.arise.HomeActivity;
import arise.arise.org.arise.LogoutActivity;

/**
 * Created by Arpit Phanda on 3/11/2015.
 */
public class NavigationDrawerListener implements ListView.OnItemClickListener {

    private Activity context;

    public NavigationDrawerListener(Context context) {
        this.context = (Activity) context;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        for(int a = 0; a < parent.getChildCount(); a++)
        {
            if(a!=position) {
                parent.getChildAt(a).setBackgroundColor(Color.parseColor("#757575"));
            }
        }

        view.setBackgroundColor(Color.parseColor("#D32F2F"));
        setView(position);
    }

    private void setView(int position) {
        switch (position) {
            case 0:
                Intent homeScreen = new Intent(context, HomeActivity.class);
                context.startActivity(homeScreen);
                break;
            case 1:
                Intent aboutUser = new Intent(context, AboutUser.class);
                context.startActivity(aboutUser);
                break;
            case 2:
                Intent aboutARISE = new Intent(context, AboutArise.class);
                context.startActivity(aboutARISE);
                break;

            case 3:
                LogoutActivity logout = new LogoutActivity(context);
                break;


        }

    }


}

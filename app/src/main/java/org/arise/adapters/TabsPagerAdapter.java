package org.arise.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import org.arise.tabs.views.AllCoursesDetail;
import org.arise.tabs.views.CompletedCoursesDetail;
import org.arise.tabs.views.CurrentCoursesDetail;

import arise.arise.org.arise.R;

/**
 * Created by Arpit Phanda on 3/4/2015.
 *
 * This adapter provides fragment views to tabs
 */
public class TabsPagerAdapter extends FragmentPagerAdapter
{
    private Context context;
    String[] titles = {"All","Current","Completed"};
    int[] icons =  {R.drawable.ic_all,R.drawable.ic_current,R.drawable.ic_completed};

    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable d = context.getResources().getDrawable(icons[position]);
        d.setBounds(0,0,48,48);
        ImageSpan imageSpan = new ImageSpan(d);
        SpannableString string = new SpannableString(" ");
        string.setSpan(imageSpan,0,string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new AllCoursesDetail();
            case 1: return new CurrentCoursesDetail();
            case 2: return new CompletedCoursesDetail();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

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

import org.arise.listeners.NavigationDrawerListener;

import arise.arise.org.arise.R;

/**
 * Created by Arpit Phanda on 3/10/2015.
 */
public class NavigationListAdapter extends BaseAdapter{
    private String[] navigationItems;
    private Context context;
    private int[] icons = {R.drawable.ic_home, R.drawable.ic_user_male, R.drawable.ic_about_us, R.drawable.ic_logout};

    public NavigationListAdapter(Context context)
    {
        this.context = context;
        navigationItems = this.context.getResources().getStringArray(R.array.navigation_drawer_items);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return navigationItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, null);
        }
        else
        {
            row = convertView;
        }

        TextView rowText = (TextView) row.findViewById(R.id.list_item_text);
        ImageView rowIcon = (ImageView) row.findViewById(R.id.list_item_image);

        rowText.setText(navigationItems[position]);
        rowIcon.setImageResource(icons[position]);
        return row;
    }
}

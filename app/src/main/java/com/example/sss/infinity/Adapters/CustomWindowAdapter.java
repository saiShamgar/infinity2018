package com.example.sss.infinity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sss.infinity.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter
{
    private final View mWindow;
    private Context context;

    public CustomWindowAdapter(Context context) {
        this.context = context;

        mWindow= LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
    }

    private void rendomWindowText(Marker marker,View view)
    {
        String title=marker.getTitle();
        TextView tvTitle=(TextView)view.findViewById(R.id.tvtitle);

        if (!title.equals(""))
        {
            tvTitle.setText(title);
        }

        String snippet=marker.getSnippet();
        TextView tvsnippet=(TextView)view.findViewById(R.id.snippet);

        if (!snippet.equals(""))
        {
            tvsnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker)
    {

        rendomWindowText(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker)
    {
        rendomWindowText(marker,mWindow);
        return mWindow;
    }
}

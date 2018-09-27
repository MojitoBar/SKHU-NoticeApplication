package com.example.judongseok.itcontest;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by judongseok on 2018-09-25.
 */
public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    public ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;
    String url;
    int i;

    public ListAdapter(ArrayList<ItemData> _oData) {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount() {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }
        i = position;
        TextView oTextDate = (TextView) convertView.findViewById(R.id.textDate);
        TextView oTextTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView oTextNum = (TextView) convertView.findViewById(R.id.textNum);

        url = m_oData.get(position).URL;

        oTextNum.setText(m_oData.get(position).strNum);
        oTextTitle.setText(m_oData.get(position).strTitle);
        oTextDate.setText(m_oData.get(position).strDate);
        return convertView;
    }
}
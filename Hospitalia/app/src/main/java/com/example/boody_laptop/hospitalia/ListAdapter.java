package com.example.boody_laptop.hospitalia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by j on 23/04/2018.
 */

public class ListAdapter extends BaseAdapter
{
    Context context;

    List<Doctor> subject_list;

    public ListAdapter(List<Doctor> listValue, Context context)
    {
        this.context = context;
        this.subject_list = listValue;
    }

    @Override
    public int getCount()
    {
        return this.subject_list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.subject_list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;
        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.listview_items, null);
          viewItem.SubNameTextView = (TextView)convertView.findViewById(R.id.SubjectNameTextView);
            viewItem.SubNameTextView1 = (TextView)convertView.findViewById(R.id.SubjectNameTextView1);

        //  viewItem.SubFullFormTextView = (Button) convertView.findViewById(R.id.btnadd);
            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.SubNameTextView.setText(subject_list.get(position).Doctor_Name);
        viewItem.SubNameTextView1.setText(subject_list.get(position).Doctor_Category);

//        viewItem.SubFullFormTextView.setText((CharSequence) subject_list.get(position));

        return convertView;
    }


}


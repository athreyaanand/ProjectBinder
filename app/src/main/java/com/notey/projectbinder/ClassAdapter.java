package com.notey.projectbinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.notey.projectbinder.activity.ClassDetailsActivity;
import com.notey.projectbinder.activity.MainActivity;

/**
 * Created by athreyaanand on 10/14/17.
 */

public class ClassAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public ClassAdapter(Activity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_classes, null);
        holder.tv=(TextView) rowView.findViewById(R.id.className);
        holder.img=(ImageView) rowView.findViewById(R.id.subjectImg);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, ClassDetailsActivity.class);
                i.putExtra("Title", result[position]);
                context.startActivity(i);
            }
        });
        return rowView;
    }

}
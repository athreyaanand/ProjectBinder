package com.notey.projectbinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassAdapter extends BaseAdapter {
    ArrayList<Classperiod> classes;
    Context context;
    private static LayoutInflater inflater=null;
    public ClassAdapter(Activity context, ArrayList<Classperiod> classes) {
        this.classes=classes;
        this.context=context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Object getItem(int position) {
        return classes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView time;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        if(classes.get(position).getName()==null && classes.get(position).getStartTime()==null && classes.get(position).getWeekdays()==null){
            rowView = inflater.inflate(R.layout.list_weekday, null);
            holder.tv= rowView.findViewById(R.id.weekDay);
            holder.tv.setText(classes.get(position).getSubject());
        } else {
            rowView = inflater.inflate(R.layout.list_classes, null);
            holder.tv= rowView.findViewById(R.id.className);
            holder.img= rowView.findViewById(R.id.subjectImg);
            holder.time= rowView.findViewById(R.id.timePeriod);
            holder.tv.setText(classes.get(position).getName());
            holder.time.setText(classes.get(position).getStartTime()+" - "+classes.get(position).getEndTime());
            holder.img.setImageResource(R.drawable.ic_evernote);
        }
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+classes.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}
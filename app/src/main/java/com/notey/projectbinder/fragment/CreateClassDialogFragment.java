package com.notey.projectbinder.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.codetroopers.betterpickers.recurrencepicker.EventRecurrenceFormatter;
import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;
import com.notey.projectbinder.Classperiod;
import com.notey.projectbinder.R;
import com.notey.projectbinder.activity.MainActivity;
import com.notey.projectbinder.task.CreateNewNotebookTask;
import com.touchboarder.weekdaysbuttons.WeekdaysDataItem;
import com.touchboarder.weekdaysbuttons.WeekdaysDataSource;

import java.sql.Time;
import java.util.ArrayList;

public class CreateClassDialogFragment extends DialogFragment implements RadialTimePickerDialogFragment.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    public interface OnCompleteListener {
        void onComplete(Classperiod period);
    }

    private OnCompleteListener mListener;

    public void setOnCompleteListener(OnCompleteListener mListener){
        this.mListener = mListener;
    }

    TextView startTime, endTime;

    private WeekdaysDataSource weekdaysDataSource;

    public static final String TAG = "CreateClassDialogFragment";

    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";

    boolean isStart;

    View view;

    String[] subjects ={"CS","Science","Math","History","English","Other"};

    //Class Object Vars
    String className;
    String subject = subjects[0];
    String sTime;
    String eTime;
    String weekdays = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_class, null);
        final TextInputLayout titleView = view.findViewById(R.id.textInputLayout_title);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);

        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,subjects);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = true;
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(CreateClassDialogFragment.this)
                        .setThemeDark();
                rtpd.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = false;
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(CreateClassDialogFragment.this)
                        .setThemeDark();
                rtpd.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        TextInputLayout titleView = (TextInputLayout) view.findViewById(R.id.textInputLayout_title);
                        className = titleView.getEditText().getText().toString();
                        getWeekdays();
                        Classperiod cp = new Classperiod(className, sTime, eTime, weekdays, subject);
                        mListener.onComplete(cp);
                        System.out.println("CLASSPERIOD OBJECT: "+className+" "+sTime+" "+eTime+" "+weekdays+" "+subject);

                        break;
                }
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Add a class")
                .setView(view)
                .setPositiveButton(R.string.create, onClickListener)
                .setNegativeButton(android.R.string.cancel, onClickListener)
                .create();
    }


    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        int hour = hourOfDay;
        boolean isPM = false;
            if (hourOfDay>11) isPM=true;
            if (hourOfDay>13) hour = hourOfDay-12;
        String time;
        if (isPM){
            time = getString(R.string.radial_time_picker_result_value, hour, minute)+" PM";
        } else {
            time = getString(R.string.radial_time_picker_result_value, hour, minute)+" AM";
        }

        if (isStart) {
            startTime.setText(time);
            sTime = time;
        }
        else {
            endTime.setText(time);
            eTime = time;
        }
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        RadialTimePickerDialogFragment rtpd = (RadialTimePickerDialogFragment) getFragmentManager().findFragmentByTag(FRAG_TAG_TIME_PICKER);
        if (rtpd != null) {
            rtpd.setOnTimeSetListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        subject = subjects[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        subject = subjects[5];
    }

    public void getWeekdays(){
        RadioButton buttonM = view.findViewById(R.id.radio1);
        RadioButton buttonT = view.findViewById(R.id.radio2);
        RadioButton buttonW = view.findViewById(R.id.radio3);
        RadioButton buttonTh = view.findViewById(R.id.radio4);
        RadioButton buttonF = view.findViewById(R.id.radio5);
        RadioButton buttonSa = view.findViewById(R.id.radio6);
        RadioButton buttonSu = view.findViewById(R.id.radio7);

        if(buttonM.isChecked()) weekdays+="Mo";
        if(buttonT.isChecked()) weekdays+="Tu";
        if(buttonW.isChecked()) weekdays+="We";
        if(buttonTh.isChecked()) weekdays+="Th";
        if(buttonF.isChecked()) weekdays+="Fr";
        if(buttonSa.isChecked()) weekdays+="Sa";
        if(buttonSu.isChecked()) weekdays+="Su";
    }
}

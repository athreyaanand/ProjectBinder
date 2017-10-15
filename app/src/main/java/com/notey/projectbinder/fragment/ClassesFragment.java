package com.notey.projectbinder.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.notey.projectbinder.ClassAdapter;
import com.notey.projectbinder.Classperiod;
import com.notey.projectbinder.R;
import com.notey.projectbinder.activity.ClassDetailsActivity;
import com.notey.projectbinder.activity.MainActivity;
import com.notey.projectbinder.fragment.note.CreateNoteDialogFragment;
import com.notey.projectbinder.util.ViewUtil;
import com.evernote.edam.type.Note;

import net.vrallev.android.task.TaskResult;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClassesFragment extends Fragment implements CreateClassDialogFragment.OnCompleteListener {

    ListView lv;
    ClassAdapter classAdapter;
    Context context;

    public static ArrayList<Classperiod> classList = new ArrayList<>();
    private ArrayList<Classperiod> newList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (classList == null || classList.isEmpty()) {
            //TODO: add something if list empty
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        classAdapter = new ClassAdapter(getActivity(), newList);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateClassDialogFragment ccdf = new CreateClassDialogFragment();
                ccdf.setOnCompleteListener(ClassesFragment.this);
                ccdf.show(getFragmentManager(), CreateClassDialogFragment.TAG);
            }
        });

        lv = (ListView) view.findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //new GetNoteHtmlTask(mNoteRefList.get(position)).start(NoteListFragment.this, "html");
            }
        });

        if (classList == null || classList.isEmpty()) {

        } else {
            reorganizeClassList();
            lv.setAdapter(classAdapter);
            registerForContextMenu(lv);
        }

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            //noinspection ConstantConditions
            ((MainActivity) activity).getSupportActionBar().setTitle("My Schedule");
        }
    }

    @Override
    public void onComplete(Classperiod period) {
        classList.add(period);
        Toast.makeText(getContext(), period.getName()+"ADDED!", Toast.LENGTH_LONG).show();
        reorganizeClassList();
        classAdapter.notifyDataSetChanged();
    }

    private void reorganizeClassList(){
        newList.clear();
            newList.add(new Classperiod(null, null, null, null, "S U N D A Y"));
            newList.add(new Classperiod(null, null, null, null, "M O N D A Y"));
            newList.add(new Classperiod(null, null, null, null, "T U E S D A Y"));
            newList.add(new Classperiod(null, null, null, null, "W E D N E S D A Y"));
            newList.add(new Classperiod(null, null, null, null, "T H U R S D A Y"));
            newList.add(new Classperiod(null, null, null, null, "F R I D A Y"));
            newList.add(new Classperiod(null, null, null, null, "S A T U R D A Y"));

        for (int i = 0; i < classList.size(); i++){
            if (classList.get(i).getWeekdays().contains("Su")) addAfter(newList, classList.get(i), "S U N D A Y");
            if (classList.get(i).getWeekdays().contains("Mo")) addAfter(newList, classList.get(i), "M O N D A Y");
            if (classList.get(i).getWeekdays().contains("Tu")) addAfter(newList, classList.get(i), "T U E S D A Y");
            if (classList.get(i).getWeekdays().contains("We")) addAfter(newList, classList.get(i), "W E D N E S D A Y");
            if (classList.get(i).getWeekdays().contains("Th")) addAfter(newList, classList.get(i), "T H U R S D A Y");
            if (classList.get(i).getWeekdays().contains("Fr")) addAfter(newList, classList.get(i), "F R I D A Y");
            if (classList.get(i).getWeekdays().contains("Sa")) addAfter(newList, classList.get(i), "S A T U R D A Y");
        }
    }

    private void addAfter(ArrayList<Classperiod> list, Classperiod addPeriod, String Date){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getSubject().equals(Date)){
                list.add(i+1, addPeriod);
            }
        }
    }
}


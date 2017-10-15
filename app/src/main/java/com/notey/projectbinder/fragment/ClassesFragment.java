package com.notey.projectbinder.fragment;

import android.content.Context;
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
import com.notey.projectbinder.activity.MainActivity;
import com.notey.projectbinder.fragment.note.CreateNoteDialogFragment;
import com.notey.projectbinder.util.ViewUtil;
import com.evernote.edam.type.Note;

import net.vrallev.android.task.TaskResult;

import java.util.ArrayList;

public class ClassesFragment extends Fragment implements CreateClassDialogFragment.OnCompleteListener {

    ListView lv;
    Context context;

    public static ArrayList<Classperiod> classList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (classList == null || classList.isEmpty()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EmptyFragment.create("Classes"))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateClassDialogFragment ccdf = new CreateClassDialogFragment();
                ccdf.setOnCompleteListener(ClassesFragment.this);
                ccdf.show(getFragmentManager(), CreateClassDialogFragment.TAG);
            }
        });

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
    }
}


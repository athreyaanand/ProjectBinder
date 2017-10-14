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
import com.notey.projectbinder.R;
import com.notey.projectbinder.activity.MainActivity;
import com.notey.projectbinder.fragment.note.CreateNoteDialogFragment;
import com.notey.projectbinder.util.ViewUtil;
import com.evernote.edam.type.Note;

import net.vrallev.android.task.TaskResult;

public class ClassesFragment extends Fragment {

    ListView lv;
    Context context;

    public static int [] prgmImages={R.drawable.ic_evernote,R.drawable.ic_evernote,R.drawable.ic_evernote,R.drawable.ic_evernote,R.drawable.ic_evernote,R.drawable.ic_evernote};
    public static String [] prgmNameList={"Math 1554","CS 1331","CS 1100","HTS 1080","ENGL 1101","INTA 1501"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (prgmNameList == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EmptyFragment.create("notes"))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        lv = (ListView) view.findViewById(R.id.listView);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //new GetNoteHtmlTask(mNoteRefList.get(position)).start(NoteListFragment.this, "html");
                }
            });

            lv.setAdapter(new ClassAdapter(getActivity(), prgmNameList, prgmImages));

            registerForContextMenu(lv);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    /*@Override
    public void onFabClick() {
        //TODO: Add class dialog
        new CreateNoteDialogFragment().show(getChildFragmentManager(), CreateNoteDialogFragment.TAG);
    }*/

}


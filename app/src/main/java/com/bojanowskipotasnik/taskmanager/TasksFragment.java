package com.bojanowskipotasnik.taskmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bojanowskipotasnik.taskmanager.adapter.OnTaskClickListener;
import com.bojanowskipotasnik.taskmanager.adapter.RecyclerViewAdapter;
import com.bojanowskipotasnik.taskmanager.model.SharedViewModel;
import com.bojanowskipotasnik.taskmanager.model.Task;
import com.bojanowskipotasnik.taskmanager.model.TaskViewModel;


public class TasksFragment extends Fragment implements OnTaskClickListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedViewModel sharedViewModel;
    private final String TAG = "TaskFragment";

    public TasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_tasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                TasksFragment.this.getActivity().getApplication()).create(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(TasksFragment.this.getActivity(), tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        sharedViewModel = new ViewModelProvider(TasksFragment.this.getActivity()).get(SharedViewModel.class);
        Log.i(TAG, "Attaching buttons to variables and preparing tasks list");
        return view;
    }

    @Override
    public void onTaskClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
        ((MainActivity)getActivity()).showAddingSheetDialog();
        Log.i(TAG, "Executing the showAddingSheetDialog for editing existing task");
    }

    @Override
    public void onNoteClick(Task task) {
        sharedViewModel.selectItem(task);
        ((MainActivity)getActivity()).showNoteSheetDialog();
        Log.i(TAG, "Executing the showNoteSheetDialog");
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
        Log.i(TAG, "Deleting task from database");
    }
}
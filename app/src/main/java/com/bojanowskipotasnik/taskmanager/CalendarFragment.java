package com.bojanowskipotasnik.taskmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bojanowskipotasnik.taskmanager.adapter.OnTaskClickListener;
import com.bojanowskipotasnik.taskmanager.adapter.RecyclerViewAdapter;
import com.bojanowskipotasnik.taskmanager.model.SharedViewModel;
import com.bojanowskipotasnik.taskmanager.model.Task;
import com.bojanowskipotasnik.taskmanager.model.TaskViewModel;
import com.bojanowskipotasnik.taskmanager.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.Calendar;
import java.util.Date;


public class CalendarFragment extends Fragment implements OnTaskClickListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedViewModel sharedViewModel;
    private CalendarView calendarView;
    Calendar calendar = Calendar.getInstance();
    private Date dueDate = calendar.getTime();
    private Date nextDay;
    private Chip calendarChip;
    private final String TAG = "CalendarFragment";

    public CalendarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar.add(Calendar.DATE, -1);
        nextDay = calendar.getTime();
        calendarView = view.findViewById(R.id.calendar_view);
        calendarChip = view.findViewById(R.id.date_chip_calendar);
        recyclerView = view.findViewById(R.id.recycler_view_calendar);
        calendarChip.setText(Utils.formatDate(dueDate));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                CalendarFragment.this.getActivity().getApplication()).create(TaskViewModel.class);
        taskViewModel.getAllDueDateTasks(nextDay,dueDate).observe(CalendarFragment.this.getActivity(), tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        sharedViewModel = new ViewModelProvider(CalendarFragment.this.getActivity()).get(SharedViewModel.class);
        Log.i(TAG, "Attaching buttons to variables and preparing tasks from \"today\"");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            Log.i(TAG, "Choosing date to display tasks from given date");
            calendar.clear();
            calendar.set(year,month,day);
            dueDate = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            nextDay = calendar.getTime();
            String formatted = Utils.formatDate(dueDate);
            calendarChip.setText(formatted);
            TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                    CalendarFragment.this.getActivity().getApplication()).create(TaskViewModel.class);
            taskViewModel.getAllDueDateTasks(dueDate,nextDay).observe(CalendarFragment.this.getActivity(), tasks -> {
                recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
                recyclerView.setAdapter(recyclerViewAdapter);
            });
        });

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

    @Override
    public void onRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
        Log.i(TAG, "Deleting task from database");
    }
}
package com.bojanowskipotasnik.taskmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.bojanowskipotasnik.taskmanager.model.SharedViewModel;
import com.bojanowskipotasnik.taskmanager.model.Task;
import com.bojanowskipotasnik.taskmanager.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;


public class NoteFragment extends BottomSheetDialogFragment {

    private TextView taskTitle;
    private TextView description;
    private Chip dateChip;
    private SharedViewModel sharedViewModel;
    private final String TAG = "NoteFragment";

    public NoteFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null) {
            Task task = sharedViewModel.getSelectedItem().getValue();
            taskTitle.setText(task.getTask());
            description.setText(task.getNote());
            dateChip.setText(Utils.formatDate(task.getDueDate()));
        }
        Log.i(TAG, "onResume function");
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        taskTitle = view.findViewById(R.id.Task_title);
        description = view.findViewById(R.id.Note);
        dateChip = view.findViewById(R.id.date_chip_note);
        Log.i(TAG, "Attaching buttons to variables");
        return view;
    }
}
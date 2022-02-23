package com.bojanowskipotasnik.taskmanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.bojanowskipotasnik.taskmanager.model.Priority;
import com.bojanowskipotasnik.taskmanager.model.SharedViewModel;
import com.bojanowskipotasnik.taskmanager.model.Task;
import com.bojanowskipotasnik.taskmanager.model.TaskViewModel;
import com.bojanowskipotasnik.taskmanager.model.Task_Type;
import com.bojanowskipotasnik.taskmanager.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class AddingSheetFragment extends BottomSheetDialogFragment{

    private EditText enterTask;
    private EditText enterDescription;
    private ImageButton priorityButton;
    private Chip calendarChip;
    private RadioGroup typeRadioGroup;
    private RadioButton selectedType;
    private ChipGroup priorityGroup;
    private Chip selectedPriority;
    private int selectedButtonId;
    private int selectedRadioButtonId;
    private ImageButton addButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private Priority priority;
    private Task_Type type = Task_Type.PERSONAL;
    private final String TAG = "AddingSheetFragment";

    public AddingSheetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar);
        calendarChip = view.findViewById(R.id.Date_chip);
        enterDescription = view.findViewById(R.id.enter_description);
        enterTask = view.findViewById(R.id.enter_task);
        priorityButton = view.findViewById(R.id.Priority_Button);
        priorityGroup = view.findViewById(R.id.Priority_chip_group);
        addButton = view.findViewById(R.id.Add_button);
        typeRadioGroup = view.findViewById(R.id.enter_category);
        Log.i(TAG, "Attaching buttons to variables");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getIsEdit()) {
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTask.setText(task.getTask());
            enterDescription.setText(task.getNote());
        }
        Log.i(TAG, "onResume function");
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        calendarChip.setOnClickListener(view11 -> {
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideSoftKeyboard(view11);
            Log.i(TAG, "CalendarChip was clicked");
        });

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            calendar.clear();
            calendar.set(year,month,day);
            dueDate = calendar.getTime();
            Log.i(TAG, "Choosing date");
        });

        priorityButton.setOnClickListener(view12 -> {
            Utils.hideSoftKeyboard(view12);
            priorityGroup.setVisibility(priorityGroup
                    .getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            priorityGroup.setOnCheckedChangeListener((chipGroup, checkedId) -> {
                Log.i(TAG, "Choosing priority");
                try {
                    if (priorityGroup.getVisibility() == View.VISIBLE) {
                        selectedButtonId = checkedId;
                        selectedPriority = view.findViewById(selectedButtonId);
                        if (selectedPriority.getId() == R.id.Priority_chip_1) {
                            priority = Priority.HIGH;
                        } else if (selectedPriority.getId() == R.id.Priority_chip_2) {
                            priority = Priority.MEDIUM;
                        } else if (selectedPriority.getId() == R.id.Priority_chip_3) {
                            priority = Priority.LOW;
                        } else {
                            priority = Priority.LOW;
                        }
                    } else {
                        priority = Priority.LOW;
                    }
                }
                catch (NullPointerException e){
                }
            });
        });

        typeRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            Log.i(TAG, "Choosing type");
            Utils.hideSoftKeyboard(radioGroup);
            selectedRadioButtonId = checkedId;
            selectedType = view.findViewById(selectedRadioButtonId);
            if(selectedType.getId() == R.id.cat_Home){
                type = Task_Type.HOME;
            }
            else if(selectedType.getId() == R.id.cat_Personal){
                type = Task_Type.PERSONAL;
            }
            else if(selectedType.getId() == R.id.cat_School){
                type = Task_Type.SCHOOL;
            }
            else if(selectedType.getId() == R.id.cat_Work){
                type = Task_Type.WORK;
            }
            else {
                type = Task_Type.PERSONAL;
            }
        });


        addButton.setOnClickListener(view1 -> {
            String task = enterTask.getText().toString().trim();
            String note = enterDescription.getText().toString().trim();
            if(!TextUtils.isEmpty(task) && dueDate != null && priority != null && type != null){
                Task myTask = new Task(task, note, priority,type, dueDate,
                        Calendar.getInstance().getTime(), false);

                if(isEdit){
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();

                    updateTask.setTask(task);
                    updateTask.setNote(note);
                    updateTask.setPriority(priority);
                    updateTask.setTask_type(type);
                    updateTask.setDueDate(dueDate);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);
                }
                else {
                    TaskViewModel.insert(myTask);
                }
                enterDescription.setText("");
                enterTask.setText("");
                if(this.isVisible()){
                    this.dismiss();
                }
            }
            else {
                Snackbar.make(addButton, R.string.empty_field, Snackbar.LENGTH_LONG).show();
            }
            Log.i(TAG, "Adding task");
        });
    }

}

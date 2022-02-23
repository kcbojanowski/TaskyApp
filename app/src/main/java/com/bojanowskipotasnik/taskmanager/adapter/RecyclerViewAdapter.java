package com.bojanowskipotasnik.taskmanager.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bojanowskipotasnik.taskmanager.R;
import com.bojanowskipotasnik.taskmanager.model.Task;
import com.bojanowskipotasnik.taskmanager.model.Task_Type;
import com.bojanowskipotasnik.taskmanager.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

// Class for prepare tasks to be displayed in recycler view

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Task> taskList;
    private final OnTaskClickListener taskClickListener;

    public RecyclerViewAdapter(List<Task> taskList, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.taskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    //Method that create view to display tasks
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    //Method that bind date and view
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY,
                        Utils.priorityColor(task)
                });

        viewHolder.task.setText(task.getTask());
        viewHolder.date_Chip.setText(formatted);
        viewHolder.date_Chip.setTextColor(Utils.priorityColor(task));
        viewHolder.date_Chip.setChipIconTint(colorStateList);
        viewHolder.radioButton.setButtonTintList(colorStateList);
        if(task.getTask_type() == Task_Type.HOME){
            viewHolder.category_Icon_Home.setVisibility(View.VISIBLE);
        }
        else if(task.getTask_type() == Task_Type.PERSONAL){
            viewHolder.category_Icon_Personal.setVisibility(View.VISIBLE);
        }
        else if(task.getTask_type() == Task_Type.SCHOOL){
            viewHolder.category_Icon_School.setVisibility(View.VISIBLE);
        }
        else if(task.getTask_type() == Task_Type.WORK){
            viewHolder.category_Icon_Work.setVisibility(View.VISIBLE);
        }

    }

    @Override
    //Method to get item count
    public int getItemCount() {
        return taskList.size();
    }

    //Class that bind single task and single row in recycler view
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip date_Chip;
        public ImageView category_Icon_Home;
        public ImageView category_Icon_School;
        public ImageView category_Icon_Personal;
        public ImageView category_Icon_Work;
        public ImageButton note_Image;

        OnTaskClickListener onTaskClickListener;

        //Method that bind variables to elements in row
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.task_radio_button);
            task = itemView.findViewById(R.id.task_text_view);
            date_Chip = itemView.findViewById(R.id.task_row_chip);
            category_Icon_Home = itemView.findViewById(R.id.category_icon_Home);
            category_Icon_School = itemView.findViewById(R.id.category_icon_School);
            category_Icon_Personal = itemView.findViewById(R.id.category_icon_Personal);
            category_Icon_Work = itemView.findViewById(R.id.category_icon_Work);
            note_Image = itemView.findViewById(R.id.note_button);
            this.onTaskClickListener = taskClickListener;
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
            note_Image.setOnClickListener(this);

        }

        @Override
        // Method that control clicking on single task row
        public void onClick(View view) {
            int id = view.getId();
            Task currTask = taskList.get(getAdapterPosition());
            if(id == R.id.task_row){
                onTaskClickListener.onTaskClick(currTask);
            }
            else if(id == R.id.note_button){
                onTaskClickListener.onNoteClick(currTask);
            }
            else if(id == R.id.task_radio_button){
                onTaskClickListener.onRadioButtonClick(currTask);
            }
        }
    }
}

package com.bojanowskipotasnik.taskmanager.adapter;

import com.bojanowskipotasnik.taskmanager.model.Task;

//Interface for exchanging task between fragments

public interface OnTaskClickListener {
    void onTaskClick(Task task);
    void onNoteClick(Task task);
    void onRadioButtonClick(Task task);
}

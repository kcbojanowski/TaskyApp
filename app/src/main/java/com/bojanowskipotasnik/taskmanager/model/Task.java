package com.bojanowskipotasnik.taskmanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {
    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)

    public long taskId;

    public String task;

    public String note;

    public Priority priority;

    public Task_Type task_type;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    @ColumnInfo(name = "create_date")
    public Date createDate;

    @ColumnInfo(name = "is_done")
    public Boolean isDone;

    public Task(String task,String note, Priority priority,Task_Type task_type, Date dueDate, Date createDate, Boolean isDone) {
        this.task = task;
        this.note = note;
        this.priority = priority;
        this.task_type = task_type;
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.isDone = isDone;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Task_Type getTask_type() {
        return task_type;
    }

    public void setTask_type(Task_Type task_type) {
        this.task_type = task_type;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", task='" + task + '\'' +
                ", note='" + note + '\'' +
                ", priority=" + priority +
                ", task_type=" + task_type +
                ", dueDate=" + dueDate +
                ", createDate=" + createDate +
                ", isDone=" + isDone +
                '}';
    }
}

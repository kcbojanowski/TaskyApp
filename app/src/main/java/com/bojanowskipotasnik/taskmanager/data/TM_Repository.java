package com.bojanowskipotasnik.taskmanager.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.bojanowskipotasnik.taskmanager.model.Task;
import com.bojanowskipotasnik.taskmanager.util.TaskDatabase;

import java.util.Date;
import java.util.List;

//Class that is single source of truth for all app

public class TM_Repository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;
    private final String TAG = "TM_Repository";

    public TM_Repository(Application application) {
        TaskDatabase database = TaskDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.allTasks = taskDao.getTasks();
    }
    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public void insert(Task task){
        TaskDatabase.databaseWriteExecutor.execute(()->taskDao.insertTask(task));
        Log.i(TAG, "Inserting task " + task.getTask());
    }

    public LiveData<Task> get(long id){
        return taskDao.get(id);
    }

    public void update(Task task){
        TaskDatabase.databaseWriteExecutor.execute(()-> taskDao.update(task));
        Log.i(TAG, "Updating task " + task.getTask());
    }

    public void delete(Task task){
        TaskDatabase.databaseWriteExecutor.execute(()-> taskDao.delete(task));
        Log.i(TAG, "Deleting task " + task.getTask());
    }

    public void deleteAll(){
        TaskDatabase.databaseWriteExecutor.execute(()-> taskDao.deleteAll());
        Log.i(TAG, "Deleting all tasks");
    }

    public LiveData<List<Task>> getDueDateAll(Date dueDate, Date nextDay){
        Log.i(TAG, "Getting all tasks from " + dueDate.toString());
        return taskDao.getAllFromDueDate(dueDate, nextDay);
    }
}

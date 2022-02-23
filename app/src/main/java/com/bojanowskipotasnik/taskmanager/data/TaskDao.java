package com.bojanowskipotasnik.taskmanager.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bojanowskipotasnik.taskmanager.model.Task;

import java.util.Date;
import java.util.List;

//Interface that connects repository with database (SQLite)

@Dao //Data Access Object
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM task_table WHERE task_table.task_id == :id")
    LiveData<Task> get(long id);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table WHERE (task_table.due_date >= :dueDate AND task_table.due_date < :nextDay)")
    LiveData<List<Task>> getAllFromDueDate(Date dueDate, Date nextDay);
}

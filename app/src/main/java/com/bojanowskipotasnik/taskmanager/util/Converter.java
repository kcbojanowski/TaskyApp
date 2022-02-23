package com.bojanowskipotasnik.taskmanager.util;

import androidx.room.TypeConverter;

import com.bojanowskipotasnik.taskmanager.model.Priority;
import com.bojanowskipotasnik.taskmanager.model.Task_Type;

import java.util.Date;

public class Converter {

    @TypeConverter
    public static Date date_from_Timestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long date_to_Timestamp(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority){
        return priority == null ? null : Priority.valueOf(priority);
    }

    @TypeConverter
    public static Task_Type toTask_Type(String task_type){
        return task_type == null ? null : Task_Type.valueOf(task_type);
    }

    @TypeConverter
    public static String Task_Type_toString(Task_Type task_type){
        return task_type == null ? null : task_type.name();
    }
}

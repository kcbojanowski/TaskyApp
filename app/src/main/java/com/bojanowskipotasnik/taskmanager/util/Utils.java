package com.bojanowskipotasnik.taskmanager.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bojanowskipotasnik.taskmanager.model.Priority;
import com.bojanowskipotasnik.taskmanager.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat .getDateInstance();
        simpleDateFormat.applyPattern("EEE, d, MMM");
        return simpleDateFormat.format(date);
    }
    public static void hideSoftKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static int priorityColor(Task task){
        int color;
        if(task.getPriority() == Priority.HIGH){
            color = color = Color.argb(150,246, 21, 23);
        }
        else if(task.getPriority() == Priority.MEDIUM){
            color = Color.argb(150,255, 150,0);
        }
        else if(task.getPriority() == Priority.LOW){
            color = Color.argb(150, 5, 201, 60);
        }else{
            color = Color.argb(150, 51, 181, 229);
        }
        return color;
    }
}

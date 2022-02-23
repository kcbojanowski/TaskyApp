package com.bojanowskipotasnik.taskmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.bojanowskipotasnik.taskmanager.adapter.OnTaskClickListener;
import com.bojanowskipotasnik.taskmanager.adapter.RecyclerViewAdapter;
import com.bojanowskipotasnik.taskmanager.model.SharedViewModel;
import com.bojanowskipotasnik.taskmanager.model.Task;
import com.bojanowskipotasnik.taskmanager.model.TaskViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity implements OnTaskClickListener{

    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    AddingSheetFragment addingSheetFragment;
    private SharedViewModel sharedViewModel;
    NoteFragment noteFragment;
    private final String TAG = "ActivityMain";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // adding panel implementation
        addingSheetFragment = new AddingSheetFragment();
        final ConstraintLayout constraintLayoutAdd = findViewById(R.id.addingSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehaviorAdd = BottomSheetBehavior.from(constraintLayoutAdd);
        bottomSheetBehaviorAdd.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        // notes panel implementation
        noteFragment = new NoteFragment();
        final ConstraintLayout constraintLayoutNote = findViewById(R.id.note_fragment);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehaviorNote = BottomSheetBehavior.from(constraintLayoutNote);
        bottomSheetBehaviorNote.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        //Bottom Navigation View
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new TasksFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.tasklist);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.timetable:
                        fragment = new TimetableFragment();
                        Log.i(TAG, "Opening the TimetableFragment");
                        break;

                    case R.id.calendar:
                        fragment = new CalendarFragment();
                        Log.i(TAG, "Opening the CalendarFragment");
                        break;

                    case R.id.tasklist:
                        fragment = new TasksFragment();
                        Log.i(TAG, "Opening the TasksFragment");
                        break;

                    case R.id.informations:
                        fragment = new InformationsFragment();
                        Log.i(TAG, "Opening the InformationsFragment");
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            showAddingSheetDialog();
            Log.i(TAG, "Executing the showAddingSheetDialog for adding new task");
        });

    }

    public void showAddingSheetDialog() {
        addingSheetFragment.show(getSupportFragmentManager(), addingSheetFragment.getTag());
        Log.i(TAG, "Opening the AddingSheetDialog");
    }

    public void showNoteSheetDialog(){
        noteFragment.show(getSupportFragmentManager(),noteFragment.getTag());
        Log.i(TAG, "Opening the NoteSheetDialog");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Single task actions
    @Override
    public void onTaskClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
        showAddingSheetDialog();
        Log.i(TAG, "Executing the showAddingSheetDialog for editing existing task");
    }

    @Override
    public void onNoteClick(Task task) {
        sharedViewModel.selectItem(task);
        showNoteSheetDialog();
        Log.i(TAG, "Executing the showNoteSheetDialog");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
        Log.i(TAG, "Deleting task from database");
    }
}


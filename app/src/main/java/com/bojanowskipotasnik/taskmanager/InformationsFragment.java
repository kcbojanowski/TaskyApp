package com.bojanowskipotasnik.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.DialogFragment;

import com.bojanowskipotasnik.taskmanager.util.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class InformationsFragment extends DialogFragment {

    private SwitchMaterial Set1;
    private Chip infoChip;
    private Group infoGroup;


    public InformationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informations, container, false);
        infoGroup = view.findViewById(R.id.info_group);
        infoChip = view.findViewById(R.id.Info_chip);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoChip.setOnClickListener(view11 -> {
            infoGroup.setVisibility(infoGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideSoftKeyboard(view11);
        });
    }

}

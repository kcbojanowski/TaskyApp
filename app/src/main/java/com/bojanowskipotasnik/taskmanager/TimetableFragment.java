package com.bojanowskipotasnik.taskmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;


public class TimetableFragment extends Fragment {
    Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    Button SelectButton;
    ImageView TimetableImage;
    private int RESULT_OK;
    private Bitmap bitmapImage;
    private SharedPreferences savedUri;
    private SharedPreferences.Editor editor;

    public TimetableFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedUri = requireActivity().getSharedPreferences("SAVED", Context.MODE_PRIVATE);
        String imageUri = savedUri.getString("Uri", "");
        if (!imageUri.equals("")) {
            Uri uri = Uri.parse(imageUri);
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        SelectButton = view.findViewById(R.id.select_button);
        TimetableImage = view.findViewById(R.id.timetable_image);
        if(bitmapImage != null){
            TimetableImage.setImageBitmap(bitmapImage);
        }
        SelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request permissions
                if (ActivityCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2000);
                } else {
                    startGallery();
                }
            }
        });

        return view;
    }
    // open gallery with images
    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
        cameraIntent.setType("image/*");
        cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(cameraIntent, "Select Picture"), 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        //save image as bitmap, add to ImageView
                        bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        TimetableImage.setImageBitmap(bitmapImage);
                        //save chosen image in SharedPreferences
                        savedUri = getActivity().getSharedPreferences("SAVED", Context.MODE_PRIVATE);
                        editor = savedUri.edit();
                        editor.putString("Uri", data.getData().toString());
                        editor.apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //display massage about cancel
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
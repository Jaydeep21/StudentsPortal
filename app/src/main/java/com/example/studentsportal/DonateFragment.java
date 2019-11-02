package com.example.studentsportal;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;


public class DonateFragment extends Fragment implements  View.OnClickListener{

    EditText e1, e2, e3, e4;
    ImageView i1,i2;
    Button b1;
    DbHelper dbHelper;
    Bitmap bitmap;

    private static final int SELECT_PHOTO = 1;
    SharedPreferences pref;
    private String imageFilePath;
    private boolean isimageTakenFromCamera = false;
    public DonateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        e1= view.findViewById(R.id.productName);
        e2 = view.findViewById(R.id.price);
        e3 = view.findViewById(R.id.years_used);
        e4 = view.findViewById(R.id.description);
        i1 = view.findViewById(R.id.camera);
        i2 = view.findViewById(R.id.noimagee);
        b1 = view.findViewById(R.id.donate);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            i1.setEnabled(false);

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            i1.setEnabled(true);

        }

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DbHelper(getContext());

                Log.i("jdbc","db create hona chaiye");
                addToDb(v);
                if (isimageTakenFromCamera) {
                    imageFilePath = saveImage(bitmap, e1.getText().toString());
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                i1.setEnabled(true);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "Request Code: " + requestCode + " Result code: " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_PHOTO:
                    // image is selected form gallery
                    i2.setMaxWidth(200);
                    i2.setImageBitmap(onGalleryImageSelected(data));
                    isimageTakenFromCamera = false;
                    break;
            }
        }
    }
    private Bitmap onGalleryImageSelected(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        // Get the cursor
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
        cursor.close();
        imageFilePath = imgDecodableString;
        return bitmap;
    }
    @Override
    public void onClick(View v) {

    }
    public void addToDb(View view) {

        pref = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String donated_by = pref.getString("email", null);
        String productname = e1.getText().toString();
        String price = e2.getText().toString();

        String years_used = e3.getText().toString();
        String description = e4.getText().toString();
        String borrowed_by = null;

        //Log.i("Gadbad","hua kya");

        dbHelper.addToDb(new ModelClass(donated_by, productname, price, years_used, description, imageFilePath, borrowed_by, isimageTakenFromCamera));
        //String donated_by, String productname, String price, String years_used, String description, String image, String borrowed_by
        Toast.makeText(getActivity(), "Image saved to DB successfully", Toast.LENGTH_SHORT).show();

    }
    private String saveImage(Bitmap bitmap, String title) {
        String filename = getActivity().getFilesDir() + "/images/" + title + ".jpeg";
        try {
            FileOutputStream out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }
}

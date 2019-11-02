package com.example.studentsportal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.provider.BaseColumns._ID;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "StudentsPortal.db";
    private static final int DATABASE_VERSION = 1;
    Context context;

    SQLiteDatabase sqLiteDatabase = getWritableDatabase();

    public final static String TABLE_NAME = "donate";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "donated_by varchar(20), productname varchar(20), price varchar(20), years_used varchar(20), description varchar(20), image varchar(20), borrowed_by varchar(20), FOREIGN KEY(donated_by) REFERENCES user(email) " + " );";

        db.execSQL(SQL_CREATE_IMAGE_TABLE);
        Log.i(TAG, SQL_CREATE_IMAGE_TABLE);
        Log.d(TAG, "Database Created Successfully" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addToDb(ModelClass objectModelClass){

        SQLiteDatabase db = this.getWritableDatabase();

        // TODO: add code to get the image and store it in a folder
        String newImagepath = null;
        if (!objectModelClass.isImageSaved()) {
            newImagepath = saveImage(new File(objectModelClass.getImage()), objectModelClass.getProductname());
        } else {
            newImagepath = objectModelClass.getImage();
        }


        ContentValues cv = new  ContentValues();

        cv.put("donated_by", objectModelClass.getDonated_by());
        cv.put("productname", objectModelClass.getProductname());
        cv.put("price", objectModelClass.getPrice());
        cv.put("years_used", objectModelClass.getYears_used());
        cv.put("description", objectModelClass.getDescription());
        cv.put("image",newImagepath);
        cv.put("borrowed_by", objectModelClass.getBorrowed_by());

        long checkIfQueryRuns = db.insert( TABLE_NAME, null, cv );

        if(checkIfQueryRuns!=0){

            Toast.makeText(context,"Data Inserted",Toast.LENGTH_SHORT).show();
            db.close();
        }
        else{

            Toast.makeText(context,"Not Inserted",Toast.LENGTH_SHORT).show();
        }
    }
    private String saveImage(File uploadingFile, String title) {
        String newFilePath = null;
        try {
            File internalFilesDir = context.getFilesDir();
            File imagesFolder = new File(internalFilesDir + "/images");

            if (!imagesFolder.exists()) {

                imagesFolder.mkdir();
            }


            FileInputStream fileInputStream = new FileInputStream(uploadingFile);


            File file = new File(imagesFolder + "/" + title + ".jpeg");

            FileOutputStream fileOutputStream = new FileOutputStream(file);


            byte[] bytes = new byte[1024];
            int length;


            while((length = fileInputStream.read(bytes)) != -1) {

                fileOutputStream.write(bytes, 0, length);
            }
            Log.d("TAG", "Image with name " + file.getName() + " saved at " + file.getAbsolutePath());
            fileInputStream.close();
            fileOutputStream.close();
            newFilePath = file.getAbsolutePath();
        } catch(IOException e){
            e.printStackTrace();
            Log.e("TAG", "Image couldn\'t be saved");
        }

        return newFilePath;
    }
}

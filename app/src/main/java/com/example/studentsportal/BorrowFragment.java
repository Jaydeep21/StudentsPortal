package com.example.studentsportal;


import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BorrowFragment extends Fragment implements Adapter.ItemClickListener{

    RecyclerView recyclerView;
    ArrayList<ClipData.Item> arrayList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_borrow,container,false);

    recyclerView = view.findViewById(R.id.list_recyclerview);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


    List<ModelClass> modelClassList = new ArrayList<>();

    DbHelper database = new DbHelper(getContext());
    SQLiteDatabase db = database.getWritableDatabase();

        if(db!=null){
        Cursor cursor = db.rawQuery("select * from donate ",null);
        Log.d("TAG", "Cursor Count: " + cursor.getCount());
        if(cursor.getCount()==0){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                //byte[] imagebyte = cursor.getBlob(5);
                //Bitmap objectBitmap = BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);

                modelClassList.add(
                        new ModelClass(cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3) ,
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7)
                        )
                );
            }
        }
        cursor.close();
    }
    Adapter adapter = new Adapter(getContext(), modelClassList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setItemClickListener(
                new Adapter.ItemClickListener() {
        @Override
        public void onClick(String donated_by, String productname, String price, String years_used, String description, String image, String borrowed_by) {

                    Intent intent = new Intent(getActivity(), ProductDetails.class);
                    intent.putExtra("donated_by", donated_by);
                    intent.putExtra("productname", productname);
                    intent.putExtra("price", price);
                    intent.putExtra("years_used", years_used);
                    intent.putExtra("description", description);
                    intent.putExtra("image",   image);
                    intent.putExtra("borrowed_by",   borrowed_by);
                    startActivity(intent);
                }
            }
        );

        return view;
}


    @Override
    public void onClick(String donated_by, String productname, String price, String years_used, String description, String image, String borrowed_by) {
        Intent intent = new Intent(getActivity(), ProductDetails.class);
        intent.putExtra("donated_by", donated_by);
        intent.putExtra("productname", productname);
        intent.putExtra("price", price);
        intent.putExtra("years_used", years_used);
        intent.putExtra("description", description);
        intent.putExtra("image",   image);
        intent.putExtra("borrowed_by",   borrowed_by);
        startActivity(intent);
    }
}

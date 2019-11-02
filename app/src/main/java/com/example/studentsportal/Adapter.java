package com.example.studentsportal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {

    private ItemClickListener itemClickListener;
    private List<ModelClass> modelClassesList;
    private Context ctx;

    public Adapter(Context ctx, List<ModelClass> modelClassesList) {
        this.ctx = ctx;
        this.modelClassesList = modelClassesList;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Viewholder viewholder, final int i) {
        ModelClass mc = modelClassesList.get(i);
        viewholder.image.setImageBitmap(BitmapFactory.decodeFile(mc.getImage()));
        viewholder.productname.setText(mc.getProductname());
        viewholder.price.setText(mc.getPrice());

        viewholder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModelClass item = modelClassesList.get(i);
                        itemClickListener.onClick( item.getDonated_by(), item.getProductname(), item.getPrice(), item.getYears_used(), item.getDescription(), item.getBorrowed_by(), item.getImage());
                        //item.getVideo();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class Viewholder extends RecyclerView.ViewHolder{

        private TextView productname;
        private TextView price;
        private ImageView image;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            productname = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);

            //setData(imageView,dishName,description);
        }

    }
    public interface ItemClickListener {
        void onClick(String donated_by, String productname, String price, String years_used, String description, String image, String borrowed_by);
        // TODO: Bitmap video
    }
}

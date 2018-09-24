package com.example.boody_laptop.hospitalia;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class RecyclerViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView imageView;
    public TextView textView;

    private ItemClickListener itemClickListener;

    public RecyclerViewHolder2(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.ex_img);
        textView=(TextView) itemView.findViewById(R.id.ex_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());
    }
}

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<PatientProfileActivity> List;
    private Context context;

    public RecyclerViewAdapter2(List<PatientProfileActivity> List,Context context){
        this.List=List;
        this.context=context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView= inflater.inflate(R.layout.item_exercise,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        //holder.imageView.setImageResource(List.get(position).getImage_id());
       // holder.textView.setText(List.get(position).getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(context, ViewExercise.class);
         //       intent.putExtra("image_id",List.get(position).getImage_id());
           //     intent.putExtra("name",List.get(position).getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}

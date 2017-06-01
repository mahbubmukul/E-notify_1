package com.devbd.devmukul.e_notify;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbd.devmukul.e_notify.Activities.DetailsActivity;
import com.devbd.devmukul.e_notify.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    private List<Item> sProjectList = new ArrayList<Item>();

    public DataAdapter(Context context) {
        this.context = context;
    }

    public DataAdapter(Context context, List<Item> sProjectList) {
        this.context = context;
        this.sProjectList = sProjectList;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder,final int i) {

        viewHolder.tv_name.setText(sProjectList.get(i).getProjectName());
        viewHolder.tv_desc.setText(sProjectList.get(i).getShortDescription());
        viewHolder.img_android.setImageResource(R.mipmap.spring);

        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("Position", i);
                context.startActivity(intent);
            }
        });

        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("Position", i);
                context.startActivity(intent);
            }
        });

        viewHolder.tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Position", i);
                context.startActivity(intent);
            }
        });


        viewHolder.img_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("Position", i);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return sProjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_desc;
        private ImageView img_android;
        private CardView img_card;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.name);
            tv_desc = (TextView)view.findViewById(R.id.desc);
            img_android = (ImageView) view.findViewById(R.id.android_img);
            img_card = (CardView) view.findViewById(R.id.card_view);
        }
    }

}

package com.example.coco.refreshview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by coco on 2017/11/2.
 */

public class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<String> data;
    private Context context;

    public MyAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.tv.setText("my position is " + position);
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "我是" + position + "号", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView tv;

    public ItemViewHolder(View view) {
        super(view);
        tv = (TextView) view.findViewById(R.id.mTv);
    }
}


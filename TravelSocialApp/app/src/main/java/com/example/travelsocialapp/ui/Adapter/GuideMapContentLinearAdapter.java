package com.example.travelsocialapp.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelsocialapp.R;

public class GuideMapContentLinearAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<GuideMapContentLinearAdapter.LinearViewHolder> {
    private Context mcontext;

    public GuideMapContentLinearAdapter(Context context){
        this.mcontext = context;
    }


    @NonNull
    @Override
    public GuideMapContentLinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new GuideMapContentLinearAdapter.LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_recyclerview_guide_map_content,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuideMapContentLinearAdapter.LinearViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{//绑定控件

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }
}

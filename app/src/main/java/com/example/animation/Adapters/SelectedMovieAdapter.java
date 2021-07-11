package com.example.animation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animation.Interfaces.OnRemovedListener;
import com.example.animation.Models.DialogData;
import com.example.animation.R;
import com.example.animation.Utils.Base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectedMovieAdapter extends RecyclerView.Adapter<SelectedMovieAdapter.mySelectedMovieHolder> {
    List<DialogData> dialogData;
    View emptyView;
    RecyclerView recyclerView;
    OnRemovedListener onRemovedListener;

    public SelectedMovieAdapter() {
        dialogData = new ArrayList<>();
    }

    @NonNull
    @Override
    public mySelectedMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie,parent,false);
        return new mySelectedMovieHolder(view);
    }

    public void setView(View emptyView) {
        this.emptyView = emptyView;
    }
    public void setRecView(RecyclerView recView){
        this.recyclerView=recView;
    }
    public void setEmptyView(boolean show){
       if (show){
          emptyView.setVisibility(View.VISIBLE);
          recyclerView.setVisibility(View.GONE);
       }
       else {
           emptyView.setVisibility(View.GONE);
           recyclerView.setVisibility(View.VISIBLE);
       }
    }

    public void setDialogData(List<DialogData> dialogData) {
        if (dialogData.size()==0 || dialogData==null){
              this.dialogData.clear();
              setEmptyView(true);

        }
        else {
            this.dialogData=dialogData;
             setEmptyView(false);
        }
       notifyDataSetChanged();
    }

    public void setOnRemovedListener(OnRemovedListener onRemovedListener) {
        this.onRemovedListener = onRemovedListener;
    }

    @Override
    public void onBindViewHolder(@NonNull mySelectedMovieHolder holder, int position) {
        String image_url = Base.IMAGE_URL_BASE_PATH + dialogData.get(position).getMovieImage();
        holder.movie_name.setText(dialogData.get(position).getMovieName());
        Glide.with(holder.getItemView().getContext()).load(image_url).placeholder(R.drawable.progress_animation).into(holder.imageSelected);
    }
     public void Remove(DialogData data){
        if (dialogData == null) return;
            int index=dialogData.indexOf(data);
            if (index >= 0 ) {
                dialogData.remove(data);
                notifyItemRemoved(index);
            }
            setEmptyView(dialogData.size() == 0);
        }
    @Override
    public int getItemCount() {
        return dialogData.size();
    }

    class mySelectedMovieHolder extends RecyclerView.ViewHolder {
        ImageView imageSelected,imgRemove;
        TextView movie_name;
        public mySelectedMovieHolder(@NonNull View itemView) {
            super(itemView);
            imageSelected = itemView.findViewById(R.id.imageSelected);
            movie_name = itemView.findViewById(R.id.movie_name);
            imgRemove=itemView.findViewById(R.id.imageRemove);
            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onRemovedListener.onRemoved(dialogData.get(getAdapterPosition()));
                }
            });
        }
        public View getItemView(){
            return itemView;
        }
    }
}

package com.example.animation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animation.Adapters.SelectedMovieAdapter;
import com.example.animation.Interfaces.OnRemovedListener;
import com.example.animation.Models.DialogData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.objectbox.Box;

public class FavouriteFrag  extends Fragment implements View.OnClickListener,OnRemovedListener {
    FloatingActionButton mFloatBar;
    Box<DialogData> dialogBox;
    SelectedMovieAdapter selectedMovieAdapter;
    FrameLayout emptyView;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_layout,container,false);
        mFloatBar=view.findViewById(R.id.add);
        List<DialogData> dialogDataList=dialogBox.getAll();
        recyclerView=view.findViewById(R.id.movie_Items);
        emptyView=view.findViewById(R.id.emptyView);
        selectedMovieAdapter=new SelectedMovieAdapter();
        selectedMovieAdapter.setView(emptyView);
        selectedMovieAdapter.setRecView(recyclerView);
        selectedMovieAdapter.setDialogData(dialogDataList);
        selectedMovieAdapter.setOnRemovedListener(this);
        recyclerView.setAdapter(selectedMovieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFloatBar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getContext(),MainActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dialogBox = Initializer.getBoxStore().boxFor(DialogData.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRemoved(DialogData dialogData) {
        dialogBox.remove(dialogData);
        selectedMovieAdapter.Remove(dialogData);
    }
}

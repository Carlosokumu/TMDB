package com.example.animation;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.animation.Interfaces.OnMovieListener;
import com.example.animation.Models.DialogData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MoviesDialog  extends BottomSheetDialogFragment  {
    TextView txtMovieName,addFav;
    ImageView imgMovie;
    private static final String DATA = "DATA";
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    DialogData dialogData;
    MaterialProgressBar materialProgressBar;
    OnMovieListener onMovieListener;
    public MoviesDialog newInstance(DialogData dialogData) {

        Bundle args = new Bundle();
        args.putParcelable("DATA",dialogData);
        MoviesDialog fragment = new MoviesDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.movies_dialog,container,false);
        txtMovieName=view.findViewById(R.id.txtMovieName);
        addFav=view.findViewById(R.id.addFav);
        imgMovie=view.findViewById(R.id.imageMovie);
        dialogData=getArguments().getParcelable(DATA);
        if (dialogData != null){
            txtMovieName.setText(dialogData.getMovieName());
            String image_url = IMAGE_URL_BASE_PATH + dialogData.getMovieImage();
            Glide.with(this).load(image_url).placeholder(R.drawable.progress_animation).into(imgMovie);
        }
        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovieListener.movieListener(dialogData);
                dismiss();
            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof OnMovieListener) {
            onMovieListener = (OnMovieListener) getActivity();
        } else {
            dismiss();
        }
    }


}

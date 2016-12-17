package com.myhexaville.immersiveapp;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.myhexaville.immersiveapp.databinding.ActivityDetailsBinding;

import static android.view.View.GONE;


public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE = "movie";
    private static final String LOG_TAG = "DetailsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_details);

        Movie movie = getIntent().getParcelableExtra(MOVIE);

        binding.setMovie(movie);
        binding.navBar.setBackgroundColor(movie.getColor());
        binding.scroll.setBackgroundColor(movie.getColor());

        if (Build.VERSION.SDK_INT < 21) {
            binding.navBar.setVisibility(GONE);
        }
    }
}
package com.myhexaville.immersiveapp;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.myhexaville.immersiveapp.databinding.ActivityDetailsBinding;

import static android.view.View.GONE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE = "movie";
    private static final String LOG_TAG = "DetailsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate: ");

        super.onCreate(savedInstanceState);

        supportPostponeEnterTransition();
        final long start = System.currentTimeMillis();

        final ActivityDetailsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_details);

        setupBackdropHeight(binding);

        Movie movie = getIntent().getParcelableExtra(MOVIE);

        binding.setMovie(movie);

        binding.navBar.setBackgroundColor(movie.getColor());
        binding.scroll.setBackgroundColor(movie.getColor());
        Glide.with(this)
                .load(movie.getPosterUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        DetailsActivity.this.supportStartPostponedEnterTransition();
                        Log.d(LOG_TAG, "onResourceReady: " + (System.currentTimeMillis() - start));
                        return false;
                    }
                })
                .into(binding.backdrop);

//        binding.text.setTextColor(movie.getTextColor());

        if (Build.VERSION.SDK_INT < 21) {
            binding.navBar.setVisibility(GONE);
        }

    }

    private void setupBackdropHeight(ActivityDetailsBinding binding) {
        CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(MATCH_PARENT,
                getBackdropHeight());
        params.setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX);

        binding.backdrop.setLayoutParams(params);
    }

    public int getBackdropHeight() {
        return (int) (getResources().getDisplayMetrics().widthPixels / 1.5f);
    }
}
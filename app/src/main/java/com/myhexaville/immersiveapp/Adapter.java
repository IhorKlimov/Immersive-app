/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myhexaville.immersiveapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.myhexaville.immersiveapp.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Holder> {
    private static final String LOG_TAG = "Adapter";
    private List<Movie> mMovies = new ArrayList<>();
    private Activity mActivity;

    public Adapter(Activity a) {
        mActivity = a;

        populateMovieList();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding inflate = DataBindingUtil
                .inflate(LayoutInflater
                        .from(parent.getContext()), R.layout.list_item, parent, false);

        return new Holder(mActivity, inflate.getRoot());
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final Movie movie = mMovies.get(position);
        holder.setMovie(movie);

        Glide.with(mActivity)
                .load(movie.getPosterUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new ImageViewTarget<GlideDrawable>(holder.binding.image) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        setImage(resource);

                        if (!hasExtractedColorAlready()) {
                            extractColors(resource);
                        }
                    }

                    private boolean hasExtractedColorAlready() {
                        return movie.getColor() != 0;
                    }

                    private void setImage(GlideDrawable resource) {
                        holder.binding.image.setImageDrawable(resource.getCurrent());
                    }

                    private void extractColors(GlideDrawable resource) {
                        Bitmap b = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                        Palette p = Palette.from(b).generate();

                        extractBackgroundColor(p);

//                        extractTextColor(p);
                    }

                    private void extractBackgroundColor(Palette p) {
                        int defaultColor = mActivity.getResources().getColor(R.color.primary);


                        //                        int color = p.getDominantColor(defaultColor);
                        //                        int color = p.getMutedColor(defaultColor);
                        //                        int color = p.getLightMutedColor(defaultColor);
                        //                        int color = p.getDarkMutedColor(defaultColor);
                        //                        int color = p.getVibrantColor(defaultColor);
                        //                        int color = p.getLightVibrantColor(defaultColor);
                        int color = p.getDarkVibrantColor(defaultColor);
                        movie.setBackgroundColor(color);
                    }

                    private void extractTextColor(Palette p) {
                        Palette.Swatch swatch = p.getDarkVibrantSwatch();
                        int textColor = Color.parseColor("#ffffff");
                        if (swatch!= null) {
                            textColor = swatch.getBodyTextColor();
                        }
                        movie.setTextColor(textColor);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private void populateMovieList() {
        for (int i = 0; i < 10; i++) {
            mMovies.add(new Movie("http://www.theonlinebeacon.com/wp-content/uploads/2016/01/star_wars_battlefront_dice.0.jpg"));
            mMovies.add(new Movie("http://fantasynscifi.com/wp-content/uploads/2016/06/ogimage_img.jpg"));
            mMovies.add(new Movie("https://secure.cdn2.wdpromedia.com/resize/mwImage/1/900/360/90/wdpromedia.disney.go.com/media/wdpro-hkdl-assets/prod/en-intl/system/images/hkdl-event-star-wars-hero-4character.jpg"));
            mMovies.add(new Movie("http://a.dilcdn.com/bl/wp-content/uploads/sites/6/2016/01/sullust_04-2400x1200-795653638057.jpg"));
            mMovies.add(new Movie("https://media.starwars.ea.com/content/starwars-ea-com/en_US/starwars/battlefront/_jcr_content/ogimage.img.jpeg"));
            mMovies.add(new Movie("http://overmental.com/wp-content/uploads/2015/07/James-Earl-Jones-Darth-Vader-Star-Wars-Rebels-790x494.jpg"));
            mMovies.add(new Movie("https://cdn1.vox-cdn.com/uploads/chorus_asset/file/7617317/star_wars_republic_command_hard_contact.png"));
            mMovies.add(new Movie("http://tech.co/wp-content/uploads/2015/09/flat-design-successful.png"));
            mMovies.add(new Movie("http://digitalsynopsis.com/wp-content/uploads/2013/12/flat-design-rounded-corner-icons.jpg"));
        }
    }
}

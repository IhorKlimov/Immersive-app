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
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.myhexaville.immersiveapp.databinding.ListItemBinding;

public class Holder extends RecyclerView.ViewHolder {
    private static final String LOG_TAG = "Holder";
    private Activity mActivity;
    private Movie mMovie;
    ListItemBinding binding;

    public Holder(Activity a, final View itemView) {
        super(itemView);
        mActivity = a;

        binding = DataBindingUtil.bind(itemView);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailsActivity.class)
                        .putExtra(DetailsActivity.MOVIE, mMovie);

                itemView.getContext().startActivity(intent);

            }
        });
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
    }
}

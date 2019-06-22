/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.bilibili.boxing.impl;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.testapplication.Base64Util;
import com.example.testapplication.R;
import com.bilibili.boxing.loader.IBoxingCallback;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.testapplication.IBoxingMediaLoadAndTranser;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * use https://github.com/bumptech/glide as media loader.
 * can <b>not</b> be used in Production Environment.
 *
 * @author ChenSL
 */
public class BoxingGlideLoader implements IBoxingMediaLoadAndTranser {

    @Override
    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int height) {
        Log.d("++++++++++", "displayThumbnail: ");
        String path = "file://" + absPath;
        try {
            // https://github.com/bumptech/glide/issues/1531
            Glide.with(img.getContext()).load(path).placeholder(R.drawable.ic_boxing_default_image).crossFade().centerCrop().override(width, height).into(img);
        } catch(IllegalArgumentException ignore) {
        }

    }

    @Override
    public void displayRaw(@NonNull final ImageView img, @NonNull String absPath, int width, int height, final IBoxingCallback callback) {
        Log.d("******", "displayRaw: ");
        String path = "file://" + absPath;
        BitmapTypeRequest<String> request = Glide.with(img.getContext())
                .load(path)
                .asBitmap();
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        request.listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                if (callback != null) {
                    callback.onFail(e);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (resource != null && callback != null) {
                    img.setImageBitmap(resource);
                    callback.onSuccess();
                    return true;
                }
                return false;
            }
        }).into(img);

    }


    @Override
    public void displayThumbnailandTran(@NonNull ImageView img, @NonNull LinkedList<String> bag, @NonNull String absPath, int width, int height) {
        Log.d("-------", "displayThumbnailandTran: ");
        String path = "file://" + absPath;
        try {
            // https://github.com/bumptech/glide/issues/1531
            Glide.with(img.getContext()).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    img.setImageBitmap(resource);
                    bag.add(Base64Util.BitmapToBase64(resource));
                    Log.d(bag.getLast(), "onResourceReady: ");
                }
            });
            Glide.with(img.getContext()).load(path).placeholder(R.drawable.ic_boxing_default_image).crossFade().centerCrop().override(width, height).into(img);
        } catch(IllegalArgumentException ignore) {
            ignore.printStackTrace();
        }
    }

    @Override
    public void displayRawandTran(@NonNull ImageView img, @NonNull LinkedList<String> bag, @NonNull String absPath, int width, int height, IBoxingCallback callback) {

    }
}

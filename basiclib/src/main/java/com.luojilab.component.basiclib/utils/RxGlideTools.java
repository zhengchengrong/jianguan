package com.luojilab.component.basiclib.utils;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import static com.luojilab.component.basiclib.utils.RxTool.getContext;

/**
 * Created by Hao on 16/6/24.
 */
public class RxGlideTools {

    public static void GlideGif(String imgUrl, ImageView view, int resourceId) {
            Glide(imgUrl, view, resourceId);

    }
    public static void Glide(String imgUrl, ImageView view, int resourceId) {
        Glide.with(getContext()).load(imgUrl).into(view);
    }


}

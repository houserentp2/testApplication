package com.example.testapplication;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bilibili.boxing.loader.IBoxingCallback;
import com.bilibili.boxing.loader.IBoxingMediaLoader;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IBoxingMediaLoadAndTranser extends IBoxingMediaLoader {
    /**
     * display thumbnail images for a ImageView.
     *
     * @param img     the display ImageView. Through ImageView.getTag(R.string.boxing_app_name) to get the absolute path of the exact path to display.
     * @param bag       the arraylist of  base64 encode images
     * @param absPath the absolute path to display, may be out of date when fast scrolling.
     * @param width   the resize with for the image.
     * @param height  the resize height for the image.
     */
    void displayThumbnailandTran(@NonNull ImageView img, @NonNull LinkedList<String> bag, @NonNull String absPath, int width, int height);

    /**
     * display raw images for a ImageView, need more work to do.
     *
     * @param img      the display ImageView.Through ImageView.getTag(R.string.boxing_app_name) to get the absolute path of the exact path to display.
     * @param bag       the arraylist of  base64 encode images
     * @param absPath  the absolute path to display, may be out of date when fast scrolling.
     * @param width the expected width, 0 means the raw width.
     * @param height the expected height, 0 means the raw height.
     * @param callback the callback for the load result.
     */
    void displayRawandTran(@NonNull ImageView img, @NonNull LinkedList<String> bag, @NonNull String absPath, int width, int height, IBoxingCallback callback);
}

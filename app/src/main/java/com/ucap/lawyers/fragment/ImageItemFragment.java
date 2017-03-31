package com.ucap.lawyers.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ucap.lawyers.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wekingwang on 16/10/26.
 */
public class ImageItemFragment extends Fragment {

    public PhotoViewAttacher photoViewAttacher;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_item, null);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        Bundle arguments = getArguments();
        Glide.with(this).load(arguments.getString("uri")).into(photoView);
        photoViewAttacher = new PhotoViewAttacher(photoView);
        return view;
    }

    /**
     * 切换图片时刷行图片回到原始比例
     */
    public void update() {
        photoViewAttacher.update();
    }

}

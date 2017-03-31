package com.ucap.lawyers.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;

/**
 * Created by wekingwang on 16/9/29.
 */
public class FilesLstView extends ListView {
    public FilesLstView(Context context) {
        super(context);
    }

    public FilesLstView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilesLstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);//请求父控件不要拦截滑动时间
         return super.dispatchTouchEvent(ev);
    }


}

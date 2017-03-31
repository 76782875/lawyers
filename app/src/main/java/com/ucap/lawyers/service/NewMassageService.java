package com.ucap.lawyers.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by wekingwang on 2016/12/9.
 */

public class NewMassageService extends Service {

    public PushAgent mPushAgent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = Service.START_STICKY_COMPATIBILITY;
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("mytoken", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
            }

        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, NewMassageService.class);
        startService(intent);
    }
}

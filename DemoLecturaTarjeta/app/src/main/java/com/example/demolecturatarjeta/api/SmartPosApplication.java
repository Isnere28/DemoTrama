package com.example.demolecturatarjeta.api;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.demolecturatarjeta.ConsumeData;
import com.example.demolecturatarjeta.card.DeviceTopUsdkServiceManager;

public class SmartPosApplication extends Application {
    private static final String TAG = "Prueba";

    private Context mContext;
    private static SmartPosApplication mPosApplication;

    public ConsumeData mConsumeData;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        mContext = getApplicationContext();
        mPosApplication = this;
        DeviceTopUsdkServiceManager.getInstance().bindDeviceService();
    }

    public static SmartPosApplication getApp() {
        return mPosApplication;
    }

    public void setConsumeData() {
        mConsumeData = new ConsumeData();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate");
        System.exit(0);
    }

}

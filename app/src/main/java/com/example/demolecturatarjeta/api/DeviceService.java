package com.example.demolecturatarjeta.api;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.topwise.cloudpos.aidl.AidlDeviceService;

import java.util.List;

public abstract class DeviceService extends Activity {
    public static final String TOPWISE_SERVICE_ACTION = "topwise_cloudpos_device_service";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        bindService();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        this.unbindService(conn);
    }

    public void bindService(){
        Intent intent = new Intent();
        intent.setAction(TOPWISE_SERVICE_ACTION);
        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));
        boolean flag = bindService(eintent, conn, Context.BIND_AUTO_CREATE);
    }
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    /** service binding */
    private ServiceConnection conn = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
            if(serviceBinder != null){
                AidlDeviceService serviceManager = AidlDeviceService.Stub.asInterface(serviceBinder);
                onDeviceConnected(serviceManager);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("Prueba", "Desconectado:");
        }
    };

    public abstract void onDeviceConnected(AidlDeviceService serviceManager);
}

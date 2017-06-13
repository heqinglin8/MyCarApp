package cn.com.autohome.mycarapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by heqinglin8 on 17/4/8.
 */
public class BaseApplication extends Application{
    private static BaseApplication instance;
    private static Context sContext;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}

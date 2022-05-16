package com.xinxing.cixi_community;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.xinxing.cixi_community.config.TCConfigManager;
import com.tencent.qcloud.ugckit.UGCKit;
import com.tencent.qcloud.ugckit.utils.TCUserMgr;
import com.tencent.qcloud.ugckit.utils.LogReport;
import com.tencent.rtmp.TXLog;
import com.tencent.ugc.TXUGCBase;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

/**
 * 小视频应用类，用于全局的操作，如
 * sdk初始化,全局提示框
 */
public class TCApplication extends MultiDexApplication {
    private static final String TAG = "TCApplication";

//    private RefWatcher mRefWatcher;

    private static TCApplication instance;

    private String ugcKey = "9cae1c84cb918946129740fcd792f0b5";
    private String ugcLicenceUrl = "https://license.vod2.myqcloud.com/license/v2/1300663734_1/v_cube.license";


//private String ugcKey = "9cae1c84cb918946129740fcd792f0b5";
//private String ugcLicenceUrl = "https://license.vod2.myqcloud.com/license/v2/1300663734_1/v_cube.license";




    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        TCConfigManager.init(this);
        initSDK();

//        mRefWatcher = LeakCanary.install(this);

        // 短视频licence设置
        TXUGCBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);
        UGCKit.init(this);

        // ELK数据上报：启动次数
        LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_START_UP, 0, "");

        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks(this));
    }

    public static TCApplication getApplication() {
        return instance;
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        TCApplication application = (TCApplication) context.getApplicationContext();
//        return application.mRefWatcher;
//    }

    /**
     * 初始化SDK，包括Bugly，LiteAVSDK等
     */
    public void initSDK() {
        TCUserMgr.getInstance().initContext(getApplicationContext());
        TXLog.w(TAG, "app init sdk");

    }

    private class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        private int foregroundActivities;
        private boolean isChangingConfiguration;
        private long time;

        public MyActivityLifecycleCallbacks(TCApplication tcApplication) {

        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                // 应用进入前台
                time = System.currentTimeMillis();
            }
            isChangingConfiguration = false;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
                // 应用切入后台
                long bgTime = System.currentTimeMillis();
                long diff = (bgTime - time) / 1000;
                // ELK数据上报：使用时间
                LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_STAY_TIME, diff, "");
            }
            isChangingConfiguration = activity.isChangingConfigurations();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}

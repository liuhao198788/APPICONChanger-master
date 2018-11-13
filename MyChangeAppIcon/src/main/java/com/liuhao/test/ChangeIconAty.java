package com.liuhao.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class ChangeIconAty extends Activity {

    ComponentName defaultComponent;
    ComponentName testComponent;

    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_layout);

        //
        packageManager = getApplicationContext().getPackageManager();

        defaultComponent = new ComponentName(getBaseContext(),"com.liuhao.test.ChangeIconAty");
        testComponent = new ComponentName(getBaseContext(),"com.liuhao.test.TestChange");
    }

    /**
     * 启用组件
     *
     * @param componentName
     */
    private void enableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            //已经启用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName
     */
    private void disableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            //已经禁用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void OnClickChange2Camera(View view){
        disableComponent(defaultComponent);
        enableComponent(testComponent);

        //Intent 重启 Launcher 应用
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,0);
        for (ResolveInfo res : resolveInfos){
            if (res.activityInfo!=null){
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }

    public void OnClickChange2Default(View view){
        enableComponent(defaultComponent);
        disableComponent(testComponent);

        //Intent 重启 Launcher 应用
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,0);
        for (ResolveInfo res : resolveInfos){
            if (res.activityInfo!=null){
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }

}

package com.liuhao.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private ComponentName defaultComponent;
    private ComponentName testComponent;
    private PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //拿到当前activity注册的组件名称
        ComponentName componentName = getComponentName();

        //拿到我们注册的MainActivity组件
        defaultComponent = new ComponentName(getBaseContext(), "com.liuhao.test.MainActivity");  //拿到默认的组件
        //拿到我注册的别名test组件
        testComponent = new ComponentName(getBaseContext(), "com.liuhao.test.test");

        packageManager = getApplicationContext().getPackageManager();
    }

    public void changeIcon(View view) {
        disableComponent(defaultComponent);
        enableComponent(testComponent);
    }

    public void changeDefaultIcon(View view) {
        enableComponent(defaultComponent);
        disableComponent(testComponent);
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

}

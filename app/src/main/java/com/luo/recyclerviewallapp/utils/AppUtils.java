package com.luo.recyclerviewallapp.utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ResolveInfo.DisplayNameComparator;

import com.luo.recyclerviewallapp.bean.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by luo on 2019/8/12.
 */

public class AppUtils {

    /**
     * get all app info
     *
     * @param context
     * @return
     */
    public static final ArrayList<AppInfo> getAppInfos(Context context) {
        ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
        PackageManager manager = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfos, new DisplayNameComparator(manager));
        if (appInfos != null) {
            appInfos.clear();
            for (ResolveInfo resolveInfo : resolveInfos) {
                if (resolveInfo.activityInfo.packageName.equals("com.mstar.tv.tvplayer.ui")) {
                    continue;
                }
                AppInfo appInfo = new AppInfo();
                appInfo.setAppIcon(resolveInfo.loadIcon(manager));
                appInfo.setAppName((String) resolveInfo.loadLabel(manager));
                appInfo.setPackageName(resolveInfo.activityInfo.packageName);
                appInfos.add(appInfo);
            }
        }
        return appInfos;
    }

}

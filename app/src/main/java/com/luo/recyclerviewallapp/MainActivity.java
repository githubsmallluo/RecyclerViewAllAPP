package com.luo.recyclerviewallapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luo.recyclerviewallapp.adapter.AllAdapter;
import com.luo.recyclerviewallapp.bean.AppInfo;
import com.luo.recyclerviewallapp.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luo on 2019/8/12.
 */


public class MainActivity extends AppCompatActivity {

    private List<AppInfo> allApp;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManagertwo;
    private AllAdapter allMyAdapater;
    private RecyclerView allAppRecycler;
    private RecyclerView allAppRecyclertwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        allApp = new ArrayList<AppInfo>();
        allApp = AppUtils.getAppInfos(this);
        allMyAdapater = new AllAdapter(this, allApp);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManagertwo = new LinearLayoutManager(this);
    }

    private void initView() {
        allAppRecycler = (RecyclerView) findViewById(R.id.recyclerview);
        allAppRecyclertwo = (RecyclerView) findViewById(R.id.recyclerviewtwo);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManagertwo.setOrientation(LinearLayoutManager.HORIZONTAL);

        allAppRecyclertwo.setLayoutManager(linearLayoutManagertwo);
        allAppRecyclertwo.setAdapter(allMyAdapater);

        allAppRecycler.setLayoutManager(linearLayoutManager);
        allAppRecycler.setAdapter(allMyAdapater);

        allMyAdapater.setOnItemClickListener(new AllAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent mIntent = new Intent();
                PackageManager packageManager = getPackageManager();
                AppInfo appInfo = allApp.get(position);
                mIntent = packageManager.getLaunchIntentForPackage(appInfo.getPackageName());
                if (mIntent != null) {
                    startActivity(mIntent);
                }
            }
        });

        allMyAdapater.setOnItemLongClickListener(new AllAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
                AppInfo appInfo = allApp.get(position);
                uninstall(appInfo.getPackageName());
            }
        });
    }

    public boolean uninstall(String packageName) {
        boolean b = checkApplication(packageName);
        if (b) {
            Uri packageURI = Uri.parse("package:".concat(packageName));
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(packageURI);
            this.startActivity(intent);
            allMyAdapater.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    private boolean checkApplication(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        return true;
    }
}


package com.example.tt.createlauncherdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends ActionBarActivity {
    private PackageManager manager;
    private List<AppDetail> apps;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        loadApps();
        loatListView();
        addClickListener();
    }

    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<AppDetail>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivitys = manager.queryIntentActivities(i, 0);
        for (ResolveInfo r : availableActivitys) {
            AppDetail app = new AppDetail();
            app.lable = r.loadLabel(manager);
            app.name = r.activityInfo.name;
            app.icon = r.activityInfo.loadIcon(manager);
            apps.add(app);

        }

    }

    private void loatListView() {
        list = (ListView) findViewById(R.id.app_list);
        ArrayAdapter<AppDetail> adap = new ArrayAdapter<AppDetail>(this, R.layout.list_item, apps){
            @Override
        public View getView(int position, View convertView,ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item, null);

                }
                ImageView appIcon  =(ImageView) convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);
                TextView lableApp = (TextView) convertView.findViewById(R.id.item_app_lable);
                lableApp.setText(apps.get(position).lable);

                TextView nameApp = (TextView) convertView.findViewById(R.id.item_app_name);
                nameApp.setText(apps.get(position).name);
                return  convertView;
            }
        };
        list.setAdapter(adap);
    }

    private void addClickListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = manager.getLaunchIntentForPackage(apps.get(position).name.toString());
                AppListActivity.this.startActivity(i);
            }
        });
    }
}

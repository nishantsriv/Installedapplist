package com.example.nishant.myapplication;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Systemapp extends AppCompatActivity {
    List<ApplicationInfo> system = new ArrayList<>();
    private RecyclerView recyclerView_system;
    List<ApplicationInfo> packages = new ArrayList<>();
    List<ApplicationInfo> launchableapps = new ArrayList<>();
    private Adapter adapter_system;
    int flags = PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemapp);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        title_toolbar = (TextView) findViewById(R.id.txt_toolbar);
        title_toolbar.setText("User Applications");
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportFragmentManager().beginTransaction().add(R.id.containr, new NavigationFragment()).commit();

        final PackageManager pm = getPackageManager();
        packages = pm.getInstalledApplications(flags);
        for (ApplicationInfo appinfo : packages) {
            if ((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                system.add(appinfo);

            }
        }
        for (int i = 0; i < system.size(); i++) {
            if (getPackageManager().getLaunchIntentForPackage(system.get(i).packageName) != null) {
                launchableapps.add(system.get(i));
            }
        }

        //system app
        recyclerView_system = (RecyclerView) findViewById(R.id.rview);
        adapter_system = new Adapter(this, launchableapps);
        recyclerView_system.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_system.setAdapter(adapter_system);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

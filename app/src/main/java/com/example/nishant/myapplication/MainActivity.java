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
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<ApplicationInfo> packages = new ArrayList<>();
    List<ApplicationInfo> user = new ArrayList<>();
    private RecyclerView recyclerView_user;
    private Adapter adapter_user;
    int flags = PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        title_toolbar = (TextView) findViewById(R.id.txt_toolbar);
        title_toolbar.setText("User Applications");
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();


        final PackageManager pm = getPackageManager();
        packages = pm.getInstalledApplications(flags);
        for (ApplicationInfo appinfo : packages) {
            if (!((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
                user.add(appinfo);

            }
        }

        getSupportFragmentManager().beginTransaction().add(R.id.container, new NavigationFragment()).commit();
        //USERapp
        recyclerView_user = (RecyclerView) findViewById(R.id.rview2);
        adapter_user = new Adapter(this, user);
        recyclerView_user.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_user.setAdapter(adapter_user);
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

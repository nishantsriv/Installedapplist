package com.example.nishant.myapplication;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener {
    LinearLayout btn_systemapp, btn_userapp;
    TextView textView;
    String version;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        btn_systemapp = (LinearLayout) view.findViewById(R.id.click_systemapp);
        btn_systemapp.setOnClickListener(this);
        btn_userapp = (LinearLayout) view.findViewById(R.id.click_userapp);
        btn_userapp.setOnClickListener(this);
        textView = (TextView) view.findViewById(R.id.txt_appversion);
        try {
            appversion();
            textView.setText("App Version" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void appversion() throws PackageManager.NameNotFoundException {
        PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        version = info.versionName;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_systemapp) {
            Intent intent = new Intent(getActivity(), Systemapp.class);
            startActivity(intent);
            getActivity().finish();
        } else if (v == btn_userapp) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}

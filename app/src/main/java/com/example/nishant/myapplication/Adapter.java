package com.example.nishant.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nishant on 04-03-2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private List<ApplicationInfo> applicationInfoList;
    private LayoutInflater inflater;

    public Adapter(Context context, List<ApplicationInfo> applicationInfoList) {
        this.context = context;
        this.applicationInfoList = applicationInfoList;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.model, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        //image
        try {
            Drawable drawable = context.getPackageManager().getApplicationIcon(applicationInfoList.get(position).packageName);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            holder.imageView.setImageBitmap(resized);
            int color = getDominantColor(bitmap);
            holder.launchbutton.setBackgroundColor(color);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //app name
        holder.text_appname.setText(applicationInfoList.get(position).loadLabel(context.getPackageManager()).toString());

        //packge name
        holder.text_packagename.setText(applicationInfoList.get(position).packageName);
        //launch button click//
        holder.launchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(applicationInfoList.get(position).packageName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationInfoList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView text_appname;
        TextView text_packagename;
        ImageView imageView;
        LinearLayout launchbutton;

        public Holder(View itemView) {
            super(itemView);
            launchbutton = (LinearLayout) itemView.findViewById(R.id.btn_launch);
            text_appname = (TextView) itemView.findViewById(R.id.text_appname);
            text_packagename = (TextView) itemView.findViewById(R.id.text_packagename);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }

    public static int getDominantColor(Bitmap bitmap) {
        if (null == bitmap) return Color.TRANSPARENT;

        int redBucket = 0;
        int greenBucket = 0;
        int blueBucket = 0;
        int alphaBucket = 0;

        boolean hasAlpha = bitmap.hasAlpha();
        int pixelCount = bitmap.getWidth() * bitmap.getHeight();
        int[] pixels = new int[pixelCount];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int y = 0, h = bitmap.getHeight(); y < h; y++) {
            for (int x = 0, w = bitmap.getWidth(); x < w; x++) {
                int color = pixels[x + y * w]; // x + y * width
                redBucket += (color >> 16) & 0xFF; // Color.red
                greenBucket += (color >> 8) & 0xFF; // Color.greed
                blueBucket += (color & 0xFF); // Color.blue
                if (hasAlpha) alphaBucket += (color >>> 24); // Color.alpha
            }
        }

        return Color.argb(
                (hasAlpha) ? (alphaBucket / pixelCount) : 255,
                redBucket / pixelCount,
                greenBucket / pixelCount,
                blueBucket / pixelCount);
    }

}

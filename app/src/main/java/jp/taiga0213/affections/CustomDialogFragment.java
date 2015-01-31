package jp.taiga0213.affections;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feapar on 2015/01/31.
 */
public class CustomDialogFragment extends DialogFragment {

    private String appName;
    private String affection;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ArrayList<String> appList = new ArrayList<String>();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        // 起動中のアプリ情報を取得
        List<ActivityManager.RunningAppProcessInfo> runningApp = activityManager.getRunningAppProcesses();
        PackageManager packageManager = getActivity().getPackageManager();
        try {
            appName =  (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(runningApp.get(1).processName, 0));
            affection =  getActivity().getIntent().getStringExtra("af");

            TextView mes = (TextView)dialog.findViewById(R.id.message);
            mes.setText(appName+" + "+affection +"?");

            ImageView icon = (ImageView)dialog.findViewById(R.id.app_icon);
            icon.setImageBitmap(((BitmapDrawable) packageManager.getApplicationInfo(runningApp.get(1).processName, 0).loadIcon(packageManager)).getBitmap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // OK ボタンのリスナ
        dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), appName+" + "+affection, Toast.LENGTH_SHORT).show();

                getActivity().finish();
//                dismiss();
            }
        });
        // Close ボタンのリスナ
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
//                dismiss();
            }
        });

        return dialog;
    }
}

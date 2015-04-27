package jp.taiga0213.affections;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import jp.taiga0213.beans.AffectionBean;
import jp.taiga0213.map.AffectionMap;

/**
 * Created by feapar on 2015/01/31.
 */
public class CustomDialogFragment extends DialogFragment {

    private String appName;
    private String appPackage;
    private String affection;
    private byte[] appIcon;

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
            appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(runningApp.get(1).processName, 0));
            affection = getActivity().getIntent().getStringExtra("af");
            appPackage = packageManager.getApplicationInfo(runningApp.get(1).processName, 0).packageName;

            TextView mes = (TextView) dialog.findViewById(R.id.message);
            mes.setText(appName + " + " + affection + "?");

            AffectionMap ImageMap = new AffectionMap();
            ImageView affectionsIcon = (ImageView)dialog.findViewById(R.id.affections_icon);

            affectionsIcon.setImageResource(ImageMap.getImageMap().get(affection));

            ImageView icon = (ImageView) dialog.findViewById(R.id.app_icon);
            icon.setImageBitmap(((BitmapDrawable) packageManager.getApplicationInfo(runningApp.get(1).processName, 0).loadIcon(packageManager)).getBitmap());

            // Bitmap形式をバイナリに変換

            Bitmap bitmap = ((BitmapDrawable) packageManager.getApplicationInfo(runningApp.get(1).processName, 0).loadIcon(packageManager)).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            appIcon = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // OK ボタンのリスナ
        dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), appName + " + " + affection, Toast.LENGTH_SHORT).show();

                Toast.makeText(getActivity(), appPackage, Toast.LENGTH_SHORT).show();

                AffectionBean bean = new AffectionBean();
                bean.setAppName(appName);
                bean.setAppPackage(appPackage);
                bean.setAffections(affection);
                bean.setAppIcon(appIcon);

                new UploadAsyncTask(getActivity()).execute(bean);

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

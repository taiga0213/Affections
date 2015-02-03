package jp.taiga0213.affections;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final int REQUEST_C = 404;
    private final int REQUEST_K = 1001;
    private final int REQUEST_D = 2001;
    private final int REQUEST_A = 3001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MyService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setEmptyView((LinearLayout)findViewById(R.id.empty));
        new DownloadAsyncTask(this,listView).execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendNotification() {

        Intent kiintent = new Intent(this, DialogActivity.class);
        kiintent.putExtra("af", "喜");
        PendingIntent ki = PendingIntent.getActivity(this, REQUEST_K, kiintent, 0);

        Intent dintent = new Intent(this, DialogActivity.class);
        dintent.putExtra("af", "怒");
        PendingIntent d = PendingIntent.getActivity(this, REQUEST_D, dintent, 0);

        Intent aiintent = new Intent(this, DialogActivity.class);
        aiintent.putExtra("af", "哀");
        PendingIntent ai = PendingIntent.getActivity(this, REQUEST_A, aiintent, 0);

        // LargeIcon の Bitmap を生成
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        // NotificationBuilderを作成
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext());
        // ステータスバーに表示されるテキスト
        builder.setTicker("Ticker");
        // アイコン
        builder.setSmallIcon(R.drawable.ic_launcher);
        // Notificationを開いたときに表示されるタイトル
        builder.setContentTitle("感情を共有");
        // Notificationを開いたときに表示されるサブタイトル
        builder.setContentText("あなたの今の感情は？");
        // Notificationを開いたときに表示されるアイコン
        builder.setLargeIcon(largeIcon);
        //アクション
        builder.addAction(R.drawable.ic_launcher, "喜", ki);
        builder.addAction(R.drawable.ic_launcher, "怒", d);
        builder.addAction(R.drawable.ic_launcher, "哀", ai);

        Notification notification = builder.build();

        notification.flags = Notification.FLAG_ONGOING_EVENT;


        // NotificationManagerを取得
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        // Notificationを作成して通知
        manager.notify(REQUEST_C, notification);
    }
}

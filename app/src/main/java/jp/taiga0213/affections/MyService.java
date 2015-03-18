package jp.taiga0213.affections;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;

public class MyService extends Service {

    private static final HashMap<Integer, Integer> affectionIcon = new HashMap<Integer, Integer> ();
    private static final HashMap<Integer, PendingIntent> affectionIntent = new HashMap<Integer, PendingIntent> ();
    private final int REQUEST_C = 404;
    private final int REQUEST_K = 1001;
    private final int REQUEST_D = 2001;
    private final int REQUEST_A = 3001;
    NotificationManager manager;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

        affectionIcon.put(REQUEST_K, R.drawable.img1001);
        affectionIcon.put(REQUEST_D, R.drawable.img2001);
        affectionIcon.put(REQUEST_A, R.drawable.img3001);

        affectionIntent.put(REQUEST_K, ki);
        affectionIntent.put(REQUEST_D, d);
        affectionIntent.put(REQUEST_A, ai);


        //アクション
        builder.addAction(affectionIcon.get(REQUEST_K), null, affectionIntent.get(REQUEST_K));
        builder.addAction(affectionIcon.get(REQUEST_D), null, affectionIntent.get(REQUEST_D));
        builder.addAction(affectionIcon.get(REQUEST_A), null, affectionIntent.get(REQUEST_A));


        Notification notification = builder.build();

        notification.flags = Notification.FLAG_ONGOING_EVENT;


        // NotificationManagerを取得
        manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        // Notificationを作成して通知
        manager.notify(REQUEST_C, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        manager.cancelAll();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

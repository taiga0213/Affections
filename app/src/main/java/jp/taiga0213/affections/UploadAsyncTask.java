package jp.taiga0213.affections;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import jp.taiga0213.beans.AffectionBean;

/**
 * Created by feapar on 2015/02/01.
 */
public class UploadAsyncTask
        extends AsyncTask<AffectionBean, Integer, String> {

    ProgressDialog dialog;
    Context context;

    public UploadAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(AffectionBean... params) {

        HttpClient client = new DefaultHttpClient();
        String str = "";
        String url = "http://192.168.43.94:8080/AffectionsServer/AffectionServer";//テザリング
//        String url = "http://192.168.0.2:8080/Affections/AffectionServer";//家

        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.setCharset(Charset.forName("UTF-8"));
        try {
            // 画像をセット
            // 第一引数：パラメータ名
            // 第二引数：画像データ
            // 第三引数：画像のタイプ。jpegかpngかは自由
            // 第四引数：画像ファイル名。ホントはContentProvider経由とかで取って来るべきなんだろうけど、今回は見えない部分なのでパス
            entity.addBinaryBody("avater", params[0].getAppIcon(), ContentType.create("image/png"), params[0].getAppPackage() + ".png");

            // 画像以外のデータを送る場合はaddTextBodyを使う
            ContentType textContentType = ContentType.create("application/json", "UTF-8");
            entity.addTextBody("app_name", params[0].getAppName(), textContentType);
            entity.addTextBody("app_package", params[0].getAppPackage(), textContentType);
            entity.addTextBody("affections", params[0].getAffections(), textContentType);

            HttpPost post = new HttpPost(url);
            post.setEntity(entity.build());

            HttpResponse httpResponse = client.execute(post);
            str = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

            Log.i("HTTP status Line", httpResponse.getStatusLine().toString());
            Log.i("HTTP response", new String(str));
//            Log.i("HTTP response", json.toString());
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    protected void onPostExecute(String result) {
//        if(dialog != null){
//            dialog.dismiss();
//        }
        Log.i("HTTP", "END");
    }

    @Override
    protected void onPreExecute() {
//        dialog = new ProgressDialog(context);
//        dialog.setTitle("Please wait");
//        dialog.setMessage("Uploading...");
//
//        dialog.show();
        Log.i("HTTP", "START");
    }
}

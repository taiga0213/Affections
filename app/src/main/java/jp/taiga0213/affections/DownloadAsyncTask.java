package jp.taiga0213.affections;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.taiga0213.beans.AffectionBean;

/**
 * Created by Taiga on 2015/02/04.
 */
public class DownloadAsyncTask extends AsyncTask<String, Integer, List<AffectionBean>> {
    ProgressDialog dialog;
    Context context;
    ListView listView;

    public DownloadAsyncTask(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
//        dialog = new ProgressDialog(context);
//        dialog.setTitle("Please wait");
//        dialog.setMessage("Uploading...");
//
//        dialog.show();
    }

    @Override
    protected List<AffectionBean> doInBackground(String[] params) {

        HttpClient client = new DefaultHttpClient();
        String str = "";
        String url = "http://192.168.43.94:8080/AffectionsServer/AffectionServer";//テザリング
//        String url = "http://192.168.0.2:8080/Affections/AffectionServer";//家

        try {
            HttpGet get = new HttpGet(url);
            HttpResponse httpResponse = client.execute(get);
            int status = httpResponse.getStatusLine().getStatusCode();
            if(status==200) {
                str = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

                JSONArray json = new JSONArray(str);

                ArrayList<AffectionBean> affectionBeans = new ArrayList<AffectionBean>();

                for (int i = 0; i < json.length(); i++) {
                    AffectionBean affectionBean = new AffectionBean();
                    JSONObject jsonObject = json.getJSONObject(i);
                    affectionBean.setId(jsonObject.getInt("id"));
                    affectionBean.setAppName(jsonObject.getString("appName"));
                    affectionBean.setAppPackage(jsonObject.getString("appPackage"));
                    affectionBean.setAffections(jsonObject.getString("affections"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                affectionBean.setDate((Date) dateFormat.parse(jsonObject.getString("date")));
                    byte[] decode = Base64.decode(jsonObject.getString("appIcon"), Base64.DEFAULT);
                    affectionBean.setAppIcon(decode);

                    affectionBeans.add(affectionBean);

                }

                Log.i("HTTP status Line", httpResponse.getStatusLine().toString());
                Log.i("HTTP response", new String(str));
                Log.i("HTTP json", json.toString());

                return affectionBeans;
            }else{
                Log.d("status",String.valueOf(status));
            }
//        } catch (ClientProtocolException e) {
            //throw new RuntimeException(e);
//      } catch (IOException e) {
            //throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<AffectionBean> result) {
//        if (dialog != null) {
//            dialog.dismiss();
//        }
        if (result!=null) {
            AffectionsAdapter affectionsAdapter = new AffectionsAdapter(context, 0, result);
            listView.setAdapter(affectionsAdapter);
        }
    }
}

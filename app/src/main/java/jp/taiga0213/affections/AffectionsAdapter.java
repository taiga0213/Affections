package jp.taiga0213.affections;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import jp.taiga0213.beans.AffectionBean;
import jp.taiga0213.map.AffectionMap;

/**
 * Created by Taiga on 2015/02/04.
 */
public class AffectionsAdapter extends ArrayAdapter<AffectionBean> {

    LayoutInflater layoutInflater;

    public AffectionsAdapter(Context context, int resource, List<AffectionBean> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AffectionBean bean = (AffectionBean) getItem(position);

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.layout_affections_adapter, null);
        }

        ImageView appIcon = (ImageView) convertView.findViewById(R.id.appIcon);
        ImageView affectionIcon = (ImageView)convertView.findViewById(R.id.affectionIcon);
        TextView appName = (TextView) convertView.findViewById(R.id.appName);
        TextView appPackage = (TextView) convertView.findViewById(R.id.appPackage);
        TextView affections = (TextView) convertView.findViewById(R.id.affections);

        Bitmap icon = null;
        byte[] bytes = bean.getAppIcon(); //ここに画像データが入っているものとする
        if (bytes != null) {
            icon = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            appIcon.setImageBitmap(icon);
        }


        AffectionMap ImageMap = new AffectionMap();
        HashMap<String,Integer> affectionMap = ImageMap.getImageMap();

        affectionIcon.setImageResource(affectionMap.get(bean.getAffections()));

        appName.setText(bean.getAppName());
        appPackage.setText(bean.getAppPackage());
        affections.setText(bean.getAffections());

        return convertView;
    }
}

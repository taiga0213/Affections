package jp.taiga0213.map;

import java.util.HashMap;

import jp.taiga0213.affections.R;

/**
 * Created by Taiga on 2015/04/27.
 */
public class AffectionMap {
    public HashMap<String,Integer> getImageMap(){

        HashMap<String,Integer> affectionMap = new HashMap<String,Integer>();
        affectionMap.put("喜", R.drawable.img1001);
        affectionMap.put("怒",R.drawable.img2001);
        affectionMap.put("哀",R.drawable.img3001);

        return affectionMap;
    }
}

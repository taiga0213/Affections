package jp.taiga0213.beans;

import java.util.Date;

/**
 * Created by feapar on 2015/02/01.
 */
public class AffectionBean {

    private int id;
    private String appName;
    private String appPackage;
    private String affections;
    private byte[] appIcon;
    private Date date;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAffections() {
        return affections;
    }

    public void setAffections(String affections) {
        this.affections = affections;
    }

    public byte[] getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(byte[] appIcon) {
        this.appIcon = appIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

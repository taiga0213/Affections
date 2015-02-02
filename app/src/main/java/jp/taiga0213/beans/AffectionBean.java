package jp.taiga0213.beans;

/**
 * Created by feapar on 2015/02/01.
 */
public class AffectionBean {
    private String appName;
    private String appPackage;
    private String affection;
    private byte[] appIcon;

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

    public String getAffection() {
        return affection;
    }

    public void setAffection(String affection) {
        this.affection = affection;
    }

    public byte[] getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(byte[] appIcon) {
        this.appIcon = appIcon;
    }
}

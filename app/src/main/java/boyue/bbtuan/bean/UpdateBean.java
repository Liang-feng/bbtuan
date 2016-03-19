package boyue.bbtuan.bean;


import java.io.Serializable;

public class UpdateBean implements Serializable{
    public  String newVersion;
    public  String newVerTime;
    public  String newVerCap;
    public  String newVerMessage;
    public  String newApkPath;

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getNewVerTime() {
        return newVerTime;
    }

    public void setNewVerTime(String newVerTime) {
        this.newVerTime = newVerTime;
    }

    public String getNewVerCap() {
        return newVerCap;
    }

    public void setNewVerCap(String newVerCap) {
        this.newVerCap = newVerCap;
    }

    public String getNewApkPath() {
        return newApkPath;
    }

    public void setNewApkPath(String newApkPath) {
        this.newApkPath = newApkPath;
    }

    public String getNewVerMessage() {
        return newVerMessage;
    }

    public void setNewVerMessage(String newVerMessage) {
        this.newVerMessage = newVerMessage;
    }
}

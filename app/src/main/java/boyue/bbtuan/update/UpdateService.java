package boyue.bbtuan.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import boyue.bbtuan.R;
import boyue.bbtuan.tabmain.TabMainActivity;

public class UpdateService extends Service {

    // 标题
    private int titleId = 0;
    private String apkpath="";
    // 文件存储
    private File updateDir = null;
    private File updateFile = null;

    // 通知栏
    private NotificationManager updateNotificationManager = null;
    private static Notification.Builder builder=null;
    private Notification updateNotification;
    // 通知栏跳转Intent
    private Intent updateIntent = null;
    private PendingIntent updatePendingIntent = null;
    private Context context;
    // 在onStartCommand()方法中准备相关的下载工作：
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取传值
        titleId = intent.getIntExtra("titleId", 0);
        apkpath=intent.getStringExtra("apkpath");
        // 创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            updateDir = new File(Environment.getExternalStorageDirectory(),
                    "/BoYue/");
            updateFile = new File(updateDir.getPath(), getResources()
                    .getString(titleId) + ".apk");
        }

        updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder= new Notification.Builder(getApplicationContext());


        // 设置下载过程中，点击通知栏，回到主界面
        updateIntent = new Intent(this, TabMainActivity.class);
        updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent,
                0);
        // 设置通知栏显示内容
        builder.setAutoCancel(true);
        builder.setTicker("开始下载");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(updatePendingIntent);
        builder.setContentTitle("抱抱团");
        builder.setContentText("0%");
        updateNotification=builder.getNotification();
        // 发出通知
        updateNotificationManager.notify(0, updateNotification);
        // 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
        new Thread(new updateRunnable()).start();// 这个是下载的重点，是下载的过程

        return super.onStartCommand(intent, flags, startId);
    }

    // 定义2个常量来表示下载状态：

    // 下载状态
    private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;

    // 根据下载状态处理主线程：

    private Handler updateHandler = new MyHandler(this);

    static class MyHandler extends Handler
    {
        private UpdateService mService = null;

        public MyHandler(UpdateService service)
        {
            this.mService = service;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    // 点击安装PendingIntent
                    Uri uri = Uri.fromFile(mService.updateFile);
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(uri,
                            "application/vnd.android.package-archive");
                    mService.updatePendingIntent = PendingIntent.getActivity(
                            mService, 0, installIntent, 0);
                    builder.setAutoCancel(true);
                    builder.setTicker("下载成功");
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setWhen(System.currentTimeMillis());
                    builder.setContentIntent(mService.updatePendingIntent);
                    builder.setContentTitle("抱抱团");
                    builder.setContentText("下载完成,点击安装");
                    mService.updateNotification=builder.getNotification();
                    mService.updateNotification.defaults = Notification.DEFAULT_SOUND;// 铃声提醒
                    mService.updateNotificationManager.notify(0, mService.updateNotification);
                    // 停止服务
                    mService.stopSelf();
                    break;
                case DOWNLOAD_FAIL:
                    // 下载失败
                    builder.setAutoCancel(true);
                    builder.setTicker("下载失败");
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setWhen(System.currentTimeMillis());
                    builder.setContentIntent(mService.updatePendingIntent);
                    builder.setContentTitle("抱抱团");
                    builder.setContentText("下载失败!");
                    mService.updateNotification=builder.getNotification();
                    mService.updateNotificationManager.notify(0, mService.updateNotification);
                    break;
                default:
                    mService.stopSelf();
            }
        }
    }

    // 创建updateRunnable类的真正实现：
    class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            message.what = DOWNLOAD_COMPLETE;
            try {
                // 增加权限;
                if (!updateDir.exists()) {
                    updateDir.mkdirs();
                }
                if (!updateFile.exists()) {
                    updateFile.createNewFile();
                }
                // 增加权限，为了防止数据溢出使用long整型变量;
                long downloadSize = downloadUpdateFile(
                        apkpath,
                        updateFile);
                if (downloadSize > 0) {
                    // 下载成功
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                // 下载失败
                updateHandler.sendMessage(message);
            }
        }
    }

    // 下载函数的实现有很多，我这里把代码贴出来，而且我们要在下载的时候通知用户下载进度：
    public long downloadUpdateFile(String downloadUrl, File saveFile)
            throws Exception {
        // 这样的下载代码很多，我就不做过多的说明
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection
                    .setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes="
                        + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;
            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                // 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
                if ((downloadCount == 0)
                        || (int) (totalSize * 100 / updateTotalSize) - 10 > downloadCount) {
                    downloadCount += 10;
                    builder.setAutoCancel(true);
                    builder.setTicker("正在下载");
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setWhen(System.currentTimeMillis());
                    builder.setContentIntent(updatePendingIntent);
                    builder.setContentTitle("抱抱团");
                    builder.setContentText((int) totalSize * 100 / updateTotalSize
                            + "%");
                   updateNotification=builder.getNotification();
                    updateNotificationManager.notify(0, updateNotification);
                }
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

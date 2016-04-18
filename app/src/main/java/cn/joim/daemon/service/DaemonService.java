package cn.joim.daemon.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.joim.daemon.controlller.CMDExecute;

public class DaemonService extends Service {

    private static final String FILE_NAME = "daemon";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("joim", "start DaemonService");

        runDaemonThread();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("joim", "daemon.c call DaemonService.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void runDaemonThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (!isDaemonExists()) {
                    saveDaemonResource();
                }
                setDaemonResourcePermission();
                CMDExecute mExecutor = new CMDExecute();

                String m_strResult = "";
                String DAEMON_FILE_PATH = getFilesDir()
                        .getAbsolutePath();

                String DAEMON_FILE_NAME = FILE_NAME;
                String arg[] = {DAEMON_FILE_PATH + "/" + DAEMON_FILE_NAME};
                try {
                    m_strResult = mExecutor.run(DaemonService.this, DaemonService.class, arg, DAEMON_FILE_PATH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("joim", "exe result:" + m_strResult);
            }
        }, "daemon-java").start();
    }


    private void saveDaemonResource() {

        AssetManager aManage = getAssets();
        String path = getFilesDir()
                .getAbsolutePath();   //data/data目录
        File file = new File(path + "/" + FILE_NAME);
        try {
            InputStream in = aManage.open("armeabi/" + FILE_NAME);  //从assets目录下复制
            FileOutputStream out = new FileOutputStream(file);
            int length = -1;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("joim", "copy success");
    }

    private void setDaemonResourcePermission() {
        String DAEMON_FILE_PATH = this.getFilesDir()
                .getAbsolutePath() + "/";

        String DAEMON_FILE_NAME = FILE_NAME;
        //711
        String cmdLine = "chmod 0755 " + DAEMON_FILE_PATH + DAEMON_FILE_NAME;
        try {
            Runtime.getRuntime().exec(cmdLine);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private boolean isDaemonExists() {
        try {
            String DAEMON_FILE_PATH = this.getFilesDir()
                    .getAbsolutePath() + "/";

            String DAEMON_FILE_NAME = FILE_NAME;
            File file = new File(DAEMON_FILE_PATH + DAEMON_FILE_NAME);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

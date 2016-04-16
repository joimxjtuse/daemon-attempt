package cn.joim.daemon.controlller;

import android.content.res.AssetManager;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {


    /**
     * 1. compile hello.c;
     * these articles may be help:
     * http://www.cnblogs.com/candycaicai/p/3282214.html;
     * http://corochann.com/build-executable-file-with-android-ndk-after-lollipop-android-api-21-388.html;
     * 2. Maybe, in order to run "hello", i should copy it into data/data/pack.. dir,
     * http://blog.csdn.net/wangzhiyu1980/article/details/16972937
     * http://cubie.cc/forum.php?mod=viewthread&tid=758
     * 3. run "hello" whith linux command, "./hello";
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String CPU_ABI = android.os.Build.CPU_ABI;
        String CPU_ABI2 = "none";
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) { // CPU_ABI2 since 2.2
            try {
                CPU_ABI2 = (String) android.os.Build.class.getDeclaredField("CPU_ABI2").get(null);
            } catch (Exception e) {
            }
        }
        Log.i("joim", "CPU_ABI:" + CPU_ABI + "; CPU_ABI2:" + CPU_ABI2);

        int index = -1;
        if ("x86".equals(CPU_ABI) || "x86".equals(CPU_ABI2)) {
            index = 3;
        } else if ("armeabi-v7a".equals(CPU_ABI) || "armeabi-v7a".equals(CPU_ABI2)) {
            index = 1;
        } else if ("armeabi".equals(CPU_ABI) || "armeabi".equals(CPU_ABI2)) {
            index = 0;
        } else if ("mips".equals(CPU_ABI) || "mips".equals(CPU_ABI2)) {
            index = 2;
        }

        Button btn = (Button) findViewById(R.id.btn_check_hello);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDaemonExists()) {
                    saveDaemonResource();
                }
                setDaemonResourcePermission();
                CMDExecute mExecutor = new CMDExecute();

                String m_strResult = "";
                String DAEMON_FILE_PATH = getFilesDir()
                        .getAbsolutePath();

                String DAEMON_FILE_NAME = "hello";
                String arg[] = {DAEMON_FILE_PATH + "/" + DAEMON_FILE_NAME};
                try {
                    m_strResult = mExecutor.run(arg, DAEMON_FILE_PATH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("joim","exe result:" + m_strResult);

            }
        });
    }


    private void saveDaemonResource() {

        AssetManager aManage = getAssets();
        String path = getFilesDir()
                .getAbsolutePath();   //data/data目录
        File file = new File(path + "/" + "hello");
        try {
            InputStream in = aManage.open("armeabi/hello");  //从assets目录下复制
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

        String DAEMON_FILE_NAME = "hello";
        String cmdLine = "chmod 711 " + DAEMON_FILE_PATH + DAEMON_FILE_NAME;
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

            String DAEMON_FILE_NAME = "hello";
            File file = new File(DAEMON_FILE_PATH + DAEMON_FILE_NAME);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

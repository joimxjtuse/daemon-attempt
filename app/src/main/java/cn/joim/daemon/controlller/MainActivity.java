package cn.joim.daemon.controlller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import cn.joim.daemon.service.DaemonService;


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
     * <p/>
     * <p/>
     * 1.用户启动ifeng-news-app 后，运行 DaemonService；
     * 2.DaemonService使用exec()运行daemon.c代码;
     * 3.daemon.c里面做两件事情：
     * 1)fork一个子进程；
     * 父进程什么也不做，子进程负责重启push-service.
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
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        startService(new Intent(this, DaemonService.class));
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

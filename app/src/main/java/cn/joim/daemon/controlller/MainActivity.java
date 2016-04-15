package cn.joim.daemon.controlller;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    /**
     * 1. compile hello.c;
     * <p/>
     * <p/>
     * <p/>
     * 2
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

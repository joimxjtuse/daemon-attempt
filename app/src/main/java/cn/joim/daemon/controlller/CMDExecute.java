package cn.joim.daemon.controlller;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Joim-PC on 2016/4/16.
 */
public class CMDExecute {

    public synchronized String run(String[] cmd, String workdirectory) throws Exception {
        String result = "";

        try {
            if (workdirectory != null) {
                Process process = Runtime.getRuntime().exec("./" + workdirectory + "/" + "hello");
                InputStream in = process.getInputStream();
                byte[] re = new byte[1024];

                while (in.read(re) != -1) {
                    System.out.println(new String(re));
                    result = result + new String(re);
                }
                in.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

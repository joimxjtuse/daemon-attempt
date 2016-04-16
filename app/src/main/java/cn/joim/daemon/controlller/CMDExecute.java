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
            ProcessBuilder builder = new ProcessBuilder(cmd);
            if (workdirectory != null) {
                builder.directory(new File(workdirectory));
                builder.redirectErrorStream(true);

                Process process = builder.start();
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

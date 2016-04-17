package cn.joim.daemon.controlller;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Joim-PC on 2016/4/16.
 */
public class CMDExecute {

    public synchronized String run(String[] cmd, String workdirectory) throws Exception {
        String result = "";
        boolean runSuccess = false;

        try {
            if (workdirectory != null) {
                Runtime.
                        getRuntime().exec( workdirectory + "/" + "daemon" +
                        "  -p cn.joim.daemon.controlller -s com.ifeng.ipush.client.service.PushService -t 120").waitFor();
//                Process process = Runtime.
//                        getRuntime().exec("./" + workdirectory + "/" + "daemon" +
//                        "  -p com.coolerfall.daemon.sample -s com.coolerfall.service.DaemonService -t 120");
//                InputStream in = process.getInputStream();
//                byte[] re = new byte[1024];
//
//                while (in.read(re) != -1) {
//                    result = result + new String(re);
//                }
//                in.close();
                runSuccess = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.valueOf(runSuccess);
    }
}

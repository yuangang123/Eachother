package util;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by 袁刚 on 2017/5/10.
 */

public class SendToServer {

    public static void send(String ip,int port,Object object){
        try {
            Log.d("yuangang", "send方法激活 ");
            Socket socket = new Socket(ip,port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

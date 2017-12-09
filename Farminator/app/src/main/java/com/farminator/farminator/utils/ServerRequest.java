package com.farminator.farminator.utils;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public class ServerRequest extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        String answer="";
        try {
            Socket s = null;
            s = new Socket("192.168.43.62", 4300);
            DataOutputStream out = null;
            out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(strings[0]);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            answer = inFromServer.readLine();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

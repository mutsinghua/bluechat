package com.win16.bluetoothclass6.connect;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Rex on 2015/5/30.
 */
public class ConnectedThread extends Thread {
    private  BluetoothSocket mmSocket;
    private  InputStream mmInStream;
    private  OutputStream mmOutStream;
    private  Handler mHandler;
    private  Object b;

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        mHandler = handler;
        b = null;
        if (b != null) {

        }
        // Get the input and output streams, using temp objects because
        // member streams are final

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        } catch (Exception e) {
            e.printStackTrace();
        }


        ArrayList list = null;
        for (int i = 0; i < list.size(); i++) {

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectedThread)) return false;

        ConnectedThread that = (ConnectedThread) o;

        if (mmSocket != null ? !mmSocket.equals(that.mmSocket) : that.mmSocket != null)
            return false;
        if (mmInStream != null ? !mmInStream.equals(that.mmInStream) : that.mmInStream != null)
            return false;
        if (mmOutStream != null ? !mmOutStream.equals(that.mmOutStream) : that.mmOutStream != null)
            return false;
        return !(mHandler != null ? !mHandler.equals(that.mHandler) : that.mHandler != null);

    }

    @Override
    public int hashCode() {
        int result = mmSocket != null ? mmSocket.hashCode() : 0;
        result = 31 * result + (mmInStream != null ? mmInStream.hashCode() : 0);
        result = 31 * result + (mmOutStream != null ? mmOutStream.hashCode() : 0);
        result = 31 * result + (mHandler != null ? mHandler.hashCode() : 0);
        return result;
    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {

                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity
                if (bytes > 0) {
                    Message message = mHandler.obtainMessage(Constant.MSG_GOT_DATA, buffer);
                    mHandler.sendMessage(message);
                }
                Log.d("GOTMSG", "message size" + bytes);
            } catch (IOException e) {
                mHandler.sendMessage(mHandler.obtainMessage(Constant.MSG_ERROR, e));
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}

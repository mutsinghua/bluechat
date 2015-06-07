package com.win16.bluetoothclass4.connect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Rex on 2015/6/5.
 */
public class ChatControl {

    /**
     * 网络协议的处理函数
     */
    private static class ChatProtocol implements ProtocolHandler<String> {

        private static final String CHARSET_NAME = "utf-8";

        @Override
        public byte[] encodePackage(String data) {
            if( data == null) {
                return new byte[0];
            }
            else {
                try {
                    return data.getBytes(CHARSET_NAME);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return new byte[0];
                }
            }
        }

        @Override
        public String decodePackage(byte[] netData) {
            if( netData == null) {
                return "";
            }
            try {
                return new String(netData, CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * 协议处理
     */
    private ChatProtocol mProtocol = new ChatProtocol();

    /**
     * 发送数据线程
     */
    private ConnectedThread mConnectedThread;


    public void startChatWith(BluetoothDevice device) {

    }

    public void waitingForFriends(BluetoothAdapter adapter) {

    }

    /**
     * 发出消息
     * @param msg
     */
    public void sendMessage(String msg) {
        if(mConnectedThread != null) {
            mConnectedThread.write(mProtocol.encodePackage(msg));
        }
    }

    /**
     * 单例的写法，优点可以自己想想
     */
    public static class ChatControlHolder {
        private static ChatControl mInstance = new ChatControl();
    }

    public static ChatControl getInstance() {
        return ChatControlHolder.mInstance;
    }

    public interface MessageReceiver {
        void onReceiverMessage();
    }
}

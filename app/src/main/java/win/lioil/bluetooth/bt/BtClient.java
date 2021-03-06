package win.lioil.bluetooth.bt;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import win.lioil.bluetooth.util.Util;

/**
 * 客户端，与服务端建立长连接
 */
public class BtClient extends BtBase {
    public BtClient(Listener listener) {
        super(listener);
    }

    /**
     * 与远端设备建立长连接
     *
     * @param dev 远端设备
     */
    public void connect(BluetoothDevice dev) {
        close();
        try {
            // final BluetoothSocket socket = dev.createRfcommSocketToServiceRecord(SPP_UUID); //加密传输，会自动执行配对(系统UI弹出配对码)
            final BluetoothSocket socket = dev.createInsecureRfcommSocketToServiceRecord(SPP_UUID); //明文传输，无需配对
            // 开启子线程
            Util.EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    loopRead(socket); //循环读取
                }
            });
        } catch (Throwable e) {
            close();
        }
    }
}
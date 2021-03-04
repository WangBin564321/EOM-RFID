package com.example.eom_rfid.ui.activity.write;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.lifecycle.Observer;

import com.example.eom_rfid.BR;
import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseActivity;
import com.example.eom_rfid.base.BaseApplication;
import com.example.eom_rfid.bean.entity.ScanRfidResult;
import com.example.eom_rfid.databinding.ActivityWriteBinding;
import com.example.eom_rfid.scan.ScanThread;
import com.example.eom_rfid.utils.ToolUtil;
import com.example.eom_rfid.utils.Util;
import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import cn.pda.serialport.Tools;

import static com.example.eom_rfid.utils.Constants.HTAG;

public class WriteActivity extends BaseActivity<ActivityWriteBinding, WriteViewModel> implements View.OnClickListener {
    private boolean isStart = false;
    private List<String> epcSingleList;
    private UHFRManager uhfrManager;

    private ScanThread scanThread;
    List<String> scanList;

    @Override
    protected void onStart() {
        super.onStart();

        try {
            scanThread = new ScanThread(mHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanThread.start();
        uhfrManager = UHFRManager.getInstance();
        uhfrManager.setPower(15, 15);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == ScanThread.SCAN) {
                byte[] dataBytes = msg.getData().getByteArray("dataBytes");
                if (dataBytes == null || dataBytes.length == 0) {
                    scanThread.scan();
                    return;
                }
                String data = "";
                data = new String(dataBytes, 0, dataBytes.length);
                Log.e("TAG", "data = " + data);
                if (!TextUtils.isEmpty(data)) {
                    scanList.add(data);
                    Util.play(1, 0);
                    if (scanList.size() == 1) {
                        scanThread.stopScan();
                        if (data.length() > 17) {
                            viewModel.rfid.setValue(data.substring(0, 17));
                            String finalData = data;
                            viewModel.getSuccess.observe(WriteActivity.this, new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {
                                    if (aBoolean) {
                                        Log.d(HTAG, "onChanged: =============>" + viewModel.num.getValue());
                                        if (!TextUtils.isEmpty(viewModel.num.getValue()))
                                            viewModel.rfidCode.setValue(processCode(finalData.substring(0, 17)) + plusZone(viewModel.num.getValue()));
                                    }
                                }
                            });
                            viewModel.getSingleInfo(data.substring(0, 17));
                        }

                    }
                }
            }
        }
    };

    private String processCode(String rfidCode) {
        StringBuilder stringBuilder = new StringBuilder();
        String head = rfidCode.substring(0, 2);
        char[] chars = head.toCharArray();
        stringBuilder.append((int) chars[0]);
        stringBuilder.append((int) chars[1]);
        stringBuilder.append(rfidCode.substring(2, 17));
        return stringBuilder.toString();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        uhfrManager.asyncStopReading();
        handler1.removeCallbacks(runnable_MainActivity);
        unregisterReceiver(keyReceiver);
//        if (uhfrManager != null) {
//            //close uhf module
//            uhfrManager.close();
//            uhfrManager = null;
//        }
    }

    @Override
    protected void initData() {

    }

    //key receiver
    private long startTime = 0;
    private boolean keyUpFalg = true;
    private BroadcastReceiver keyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int keyCode = intent.getIntExtra("keyCode", 0);
            if (keyCode == 0) {//H941
                keyCode = intent.getIntExtra("keycode", 0);
            }
            Log.e("key ", "keyCode = " + keyCode);
            boolean keyDown = intent.getBooleanExtra("keydown", false);
            Log.e("key ", "down = " + keyDown);
            if (keyUpFalg && keyDown && System.currentTimeMillis() - startTime > 500) {
                keyUpFalg = false;
                startTime = System.currentTimeMillis();
                if ((//keyCode == KeyEvent.KEYCODE_F1 || keyCode == KeyEvent.KEYCODE_F2
                        keyCode == KeyEvent.KEYCODE_F3 ||
//                                 keyCode == KeyEvent.KEYCODE_F4 ||
                                keyCode == KeyEvent.KEYCODE_F4)) {
                    Log.e("key ", "inventory.... ");
                    onClick(dataBinding.btnRead);
                }
                return;
            } else if (keyDown) {
                startTime = System.currentTimeMillis();
            } else {
                keyUpFalg = true;
            }
        }
    };

    public void isRead() {
        if (uhfrManager == null) {
            return;
        }
        if (!isStart) {
//            uhfrManager.asyncStartReading();
//            uhfrManager.setGen2session(false);
            //写完不读必加
            uhfrManager.setCancleInventoryFilter();
            handler1.postDelayed(runnable_MainActivity, 0);
            isStart = true;
        } else {
            handler1.removeCallbacks(runnable_MainActivity);
            uhfrManager.stopTagInventory();
            isStart = false;
        }
    }

    private Runnable runnable_MainActivity = new Runnable() {
        @Override
        public void run() {
            List<Reader.TAGINFO> list1;
            list1 = uhfrManager.tagInventoryByTimer((short) 50);
            String data;
            if (list1 != null && list1.size() > 0) {
                Log.e(HTAG, list1.size() + "");
                Util.play(1, 0);
                for (Reader.TAGINFO tfs : list1) {
                    byte[] epcdata = tfs.EpcId;
                    data = Tools.Bytes2HexString(epcdata, epcdata.length);
                    int rssi = tfs.RSSI;
                    Message msg = new Message();
                    msg.what = 1;
                    Bundle b = new Bundle();
                    Log.d(HTAG, "run==========>: " + data);
                    b.putString("data", data);
                    b.putString("rssi", rssi + "");
                    msg.setData(b);
                    handler1.sendMessage(msg);
                }
            }
        }
    };

    //handler
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String epc1 = msg.getData().getString("data");
                    viewModel.rfid.setValue(processRfid(epc1));
                    viewModel.rfidCode.setValue(epc1);
                    if (epc1 == null || epc1.length() == 0) {
                        return;
                    }
                    epcSingleList.add(epc1);
                    if (epcSingleList.size() < 2) {
                        isRead();
                        viewModel.getSingleInfo(processRfid(epc1));
                    }
                    break;
            }
        }
    };

    private String processRfid(String rfidCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char) Integer.parseInt(rfidCode.substring(0, 2)));
        stringBuilder.append((char) Integer.parseInt(rfidCode.substring(2, 4)));
        stringBuilder.append(rfidCode.substring(4, 19));
        return stringBuilder.toString();
    }

    //    /**
//     * write tag memory bank
//     */
    private void writeData() {
        String startAddrStr = "7";
        String accessStr = "00000000";
        String writeStr = plusZone(viewModel.num.getValue().trim());
        if (startAddrStr == null || startAddrStr.length() == 0) {
//            showToast(getResources().getString(R.string.please_start_addr));
            return;
        }
        if (accessStr == null || accessStr.length() != 8) {
//            showToast(getResources().getString(R.string.please_access_password));
            return;
        }
        if (writeStr == null || writeStr.length() == 0) {
//            showToast(getResources().getString(R.string.please_write_data));
            return;
        }
        byte[] writeDataBytes = null;
        try {
            writeDataBytes = Tools.HexString2Bytes(writeStr);
            if (writeDataBytes.length % 2 != 0) {
//                showToast(getResources().getString(R.string.please_write_data_type_error));
                return;
            }
        } catch (Exception e) {
//            showToast(getResources().getString(R.string.please_write_data_type_error));
            return;
        }
        int addr = Integer.valueOf(startAddrStr);
        Log.d(HTAG, "writeData: =============>" + viewModel.rfidCode.getValue());
        byte[] epcBytes = Tools.HexString2Bytes(viewModel.rfidCode.getValue());
        byte[] accessBytes = Tools.HexString2Bytes(accessStr);

        Reader.READER_ERR er;
        //change epc:
//			er = MainActivity.mUhfrManager.writeTagEPCByFilter(writeDataBytes, accessBytes,(short)1000, epcBytes,1, 2,true);
        //write data
        er = uhfrManager.writeTagDataByFilter((char) 1, addr, writeDataBytes, writeDataBytes.length, accessBytes, (short) 1000, epcBytes, 1, 2, true);

        if (er == Reader.READER_ERR.MT_OK_ERR) {
            viewModel.writeNum();
            viewModel.showToast("写入成功", 0);
        } else {
            viewModel.showToast("写入失败", 1);
        }
    }

    private String plusZone(String num) {
        StringBuilder stringBuilder = new StringBuilder();
        if (TextUtils.isEmpty(num)) {
            for (int i = 0; i < 12; i++) {
                stringBuilder.append("0");
            }
        } else {
            for (int i = num.length(); i < 12; i++) {
                stringBuilder.append("0");
            }
        }
        stringBuilder.append(num);
        return stringBuilder.toString();
    }

    @Override
    protected int initVariableId() {
        return BR.ViewModel;
    }

    @Override
    protected int setContentViewSrc(Bundle savedInstanceState) {
        return R.layout.activity_write;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read:
                epcSingleList = new ArrayList<>();
                isRead();
                break;
            case R.id.btn_write:
                writeData();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_scan:
                scanList = new ArrayList<>();
                scanThread.scan();
                break;
        }
    }
}

package com.example.eom_rfid.ui.activity.checkout;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eom_rfid.BR;
import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseActivity;
import com.example.eom_rfid.base.BaseDialog;
import com.example.eom_rfid.base.BaseRecyclerAdapter;
import com.example.eom_rfid.base.RecyclerViewHolder;
import com.example.eom_rfid.bean.body.CheckoutBatchBody;
import com.example.eom_rfid.bean.body.CheckoutBody;
import com.example.eom_rfid.bean.entity.CheckoutType;
import com.example.eom_rfid.bean.entity.ScanRfidResult;
import com.example.eom_rfid.bean.response.BillListResponse;
import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;
import com.example.eom_rfid.bean.response.TaskResponse;
import com.example.eom_rfid.databinding.ActivityCheckoutBinding;
import com.example.eom_rfid.scan.ScanThread;
import com.example.eom_rfid.ui.activity.check.result.ScanResultActivity;
import com.example.eom_rfid.utils.ToolUtil;
import com.example.eom_rfid.utils.Util;
import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.pda.serialport.Tools;

import static com.example.eom_rfid.utils.Constants.HTAG;


/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 19:16
 */
public class CheckoutActivity extends BaseActivity<ActivityCheckoutBinding, CheckoutViewModel> implements View.OnClickListener {

    private boolean isStart = false;
    private List<String> epcSingleList;
    private Set<String> epcSet = null; //store different EPC
    private List<ScanRfidResult.EpcDataModel> listEpc = null;//EPC list
    private Map<String, Integer> mapEpc = null; //store EPC position
    public static Set<String> mSetEpcs; //epc set ,epc list
    private ScanThread scanThread;
    List<String> scanList;

    private UHFRManager uhfrManager;

    SpinnerAdapter typeAdapter;

    SpinnerAdapter taskAdapter;

    ArrayAdapter<String> destroyCauseAdapter;
    String[] destroyCauseList = {"过期", "破损", "变质", "受污染", "其他"};

    List<CheckoutType> checkoutTypeList;


    private DatePickerDialog datePickerDialog;


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
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        unregisterReceiver(keyReceiver);
        uhfrManager.asyncStopReading();
        handler1.removeCallbacks(runnable_MainActivity);
//        unregisterReceiver(keyReceiver);
//        if (uhfrManager != null) {
//            //close uhf module
//            uhfrManager.close();
//            uhfrManager = null;
//        }
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
                }
                Util.play(1, 0);
                if (scanList.size() == 1) {
                    scanThread.stopScan();
                    if (data.length() > 17) {
                        viewModel.getSingleInfo(data.substring(0, 17));
                    }

                }
            }
        }
    };

    @Override
    protected void initData() {
        checkoutTypeList = new ArrayList<>();
        checkoutTypeList.add(new CheckoutType("配发", "0"));
        checkoutTypeList.add(new CheckoutType("销毁", "1"));
        checkoutTypeList.add(new CheckoutType("借用", "2"));
        checkoutTypeList.add(new CheckoutType("维修", "3"));
        checkoutTypeList.add(new CheckoutType("年审", "4"));
        checkoutTypeList.add(new CheckoutType("其他", "5"));

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                viewModel.returnDate.setValue(year + "-" + ToolUtil.monthPlusZero(month + 1) + "-" + ToolUtil.monthPlusZero(dayOfMonth));

            }
        }, ToolUtil.currentDate().get(0), ToolUtil.currentDate().get(1), ToolUtil.currentDate().get(2));

        typeAdapter = new ArrayAdapter<CheckoutType>(this, R.layout.simple_spinner_item, checkoutTypeList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                        null);
                CheckedTextView label = (CheckedTextView) view
                        .findViewById(R.id.rb_item);
                label.setText(checkoutTypeList.get(position).getTypeName());
                if (dataBinding.spinnerCheckoutType.getSelectedItemPosition() == position) {
                    label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                } else {
                    label.setBackgroundColor(getResources().getColor(
                            R.color.white));
                }
                return view;
            }
        };

        dataBinding.spinnerCheckoutType.setAdapter(typeAdapter);


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
                    onClick(dataBinding.tvCheckoutSingle);
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
            uhfrManager.setGen2session(false);
            handler1.postDelayed(runnable_MainActivity, 0);
            isStart = true;
        } else {
            uhfrManager.stopTagInventory();
            handler1.removeCallbacks(runnable_MainActivity);
            isStart = false;
        }
    }

    //handler
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String epc = msg.getData().getString("data");
                    String rssi = msg.getData().getString("rssi");
                    if (epc == null || epc.length() == 0) {
                        return;
                    }
                    int position;
                    if (epcSet == null) {//first
                        epcSet = new HashSet<String>();
                        listEpc = new ArrayList<ScanRfidResult.EpcDataModel>();
                        mapEpc = new HashMap<String, Integer>();
                        epcSet.add(epc);
                        mapEpc.put(epc, 0);
                        ScanRfidResult.EpcDataModel epcTag = new ScanRfidResult.EpcDataModel();
                        epcTag.setepc(epc);
                        epcTag.setrssi(rssi);
                        epcTag.setCount(1);
                        listEpc.add(epcTag);
                        mSetEpcs = epcSet;
                    } else {
                        if (epcSet.contains(epc)) {//set already exit
                            position = mapEpc.get(epc);
                            ScanRfidResult.EpcDataModel epcOld = listEpc.get(position);
                            epcOld.setrssi(rssi);
                            epcOld.setCount(epcOld.getCount() + 1);
                            listEpc.set(position, epcOld);
                        } else {
                            epcSet.add(epc);
                            mapEpc.put(epc, listEpc.size());
                            ScanRfidResult.EpcDataModel epcTag = new ScanRfidResult.EpcDataModel();
                            epcTag.setepc(epc);
                            epcTag.setrssi(rssi);
                            epcTag.setCount(1);
                            listEpc.add(epcTag);
                            mSetEpcs = epcSet;
                        }
                        viewModel.epcMutableLiveData.setValue(listEpc);
//                        viewModel.scanRfidResultMutableLiveData.getValue().setEpcDataModelList(listEpc);
//                        viewModel.scanRfidResultMutableLiveData.setValue(viewModel.scanRfidResultMutableLiveData.getValue());
                    }
                    break;
                case 1980:
                    break;
                case 2:
                    String epc1 = msg.getData().getString("data");
                    Log.d(HTAG, "handleMessage222: =============>" + epc1);
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
        if (rfidCode.length() >= 19) {
            try {
                stringBuilder.append((char) Integer.parseInt(rfidCode.substring(0, 2)));
                stringBuilder.append((char) Integer.parseInt(rfidCode.substring(2, 4)));
            } catch (Exception e) {
                viewModel.showToast(e.getMessage(), 1);
            }
            stringBuilder.append(rfidCode.substring(4, 19));
        } else {
            viewModel.showToast("RFID格式错误", 1);
        }
        return stringBuilder.toString();
    }


    private Runnable runnable_MainActivity = new Runnable() {

        @Override
        public void run() {
            List<Reader.TAGINFO> list1;
            list1 = uhfrManager.tagInventoryByTimer((short) 50);
            String data;
            handler1.sendEmptyMessage(1980);
            if (list1 != null && list1.size() > 0) {
                Log.e(HTAG, list1.size() + "");
                Util.play(1, 0);
                for (Reader.TAGINFO tfs : list1) {
                    byte[] epcdata = tfs.EpcId;
                    data = Tools.Bytes2HexString(epcdata, epcdata.length);
                    int rssi = tfs.RSSI;
                    Message msg = new Message();
                    if (type == 1) {
                        msg.what = 2;
                    } else if (type == 2) {
                        msg.what = 1;
                    }
                    Bundle b = new Bundle();
                    Log.d(HTAG, "run==========>: " + data);
                    b.putString("data", data);
                    b.putString("rssi", rssi + "");
                    msg.setData(b);
                    handler1.sendMessage(msg);
                }
            }
            handler1.postDelayed(runnable_MainActivity, 0);
        }
    };


    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();
        viewModel.getSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    BaseDialog baseDialog = new BaseDialog(CheckoutActivity.this, R.layout.dialog_check_single);
                    baseDialog.getWindow().setLayout(ToolUtil.dp2px(CheckoutActivity.this, 400),
                            ToolUtil.dp2px(CheckoutActivity.this, 300));
                    baseDialog.setTextViewContent(R.id.tv_name, viewModel.checkoutSingleInfoResponseMutableLiveData.getValue().getName());
                    baseDialog.setTextViewContent(R.id.tv_specs, viewModel.checkoutSingleInfoResponseMutableLiveData.getValue().getSpecs());
                    baseDialog.setTextViewContent(R.id.tv_num, viewModel.checkoutSingleInfoResponseMutableLiveData.getValue().getStockNum()
                            + viewModel.checkoutSingleInfoResponseMutableLiveData.getValue().getStockUnit());
                    baseDialog.setTextViewContent(R.id.tv_shelf, viewModel.checkoutSingleInfoResponseMutableLiveData.getValue().getShelfNumber());
                    baseDialog.getView(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            baseDialog.dismiss();
                        }
                    });
                    baseDialog.getView(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (viewModel.checkoutSingleInfoResponseMutableLiveData.getValue().getStockNum() == 0) {
                                viewModel.showToast("数量为0不能出库", 1);
                            } else {
                                CheckoutBody checkoutBody = new CheckoutBody();
                                List<CheckoutSingleInfoResponse.DataBean> dataBeans = new ArrayList<>();
                                dataBeans.add(viewModel.checkoutSingleInfoResponseMutableLiveData.getValue());
                                checkoutBody.setWmsOutWarehouseItemDTOList(dataBeans);
                                checkoutBody.setType(viewModel.checkoutType.getValue());
                                checkoutBody.setTaskId(viewModel.taskId.getValue());
                                checkoutBody.setDestroyCause(viewModel.destroyCause.getValue());
                                if (!TextUtils.isEmpty(viewModel.receiver.getValue()))
                                    checkoutBody.setReceiver(viewModel.receiver.getValue());
                                checkoutBody.setReturnDate(viewModel.returnDate.getValue());
                                checkoutBody.setRemark(viewModel.remark.getValue());
                                checkoutBody.setStatus(1);
                                viewModel.checkout(checkoutBody);

                            }
                            baseDialog.dismiss();
                        }
                    });

                    baseDialog.show();

                }
            }
        });

        dataBinding.spinnerCheckoutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        dataBinding.linearLayoutTask.setVisibility(View.VISIBLE);
                        dataBinding.linearLayoutCause.setVisibility(View.GONE);
                        dataBinding.linearLayoutReceiver.setVisibility(View.GONE);
                        dataBinding.linearLayoutReturnDate.setVisibility(View.GONE);
                        viewModel.checkoutType.setValue("0");
                        viewModel.destroyCause.setValue(null);
                        viewModel.remark.setValue(null);
                        viewModel.receiver.setValue(null);
                        viewModel.getTaskList();
                        break;
                    case 1:
                        dataBinding.linearLayoutTask.setVisibility(View.GONE);
                        dataBinding.linearLayoutCause.setVisibility(View.VISIBLE);
                        dataBinding.linearLayoutReceiver.setVisibility(View.GONE);
                        dataBinding.linearLayoutReturnDate.setVisibility(View.GONE);
                        viewModel.checkoutType.setValue("1");
                        viewModel.taskId.setValue(null);
                        viewModel.remark.setValue(null);
                        viewModel.receiver.setValue(null);

                        destroyCauseAdapter = new ArrayAdapter<String>(CheckoutActivity.this,
                                R.layout.simple_spinner_item, destroyCauseList) {
                            @Override
                            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                                        null);
                                CheckedTextView label = (CheckedTextView) view
                                        .findViewById(R.id.rb_item);
                                label.setText(destroyCauseList[position]);
                                if (dataBinding.spinnerCheckoutTask.getSelectedItemPosition() == position) {
                                    label.setBackgroundColor(getResources().getColor(R.color.colorBgGreen));
                                } else {
                                    label.setBackgroundColor(getResources().getColor(
                                            R.color.white));
                                }

                                return view;
                            }
                        };

                        dataBinding.spinnerCheckoutDestroyCause.setAdapter(destroyCauseAdapter);
                        break;
                    case 2:
                        dataBinding.linearLayoutTask.setVisibility(View.GONE);
                        dataBinding.linearLayoutCause.setVisibility(View.GONE);
                        dataBinding.linearLayoutReceiver.setVisibility(View.VISIBLE);
                        dataBinding.linearLayoutReturnDate.setVisibility(View.VISIBLE);
                        viewModel.checkoutType.setValue("2");
                        viewModel.taskId.setValue(null);
                        viewModel.destroyCause.setValue(null);
                        viewModel.remark.setValue(null);
                        viewModel.receiver.setValue(null);
                        break;
                    case 3:
                        dataBinding.linearLayoutTask.setVisibility(View.GONE);
                        dataBinding.linearLayoutCause.setVisibility(View.GONE);
                        dataBinding.linearLayoutReceiver.setVisibility(View.VISIBLE);
                        dataBinding.linearLayoutReturnDate.setVisibility(View.GONE);
                        viewModel.checkoutType.setValue("3");
                        viewModel.taskId.setValue(null);
                        viewModel.destroyCause.setValue(null);
                        viewModel.remark.setValue(null);
                        viewModel.receiver.setValue(null);
                        viewModel.returnDate.setValue(null);
                        break;
                    case 4:
                        dataBinding.linearLayoutTask.setVisibility(View.GONE);
                        dataBinding.linearLayoutCause.setVisibility(View.GONE);
                        dataBinding.linearLayoutReceiver.setVisibility(View.VISIBLE);
                        dataBinding.linearLayoutReturnDate.setVisibility(View.GONE);
                        viewModel.checkoutType.setValue("4");
                        viewModel.taskId.setValue(null);
                        viewModel.destroyCause.setValue(null);
                        viewModel.remark.setValue(null);
                        viewModel.receiver.setValue(null);
                        viewModel.returnDate.setValue(null);
                        break;
                    case 5:
                        dataBinding.linearLayoutTask.setVisibility(View.GONE);
                        dataBinding.linearLayoutCause.setVisibility(View.GONE);
                        dataBinding.linearLayoutReceiver.setVisibility(View.GONE);
                        dataBinding.linearLayoutReturnDate.setVisibility(View.GONE);
                        viewModel.checkoutType.setValue("5");
                        viewModel.taskId.setValue(null);
                        viewModel.destroyCause.setValue(null);
                        viewModel.remark.setValue(null);
                        viewModel.receiver.setValue(null);
                        viewModel.returnDate.setValue(null);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewModel.taskMutableLiveData.observe(this, new Observer<TaskResponse>() {
            @Override
            public void onChanged(TaskResponse taskResponse) {
                if (taskResponse.getList().size() > 0) {
                    taskAdapter = new ArrayAdapter<TaskResponse.ListBean>(CheckoutActivity.this,
                            R.layout.simple_spinner_item, taskResponse.getList()) {
                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                                    null);
                            CheckedTextView label = (CheckedTextView) view
                                    .findViewById(R.id.rb_item);
                            label.setText(taskResponse.getList().get(position).getName());

                            if (dataBinding.spinnerCheckoutTask.getSelectedItemPosition() == position) {
                                label.setBackgroundColor(getResources().getColor(R.color.colorBgGreen));
                            } else {
                                label.setBackgroundColor(getResources().getColor(
                                        R.color.white));
                            }

                            return view;
                        }
                    };

                    dataBinding.spinnerCheckoutTask.setAdapter(taskAdapter);
                }
            }
        });

        //保存当前选择任务
        dataBinding.spinnerCheckoutTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.taskId.setValue(viewModel.taskMutableLiveData.getValue().getList().get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataBinding.spinnerCheckoutDestroyCause.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.destroyCause.setValue(destroyCauseList[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    @Override
    protected int initVariableId() {
        return BR.ViewModel;
    }

    @Override
    protected int setContentViewSrc(Bundle savedInstanceState) {
        return R.layout.activity_checkout;
    }

    int type = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                scanList = new ArrayList<>();
                scanThread.scan();
                break;
            case R.id.iv_back:
                uhfrManager.setPower(33, 33);
                finish();
                break;
            case R.id.tv_checkout_single:
                uhfrManager.setPower(15, 15);
                epcSingleList = new ArrayList<>();
                type = 1;
                isRead();
                break;
            case R.id.tv_checkout_batch:
                startActivity(BatchCheckoutActivity.class);
                break;
            case R.id.tv_select_date:
                datePickerDialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uhfrManager != null) {
            //close uhf module
            uhfrManager.close();
            uhfrManager = null;
        }

    }
}

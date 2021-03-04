package com.example.eom_rfid.ui.activity.warehouse;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.eom_rfid.bean.body.CheckoutBody;
import com.example.eom_rfid.bean.entity.CheckType;
import com.example.eom_rfid.bean.entity.CheckoutType;
import com.example.eom_rfid.bean.response.BillListResponse;
import com.example.eom_rfid.bean.response.CheckoutSingleInfoResponse;
import com.example.eom_rfid.bean.response.DictTypeResponse;
import com.example.eom_rfid.databinding.ActivityWarehouseBinding;
import com.example.eom_rfid.scan.ScanThread;
import com.example.eom_rfid.ui.activity.checkout.CheckoutActivity;
import com.example.eom_rfid.ui.activity.drug_search.DrugSearchActivity;
import com.example.eom_rfid.utils.ToolUtil;
import com.example.eom_rfid.utils.Util;
import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pda.serialport.Tools;

import static com.example.eom_rfid.utils.Constants.HTAG;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 19:18
 */
public class WarehouseActivity extends BaseActivity<ActivityWarehouseBinding, WarehouseViewModel> implements View.OnClickListener {
    SpinnerAdapter warehouseAdapter;
    SpinnerAdapter zoneAdapter;
    SpinnerAdapter shelfAdapter;
    SpinnerAdapter typeAdapter;
    List<CheckType> checkTypeList;
    BaseRecyclerAdapter<BillListResponse.DataBean> beanBaseRecyclerAdapter;
    private UHFRManager uhfrManager;
    List<String> epcSingleList;
    boolean isStart = false;

    private ScanThread scanThread;
    List<String> scanList;
    @Override
    protected void onStart() {
        super.onStart();
        uhfrManager = UHFRManager.getInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter);

        try {
            scanThread = new ScanThread(mHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanThread.start();
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
                        viewModel.getWarehouseSingleInfo(data.substring(0, 17));
                    }

                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uhfrManager != null) {
            //close uhf module
            uhfrManager.close();
            uhfrManager = null;
        }

        unregisterReceiver(keyReceiver);
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
                    onClick(dataBinding.tvWarehouseSingle);
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
                        viewModel.getWarehouseSingleInfo(processRfid(epc1));
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

    @Override
    protected void initData() {
        viewModel.getWarehouse();
        viewModel.getZone();
        viewModel.getShelf();
        checkTypeList = new ArrayList<>();

        checkTypeList.add(new CheckType("采购", "0"));

        typeAdapter = new ArrayAdapter<CheckType>(this, R.layout.simple_spinner_item, checkTypeList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                        null);
                CheckedTextView label = (CheckedTextView) view
                        .findViewById(R.id.rb_item);
                label.setText(checkTypeList.get(position).getTypeName());
                if (dataBinding.spinnerType.getSelectedItemPosition() == position) {
                    label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                } else {
                    label.setBackgroundColor(getResources().getColor(
                            R.color.white));
                }
                return view;
            }
        };

        dataBinding.spinnerType.setAdapter(typeAdapter);

        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<BillListResponse.DataBean>(this, null) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.recycler_view_item_bill;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, BillListResponse.DataBean item) {
                holder.setText(R.id.tv_bill, item.getNumber());
            }
        };

    }

    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();
        viewModel.getSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    BaseDialog baseDialog = new BaseDialog(WarehouseActivity.this, R.layout.dialog_warehouse_single);
                    baseDialog.getWindow().setLayout(ToolUtil.dp2px(WarehouseActivity.this, 400),
                            ToolUtil.dp2px(WarehouseActivity.this, 250));
                    baseDialog.setTextViewContent(R.id.tv_name, viewModel.warehouseSingleInfoResponseMutableLiveData.getValue().getName());
                    baseDialog.setTextViewContent(R.id.tv_specs, viewModel.warehouseSingleInfoResponseMutableLiveData.getValue().getSpecs());
                    baseDialog.setTextViewContent(R.id.tv_num, viewModel.warehouseSingleInfoResponseMutableLiveData.getValue().getInNum()
                            + viewModel.warehouseSingleInfoResponseMutableLiveData.getValue().getInUnit());
                    baseDialog.getView(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            baseDialog.dismiss();
                        }
                    });
                    baseDialog.getView(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (viewModel.warehouseSingleInfoResponseMutableLiveData.getValue().getInNum() == 0) {
                                viewModel.showToast("数量为0不能出库", 1);
                            } else {
                                viewModel.warehouse();
                            }
                            baseDialog.dismiss();
                        }
                    });

                    baseDialog.show();

                }
            }
        });

        viewModel.warehouseMutableLiveData.observe(WarehouseActivity.this, new Observer<DictTypeResponse>() {
            @Override
            public void onChanged(DictTypeResponse dictTypeResponse) {
                warehouseAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(WarehouseActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                                null);
                        CheckedTextView label = (CheckedTextView) view
                                .findViewById(R.id.rb_item);
                        label.setText(dictTypeResponse.getList().get(position).getDictLabel());
                        if (position == dictTypeResponse.getList().size() - 1) {
                            label.setVisibility(View.GONE);
                        } else {
                            if (dataBinding.spinnerWarehouse.getSelectedItemPosition() == position) {
                                label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                            } else {
                                label.setBackgroundColor(getResources().getColor(
                                        R.color.white));
                            }
                        }
                        return view;
                    }
                };

                dataBinding.spinnerWarehouse.setAdapter(warehouseAdapter);
                dataBinding.spinnerWarehouse.setSelection(dictTypeResponse.getList().size() - 1, true);
            }
        });





        viewModel.zoneMutableLiveData.observe(WarehouseActivity.this, new Observer<DictTypeResponse>() {
            @Override
            public void onChanged(DictTypeResponse dictTypeResponse) {
                zoneAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(WarehouseActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                                null);
                        CheckedTextView label = (CheckedTextView) view
                                .findViewById(R.id.rb_item);
                        label.setText(dictTypeResponse.getList().get(position).getDictLabel());
                        if (position == dictTypeResponse.getList().size() - 1) {
                            label.setVisibility(View.GONE);
                        } else {
                            if (dataBinding.spinnerZone.getSelectedItemPosition() == position) {
                                label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                            } else {
                                label.setBackgroundColor(getResources().getColor(
                                        R.color.white));
                            }
                        }
                        return view;
                    }
                };

                dataBinding.spinnerZone.setAdapter(zoneAdapter);
                dataBinding.spinnerZone.setSelection(dictTypeResponse.getList().size() - 1, true);
            }
        });

        viewModel.shelfMutableLiveData.observe(WarehouseActivity.this, new Observer<DictTypeResponse>() {
            @Override
            public void onChanged(DictTypeResponse dictTypeResponse) {
                shelfAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(WarehouseActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                                null);
                        CheckedTextView label = (CheckedTextView) view
                                .findViewById(R.id.rb_item);
                        label.setText(dictTypeResponse.getList().get(position).getDictLabel());
                        if (position == dictTypeResponse.getList().size() - 1) {
                            label.setVisibility(View.GONE);
                        } else {
                            if (dataBinding.spinnerShelf.getSelectedItemPosition() == position) {
                                label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                            } else {
                                label.setBackgroundColor(getResources().getColor(
                                        R.color.white));
                            }
                        }
                        return view;
                    }
                };

                dataBinding.spinnerShelf.setAdapter(shelfAdapter);
                dataBinding.spinnerShelf.setSelection(dictTypeResponse.getList().size() - 1, true);
            }
        });

        dataBinding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.warehouseTypeCode.setValue(checkTypeList.get(i).getTypeCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataBinding.spinnerWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != viewModel.warehouseMutableLiveData.getValue().getList().size() - 1)
                    viewModel.warehouse.setValue(viewModel.warehouseMutableLiveData.getValue().getList().get(i).getDictLabel());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataBinding.spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != viewModel.zoneMutableLiveData.getValue().getList().size() - 1)
                    viewModel.zone.setValue(viewModel.zoneMutableLiveData.getValue().getList().get(i).getDictLabel());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataBinding.spinnerShelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != viewModel.shelfMutableLiveData.getValue().getList().size() - 1)
                    viewModel.shelf.setValue(viewModel.shelfMutableLiveData.getValue().getList().get(i).getDictLabel());
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
        return R.layout.activity_warehouse;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_warehouse_single:
                uhfrManager.setPower(15, 15);
                epcSingleList = new ArrayList<>();
                isRead();
                break;
            case R.id.tv_warehouse_batch:
                startActivity(BatchWarehouseActivity.class);
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

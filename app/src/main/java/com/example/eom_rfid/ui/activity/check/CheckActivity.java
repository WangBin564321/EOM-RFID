package com.example.eom_rfid.ui.activity.check;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.SpinnerAdapter;

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
import com.example.eom_rfid.bean.entity.CheckType;
import com.example.eom_rfid.bean.entity.ScanRfidResult;
import com.example.eom_rfid.bean.response.DictTypeResponse;
import com.example.eom_rfid.databinding.ActivityCheckBinding;
import com.example.eom_rfid.ui.activity.check.result.ScanResultActivity;
import com.example.eom_rfid.ui.activity.checkout.CheckoutActivity;
import com.example.eom_rfid.ui.activity.warehouse.WarehouseActivity;
import com.example.eom_rfid.utils.DialogUtil;
import com.example.eom_rfid.utils.ToolUtil;
import com.example.eom_rfid.utils.Util;
import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

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
 * Date:2021/1/13 19:11
 */
public class CheckActivity extends BaseActivity<ActivityCheckBinding, CheckViewModel> implements View.OnClickListener {

    public UHFRManager mUhfrManager;//uhf
    private boolean isStart = false;
    private List<String> scanRfidList;
    private List<String> scanRfidCodeList;

    SpinnerAdapter typeAdapter;
    List<CheckType> checkTypeList;

    SpinnerAdapter warehouseAdapter;
    SpinnerAdapter zoneAdapter;


    @Override
    protected void onStart() {
        super.onStart();
        mUhfrManager = UHFRManager.getInstance();// Init Uhf module
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter);

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
                    onClick(dataBinding.tvScan);
                }
                return;
            } else if (keyDown) {
                startTime = System.currentTimeMillis();
            } else {
                keyUpFalg = true;
            }
        }
    };

    @Override
    protected void initData() {
        checkTypeList = new ArrayList<>();
        checkTypeList.add(new CheckType("全部物料", "all"));
        checkTypeList.add(new CheckType("药品", "drug"));
        checkTypeList.add(new CheckType("物资", "material"));
        checkTypeList.add(new CheckType("设备", "equipment"));

        scanRfidList = new ArrayList<>();
        scanRfidCodeList = new ArrayList<>();

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

        viewModel.getWarehouse();
        viewModel.getZone();

    }

    //handler
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String epc = msg.getData().getString("data");
                    if (epc == null || epc.length() == 0) {
                        return;
                    }

                    if (!scanRfidList.contains(processRfid(epc))) {
                        scanRfidList.add(processRfid(epc));
                        scanRfidCodeList.add(epc);
                    }
                    break;
            }

        }
    };

    private Runnable runnable_MainActivity = new Runnable() {

        @Override
        public void run() {

            List<Reader.TAGINFO> list1;
            Log.e(HTAG, "runnable-isMulti-true");
            list1 = mUhfrManager.tagInventoryByTimer((short) 50);

            String data;
            if (list1 != null && list1.size() > 0) {
                Log.e(HTAG, list1.size() + "");

                Util.play(1, 0);

                for (Reader.TAGINFO tfs : list1) {
                    byte[] epcdata = tfs.EpcId;
                    data = Tools.Bytes2HexString(epcdata, epcdata.length);
                    Message msg = new Message();
                    msg.what = 1;
                    Bundle b = new Bundle();
                    Log.d(HTAG, "run==========>: " + data);
                    b.putString("data", data);
                    msg.setData(b);
                    handler1.sendMessage(msg);
                }
            }
            handler1.postDelayed(runnable_MainActivity, 0);
        }
    };

    public void isRead() {
        if (mUhfrManager == null) {
            return;
        }
        if (!isStart) {
            handler1.postDelayed(runnable_MainActivity, 0);
            viewModel.checkTxt.setValue("停止盘点");
            isStart = true;
        } else {
            viewModel.checkTxt.setValue("开始盘点");
            handler1.removeCallbacks(runnable_MainActivity);
            isStart = false;
        }
    }

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


    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();
        click(new ClickListener() {
            @Override
            public void click() {
                onClick(dataBinding.tvScan);
            }
        });

        viewModel.warehouseMutableLiveData.observe(this, new Observer<DictTypeResponse>() {
            @Override
            public void onChanged(DictTypeResponse dictTypeResponse) {
                warehouseAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(CheckActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                                null);
                        CheckedTextView label = (CheckedTextView) view
                                .findViewById(R.id.rb_item);
                        label.setText(dictTypeResponse.getList().get(position).getDictLabel());

                        if (dataBinding.spinnerWarehouse.getSelectedItemPosition() == position) {
                            label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                        } else {
                            label.setBackgroundColor(getResources().getColor(
                                    R.color.white));
                        }
                        return view;
                    }
                };

                dataBinding.spinnerWarehouse.setAdapter(warehouseAdapter);
            }
        });

        viewModel.zoneMutableLiveData.observe(this, new Observer<DictTypeResponse>() {
            @Override
            public void onChanged(DictTypeResponse dictTypeResponse) {
                zoneAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(CheckActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,
                                null);
                        CheckedTextView label = (CheckedTextView) view
                                .findViewById(R.id.rb_item);
                        label.setText(dictTypeResponse.getList().get(position).getDictLabel());

                        if (dataBinding.spinnerZone.getSelectedItemPosition() == position) {
                            label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                        } else {
                            label.setBackgroundColor(getResources().getColor(
                                    R.color.white));
                        }
                        return view;
                    }
                };

                dataBinding.spinnerZone.setAdapter(zoneAdapter);
            }
        });


        dataBinding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.checkTypeName.setValue(checkTypeList.get(i).getTypeName());
                viewModel.checkTypeCode.setValue(checkTypeList.get(i).getTypeCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataBinding.spinnerWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.warehouse.setValue(viewModel.warehouseMutableLiveData.getValue().getList().get(i).getDictLabel());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataBinding.spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                    viewModel.zone.setValue(viewModel.zoneMutableLiveData.getValue().getList().get(i).getDictLabel());
                else
                    viewModel.zone.setValue(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        viewModel.epcMutableLiveData.observe(this, new Observer<List<ScanRfidResult.EpcDataModel>>() {
            @Override
            public void onChanged(List<ScanRfidResult.EpcDataModel> epcDataModels) {
                if (epcDataModels != null)
                    viewModel.checkedNum.setValue(String.valueOf(epcDataModels.size()));

                for (int i = 0; i < epcDataModels.size(); i++) {
                }

            }
        });


    }

    @Override
    protected int initVariableId() {
        return BR.ViewModel;
    }

    @Override
    protected int setContentViewSrc(Bundle savedInstanceState) {
        return R.layout.activity_check;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                viewModel.getCheckList();
                break;
            case R.id.btn_result:
                if (viewModel.getSuccess.getValue()) {
                    Intent intent = new Intent(this, ScanResultActivity.class);
                    intent.putExtra("CheckListBean", viewModel.checkListResponseMutableLiveData.getValue());
//                intent.putExtra("CheckedListBean", viewModel.scanRfidResultMutableLiveData.getValue());
                    startActivity(intent);
                } else {
                    DialogUtil.showCallbackDialog(this, "当前无盘点对象");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_scan:
                if (viewModel.getSuccess.getValue() && !viewModel.checkNum.getValue().equals("0")) {
                    isRead();
                } else {
                    DialogUtil.showCallbackDialog(this, "当前无盘点对象");
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mUhfrManager != null) {
            //close uhf module
            mUhfrManager.close();
            mUhfrManager = null;
        }

        unregisterReceiver(keyReceiver);
    }
}

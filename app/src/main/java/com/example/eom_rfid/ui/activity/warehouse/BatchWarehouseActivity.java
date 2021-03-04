package com.example.eom_rfid.ui.activity.warehouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eom_rfid.BR;
import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseActivity;
import com.example.eom_rfid.base.BaseDialog;
import com.example.eom_rfid.base.BaseRecyclerAdapter;
import com.example.eom_rfid.base.RecyclerViewHolder;
import com.example.eom_rfid.bean.entity.BatchBean;
import com.example.eom_rfid.bean.response.BatchInfoResponse;
import com.example.eom_rfid.bean.response.BillListResponse;
import com.example.eom_rfid.databinding.ActivityCheckoutBatchBinding;
import com.example.eom_rfid.databinding.ActivityWarehouseBatchBinding;
import com.example.eom_rfid.utils.ToolUtil;
import com.example.eom_rfid.utils.Util;
import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

import java.util.ArrayList;
import java.util.List;

import cn.pda.serialport.Tools;

import static com.example.eom_rfid.utils.Constants.HTAG;


public class BatchWarehouseActivity extends BaseActivity<ActivityWarehouseBatchBinding, BatchWarehouseViewModel> implements View.OnClickListener {

    BaseRecyclerAdapter<BillListResponse.DataBean> beanBaseRecyclerAdapter;
    BaseRecyclerAdapter<BatchBean> batchListAdapter;

    public MutableLiveData<List<String>> rfidListMutableLiveData = new MutableLiveData<>();
    List<String> rfidList;
    private UHFRManager uhfrManager;
    boolean isStart = false;


    @Override
    protected void onStart() {
        super.onStart();

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

        uhfrManager.asyncStopReading();
        handler1.removeCallbacks(runnable_MainActivity);
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
                    isRead();
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
                    if (epc == null || epc.length() == 0) {
                        return;
                    }
                    for (int i = 0; i < viewModel.batchBeanMutableLiveData.getValue().size(); i++) {
                        if (viewModel.batchBeanMutableLiveData.getValue().get(i).getRfidCode().equals(processRfid(epc))) {
                            if (!rfidList.contains(processRfid(epc))) {
                                Util.play(1, 0);
                                rfidList.add(processRfid(epc));
                            }
                        }
                    }
                    rfidListMutableLiveData.setValue(rfidList);
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

    @Override
    protected void initData() {
        rfidList = new ArrayList<>();
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

        batchListAdapter = new BaseRecyclerAdapter<BatchBean>(this, null) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.recycler_view_item_batch;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, BatchBean item) {
                holder.setText(R.id.tv_pos, String.valueOf(position + 1));
                holder.setText(R.id.tv_rfid, item.getRfidCode());
                holder.setText(R.id.tv_name, item.getName());
                if (!item.isScan()) {
                    holder.getTextView(R.id.tv_rfid).setTextColor(getResources().getColor(R.color.colorTxtRed));
                } else {
                    holder.getTextView(R.id.tv_rfid).setTextColor(getResources().getColor(R.color.colorTxtBlack));
                }
            }
        };

        dataBinding.recyclerViewWarehouse.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerViewWarehouse.setAdapter(batchListAdapter);


    }

    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();

        viewModel.batchBeanMutableLiveData.observe(this, new Observer<List<BatchBean>>() {
            @Override
            public void onChanged(List<BatchBean> batchBeans) {
                batchListAdapter.setmItems(batchBeans);
                batchListAdapter.notifyDataSetChanged();
            }
        });

        rfidListMutableLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                for (int i = 0; i < viewModel.batchBeanMutableLiveData.getValue().size(); i++) {
                    for (int j = 0; j < rfidList.size(); j++) {
                        if (rfidList.get(j).equals(viewModel.batchBeanMutableLiveData.getValue().get(i).getRfidCode())) {
                            viewModel.batchBeanMutableLiveData.getValue().get(i).setScan(true);
                            viewModel.batchBeanMutableLiveData.setValue(viewModel.batchBeanMutableLiveData.getValue());
                        }
                    }
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
        return R.layout.activity_warehouse_batch;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_checkout:
                viewModel.warehouseBatch();
                break;
            case R.id.tv_select_bill:
                BaseDialog baseDialog = new BaseDialog(this, R.layout.dialog_select_bill);
                baseDialog.getWindow().setLayout(ToolUtil.dp2px(this, 400), ToolUtil.dp2px(this,
                        500));
                baseDialog.setTextViewContent(R.id.tv_title, "入库单号");
                EditText editText = baseDialog.getView(R.id.include_search).findViewById(R.id.et_search);
                editText.setHint("请输入入库清单号");
                ImageView imageView = (ImageView) baseDialog.getView(R.id.iv_clear);
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                            ToolUtil.hideKeyboard(editText);
                            editText.clearFocus();
                            viewModel.getWarehouseBillList(editText.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            imageView.setVisibility(View.VISIBLE);
                        } else {
                            imageView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                RecyclerView recyclerView = (RecyclerView) baseDialog.getView(R.id.recycler_view_bill);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(beanBaseRecyclerAdapter);

                viewModel.billListResponseMutableLiveData.observe(this, new Observer<BillListResponse>() {
                    @Override
                    public void onChanged(BillListResponse billListResponse) {
                        if (billListResponse.getData() != null) {
                            beanBaseRecyclerAdapter.setmItems(billListResponse.getData());
                            beanBaseRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                });

                beanBaseRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int pos) {
                        viewModel.bill.setValue(beanBaseRecyclerAdapter.getmItems(pos).getNumber());
                        viewModel.billMutableLiveData.setValue(viewModel.billListResponseMutableLiveData.getValue().getData().get(pos));
                        viewModel.getBatchInfo();
                        rfidList = new ArrayList<>();
                        baseDialog.dismiss();
                    }
                });
                baseDialog.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baseDialog.dismiss();
                    }
                });
                baseDialog.show();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}

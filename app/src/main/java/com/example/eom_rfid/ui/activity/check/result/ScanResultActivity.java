package com.example.eom_rfid.ui.activity.check.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eom_rfid.BR;
import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseActivity;
import com.example.eom_rfid.base.BaseDialog;
import com.example.eom_rfid.base.BaseRecyclerAdapter;
import com.example.eom_rfid.base.RecyclerViewHolder;
import com.example.eom_rfid.bean.entity.ScanRfidResult;
import com.example.eom_rfid.bean.response.CheckListResponse;
import com.example.eom_rfid.databinding.ActivityScanResultBinding;
import com.example.eom_rfid.utils.ToolUtil;

import java.util.List;

import static com.example.eom_rfid.utils.Constants.HTAG;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/15 19:27
 */
public class ScanResultActivity extends BaseActivity<ActivityScanResultBinding,
        ScanResultViewModel> implements View.OnClickListener {

    BaseRecyclerAdapter<CheckListResponse.DataBean> checkAdapter;
    public MutableLiveData<CheckListResponse> checkListResponseMutableLiveData = new MutableLiveData<>();
    CheckListResponse checkListResponse;
    List<ScanRfidResult.EpcDataModel> epcDataModelList;

    @Override
    protected void initData() {

        Intent intent = getIntent();//获取当前活动的 Intent

        if (intent != null) {
            checkListResponse = (CheckListResponse) intent.getSerializableExtra("CheckListBean");
            checkListResponseMutableLiveData.setValue(checkListResponse);
        }

//        if (intent != null) {
//            epcDataModelList = (List<ScanRfidResult.EpcDataModel>) intent.getSerializableExtra("CheckedListBean");
//            Log.e("TAG", String.valueOf(epcDataModelList.size()));
//        }

        if (checkListResponse != null) {
            checkAdapter = new BaseRecyclerAdapter<CheckListResponse.DataBean>(this, checkListResponse.getData()) {
                @Override
                protected int getHeaderLayoutId() {
                    return R.layout.head;
                }

                @Override
                protected int getItemLayoutId(int viewType) {
                    return R.layout.recycler_view_item_check_result;
                }

                @Override
                protected void bindData(RecyclerViewHolder holder, int position, CheckListResponse.DataBean item) {
                    holder.setText(R.id.tv_rfid, item.getRfidCode());
                    holder.setText(R.id.tv_name, item.getName());
                    holder.setText(R.id.tv_region, item.getZone());
                    holder.setText(R.id.tv_shelf_num, item.getShelfNumber());
                    holder.setText(R.id.tv_store_num, String.valueOf(item.getStockNum()));
                    holder.getTextView(R.id.tv_actual_num).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BaseDialog editNumDialog = new BaseDialog(ScanResultActivity.this, R.layout.dialog_edit_num);
                            editNumDialog.getWindow().setLayout(ToolUtil.dp2px(ScanResultActivity.this, 300),
                                    ToolUtil.dp2px(ScanResultActivity.this, 200));
                            editNumDialog.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    editNumDialog.dismiss();
                                }
                            });
                            editNumDialog.getView(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    holder.setText(R.id.tv_actual_num, editNumDialog.getEdittext(R.id.et_num).getText().toString());
                                    editNumDialog.dismiss();
                                }
                            });
                            editNumDialog.show();
                        }
                    });
                }
            };
        }


    }

    @Override
    public void initViewParameters() {
        super.initViewParameters();

    }

    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();

        dataBinding.recyclerViewRecord.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerViewRecord.setAdapter(checkAdapter);

        checkListResponseMutableLiveData.observe(this, new Observer<CheckListResponse>() {
            @Override
            public void onChanged(CheckListResponse checkListResponse) {
                if (checkListResponse != null && checkListResponse.getData() != null) {
                    checkAdapter.setmItems(checkListResponse.getData());
                    checkAdapter.notifyDataSetChanged();
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
        return R.layout.activity_scan_result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit_check:
                viewModel.submitCheck();
                break;
        }
    }
}

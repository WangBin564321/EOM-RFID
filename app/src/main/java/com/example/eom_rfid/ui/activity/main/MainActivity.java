package com.example.eom_rfid.ui.activity.main;

import android.os.Bundle;
import android.view.View;

import com.example.eom_rfid.BR;
import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseActivity;
import com.example.eom_rfid.base.BaseDialog;
import com.example.eom_rfid.databinding.ActivityMainBinding;
import com.example.eom_rfid.ui.activity.check.CheckActivity;
import com.example.eom_rfid.ui.activity.checkout.CheckoutActivity;
import com.example.eom_rfid.ui.activity.drug_search.DrugSearchActivity;
import com.example.eom_rfid.ui.activity.warehouse.WarehouseActivity;
import com.example.eom_rfid.ui.activity.write.WriteActivity;
import com.example.eom_rfid.utils.SPUtil;
import com.example.eom_rfid.utils.ToolUtil;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 17:57
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements View.OnClickListener {
    @Override
    protected void initData() {
        viewModel.userName.setValue("账号：" + SPUtil.getUserName(this));
    }

    @Override
    protected int initVariableId() {
        return BR.ViewModel;
    }

    @Override
    protected int setContentViewSrc(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check:
                startActivity(CheckActivity.class);
                break;
            case R.id.tv_checkout:
                startActivity(CheckoutActivity.class);
                break;
            case R.id.tv_warehouse:
                startActivity(WarehouseActivity.class);
                break;
            case R.id.tv_drug_search:
                startActivity(DrugSearchActivity.class);
                break;
            case R.id.tv_logout:
                BaseDialog exitDialog = new BaseDialog(this, R.layout.dialog_logout);
                exitDialog.getWindow().setLayout(ToolUtil.dp2px(this, 350),
                        ToolUtil.dp2px(this, 240));
                exitDialog.getTextView(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                        viewModel.logout();
                    }
                });

                exitDialog.getTextView(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });

                exitDialog.show();
                break;
            case R.id.tv_write:
                startActivity(WriteActivity.class);
                break;
        }
    }
}

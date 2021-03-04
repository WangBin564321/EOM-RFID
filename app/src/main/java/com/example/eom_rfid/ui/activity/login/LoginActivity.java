package com.example.eom_rfid.ui.activity.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.library.baseAdapters.BR;

import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseActivity;
import com.example.eom_rfid.base.BaseDialog;
import com.example.eom_rfid.databinding.ActivityLoginBinding;
import com.example.eom_rfid.utils.SPUtil;
import com.example.eom_rfid.utils.ToolUtil;

import static com.example.eom_rfid.utils.Constants.HTAG;


/**
 * Description:
 * Author:bwang
 * Date:2020/1/13 5:47
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements View.OnClickListener {

    @Override
    protected void initData() {
        if (SPUtil.getIsRemember(this)) {
            viewModel.userName.setValue(SPUtil.getUserName(this));
            viewModel.password.setValue(SPUtil.getPassword(this));
            dataBinding.checkbox.setChecked(true);
            viewModel.isRemember.setValue(true);
        }
    }

    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();
        dataBinding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(HTAG, "onCheckedChanged: =============>" + b);
                viewModel.isRemember.setValue(b);
            }
        });
    }

    @Override
    protected int initVariableId() {
        return BR.ViewModel;
    }

    @Override
    protected int setContentViewSrc(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
                BaseDialog baseDialog = new BaseDialog(this, R.layout.dialog_tip);
                baseDialog.getWindow().setLayout(ToolUtil.dp2px(this, 350), ToolUtil.dp2px(this,
                        240));
                baseDialog.getView(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseDialog.dismiss();
                    }
                });
                baseDialog.show();
                break;
        }
    }
}

package com.example.eom_rfid.ui.activity.drug_search;

import android.os.Bundle;
import android.text.Editable;
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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eom_rfid.BR;
import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseActivity;
import com.example.eom_rfid.base.BaseDialog;
import com.example.eom_rfid.base.BaseRecyclerAdapter;
import com.example.eom_rfid.base.RecyclerViewHolder;
import com.example.eom_rfid.bean.entity.CheckType;
import com.example.eom_rfid.bean.response.DictTypeResponse;
import com.example.eom_rfid.bean.response.DrugSearchResponse;
import com.example.eom_rfid.databinding.ActivityDrugSearchBinding;
import com.example.eom_rfid.ui.activity.checkout.CheckoutActivity;
import com.example.eom_rfid.utils.ToolUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.eom_rfid.utils.Constants.HTAG;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/13 19:30
 */
public class DrugSearchActivity extends BaseActivity<ActivityDrugSearchBinding, DrugSearchViewModel> implements View.OnClickListener {

    SpinnerAdapter typeAdapter;

    List<CheckType> checkTypeList;

    BaseRecyclerAdapter<DrugSearchResponse.DataBean> drugSearchAdapter;
    SpinnerAdapter warehouseAdapter;
    SpinnerAdapter zoneAdapter;
    SpinnerAdapter shelfAdapter;

    @Override
    protected void initData() {

        checkTypeList = new ArrayList<>();
        checkTypeList.add(new CheckType("全部物料", ""));
        checkTypeList.add(new CheckType("药品", "drug"));
        checkTypeList.add(new CheckType("物资", "material"));
        checkTypeList.add(new CheckType("设备", "equipment"));

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

        drugSearchAdapter = new BaseRecyclerAdapter<DrugSearchResponse.DataBean>(this, null) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.recycler_view_item_drug_search;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, DrugSearchResponse.DataBean item) {
                holder.setText(R.id.tv_region, ToolUtil.isNullTo(item.getWarehouse()) + "/" + ToolUtil.isNullTo(item.getZone()) + "/" + ToolUtil.isNullTo(item.getShelfNumber()));
                holder.setText(R.id.tv_name, item.getName());
                String type = "";
                switch (item.getMaterialType()) {
                    case "drug":
                        type = "药品";
                        break;
                    case "material":
                        type = "物资";
                        break;
                    case "equipment":
                        type = "设备";
                        break;
                }
                holder.setText(R.id.tv_type, type);
                holder.setText(R.id.tv_specs, item.getSpecs());
                holder.setText(R.id.tv_store_num, item.getStockNum() + "");
                holder.setText(R.id.tv_date, item.getExpireDate());
            }
        };

        dataBinding.recyclerViewDrug.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerViewDrug.setAdapter(drugSearchAdapter);

    }


    @Override
    protected int initVariableId() {
        return BR.ViewModel;
    }

    @Override
    protected int setContentViewSrc(Bundle savedInstanceState) {
        return R.layout.activity_drug_search;
    }

    boolean isFirst = true;

    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();
        dataBinding.includeSearch.ivClear.setOnClickListener(this::onClick);

        dataBinding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    viewModel.checkTypeCode.setValue(null);
                } else {
                    viewModel.checkTypeCode.setValue(checkTypeList.get(i).getTypeCode());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        drugSearchAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                viewModel.dataBeanMutableLiveData.setValue(viewModel.drugSearchResponseMutableLiveData.getValue().getData().get(pos));
                viewModel.getWarehouse();
                viewModel.getZone();
                viewModel.getShelf();
                BaseDialog baseDialog = new BaseDialog(DrugSearchActivity.this, R.layout.dialog_drug_edit);
                baseDialog.getWindow().setLayout(ToolUtil.dp2px(DrugSearchActivity.this, 400),
                        ToolUtil.dp2px(DrugSearchActivity.this, 300));
                Spinner spinnerWarehouse = (Spinner) baseDialog.getView(R.id.spinner_warehouse);
                Spinner spinnerZone = (Spinner) baseDialog.getView(R.id.spinner_region);
                Spinner spinnerShelf = (Spinner) baseDialog.getView(R.id.spinner_shelf);
                viewModel.warehouseMutableLiveData.observe(DrugSearchActivity.this, new Observer<DictTypeResponse>() {
                    @Override
                    public void onChanged(DictTypeResponse dictTypeResponse) {
                        warehouseAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(DrugSearchActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
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
                                    if (spinnerWarehouse.getSelectedItemPosition() == position) {
                                        label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                                    } else {
                                        label.setBackgroundColor(getResources().getColor(
                                                R.color.white));
                                    }
                                }
                                return view;
                            }
                        };

                        spinnerWarehouse.setAdapter(warehouseAdapter);
                        spinnerWarehouse.setSelection(dictTypeResponse.getList().size() - 1, true);
                    }
                });

                viewModel.zoneMutableLiveData.observe(DrugSearchActivity.this, new Observer<DictTypeResponse>() {
                    @Override
                    public void onChanged(DictTypeResponse dictTypeResponse) {
                        zoneAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(DrugSearchActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
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
                                    if (spinnerZone.getSelectedItemPosition() == position) {
                                        label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                                    } else {
                                        label.setBackgroundColor(getResources().getColor(
                                                R.color.white));
                                    }
                                }
                                return view;
                            }
                        };

                        spinnerZone.setAdapter(zoneAdapter);
                        spinnerZone.setSelection(dictTypeResponse.getList().size() - 1, true);
                    }
                });

                viewModel.shelfMutableLiveData.observe(DrugSearchActivity.this, new Observer<DictTypeResponse>() {
                    @Override
                    public void onChanged(DictTypeResponse dictTypeResponse) {
                        shelfAdapter = new ArrayAdapter<DictTypeResponse.ListBean>(DrugSearchActivity.this, R.layout.simple_spinner_item, dictTypeResponse.getList()) {
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
                                    if (spinnerShelf.getSelectedItemPosition() == position) {
                                        label.setBackgroundColor(getResources().getColor(R.color.colorTxtGreen));
                                    } else {
                                        label.setBackgroundColor(getResources().getColor(
                                                R.color.white));
                                    }
                                }
                                return view;
                            }
                        };

                        spinnerShelf.setAdapter(shelfAdapter);
                        spinnerShelf.setSelection(dictTypeResponse.getList().size() - 1, true);
                    }
                });


                spinnerWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != viewModel.warehouseMutableLiveData.getValue().getList().size() - 1)
                            viewModel.warehouse.setValue(viewModel.warehouseMutableLiveData.getValue().getList().get(i).getDictLabel());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != viewModel.zoneMutableLiveData.getValue().getList().size() - 1)
                            viewModel.zone.setValue(viewModel.zoneMutableLiveData.getValue().getList().get(i).getDictLabel());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinnerShelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != viewModel.shelfMutableLiveData.getValue().getList().size() - 1)
                            viewModel.shelf.setValue(viewModel.shelfMutableLiveData.getValue().getList().get(i).getDictLabel());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                baseDialog.getView(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.editDrug();
                        baseDialog.dismiss();
                    }
                });
                baseDialog.getView(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baseDialog.dismiss();
                    }
                });
                baseDialog.show();

            }
        });


        dataBinding.includeSearch.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    ToolUtil.hideKeyboard(dataBinding.includeSearch.etSearch);
                    dataBinding.includeSearch.etSearch.clearFocus();
                    viewModel.searchDrug(dataBinding.includeSearch.etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        dataBinding.includeSearch.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    dataBinding.includeSearch.ivClear.setVisibility(View.VISIBLE);
                } else {
                    dataBinding.includeSearch.ivClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewModel.drugSearchResponseMutableLiveData.observe(this, new Observer<DrugSearchResponse>() {
            @Override
            public void onChanged(DrugSearchResponse drugSearchResponse) {
                if (drugSearchResponse.getData() != null) {
                    drugSearchAdapter.setmItems(drugSearchResponse.getData());
                    drugSearchAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clear:
                dataBinding.includeSearch.etSearch.setText("");
                break;
        }
    }
}

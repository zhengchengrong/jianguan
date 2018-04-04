package com.jg.chipcomponent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jg.chipcomponent.nfc.NfcActivity;
import com.luojilab.component.basiclib.Const;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.utils.RxToast;
import com.luojilab.component.basiclib.utils.RxTool;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.componentservice.share.bean.Author;
import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;
import com.znq.zbarcode.CaptureActivity;

@RouteNode(path = "/chipPage", desc = "芯片扫描")
public class ChipActivity extends BaseActivity {
    @Autowired
    String id;


    private ImageView ivChipCode,ivChipNfc;

    private TextView tvChipSearch;

    private EditText tvChipInput;
    private int QR_CODE =1;


    @Override
    protected int attachLayoutRes() {
        return R.layout.chip_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initTitle(true,"芯片扫描");
        ivChipCode = this.findViewById(R.id.iv_chip_code);
        ivChipNfc = this.findViewById(R.id.iv_chip_nfc);
        tvChipSearch = this.findViewById(R.id.tv_chip_search);
        tvChipInput = this.findViewById(R.id.et_chip_input);

        ivChipCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ChipActivity.this, CaptureActivity.class);
                startActivityForResult(intent1, QR_CODE);
            }
        });
        ivChipNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("NFC扫描");
                Intent intent1 = new Intent(ChipActivity.this, NfcActivity.class);
                intent1.putExtra(Const.ID,id);
                startActivity(intent1);
            }
        });
        tvChipSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(tvChipInput.getText().toString())){
                    RxToast.showToast("请输入芯片外码");
                    return;
                }
                Intent intent = new Intent(ChipActivity.this,ChipAddActivity.class);
                intent.putExtra(Const.CODE,tvChipInput.getText().toString());
                intent.putExtra(Const.ID,id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QR_CODE&&resultCode==RESULT_OK) {
            if(null==data) return;
            Bundle b=data.getExtras();
            String result = b.getString(CaptureActivity.EXTRA_STRING);
            // Toast.makeText(this, result + "", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChipActivity.this,ChipAddActivity.class);
            intent.putExtra(Const.CODE,result);
            intent.putExtra(Const.ID,id);
            startActivity(intent);

        }
    }
}

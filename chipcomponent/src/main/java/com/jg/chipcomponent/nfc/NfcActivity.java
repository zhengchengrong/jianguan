package com.jg.chipcomponent.nfc;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.jg.chipcomponent.ChipActivity;
import com.jg.chipcomponent.ChipAddActivity;
import com.jg.chipcomponent.R;
import com.luojilab.component.basiclib.Const;

public class NfcActivity extends Activity {

	TextView sn;

    private NfcManager nfcMgr = new NfcManager();
    private NfcAdapter nfcAdapter;
    PendingIntent mPendingIntent;
    IntentFilter[] intentFiltersArray;
    String[][] techListsArray;
    private IntentFilter ifTech;

    private String id;
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nfc);
        sn = (TextView) findViewById(R.id.sn);
        id = getIntent().getStringExtra(Const.ID);

        TextView titleback = (TextView) findViewById(R.id.titleback);
        titleback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        // 获取nfc适配器，判断设备是否支持NFC功能
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "该设备不支持NFC!",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "未打开NFC功能!",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //开始意图过滤
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        ifTech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{ifTech};
        techListsArray = new String[][]{new String[]{MifareClassic.class.getName()}};
        try {
            ifTech.addDataType("*/*");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //singleTop 模式 重新启动这个activity是调用次方法
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String result = "";
        try {
        	result= this.nfcMgr.readTag(intent);
            sn.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent1 = new Intent(NfcActivity.this,ChipAddActivity.class);
        intent1.putExtra(Const.CODE,result);
        intent1.putExtra(Const.ID,id);
        startActivity(intent1);
        this.finish();
    }

    @SuppressLint("NewApi")
	@Override
    protected void onResume() {
        super.onResume();
        ///注册我们的过滤器
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        nfcAdapter = null;
        mPendingIntent = null;
        ifTech = null;
        super.onDestroy();
    }
    @SuppressLint("NewApi")
	@Override
    protected void onPause() {
        super.onPause();
        //关闭我们的过滤器
        nfcAdapter.disableForegroundDispatch(this);
    }


}

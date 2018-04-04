package com.jg.chipcomponent.nfc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;

/**
 * Created by 3hlyw on 2016-07-29.
 */
public class NfcManager {
	//本机设定的验证扇区的密钥
    private final static byte[] CARD_PASSWORD = { (byte)0xA6, (byte)0xB5, (byte)0xC4, (byte)0xD3, (byte)0xE2, (byte)0xF1 };
    //用于验证扇区的默认密钥
    private final static byte[] CARD_PASSWORD_DEFAULT = { (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF };

    @SuppressLint("NewApi")
	public String readTag(Intent intent) throws Exception{

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if(null == tag){
            return "00";
        }

        /**
         * 1. NfcAdapter能直接根据标签的tag得到id
         * 2. 再tag中的id是一个字符数组，将其转换成16进制的的long类型数据，最后将其转换成String类型的数据
         * 3. 这块芯片没有按照标准的NFC格式来写。所以直接调用的TechList(非标准格式的NFC数据)。遍历整个TechList，将其中的信息全部都添加到ArrayList中去
         * 4. 
         */
        byte[] id = tag.getId();
        String cardId = String.valueOf(Long.parseLong(Utils.byteArrayToHexString(id), 16));
        
        return cardId;

    }

 
}

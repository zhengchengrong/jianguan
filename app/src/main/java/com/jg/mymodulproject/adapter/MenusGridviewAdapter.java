package com.jg.mymodulproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jg.mymodulproject.bean.GetMenusListRsp;
import com.jg.mymodulproject.home.HomeActivity;
import com.jg.mymodulproject.sampling.SamplingActivity;
import com.jg.mymodulproject.utils.VHUtil;
import com.luojilab.component.basiclib.Const;
import com.luojilab.component.basiclib.bean.login.UserBean;
import com.luojilab.component.basiclib.utils.RxGlideTools;
import com.zcr.mymodulproject.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 3hcd on 2017/3/9.
 */

public class MenusGridviewAdapter extends android.widget.BaseAdapter {

    List<GetMenusListRsp> listRsps = new ArrayList<>();
    private Activity activity;
    private UserBean mUserBean;
    public MenusGridviewAdapter(Activity activity,UserBean userBean) {
        this.activity = activity;
        this.mUserBean = userBean;
    }
    @Override
    public int getCount() {
        return listRsps.size();
    }

    @Override
    public GetMenusListRsp getItem(int i) {
        return listRsps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int positon, View convertView, ViewGroup viewGroup) {
        if (convertView == null)
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_menus_gridview_item, viewGroup, false);
        ImageView imageView = VHUtil.ViewHolder.get(convertView, R.id.imageurl);
        TextView num = VHUtil.ViewHolder.get(convertView, R.id.textname);
        num.setText(listRsps.get(positon).menuNameShow);
        imageView.setImageResource(listRsps.get(positon).menulocUrl);
        convertView.setOnClickListener(new clicklistener(listRsps.get(positon).menuNameShow, positon));
        return convertView;
    }

    public void DateNotify(List<GetMenusListRsp> listRsps) {
        this.listRsps = listRsps;
        notifyDataSetChanged();

    }

    class clicklistener implements View.OnClickListener {
        String name;
        int pos;
        public clicklistener(String name, int pos) {
            this.name = name;
            this.pos = pos;
        }
        @Override
        public void onClick(View view) {
            Intent  intent;
            switch (pos) {
                case 0:  //工程信息
                    intent = new Intent(activity, SamplingActivity.class);
                    Bundle bundle1 = new Bundle();
                    intent.putExtra(Const.PROJECTNAME, mUserBean.getUsername());
                    intent.putExtras(bundle1);
                    activity.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}

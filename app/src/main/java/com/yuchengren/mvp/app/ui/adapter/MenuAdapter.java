package com.yuchengren.mvp.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.ui.adapter.Base.SuperAdapter;
import com.yuchengren.mvp.entity.db.MenuEntity;

import java.util.List;

/**
 * Created by yuchengren on 2017/12/20.
 */

public class MenuAdapter extends SuperAdapter<MenuEntity> {

	public MenuAdapter(Context context, List<MenuEntity> dataList) {
		super(context, dataList);
	}

	@Override
	protected View inflateItemView(int position) {
		return View.inflate(mContext,R.layout.item_menu,null);
	}

	@Override
	protected void inflateItemChildView(int position, ViewHolder holder) {
		ImageView iv_menu_icon = holder.getView(R.id.iv_menu_icon);
		TextView tv_menu_name = holder.getView(R.id.tv_menu_name);

		MenuEntity menuEntity = mDataList.get(position);
		if(menuEntity != null){
			tv_menu_name.setText(menuEntity.getName());
		}
	}
}

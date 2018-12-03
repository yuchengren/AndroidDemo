package com.yuchengren.mvp.app.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.ui.adapter.MenuAdapter;
import com.yuchengren.mvp.constant.MenuCode;
import com.yuchengren.mvp.entity.db.MenuEntity;
import com.yuchengren.mvp.greendao.gen.MenuEntityDao;
import com.yuchengren.mvp.util.business.DaoHelper;
import com.yuchengren.mvp.util.business.MenuUtil;

import java.util.List;

/**
 * Created by yuchengren on 2017/12/19.
 */

public class HomeFragment extends Fragment {

	private GridView gv_menu;
	private MenuAdapter mMenuAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container,false);
		gv_menu = (GridView) view.findViewById(R.id.gv_menu);
		initMenuAdapter();
		gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				MenuUtil.startActivity(getActivity(),mMenuAdapter.getItem(position));
			}
		});

		return view;
	}

	private void initMenuAdapter(){
		MenuEntityDao menuEntityDao = DaoHelper.getMenuEntityDao();
		List<MenuEntity> menuEntityList = menuEntityDao.queryBuilder().where(
				MenuEntityDao.Properties.ParentCode.eq(MenuCode.First.HOME)).orderAsc(MenuEntityDao.Properties.Order).build().list();
		mMenuAdapter = new MenuAdapter(getActivity(), menuEntityList);
		gv_menu.setAdapter(mMenuAdapter);
	}

}

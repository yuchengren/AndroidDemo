package com.yuchengren.demo.app.body.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuchengren.demo.R;
import com.yuchengren.demo.constant.MenuCode;
import com.yuchengren.demo.entity.db.MenuEntity;
import com.yuchengren.demo.factory.FragmentFactory;
import com.yuchengren.demo.greendao.gen.MenuEntityDao;
import com.yuchengren.demo.util.business.DaoHelper;


import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout fl_container;
    private LinearLayout ll_bottom_tab;

    protected void initData() {
        ll_bottom_tab.removeAllViews();
        MenuEntityDao menuEntityDao = DaoHelper.getMenuEntityDao();
        List<MenuEntity> menuEntityList = menuEntityDao.queryBuilder().where(
                MenuEntityDao.Properties.ParentCode.eq(MenuCode.TOP)).orderAsc(MenuEntityDao.Properties.Order).build().list();
        if(menuEntityList != null && menuEntityList.size() != 0){
            for (final MenuEntity menuEntity : menuEntityList) {
                View view = View.inflate(this, R.layout.ll_main_bottom_tab, null);
                ImageView iv_bottom_tab_icon = (ImageView) view.findViewById(R.id.iv_bottom_tab_icon);
                TextView tv_bottom_tab_name = (TextView) view.findViewById(R.id.tv_bottom_tab_name);
                tv_bottom_tab_name.setText(menuEntity.getName());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                view.setLayoutParams(layoutParams);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attachFragment(menuEntity.getCode());
                    }
                });
                view.setTag(menuEntity.getCode());
                ll_bottom_tab.addView(view);
            }
        }
        attachFragment(MenuCode.First.HOME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        ll_bottom_tab = (LinearLayout) findViewById(R.id.ll_bottom_tab);
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void attachFragment(String tag){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, FragmentFactory.getInstance().getFragment(tag));
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        FragmentFactory.getInstance().removeMainFragments();
        super.onDestroy();
    }
}

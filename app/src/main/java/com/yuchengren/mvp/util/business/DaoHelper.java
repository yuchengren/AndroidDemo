package com.yuchengren.mvp.util.business;

import com.yuchengren.mvp.app.DemoApplication;
import com.yuchengren.mvp.greendao.gen.MenuEntityDao;

/**
 * Created by yuchengren on 2017/11/30.
 */

public class DaoHelper {

	public static MenuEntityDao getMenuEntityDao(){
		return DemoApplication.getInstance().getDaoSession().getMenuEntityDao();
	}

}

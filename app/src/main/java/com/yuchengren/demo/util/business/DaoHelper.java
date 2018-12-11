package com.yuchengren.demo.util.business;

import com.yuchengren.demo.app.DemoApplication;
import com.yuchengren.demo.greendao.gen.MenuEntityDao;

/**
 * Created by yuchengren on 2017/11/30.
 */

public class DaoHelper {

	public static MenuEntityDao getMenuEntityDao(){
		return DemoApplication.getInstance().getDaoSession().getMenuEntityDao();
	}

}

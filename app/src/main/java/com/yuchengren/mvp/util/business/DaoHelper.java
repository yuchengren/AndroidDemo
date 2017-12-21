package com.yuchengren.mvp.util.business;

import com.yuchengren.mvp.app.MvpApplication;
import com.yuchengren.mvp.greendao.gen.MenuEntityDao;

/**
 * Created by yuchengren on 2017/11/30.
 */

public class DaoHelper {

	public static MenuEntityDao getMenuEntityDao(){
		return MvpApplication.getInstance().getDaoSession().getMenuEntityDao();
	}

}

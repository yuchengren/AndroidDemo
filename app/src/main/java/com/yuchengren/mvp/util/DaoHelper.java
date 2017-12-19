package com.yuchengren.mvp.util;

import com.yuchengren.mvp.app.MvpApplication;
import com.yuchengren.mvp.greendao.gen.MenuEntityDao;

/**
 * Created by yuchengren on 2017/11/30.
 */

public class DaoHelper {

	public static MenuEntityDao getUploadLogDao(){
		return MvpApplication.getInstance().getDaoSession().getMenuEntityDao();
	}

}

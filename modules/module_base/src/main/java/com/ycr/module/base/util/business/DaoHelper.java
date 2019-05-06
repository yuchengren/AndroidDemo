package com.ycr.module.base.util.business;

import com.ycr.module.base.BaseApplication;
import com.ycr.module.base.greendao.gen.MenuEntityDao;

/**
 * Created by yuchengren on 2017/11/30.
 */

public class DaoHelper {

	public static MenuEntityDao getMenuEntityDao(){
		return BaseApplication.getInstance().getDaoSession().getMenuEntityDao();
	}

}

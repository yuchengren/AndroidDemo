package com.ycr.module.base.util.business;

import com.ycr.module.base.DemoApplication;
import com.ycr.module.base.greendao.gen.MenuEntityDao;

/**
 * Created by yuchengren on 2017/11/30.
 */

public class DaoHelper {

	public static MenuEntityDao getMenuEntityDao(){
		return DemoApplication.getInstance().getDaoSession().getMenuEntityDao();
	}

}

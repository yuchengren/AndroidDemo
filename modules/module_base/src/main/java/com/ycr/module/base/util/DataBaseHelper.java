package com.ycr.module.base.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ycr.module.base.greendao.gen.DaoMaster;


/**
 * Created by yuchengren on 2017/11/28.
 */

public class DataBaseHelper extends DaoMaster.OpenHelper {


	public DataBaseHelper(Context context, String name) {
		super(context, name,null);
	}

	public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
		super(context, name, factory);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onUpgrade(db, oldVersion, newVersion);
	}
}

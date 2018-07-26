package com.ycr.kernel.log.constants;

import android.support.annotation.IntDef;
import android.util.Log;

import static com.ycr.kernel.log.constants.LogLevel.ASSERT;
import static com.ycr.kernel.log.constants.LogLevel.DEBUG;
import static com.ycr.kernel.log.constants.LogLevel.ERROR;
import static com.ycr.kernel.log.constants.LogLevel.INFO;
import static com.ycr.kernel.log.constants.LogLevel.VERBOSE;
import static com.ycr.kernel.log.constants.LogLevel.WARN;

/**
 * Created by yuchengren on 2018/7/11.
 */
@IntDef({VERBOSE,DEBUG,INFO,WARN,ERROR,ASSERT})
public @interface LogLevel {
	int VERBOSE = Log.VERBOSE;
	int DEBUG = Log.DEBUG;
	int INFO = Log.INFO;
	int WARN = Log.WARN;
	int ERROR = Log.ERROR;
	int ASSERT = Log.ASSERT;
}

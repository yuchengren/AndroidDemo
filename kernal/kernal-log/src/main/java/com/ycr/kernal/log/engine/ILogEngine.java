package com.ycr.kernal.log.engine;

import com.ycr.kernal.log.config.ILogConfig;
import com.ycr.kernal.log.control.ILogControl;
import com.ycr.kernal.log.printer.ILogPrinter;

import java.util.Map;

/**
 * Created by yuchengren on 2018/7/12.
 */
public interface ILogEngine extends ILog {

	ILogEngine config(ILogConfig logConfig);

	ILogConfig logConfig();

	ILogControl logControl();

	Map<Integer,ILogPrinter> logPrinters();

}

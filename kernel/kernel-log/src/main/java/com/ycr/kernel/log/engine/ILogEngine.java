package com.ycr.kernel.log.engine;

import com.ycr.kernel.log.config.ILogConfig;
import com.ycr.kernel.log.control.ILogControl;
import com.ycr.kernel.log.printer.ILogPrinter;

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

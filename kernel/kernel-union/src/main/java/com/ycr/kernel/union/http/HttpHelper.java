package com.ycr.kernel.union.http;

import android.content.Context;

import com.ycr.kernel.http.IApi;
import com.ycr.kernel.http.ICall;
import com.ycr.kernel.http.IHttpScheduler;
import com.ycr.kernel.http.IResponse;
import com.ycr.kernel.http.IResult;
import com.ycr.kernel.task.TaskInfo;
import com.ycr.kernel.union.exception.NetworkNotConnectedException;
import com.ycr.kernel.union.helper.ContextHelper;
import com.ycr.kernel.union.helper.UnionContainer;
import com.ycr.kernel.util.NetworkUtilsKt;
import com.ycr.kernel.util.ThreadLocalHelper;

import static com.ycr.kernel.task.TaskModuleKt.TASK_INFO;

/**
 * created by yuchengren on 2019/4/23
 */
public class HttpHelper {
    public static IHttpScheduler httpScheduler = UnionContainer.httpScheduler;
    public static Context context = ContextHelper.getContext();

    public static  <T> IResult<T> execute(IApi api, Object params){
        if(NetworkUtilsKt.isNetworkConnected(context)){
            throw new NetworkNotConnectedException();
        }
        ICall newCall = httpScheduler.newCall(api.newRequestBuilder().setParams(params).build());
        TaskInfo taskInfo = ThreadLocalHelper.INSTANCE.getThreadLocalInfo(TASK_INFO);
        IResponse response = httpScheduler.execute(newCall, taskInfo.getGroupName(), taskInfo.getTaskName());
        return httpScheduler.parse(api, response);
    }

    public static void  cancelGroup(String groupName){
        httpScheduler.cancelGroup(groupName);
    }
}

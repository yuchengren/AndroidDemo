package com.yuchengren.demo.app.widgettest.refreshview

import android.os.Bundle
import android.view.View
import com.ycr.kernel.http.IResult
import com.ycr.kernel.task.AbstractTask
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.constant.Constants
import com.ycr.module.framework.task.ApiTask
import com.ycr.kernel.union.task.SimpleResult
import com.ycr.lib.ui.pullrefresh.OnPullDownRefreshListener
import com.ycr.lib.ui.pullrefresh.OnPullUpLoadMoreListener
import com.yuchengren.demo.R
import kotlinx.android.synthetic.main.activity_refresh_view.*
import java.lang.Exception

/**
 * created by yuchengren on 2019/5/23
 */
class RefreshViewActivity: BaseActivity() {

    private var offset = 0
    private var hasNext: Boolean = true
    private var totalCount: Int = 30

    override fun getRootLayoutResId(): Int {
        return  R.layout.activity_refresh_view
    }

    override fun bindView(rootView: View?) {
        super.bindView(rootView)
        refreshView.run {
            isPullDownRefreshEnabled = true
            isPullUpLoadMoreEnabled = true
            setOnPullDownRefreshListener(OnPullDownRefreshListener {
                getDataList(true)
            })
            setOnPullUpLoadMoreListener(OnPullUpLoadMoreListener {
                getDataList(false)
            })
        }

        recyclerView.adapter = TestRefreshApdater(null).apply {
        }
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
    }

    private fun getDataList(isRefresh: Boolean) {

        submitTask(object : ApiTask<MutableList<Int>?>(){

            override fun onBeforeCall(task: AbstractTask<IResult<MutableList<Int>?>>) {
                super.onBeforeCall(task)
                if(offset == 0){
                    showLoading("",isEmpty)
                }
            }

            override fun onFinishCall(result: IResult<MutableList<Int>?>) {
                super.onFinishCall(result)
                dismissLoading(result.isSuccess())
                notifyRefreshView(isRefresh,result.isSuccess())
            }

            override fun doInBackground(): IResult<MutableList<Int>?> {
                try {
                    Thread.sleep(3000)
                }catch (e: Exception){

                }
                val offset = if(isRefresh) 0 else offset
                return SimpleResult.success(getTestDataList(offset), "", "")
            }

            override fun onSuccess(result: IResult<MutableList<Int>?>) {
                super.onSuccess(result)
                val dataList = result.data()
                if(dataList == null || dataList.isEmpty()){
                    if(isRefresh){
                        offset = 0
                    }else{
                        hasNext = false
                    }
                    return
                }

                notifyAdapter(isRefresh,dataList)
            }

        })
    }

    private fun notifyRefreshView(isRefresh: Boolean,isSuccess: Boolean) {
        if(isRefresh){
            refreshView.finishPullDownRefresh(isSuccess,!hasNext)
        }else{
            refreshView.finishPullUpLoadMore(isSuccess,!hasNext)
        }
    }

    private fun notifyAdapter(isRefresh: Boolean, dataList: MutableList<Int>?){
        val adapter = recyclerView.adapter as? TestRefreshApdater
        if(isRefresh){
            adapter?.replaceAll(dataList)
        }else{
            adapter?.addAll(dataList)
        }
        offset = adapter?.getDataSourceSize()?:0
        hasNext = offset < totalCount
    }


    private fun getTestDataList(offset: Int): MutableList<Int>{
        val list = mutableListOf<Int>()
        for (i in offset + 1 .. offset + Constants.PAGE_COUNT){
            list.add(i)
        }
        return list
    }
}
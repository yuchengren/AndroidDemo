package com.ycr.module.framework

import com.ycr.kernel.mvp.MvpActivity

/**
 * Created by yuchengren on 2018/12/3.
 */
open abstract class BaseActivity: MvpActivity() {
    val TAG = this.localClassName

}
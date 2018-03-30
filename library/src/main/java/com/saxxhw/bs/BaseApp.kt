package com.saxxhw.bs

import android.app.Application
import com.saxxhw.bs.ui.DigitalDialogInput

/**
 * Created by Saxxhw on 2018/3/30.
 * 邮箱：Saxxhw@126.com
 * 功能：
 */
abstract class BaseApp: Application() {

    companion object {
        var digitalDialogInput: DigitalDialogInput? = null
    }
}
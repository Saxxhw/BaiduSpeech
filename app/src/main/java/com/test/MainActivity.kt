package com.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.widget.Button
import com.saxxhw.bs.BaseApp
import com.saxxhw.bs.control.MyRecognizer
import com.saxxhw.bs.recognization.ChainRecogListener
import com.saxxhw.bs.recognization.MessageStatusRecogListener
import com.saxxhw.bs.recognization.online.OnlineRecogParams
import com.saxxhw.bs.ui.BaiduASRDigitalDialog
import com.saxxhw.bs.ui.DigitalDialogInput

class MainActivity : AppCompatActivity() {

    // 语音转文字相关
    private lateinit var input: DigitalDialogInput
    private lateinit var listener: ChainRecogListener
    private lateinit var myRecognizer: MyRecognizer
    private lateinit var apiParams: OnlineRecogParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化语音转文字相关
        listener = ChainRecogListener()
        listener.addListener(MessageStatusRecogListener(Handler { return@Handler true }))
        myRecognizer = MyRecognizer(this, listener)
        apiParams = OnlineRecogParams(this)

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(this)
            val params = apiParams.fetch(sp)
            input = DigitalDialogInput(myRecognizer, listener, params)
            val intent = Intent(this, BaiduASRDigitalDialog::class.java)
            BaseApp.digitalDialogInput = input
            startActivityForResult(intent, 2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myRecognizer.release()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 2) {
            val results = data?.getStringArrayListExtra("results")
            if (results != null && results.size > 0) {
                println(results[0])
            }
        }
    }
}

package com.ccuwu.a789betkhngngng

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ccuwu.a789betkhngngng.connection.BetConnectionHandler
import com.ccuwu.a789betkhngngng.databinding.ActivitySplashBinding


class BetSplashActivity : AppCompatActivity() {

    private lateinit var _splashBinding : ActivitySplashBinding
    private var checkInternetConnection = BetConnectionHandler()
    private var checknet = false

    private lateinit var webViewDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_splashBinding.root)

        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        swipeToRefresh()
        connectionCheck()
    }

    private fun swipeToRefresh(){
        _splashBinding.swipeLayout.setOnRefreshListener {
            checknet = checkInternetConnection.connectionError(this@BetSplashActivity)
            if (checknet){
                _splashBinding.swipeLayout.postDelayed({
                    _splashBinding.swipeLayout.isRefreshing = false
                    Toast.makeText(this@BetSplashActivity, "Đã khôi phục kết nối", Toast.LENGTH_LONG).show()
                    _splashBinding.splashWebView.visibility = View.VISIBLE
                    _splashBinding.close.visibility = View.VISIBLE
                    connectionCheck()
                },2000)
            }else {
                _splashBinding.swipeLayout.isRefreshing = true
                Toast.makeText(applicationContext, "Lỗi kết nối. Hãy kiểm tra kết nối internet và làm mới trang",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun connectionCheck() {
        checknet = checkInternetConnection.connectionError(this@BetSplashActivity)
        if (checknet){
            showWebViewDialog()
        } else {
            _splashBinding.splashWebView.visibility = View.GONE

            _splashBinding.close.setOnClickListener {
                webViewDialog.dismiss()
                _splashBinding.splashWebView.visibility = View.GONE
                _splashBinding.close.visibility = View.GONE
            }
            Toast.makeText(applicationContext, "Connection error, Please check internet connection.",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun showWebViewDialog() {
        webViewDialog = Dialog(this)
        webViewDialog.setTitle("Chào mừng!")
        _splashBinding.splashWebView.loadUrl("file:///android_asset/ppp/splashWeb.html")
        _splashBinding.close.setOnClickListener {

            webViewDialog.dismiss()
            _splashBinding.splashWebView.visibility = View.GONE
            _splashBinding.close.visibility = View.GONE
            Handler().postDelayed({
                startActivity(Intent(this, BetMainActivity::class.java))
                finish()
            },2000)
        }
    }
}
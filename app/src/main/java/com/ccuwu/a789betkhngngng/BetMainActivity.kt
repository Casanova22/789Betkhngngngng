package com.ccuwu.a789betkhngngng

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.ccuwu.a789betkhngngng.databinding.ActivityMainBinding

class BetMainActivity : AppCompatActivity() {

    private lateinit var _mainBinding : ActivityMainBinding
    private var exit = false

    private val currentFragment by lazy {
        findNavController(R.id.navContainerMain)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_mainBinding.root)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (currentFragment.currentDestination?.id == R.id.navContainerMain) {
            if (exit) {
                finishAffinity()
                return
            }
            exit = true
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ exit = false }, 2000)
        } else {
            findNavController(R.id.navContainerMain).navigateUp()
        }
    }
}
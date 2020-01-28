package edu.rosehulman.orgservicelogger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.rosehulman.orgservicelogger.ui.home.HomeFragment

class NoLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_login)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_no_login_frame, HomeFragment())
        transaction.commit()
    }
}

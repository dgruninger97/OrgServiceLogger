package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import edu.rosehulman.orgservicelogger.home.HomeFragment
import kotlinx.android.synthetic.main.activity_no_login.*

class NoLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_login)
        setSupportActionBar(activity_no_login_toolbar)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_no_login_frame, HomeFragment())
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}

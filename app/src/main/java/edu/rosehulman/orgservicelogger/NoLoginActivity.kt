package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import edu.rosehulman.orgservicelogger.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class NoLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frame, HomeFragment("no_login"))
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}

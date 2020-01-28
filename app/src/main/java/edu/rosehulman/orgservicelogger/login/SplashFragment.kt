package edu.rosehulman.orgservicelogger.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.rosehulman.orgservicelogger.R
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment(private val listener: OnLoginButtonPressedListener) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        view.fragment_splash_login_button.setOnClickListener {
            listener.onLoginButtonPressed()
        }
        return view
    }
}

interface OnLoginButtonPressedListener {
    fun onLoginButtonPressed()
}

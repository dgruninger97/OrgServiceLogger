package edu.rosehulman.orgservicelogger.ui.organization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrganizationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is organization Fragment"
    }
    val text: LiveData<String> = _text
}
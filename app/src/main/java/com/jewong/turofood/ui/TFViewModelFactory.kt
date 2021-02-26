package com.jewong.turofood.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TFViewModelFactory(
    private val activity: Activity?
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(activity!!.application) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }

}
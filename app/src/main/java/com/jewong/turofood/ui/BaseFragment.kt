package com.jewong.turofood.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment<DB> : Fragment() {

    protected var mViewDataBinding: DB? = null

    override fun onDestroyView() {
        mViewDataBinding = null
        super.onDestroyView()
    }

}

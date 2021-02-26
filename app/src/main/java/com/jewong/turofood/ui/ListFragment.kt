package com.jewong.turofood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jewong.turofood.R
import com.jewong.turofood.databinding.FragmentListBinding

class ListFragment : BaseFragment<FragmentListBinding>() {

    private val mListViewModel: ListViewModel by lazy { ViewModelProvider(this).get(ListViewModel::class.java) }
    private val mAdapter = BusinessesAdapter()
    private val mLayoutManager = LinearLayoutManager(context)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val mBinding = FragmentListBinding.bind(root)
        mBinding.lifecycleOwner = this
        mBinding.mListViewModel = mListViewModel
        mViewDataBinding = mBinding
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mListViewModel.getBusinesses()
        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        mListViewModel.mBusinesses.observe(this, { list -> mAdapter.addToList(list) })
    }

    private fun initRecyclerView() {
        mViewDataBinding?.apply {
            businessesRecyclerView.adapter = mAdapter
            businessesRecyclerView.layoutManager = mLayoutManager
        }
    }

}
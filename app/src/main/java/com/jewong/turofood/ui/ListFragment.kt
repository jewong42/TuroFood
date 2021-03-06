package com.jewong.turofood.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jewong.turofood.R
import com.jewong.turofood.api.data.Coordinates
import com.jewong.turofood.databinding.FragmentListBinding


class ListFragment : BaseFragment<FragmentListBinding>(),
    BusinessesAdapter.BusinessesAdapterCallback {

    private val mListViewModel: ListViewModel by lazy {
        ViewModelProvider(this, TFViewModelFactory(activity)).get(ListViewModel::class.java)
    }
    private val mAdapter = BusinessesAdapter(this)
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
        initRecyclerView()
        initObservers()
        if (savedInstanceState == null) mListViewModel.getBusinesses()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(RECYCLER_VIEW_POSITION, mLayoutManager.findFirstVisibleItemPosition())
        super.onSaveInstanceState(outState)
    }

    private fun initObservers() {
        mListViewModel.mBusinesses.observe(this, { list -> mAdapter.addToList(list) })
        mListViewModel.mShowError.observe(this, { error -> showError(error) })
    }

    private fun initRecyclerView() {
        mViewDataBinding?.businessesRecyclerView?.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (mLayoutManager.findLastVisibleItemPosition() + PAGINATION_BUFFER >= mAdapter.itemCount) {
                        mListViewModel.getBusinesses()
                    }
                }
            })
        }
    }

    private fun showError(error: String?) {
        if (error.isNullOrEmpty()) return
        mViewDataBinding?.apply {
            Snackbar.make(businessesRecyclerView, error, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCallClick(phoneNumber: String) {
        val uri = Uri.parse("tel:$phoneNumber")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }

    override fun onDirectionsClick(coordinates: Coordinates, name: String) {
        val uri = Uri.parse("geo:0,0?q=${coordinates.latitude},${coordinates.longitude}($name)")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    companion object {
        const val RECYCLER_VIEW_POSITION = "RECYCLER_VIEW_POSITION"
        const val PAGINATION_BUFFER = 10
    }

}
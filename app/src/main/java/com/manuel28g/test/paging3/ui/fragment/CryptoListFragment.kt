package com.manuel28g.test.paging3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manuel28g.test.paging3.R
import com.manuel28g.test.paging3.databinding.FragmentCryptoListBinding
import com.manuel28g.test.paging3.ui.adapter.CryptoListAdapter
import com.manuel28g.test.paging3.ui.adapter.CryptoListStatefulAdapter
import com.manuel28g.test.paging3.viewmodel.CryptoInfoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoListFragment: Fragment() {
    private lateinit var mBinding : FragmentCryptoListBinding
    private lateinit var mViewModel : CryptoInfoViewModel
    private lateinit var mAdapter: CryptoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAdapter = CryptoListAdapter(requireContext())
        mViewModel = ViewModelProvider(this).get(CryptoInfoViewModel::class.java)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_crypto_list, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.cryptoListRvContent.adapter = mAdapter
        var layout =  LinearLayoutManager(context)
        layout.orientation = LinearLayoutManager.VERTICAL
        mBinding.cryptoListRvContent.layoutManager = layout
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch{
            mViewModel.cryptoDataListObserve().collectLatest {
                it?.let { data ->
                    withContext(Dispatchers.Main){
                        mAdapter.submitData(data)
                    }
                }
            }
        }
    }
}
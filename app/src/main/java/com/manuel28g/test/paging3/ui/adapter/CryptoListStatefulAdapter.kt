package com.manuel28g.test.paging3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manuel28g.test.paging3.databinding.HeaderAndFooterStateViewItemBinding

class CryptoListStatefulAdapter(private val retry: () -> Unit):
    LoadStateAdapter<CryptoStatefulViewHolder>() {


    override fun onBindViewHolder(holder: CryptoStatefulViewHolder, loadState: LoadState) {
            holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CryptoStatefulViewHolder {

        return CryptoStatefulViewHolder.create(parent,retry)
    }

}

class CryptoStatefulViewHolder(
    private val binding: HeaderAndFooterStateViewItemBinding,
    retry: () -> Unit):
RecyclerView.ViewHolder(binding.root){

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Error) {
            binding.errorMessage.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMessage.isVisible = loadState is LoadState.Error


    }

    companion object{
        fun create(parent: ViewGroup, retry: () -> Unit): CryptoStatefulViewHolder{
            val bindingGroup = HeaderAndFooterStateViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return CryptoStatefulViewHolder(bindingGroup,retry)
        }
    }
}
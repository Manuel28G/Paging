package com.manuel28g.test.paging3.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manuel28g.test.paging3.R
import com.manuel28g.test.paging3.data.CryptoCurrency
import com.manuel28g.test.paging3.databinding.ItemCryptoCurrencyBinding

class CryptoListAdapter(private val context: Context):
    PagingDataAdapter<CryptoCurrency, CryptoViewHolder>(POST_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val bindingGroup = ItemCryptoCurrencyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CryptoViewHolder(bindingGroup, context)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = getItem(position)
        crypto?.let {
            holder.bind(it.cryptoPair,it.price )
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CryptoCurrency>() {
            override fun areContentsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean =
                oldItem.cryptoPair == newItem.cryptoPair

        }
    }

}

class CryptoViewHolder(private val binding: ItemCryptoCurrencyBinding,private val context: Context):
RecyclerView.ViewHolder(binding.root){

    fun bind(title:String, price:Float){
        binding.cryptoCurrencyTitle.text = String.format(
            context.getString(R.string.crypto_item_title),title
        )
        binding.cryptoCurrencyValue.text = String.format(
            context.getString(R.string.crypto_item_price),String.format("%.5f", price)
        )
    }
}
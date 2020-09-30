package prt.sostrovsky.onlineshopapp.ui.product.list.load_state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.databinding.ProductListFooterBinding

class ProductLoadStateViewHolder(private val binding: ProductListFooterBinding,
                                 retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.btnRetry.isVisible = loadState !is LoadState.Loading
        binding.tvErrorText.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ProductLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_list_footer, parent, false)
            val binding = ProductListFooterBinding.bind(view)
            return ProductLoadStateViewHolder(
                binding,
                retry
            )
        }
    }
}
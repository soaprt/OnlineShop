package prt.sostrovsky.onlineshopapp.ui.product.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import prt.sostrovsky.onlineshopapp.domain.Product

class ProductListAdapter :
    PagingDataAdapter<Product, RecyclerView.ViewHolder>(PRODUCT_COMPARATOR) {

    var itemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductListViewHolder.create(parent).apply {
            itemClick = { productId ->
                this@ProductListAdapter.itemClick?.invoke(productId)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val product = getItem(position)
        if (product != null) {
            (holder as ProductListViewHolder).bindView(product)
        }
    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}
package prt.sostrovsky.onlineshopapp.ui.products.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

class ProductPagingDataAdapter :
    PagingDataAdapter<ProductDTO, RecyclerView.ViewHolder>(PRODUCT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as ProductViewHolder).bindView(repoItem)
        }
    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<ProductDTO>() {
            override fun areItemsTheSame(oldItem: ProductDTO, newItem: ProductDTO): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductDTO, newItem: ProductDTO): Boolean =
                oldItem == newItem
        }
    }
}
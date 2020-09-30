package prt.sostrovsky.onlineshopapp.ui.product.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_short_data.view.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.UiComponentsUtil
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var itemClick: ((Int) -> Unit)? = null

    fun bindView(product: ProductDTO) {
        Glide.with(itemView.ivProductImage.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(itemView.ivProductImage)
        itemView.lblProductTitle.text = product.title
        itemView.lblProductShortDescription.text = product.shortDescription
        itemView.lblProductNewPrice.text = product.newPrice
        UiComponentsUtil.strikeLineThrough(itemView.lblProductOldPrice, product.oldPrice)

        itemView.setOnClickListener {
            itemClick?.invoke(product.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ProductListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_short_data, parent, false)
            return ProductListViewHolder(view)
        }
    }
}
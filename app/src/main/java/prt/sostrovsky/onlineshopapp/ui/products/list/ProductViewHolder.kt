package prt.sostrovsky.onlineshopapp.ui.products.list

import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_short_data.view.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var itemClick: ((Int) -> Unit)? = null

    fun bindView(product: ProductDTO) {
        Glide.with(itemView.ivProductImage.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(itemView.ivProductImage)
        itemView.lblProductTitle.text = product.title
        itemView.lblProductShortDescription.text = product.shortDescription
        itemView.lblProductNewPrice.text = product.newPrice
        strikeLineThrough(itemView.lblProductOldPrice, product.oldPrice)

        itemView.setOnClickListener {
            itemClick?.invoke(product.id)
        }
    }

    private fun strikeLineThrough(textView: TextView, text: String) {
        textView.setText(text, TextView.BufferType.SPANNABLE)
        val spannable = textView.text as Spannable
        spannable.setSpan(
            StrikethroughSpan(), 0, text.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

    companion object {
        fun create(parent: ViewGroup): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_short_data, parent, false)
            return ProductViewHolder(view)
        }
    }
}
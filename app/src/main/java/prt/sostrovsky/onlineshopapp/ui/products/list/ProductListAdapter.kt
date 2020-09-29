package prt.sostrovsky.onlineshopapp.ui.products.list

import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_short_data.view.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

class ProductListAdapter(private val products: ArrayList<ProductDTO>) :
    RecyclerView.Adapter<ProductListAdapter.ProductHolder>() {

    var itemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductHolder {
        val inflatedView = parent.inflate(R.layout.product_short_data, false)
        return ProductHolder(
            inflatedView
        ).apply {
            itemClick = { productId ->
                this@ProductListAdapter.itemClick?.invoke(productId)
            }
        }
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val itemProduct = products[position]
        holder.bindView(itemProduct)
    }

    class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemClick: ((Int) -> Unit)? = null
        private val imageView: ImageView = view.ivProductImage

        fun bindView(product: ProductDTO) {
            Glide.with(imageView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView)
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
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
package prt.sostrovsky.onlineshopapp.ui.products

import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_list_item_row.view.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO

class ProductListAdapter(private val tickets: ArrayList<ProductDTO>) :
    RecyclerView.Adapter<ProductListAdapter.ProductHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductHolder {
        val inflatedView = parent.inflate(R.layout.product_list_item_row, false)
        return ProductHolder(inflatedView)
    }

    override fun getItemCount(): Int = tickets.size

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val itemTicket = tickets[position]
        holder.bindTicket(itemTicket)
    }

    class ProductHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var product: ProductDTO? = null

        fun bindTicket(product: ProductDTO) {
            this.product = product
            Picasso.get()
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(view.ivProductImage)
            view.lblProductTitle.text = product.title
            view.lblProductShortDescription.text = product.shortDescription
            view.lblProductNewPrice.text = product.newPrice
            strikeLineThrough(view.lblProductOldPrice, product.oldPrice)
        }

        private fun strikeLineThrough(textView: TextView, text: String) {
            textView.setText(text, TextView.BufferType.SPANNABLE)
            val spannable = textView.text as Spannable
            spannable.setSpan(StrikethroughSpan(),0, text.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
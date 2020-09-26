package prt.sostrovsky.onlineshopapp.ui.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            view.lblProductOldPrice.text = product.oldPrice
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
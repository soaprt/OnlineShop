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

            // $price,- (price + salePercent)  $900,- 1100



            /*[
                {
                    "id": 1,
                    "title": "Apple Iphone X",
                    "short_description": "ios, ecran tactil 5.8'', GPS, 64Gb",
                    "image": "https://images-na.ssl-images-amazon.com/images/I/61lMn0%2BjtRL._SY355_.jpg",
                    "price": 900,
                    "sale_precent": 2,
                    "details": "* foarte bun\\n * ciotkii"
                }
            ]*/


//            view.lblDepartureDate.text = (view.context.getText(R.string.label_departure_date)
//                    as String).format(view.context.getText(R.string.departure_date))
//            view.departureDate.text = ticket.departureDate
//            view.departureFromTo.text = ticket.departureFromTo
//            view.departureTime.text = ticket.departureTime
//            view.carrierName.text = ticket.carrierName
//            view.flightPrice.text = ticket.flightPrice
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
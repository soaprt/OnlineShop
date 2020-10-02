package prt.sostrovsky.onlineshopapp.ui.product.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_short_data.view.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.UiComponentsUtil
import prt.sostrovsky.onlineshopapp.domain.Product

class FavoriteListAdapter(private val favorites: ArrayList<Product>) :
    RecyclerView.Adapter<FavoriteListAdapter.FavoriteHolder>() {

    var favoritesClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteHolder {
        val inflatedView = parent.inflate(R.layout.product_short_data, false)
        return FavoriteHolder(inflatedView).apply {
            favoritesClick = { productId ->
                this@FavoriteListAdapter.favoritesClick?.invoke(productId)
            }
        }
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val itemFavorite = favorites[position]
        holder.bindView(itemFavorite)
    }

    class FavoriteHolder(v: View) : RecyclerView.ViewHolder(v) {
        var favoritesClick: ((Int) -> Unit)? = null

        fun bindView(product: Product) {
            Glide.with(itemView.ivProductImage.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(itemView.ivProductImage)
            itemView.cbFavorite.isChecked = product.isFavorite
            itemView.tvProductTitle.text = product.title
            itemView.tvProductShortDescription.text = product.shortDescription
            itemView.tvProductNewPrice.text = product.newPrice
            UiComponentsUtil.strikeLineThrough(itemView.tvProductOldPrice, product.oldPrice)

            itemView.cbFavorite.setOnClickListener {
                favoritesClick?.invoke(product.id)
            }
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
}
package prt.sostrovsky.onlineshopapp.ui.product.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.product_short_data.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.UiComponentsUtil
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.ui.SecondaryFragment
import prt.sostrovsky.onlineshopapp.ui.product.ProductViewModel
import prt.sostrovsky.onlineshopapp.ui.product.ProductViewModelInjection

class ProductDetailsFragment : SecondaryFragment(R.layout.fragment_product_details) {
    private val passedArgs: ProductDetailsFragmentArgs by navArgs()

    private var changeFavoriteStateJob: Job? = null
    private var getProductJob: Job? = null

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFavoriteButton()
        getProduct()
    }

    private fun setFavoriteButton() {
        cbFavorite.setOnClickListener {
            invertFavoriteState(passedArgs.productId)
        }
    }

    private fun invertFavoriteState(productId: Int) {
        changeFavoriteStateJob?.cancel()
        changeFavoriteStateJob = lifecycleScope.launch {
            viewModel.invertFavoriteState(productId)
        }
    }

    @ExperimentalCoroutinesApi
    private fun getProduct() {
        getProductJob?.cancel()
        getProductJob = lifecycleScope.launch {
            viewModel.getProductBy(passedArgs.productId)?.let {
                show(it)
            }
        }
    }

    private fun show(product: Product) {
        Glide.with(ivProductImage.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(ivProductImage)
        cbFavorite.isChecked = product.isFavorite
        tvProductTitle.text = product.title
        tvProductShortDescription.text = product.shortDescription
        tvProductNewPrice.text = product.newPrice
        UiComponentsUtil.strikeLineThrough(tvProductOldPrice, product.oldPrice)
        lblDescriptionBody.text = product.details
    }
}
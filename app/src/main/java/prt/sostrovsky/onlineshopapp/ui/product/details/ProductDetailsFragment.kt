package prt.sostrovsky.onlineshopapp.ui.product.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
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
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO
import prt.sostrovsky.onlineshopapp.ui.MainActivity
import prt.sostrovsky.onlineshopapp.ui.product.ProductViewModel
import prt.sostrovsky.onlineshopapp.ui.product.ProductViewModelInjection

class ProductDetailsFragment : Fragment() {
    private lateinit var viewModel: ProductViewModel
    private lateinit var backButtonCallback: OnBackPressedCallback

    private val passedArgs: ProductDetailsFragmentArgs by navArgs()

    private var getProductJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackButtonCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_product_details, container,
            false
        )
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        getProduct()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarButtons()
    }

    @ExperimentalCoroutinesApi
    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            ProductViewModelInjection.provideViewModelFactory()
        )
            .get(ProductViewModel::class.java)
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

    private fun setToolbarButtons() {
        setBackButton()
    }

    private fun setBackButton() {
        (activity as MainActivity).backButtonEnable(callback = backButtonCallback)
    }

    private fun setBackButtonCallback() {
        backButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallback)
    }

    private fun show(product: ProductDTO) {
        Glide.with(ivProductImage.context)
            .load(product.imageUrl)
            .into(ivProductImage)
        lblProductTitle.text = product.title
        lblProductShortDescription.text = product.shortDescription
        lblProductNewPrice.text = product.newPrice
        UiComponentsUtil.strikeLineThrough(lblProductOldPrice, product.oldPrice)
        lblDescriptionBody.text = product.details
    }
}
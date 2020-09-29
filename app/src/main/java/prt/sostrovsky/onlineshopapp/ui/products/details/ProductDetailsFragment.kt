package prt.sostrovsky.onlineshopapp.ui.products.details

import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.product_short_data.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO
import prt.sostrovsky.onlineshopapp.ui.MainActivity
import prt.sostrovsky.onlineshopapp.ui.products.ProductsViewModel

class ProductDetailsFragment : Fragment() {
    private lateinit var viewModel: ProductsViewModel
    private val passedArgs: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_product_details, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarButtons()
    }

    private fun setToolbarButtons() {
        setBackButton()
    }

    private fun setBackButton() {
        (activity as MainActivity).backButtonEnable(callback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductListFragment()
                (activity as MainActivity).moveTo(action)
            }
        })
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

//        viewModel.fetchProduct(passedArgs.productId)
//            .observe(viewLifecycleOwner, Observer { product ->
//                product?.let { show(it) }
//            })
    }

    private fun show(product: ProductDTO) {
        Glide.with(ivProductImage.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(ivProductImage)
        lblProductTitle.text = product.title
        lblProductShortDescription.text = product.shortDescription
        lblProductNewPrice.text = product.newPrice
        strikeLineThrough(lblProductOldPrice, product.oldPrice)
        lblDescriptionBody.text = product.details
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
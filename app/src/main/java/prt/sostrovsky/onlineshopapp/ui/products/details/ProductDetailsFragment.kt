package prt.sostrovsky.onlineshopapp.ui.products.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.databinding.FragmentProductDetailsBinding
import prt.sostrovsky.onlineshopapp.ui.products.ProductsViewModel

class ProductDetailsFragment: Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var viewModel: ProductsViewModel
    private val passedArgs: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        viewModel.fetchProduct(passedArgs.productId)
    }
}
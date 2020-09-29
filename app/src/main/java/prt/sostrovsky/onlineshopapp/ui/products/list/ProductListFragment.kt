package prt.sostrovsky.onlineshopapp.ui.products.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import prt.sostrovsky.onlineshopapp.ui.products.ProductInjection
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.ui.MainActivity
import prt.sostrovsky.onlineshopapp.ui.products.ProductsViewModel

@ExperimentalCoroutinesApi
class ProductListFragment : Fragment() {
    private lateinit var viewModel: ProductsViewModel
    private val adapter = ProductPagingDataAdapter()

    private var getProductsJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setRecyclerView()
        getProducts()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarButtons()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this,
            ProductInjection.provideViewModelFactory(requireContext()))
            .get(ProductsViewModel::class.java)
    }

    private fun getProducts() {
        // Make sure we cancel the previous job before creating a new one
        getProductsJob?.cancel()
        getProductsJob = lifecycleScope.launch {
            viewModel.getProducts().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setToolbarButtons() {
        (activity as MainActivity).backButtonDisable()
    }

    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }
}


/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_product_list.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.databinding.FragmentProductListBinding
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO
import prt.sostrovsky.onlineshopapp.ui.MainActivity
import prt.sostrovsky.onlineshopapp.ui.products.ProductsViewModel

class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product_list, container,
            false
        )
        return binding.root
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
        (activity as MainActivity).backButtonDisable()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        viewModel.webServiceIsUnavailable.observe(
            viewLifecycleOwner,
            Observer { webServiceIsUnavailable ->
                if (webServiceIsUnavailable) {
                    (activity as MainActivity).showSnackBarEvent(
                        getString(R.string.web_service_unavailable_error)
                    )
                }
            })

        viewModel.fetchProducts().observe(viewLifecycleOwner, Observer { products ->
            setRecyclerView(products)
        })
    }

    private fun setRecyclerView(tickets: List<ProductDTO>) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager

        val adapter = ProductListAdapter(
            tickets as ArrayList<ProductDTO>
        ).apply {
            itemClick = { productId ->
                viewModel.selectedProductIsLoaded = false
                showProduct(productId)
            }
        }
        recyclerView.adapter = adapter
        adapter.notifyItemInserted(tickets.size - 1)
    }

    private fun showProduct(productId: Int) {
        val action =
            ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(
                productId
            )
        (activity as MainActivity).moveTo(action)
    }
}
 */
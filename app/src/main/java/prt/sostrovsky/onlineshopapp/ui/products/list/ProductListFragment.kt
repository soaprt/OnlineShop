package prt.sostrovsky.onlineshopapp.ui.products.list

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
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO
import prt.sostrovsky.onlineshopapp.ui.MainActivity
import prt.sostrovsky.onlineshopapp.ui.products.ProductsViewModel

class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        viewModel.webServiceIsUnavailable.observe(viewLifecycleOwner, Observer {
            webServiceIsUnavailable -> if (webServiceIsUnavailable) {
                (activity as MainActivity).showSnackBarEvent(
                    getString(R.string.web_service_unavailable_error)
                )
            }
        })

        viewModel.fetchProducts().observe(viewLifecycleOwner, Observer {products ->
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
                showProduct(productId)
            }
        }
        recyclerView.adapter = adapter
        adapter.notifyItemInserted(tickets.size - 1)
    }

    private fun showProduct(productId: Int) {
        val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(productId)
        (activity as MainActivity).moveTo(action)
    }
}
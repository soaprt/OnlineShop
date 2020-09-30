package prt.sostrovsky.onlineshopapp.ui.product.list

// import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.databinding.FragmentProductListBinding
import prt.sostrovsky.onlineshopapp.ui.MainActivity
import prt.sostrovsky.onlineshopapp.ui.product.ProductViewModel
import prt.sostrovsky.onlineshopapp.ui.product.ProductViewModelInjection
import prt.sostrovsky.onlineshopapp.ui.product.details.ProductDetailsFragment
import prt.sostrovsky.onlineshopapp.ui.product.list.load_state.ProductLoadStateAdapter

@ExperimentalCoroutinesApi
class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var viewModel: ProductViewModel
    private val adapter = ProductListAdapter()

    private var getProductsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
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
        viewModel = ViewModelProvider(
            this,
            ProductViewModelInjection.provideViewModelFactory()
        )
            .get(ProductViewModel::class.java)
    }

    private fun getProducts() {
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
        binding.recyclerView.layoutManager = linearLayoutManager

        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = ProductLoadStateAdapter { adapter.retry() }
        )

        adapter.apply {
            itemClick = { productId ->
                showProduct(productId)
            }
        }
    }

    private fun showProduct(productId: Int) {
        val detailsFragment = ProductDetailsFragment().apply {
            this.arguments = Bundle().apply {
                putInt("product_id", productId)
            }
        }
        (activity as MainActivity).addFragmentToBackStack(detailsFragment)
    }
}
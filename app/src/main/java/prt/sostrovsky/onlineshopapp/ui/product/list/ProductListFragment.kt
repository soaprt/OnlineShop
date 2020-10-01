package prt.sostrovsky.onlineshopapp.ui.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.databinding.FragmentProductListBinding
import prt.sostrovsky.onlineshopapp.ui.MainActivity
import prt.sostrovsky.onlineshopapp.ui.favorites.FavoritesFragment
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
        setBackStackChangeListener()
        setRecyclerView()
        setRetryButton()
        getProducts()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarButtons()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            ProductViewModelInjection.provideViewModelFactory(requireContext())
        )
            .get(ProductViewModel::class.java)
    }

    private fun setRecyclerView() {
        binding.rvProducts.adapter = adapter.withLoadStateFooter(
            footer = ProductLoadStateAdapter { adapter.retry() }
        )

        adapter.apply {
            itemClick = { productId ->
                showProduct(productId)
            }
        }

        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.rvProducts.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.pgLoad.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.clLoadDataError.tvErrorText.isVisible =
                loadState.source.refresh is LoadState.Error
            binding.clLoadDataError.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    private fun setRetryButton() {
        binding.clLoadDataError.btnRetry.setOnClickListener { adapter.retry() }
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
        enableFavoritesButton()
    }


    private fun enableFavoritesButton() {
        (activity as MainActivity).favoritesButtonEnable(callback = View.OnClickListener {
            showFavorites()
        })
    }

    private fun setBackStackChangeListener() {
        requireActivity().supportFragmentManager.apply {
            addOnBackStackChangedListener {
                if (backStackEntryCount == 0) {
                    enableFavoritesButton()
                }
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

    private fun showFavorites() {
        val favoritesFragment = FavoritesFragment()
        (activity as MainActivity).addFragmentToBackStack(favoritesFragment)
    }
}
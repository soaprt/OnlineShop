package prt.sostrovsky.onlineshopapp.ui.product.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_favorite_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.ui.SecondaryFragment

class FavoriteListFragment : SecondaryFragment(R.layout.fragment_favorite_list) {
    private var getFavoritesJob: Job? = null
    private var changeFavoriteStateJob: Job? = null

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavorites()
    }

    private fun getFavorites() {
        getFavoritesJob?.cancel()
        getFavoritesJob = lifecycleScope.launch {
            viewModel.getFavorites().let {
                if (it.isNotEmpty()) {
                    showFavorites(it)
                } else {
                    showEmptyMessage()
                }
            }
        }
    }

    private fun showFavorites(list: List<Product>) {
        val adapter = FavoriteListAdapter(list as ArrayList<Product>)
        rvFavorites.adapter = adapter.apply {
            favoritesClick = { productId, listIsEmpty ->
                invertFavoriteState(productId)

                if (listIsEmpty) showEmptyMessage()
            }
        }
        adapter.notifyItemInserted(list.size - 1)
    }

    private fun showEmptyMessage() {
        tvEmptyList.visibility = View.VISIBLE
    }

    private fun invertFavoriteState(productId: Int) {
        changeFavoriteStateJob?.cancel()
        changeFavoriteStateJob = lifecycleScope.launch {
            viewModel.invertFavoriteState(productId)
        }
    }
}
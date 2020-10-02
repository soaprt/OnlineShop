package prt.sostrovsky.onlineshopapp.ui.product.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_favorite_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.ui.SecondaryFragment

class FavoriteListFragment : SecondaryFragment(R.layout.fragment_favorite_list) {
    private var getFavoritesJob: Job? = null

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
        rvFavorites.adapter = adapter
        adapter.notifyItemInserted(list.size - 1)
    }

    private fun showEmptyMessage() {
        tvEmptyList.visibility = View.VISIBLE
    }
}
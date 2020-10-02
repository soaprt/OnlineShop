package prt.sostrovsky.onlineshopapp.ui.product.favorites

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_favorites.*
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.ui.SecondaryFragment

class FavoritesFragment : SecondaryFragment(R.layout.fragment_favorites) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvEmptyList.visibility = View.VISIBLE
    }
}
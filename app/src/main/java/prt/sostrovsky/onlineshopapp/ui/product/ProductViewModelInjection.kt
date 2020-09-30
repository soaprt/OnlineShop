package prt.sostrovsky.onlineshopapp.ui.product

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import prt.sostrovsky.onlineshopapp.database.OnlineShopDatabase
import prt.sostrovsky.onlineshopapp.datasource.ProductRepository
import prt.sostrovsky.onlineshopapp.remote.Remote

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object ProductViewModelInjection {
    private fun provideProductRepository(context: Context): ProductRepository {
        return ProductRepository(
            Remote.productApi,
            OnlineShopDatabase.getInstance(context)
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ProductViewModelFactory(provideProductRepository(context))
    }
}
package prt.sostrovsky.onlineshopapp.ui.products

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import prt.sostrovsky.onlineshopapp.data.ProductRepository
import prt.sostrovsky.onlineshopapp.service.WebService

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object ProductInjection {

    /**
     * Creates an instance of [ProductRepository] based on the [ProductService]
     */
    @ExperimentalCoroutinesApi
    private fun provideProductRepository(context: Context): ProductRepository {
        return ProductRepository(WebService.productsService)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ProductsViewModelFactory(
            provideProductRepository(
                context
            )
        )
    }
}

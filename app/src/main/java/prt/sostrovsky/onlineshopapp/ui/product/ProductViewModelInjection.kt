package prt.sostrovsky.onlineshopapp.ui.product

import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import prt.sostrovsky.onlineshopapp.repository.ProductRepository
import prt.sostrovsky.onlineshopapp.service.WebService

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object ProductViewModelInjection {

    /**
     * Creates an instance of [ProductRepository] based on the [ProductService]
     */
    @ExperimentalCoroutinesApi
    private fun provideProductRepository(): ProductRepository {
        return ProductRepository(WebService.productsService)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    @ExperimentalCoroutinesApi
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ProductViewModelFactory(
            provideProductRepository()
        )
    }
}

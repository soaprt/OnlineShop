package prt.sostrovsky.onlineshopapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import prt.sostrovsky.onlineshopapp.datasource.ProductRepository

/**
 * Factory for ViewModels
 */
class ProductViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    @ExperimentalCoroutinesApi
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

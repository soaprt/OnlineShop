package prt.sostrovsky.onlineshopapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import prt.sostrovsky.onlineshopapp.datasource.ProductRepository
import prt.sostrovsky.onlineshopapp.domain.Product

class ProductViewModel @ExperimentalCoroutinesApi constructor(private val repository: ProductRepository) :
    ViewModel() {
    private var productsResult: Flow<PagingData<Product>>? = null

    @ExperimentalCoroutinesApi
    fun getProducts(): Flow<PagingData<Product>> {
        productsResult = repository.getProducts().cachedIn(viewModelScope)
        return productsResult as Flow<PagingData<Product>>
    }

    private var product: Product? = null

    @ExperimentalCoroutinesApi
    suspend fun getProductBy(id: Int): Product? {
        if (product == null) {
            product = repository.getProductBy(id)
        }
        return product
    }

    suspend fun invertFavoriteState(productId: Int) {
        repository.invertFavoriteState(productId)
    }
}
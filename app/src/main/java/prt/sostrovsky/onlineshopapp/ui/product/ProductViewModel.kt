package prt.sostrovsky.onlineshopapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prt.sostrovsky.onlineshopapp.database.entity.asDomainModel
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.repository.ProductRepository

class ProductViewModel @ExperimentalCoroutinesApi constructor(private val repository: ProductRepository) :
    ViewModel() {
    private var productsResult: Flow<PagingData<Product>>? = null

    @ExperimentalCoroutinesApi
    fun getProducts(): Flow<PagingData<Product>> {
        productsResult = repository.getProducts().map { pagingData ->
            pagingData.map { it.asDomainModel() }
        }.cachedIn(viewModelScope)

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
}
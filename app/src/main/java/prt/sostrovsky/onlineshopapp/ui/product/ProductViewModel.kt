package prt.sostrovsky.onlineshopapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import prt.sostrovsky.onlineshopapp.repository.ProductRepository
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

class ProductViewModel @ExperimentalCoroutinesApi constructor(private val repository: ProductRepository) :
    ViewModel() {
    private var productsResult: Flow<PagingData<ProductDTO>>? = null

    @ExperimentalCoroutinesApi
    fun getProducts(): Flow<PagingData<ProductDTO>> {
        productsResult = repository.getProducts().cachedIn(viewModelScope)
        return productsResult as Flow<PagingData<ProductDTO>>
    }

    private var product: ProductDTO? = null

    @ExperimentalCoroutinesApi
    suspend fun getProductBy(id: Int): ProductDTO? {
        if (product == null) {
            product = repository.getProductBy(id)
        }
        return product
    }
}
package prt.sostrovsky.onlineshopapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.repository.ProductsFactory
import prt.sostrovsky.onlineshopapp.service.ProductService
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

/**
 * Repository class that works with local and remote data sources.
 */
@ExperimentalCoroutinesApi
class ProductRepository(private val service: ProductService) {
    fun getProducts(): Flow<PagingData<ProductDTO>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(service) }
        ).flow
    }

    suspend fun getProductBy(id: Int): ProductDTO? {
        var product: ProductDTO? = null

        withContext(Dispatchers.IO) {
            product = ProductsFactory.getFactory().fetchProductBy(id)
        }

        return product
    }

    companion object {
        private const val INITIAL_LOAD_SIZE = 1
        private const val PAGE_SIZE = 10
    }
}
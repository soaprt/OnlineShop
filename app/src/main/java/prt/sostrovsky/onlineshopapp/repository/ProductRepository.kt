package prt.sostrovsky.onlineshopapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.database.OnlineShopDatabase
import prt.sostrovsky.onlineshopapp.database.entity.ProductEntity
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.service.ProductRemoteMediator
import prt.sostrovsky.onlineshopapp.service.ProductService


class ProductRepository(
    private val service: ProductService,
    private val database: OnlineShopDatabase
) {
    fun getProducts(): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ProductRemoteMediator(service, database),
            pagingSourceFactory = { ProductPagingSource(database) }
        ).flow
    }

    suspend fun getProductBy(id: Int): Product? {
        var product: Product? = null

        withContext(Dispatchers.IO) {
            product = ProductFactory.getFactory().fetchProductBy(id)
        }

        return product
    }

    companion object {
        private const val INITIAL_LOAD_SIZE = 1
        private const val PAGE_SIZE = 10
    }
}
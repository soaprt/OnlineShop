package prt.sostrovsky.onlineshopapp.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.database.OnlineShopDatabase
import prt.sostrovsky.onlineshopapp.database.ProductDTO
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.remote.ProductApi


class ProductRepository(
    private val api: ProductApi,
    private val database: OnlineShopDatabase
) {
    fun getProducts(): Flow<PagingData<ProductDTO>> {
        val pagingSourceFactory = { database.productDao().getProducts() }

        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ProductsRemoteMediator(
                api,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getProductBy(id: Int): Product? {
        var product: Product? = null
        val dataSource: DataSource<Product> =
            ProductSource(
                api,
                database,
                id
            )

        withContext(Dispatchers.IO) {
            product = dataSource.getData()
        }

        return product
    }

    suspend fun invertFavoriteState(productId: Int) {
        withContext(Dispatchers.IO) {
            database.productDao().invertFavoriteState(productId)
        }
    }

    companion object {
        private const val INITIAL_LOAD_SIZE = 1
        private const val PAGE_SIZE = 10
    }
}
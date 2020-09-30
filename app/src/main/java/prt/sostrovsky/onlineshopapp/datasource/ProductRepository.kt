package prt.sostrovsky.onlineshopapp.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.database.OnlineShopDatabase
import prt.sostrovsky.onlineshopapp.database.ProductDTO
import prt.sostrovsky.onlineshopapp.datasource.product_paging.ProductPagingSource
import prt.sostrovsky.onlineshopapp.datasource.product_paging.ProductPagingRemoteMediator
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.remote.ProductApi


class ProductRepository(
    private val api: ProductApi,
    private val database: OnlineShopDatabase
) {
    fun getProducts(): Flow<PagingData<ProductDTO>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ProductPagingRemoteMediator(
                api,
                database
            ),
            pagingSourceFactory = {
                ProductPagingSource(
                    database
                )
            }
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

    companion object {
        private const val INITIAL_LOAD_SIZE = 1
        private const val PAGE_SIZE = 10
    }
}
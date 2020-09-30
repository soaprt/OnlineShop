package prt.sostrovsky.onlineshopapp.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.database.OnlineShopDatabase
import prt.sostrovsky.onlineshopapp.database.entity.ProductEntity


class ProductPagingSource(private val database: OnlineShopDatabase) :
    PagingSource<Int, ProductEntity>() {
    // the initial load size for the first page may be different from the requested size
    private var initialLoadSize: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        try {
            val nextPageNumber = params.key ?: 1

            if (params.key == null) {
                initialLoadSize = params.loadSize
            }

            // work out the offset into the database to retrieve records from the page number,
            // allow for a different load size for the first page
            val offsetCalc = {
                if (nextPageNumber == 2)
                    initialLoadSize
                else
                    ((nextPageNumber - 1) * params.loadSize) + (initialLoadSize - params.loadSize)
            }
            val offset = offsetCalc.invoke()

            val products = mutableListOf<ProductEntity>()

            withContext(Dispatchers.IO) {
                products.addAll(database.productDao().getProducts(offset, params.loadSize))
            }

            return LoadResult.Page(
                data = products,
                prevKey = null,
                nextKey = if (products.size < params.loadSize) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
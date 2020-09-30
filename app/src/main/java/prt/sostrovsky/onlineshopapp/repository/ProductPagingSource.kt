package prt.sostrovsky.onlineshopapp.repository

import androidx.paging.PagingSource
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.service.ProductService
import prt.sostrovsky.onlineshopapp.service.response.asDomainModel

class ProductPagingSource(private val service: ProductService) : PagingSource<Int, Product>() {

    // the initial load size for the first page may be different from the requested size
    private var initialLoadSize: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1

            if (params.key == null){
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

            val response = service.fetchProductsAsync(offset, params.loadSize).await()
            val products = response.body()!!.map {
                it.asDomainModel()
            }
            val count = products.size

            return LoadResult.Page(
                data = products,
                prevKey = null,
                nextKey = if (count < params.loadSize) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}

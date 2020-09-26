package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.network.WebService
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO
import prt.sostrovsky.onlineshopapp.network.response.safeApiCall

object ProductsOnlineFetcher : ProductsFetcher {
    override suspend fun fetch(offset: Int, limit: Int): List<ProductDTO> {
        return fetchFromWebService(offset, limit) ?: emptyList()
    }

    private suspend fun fetchFromWebService(offset: Int, limit: Int):  List<ProductDTO>? {
        return safeApiCall(
            call = {
                WebService.productsService.fetchProductsAsync(offset, limit).await()
            },
            error = "Error fetching products"
        )
    }
}
package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.service.WebService
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO
import prt.sostrovsky.onlineshopapp.service.response.safeApiCall

object ProductsOnlineFetcher : ProductsFetcher {
    override suspend fun fetchProductBy(id: Int): ProductDTO? {
        return fetchProductFromWebService(id)
    }

    private suspend fun fetchProductFromWebService(id: Int):  ProductDTO? {
        return safeApiCall(
            call = {
                WebService.productsService.fetchProductAsync(id).await()
            },
            error = "Error fetching product"
        )
    }
}
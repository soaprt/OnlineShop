package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.service.WebService
import prt.sostrovsky.onlineshopapp.service.response.ProductResponse
import prt.sostrovsky.onlineshopapp.service.response.asDomainModel
import prt.sostrovsky.onlineshopapp.service.response.safeApiCall

object ProductOnlineFetcher : ProductFetcher {
    override suspend fun fetchProductBy(id: Int): Product? {
        return fetchProductFromWebService(id)?.asDomainModel()
    }

    private suspend fun fetchProductFromWebService(id: Int):  ProductResponse? {
        return safeApiCall(
            call = {
                WebService.productsService.fetchProductAsync(id).await()
            },
            error = "Error fetching product"
        )
    }
}
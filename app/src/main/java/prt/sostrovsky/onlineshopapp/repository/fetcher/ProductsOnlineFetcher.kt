package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.service.WebService
import prt.sostrovsky.onlineshopapp.service.WebServiceUnavailableException
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO
import prt.sostrovsky.onlineshopapp.service.response.safeApiCall

object ProductsOnlineFetcher : ProductsFetcher {
    override suspend fun fetchProducts(offset: Int, limit: Int): List<ProductDTO>  {
        val result = mutableListOf<ProductDTO>()

        try {
            fetchProductsFromWebService(offset, limit)?.let { result.addAll(it) }
        } catch (ex: Exception) {
            throw WebServiceUnavailableException(
                "ProductsOnlineFetcher: fetchProducts(): error"
            )
        }
        return result
    }

    override suspend fun fetchProductBy(id: Int): ProductDTO? {
        return fetchProductFromWebService(id)
    }

    private suspend fun fetchProductsFromWebService(offset: Int, limit: Int):  List<ProductDTO>? {
        return safeApiCall(
            call = {
                WebService.productsService.fetchProductsAsync(offset, limit).await()
            },
            error = "Error fetching products"
        )
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
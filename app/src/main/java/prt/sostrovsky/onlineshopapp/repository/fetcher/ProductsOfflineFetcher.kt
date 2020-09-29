package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

object ProductsOfflineFetcher :
    ProductsFetcher {
    override suspend fun fetchProductBy(id: Int): ProductDTO? {
        return null
    }
}
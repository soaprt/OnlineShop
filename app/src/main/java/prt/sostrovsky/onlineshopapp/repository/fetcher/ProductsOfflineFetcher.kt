package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.network.response.ProductDTO

object ProductsOfflineFetcher :
    ProductsFetcher {
    override suspend fun fetch(offset: Int, limit: Int) : List<ProductDTO> {
        return emptyList()
    }
}
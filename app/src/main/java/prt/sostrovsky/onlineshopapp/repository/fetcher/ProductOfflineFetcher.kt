package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.domain.Product

object ProductOfflineFetcher :
    ProductFetcher {
    override suspend fun fetchProductBy(id: Int): Product? {
        return null
    }
}
package prt.sostrovsky.onlineshopapp.repository.fetcher

object ProductsOfflineFetcher :
    ProductsFetcher {
    override suspend fun fetch(): List<String> {
        return mutableListOf("Product from the cache")
    }
}
package prt.sostrovsky.onlineshopapp.repository.fetcher

object ProductsOnlineFetcher :
    ProductsFetcher {
    override suspend fun fetch(): List<String> {
        return mutableListOf("Product from the web")
    }
}
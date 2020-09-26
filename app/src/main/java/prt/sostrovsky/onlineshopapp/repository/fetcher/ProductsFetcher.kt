package prt.sostrovsky.onlineshopapp.repository.fetcher

interface ProductsFetcher {
    suspend fun fetch() : List<String>
}
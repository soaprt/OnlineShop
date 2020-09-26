package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.network.response.ProductDTO

interface ProductsFetcher {
    suspend fun fetch(offset: Int, limit: Int) : List<ProductDTO>
}
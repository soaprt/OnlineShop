package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.network.response.ProductDTO

interface ProductsFetcher {
    suspend fun fetchProducts(offset: Int, limit: Int) : List<ProductDTO>
    suspend fun fetchProductBy(id: Int) : ProductDTO?
}
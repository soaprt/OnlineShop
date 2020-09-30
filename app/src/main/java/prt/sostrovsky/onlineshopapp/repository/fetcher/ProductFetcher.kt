package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

interface ProductFetcher {
    suspend fun fetchProductBy(id: Int) : ProductDTO?
}
package prt.sostrovsky.onlineshopapp.repository.fetcher

import prt.sostrovsky.onlineshopapp.domain.Product

interface ProductFetcher {
    suspend fun fetchProductBy(id: Int) : Product?
}
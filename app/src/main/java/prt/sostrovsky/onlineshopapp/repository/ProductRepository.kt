package prt.sostrovsky.onlineshopapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO

object ProductRepository {
    suspend fun getProducts(offset: Int, limit: Int) : List<ProductDTO> {
        val products = mutableListOf<ProductDTO>()

        withContext(Dispatchers.IO) {
            products.addAll(ProductsFactory.getFactory().fetch(offset, limit))
        }

        return products
    }
}
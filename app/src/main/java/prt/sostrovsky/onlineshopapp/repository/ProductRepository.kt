package prt.sostrovsky.onlineshopapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO

object ProductRepository {
    suspend fun getProducts(offset: Int, limit: Int) : List<ProductDTO> {
        val products = mutableListOf<ProductDTO>()

        withContext(Dispatchers.IO) {
            products.addAll(ProductsFactory.getFactory().fetchProducts(offset, limit))
        }

        return products
    }

    suspend fun getProductBy(id: Int): ProductDTO? {
        var product: ProductDTO? = null

        withContext(Dispatchers.IO) {
            product = ProductsFactory.getFactory().fetchProductBy(id)
        }

        return product
    }
}
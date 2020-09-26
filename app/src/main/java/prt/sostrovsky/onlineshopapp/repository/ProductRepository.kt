package prt.sostrovsky.onlineshopapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ProductRepository {
    suspend fun getProducts() : List<String> {
        val products = mutableListOf<String>()

        withContext(Dispatchers.IO) {
            products.addAll(ProductsFactory.getFactory().fetch())
        }

        return products
    }
}
package prt.sostrovsky.onlineshopapp.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.database.OnlineShopDatabase
import prt.sostrovsky.onlineshopapp.database.asDomainModel
import prt.sostrovsky.onlineshopapp.domain.Product
import prt.sostrovsky.onlineshopapp.remote.ProductApi
import prt.sostrovsky.onlineshopapp.remote.response.safeApiCall
import timber.log.Timber

class ProductSource(
    private val api: ProductApi,
    private val database: OnlineShopDatabase,
    private val productId: Int) :
    DataSource<Product> {

    override suspend fun getData(): Product? {
        return getProductFromDB() ?: getProductFromRemote()
    }

    private suspend fun getProductFromDB(): Product? {
        val product: Product?

        withContext(Dispatchers.IO) {
            product = database.productDao().getProductById(productId)?.asDomainModel()
        }

        return product
    }

    private suspend fun getProductFromRemote() : Product? {
        return safeApiCall(
            call = {
                api.getProductAsync(productId).await()
            },
            error = "Error fetching product"
        )?.asDomainModel()
    }
}
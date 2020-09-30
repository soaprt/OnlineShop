package prt.sostrovsky.onlineshopapp.service

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import prt.sostrovsky.onlineshopapp.database.OnlineShopDatabase
import prt.sostrovsky.onlineshopapp.database.entity.ProductEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val service: ProductService,
    private val database: OnlineShopDatabase
) : RemoteMediator<Int, ProductEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> {
                0
            }
            LoadType.PREPEND -> {
                0
            }
            LoadType.APPEND -> {
                getOffsetForAppend()
            }
        }

        try {
            val products = mutableListOf<ProductEntity>()

            withContext(Dispatchers.IO) {
                if (loadType == LoadType.REFRESH || loadType == LoadType.APPEND) {
                    val response = service.fetchProductsAsync(offset, state.config.pageSize).await()

                    if (response.isSuccessful) {
                        products.addAll(response.body()!!)
                    }

                    database.productDao().insertAll(products)
                    val rowsInDB = database.productDao().getProductsAmount()
                }
            }
            return MediatorResult.Success(endOfPaginationReached = products.isEmpty())
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getOffsetForAppend(): Int {
        var result = mutableListOf<Int>()

        withContext(Dispatchers.IO) {
            result.add(database.productDao().getProductsAmount())
        }

        return result[0]
    }
}
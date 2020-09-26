package prt.sostrovsky.onlineshopapp.network

import kotlinx.coroutines.Deferred
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("products")
    fun fetchProductsAsync(@Query("offset") offset: Int, @Query("limit") limit: Int) :
            Deferred<Response<List<ProductDTO>>>
}
package prt.sostrovsky.onlineshopapp.service

import kotlinx.coroutines.Deferred
import prt.sostrovsky.onlineshopapp.service.response.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    fun fetchProductsAsync(@Query("offset") offset: Int, @Query("limit") limit: Int) :
            Deferred<Response<List<ProductResponse>>>

    @GET("product")
    fun fetchProductAsync(@Query("id") id: Int): Deferred<Response<ProductResponse>>
}
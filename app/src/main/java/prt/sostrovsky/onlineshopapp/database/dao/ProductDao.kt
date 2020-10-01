package prt.sostrovsky.onlineshopapp.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import prt.sostrovsky.onlineshopapp.database.ProductDTO

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductDTO>)

    @Query("SELECT COUNT(*) FROM product")
    fun getProductsAmount(): Int

    @Query("SELECT * FROM product")
    fun getProducts(): PagingSource<Int, ProductDTO>

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Int): ProductDTO?
}
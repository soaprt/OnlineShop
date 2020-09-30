package prt.sostrovsky.onlineshopapp.database.dao

import androidx.room.*
import prt.sostrovsky.onlineshopapp.database.ProductDTO

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductDTO>)

    @Query("SELECT COUNT(*) FROM product")
    fun getProductsAmount(): Int

    @Query("SELECT * FROM product LIMIT :pageSize OFFSET :offset")
    fun getProducts(offset: Int, pageSize: Int): List<ProductDTO>

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Int): ProductDTO?
}
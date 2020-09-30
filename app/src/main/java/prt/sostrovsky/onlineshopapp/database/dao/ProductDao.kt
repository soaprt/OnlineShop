package prt.sostrovsky.onlineshopapp.database.dao

import androidx.room.*
import prt.sostrovsky.onlineshopapp.database.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT COUNT(*) FROM product")
    fun getProductsAmount(): Int

    @Query("SELECT * FROM product LIMIT :pageSize OFFSET :offset")
    fun getProducts(offset: Int, pageSize: Int): List<ProductEntity>

    @Query("DELETE FROM product")
    suspend fun clearProducts()

    @Transaction
    suspend fun insertAndDeleteInTransaction(products: List<ProductEntity>): Int {
        clearProducts()
        insertAll(products)
        return getProductsAmount()
    }
}
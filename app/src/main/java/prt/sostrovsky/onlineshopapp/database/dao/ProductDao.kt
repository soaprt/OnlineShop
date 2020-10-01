package prt.sostrovsky.onlineshopapp.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import prt.sostrovsky.onlineshopapp.database.ProductDTO

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(products: List<ProductDTO>)

    @Query("SELECT COUNT(*) FROM product")
    fun getProductsAmount(): Int

    @Query("SELECT * FROM product")
    fun getProducts(): PagingSource<Int, ProductDTO>

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Int): ProductDTO?

    @Query("UPDATE product SET favorite_state = :favoriteState WHERE id = :productId")
    suspend fun updateFavoriteState(productId: Int, favoriteState: Int)

    @Transaction
    suspend fun invertFavoriteState(productId: Int) {
        getProductById(productId)?.let {
            val newFavoriteState = if (it.favorite_state == 1) 0 else 1
            updateFavoriteState(productId, newFavoriteState)
        }
    }
}
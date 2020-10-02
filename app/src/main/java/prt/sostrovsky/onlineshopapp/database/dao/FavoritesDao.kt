package prt.sostrovsky.onlineshopapp.database.dao

import androidx.room.*
import prt.sostrovsky.onlineshopapp.database.FavoritesDTO

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(products: List<FavoritesDTO>)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<FavoritesDTO>

    @Query("SELECT * FROM favorites WHERE product_id = :productId")
    fun getFavoritesById(productId: Int): FavoritesDTO?

    @Query("UPDATE favorites SET favorite_state = :favoriteState WHERE product_id = :productId")
    suspend fun updateFavoriteState(productId: Int, favoriteState: Int)

    @Update
    suspend fun update(obj: FavoritesDTO)
}
package prt.sostrovsky.onlineshopapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import prt.sostrovsky.onlineshopapp.database.dao.FavoritesDao
import prt.sostrovsky.onlineshopapp.database.dao.ProductDao

@Database(
    entities = [FavoritesDTO::class, ProductDTO::class],
    version = 1,
    exportSchema = false
)
abstract class OnlineShopDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun productDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: OnlineShopDatabase? = null

        fun getInstance(context: Context): OnlineShopDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                OnlineShopDatabase::class.java, "OnlineShop.db"
            )
                .build()
    }
}
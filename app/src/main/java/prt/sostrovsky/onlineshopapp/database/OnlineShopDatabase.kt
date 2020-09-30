package prt.sostrovsky.onlineshopapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import prt.sostrovsky.onlineshopapp.database.dao.ProductDao
import prt.sostrovsky.onlineshopapp.database.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OnlineShopDatabase : RoomDatabase() {
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
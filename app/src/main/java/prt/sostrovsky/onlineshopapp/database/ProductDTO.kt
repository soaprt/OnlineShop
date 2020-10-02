package prt.sostrovsky.onlineshopapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
data class ProductDTO(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("short_description") val short_description: String,
    @field:SerializedName("image") val image_url: String,
    @field:SerializedName("price") val price: Int,
    @field:SerializedName("sale_precent") val sale_percent: Int = 0,
    @field:SerializedName("details") val details: String
)

fun ProductDTO.asFavoritesDTO(): FavoritesDTO {
    return FavoritesDTO(product_id = id)
}
package prt.sostrovsky.onlineshopapp.network.response

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt


data class ProductDTO(val id: Int,
                      val title: String,
                      @SerializedName("short_description")
                      val shortDescription: String,
                      @SerializedName("image")
                      val imageUrl: String,
                      val price: Int,
                      @SerializedName("sale_precent")
                      val salePercent: Int = 0,
                      val details: String) {

    val newPrice: String
    get() = "$$price${if (salePercent > 0) ",-" else ""}"


    val oldPrice: String
    get() = if (salePercent > 0) "${getOldPrice()}" else ""


    private fun getOldPrice(): Int {
        val percent = (price.toDouble() / 100)
        return  (price + (percent * salePercent)).roundToInt()
    }

    override fun toString(): String {
        return "ProductDTO(id=$id, title='$title', shortDescription='$shortDescription', " +
                "imageUrl='$imageUrl', price=$price, salePercent=$salePercent, details='$details'," +
                "newPrice='$newPrice', oldPrice='$oldPrice')"
    }
}
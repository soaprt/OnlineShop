package prt.sostrovsky.onlineshopapp.network.response

import com.google.gson.annotations.SerializedName

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
    get() = "$$price,-"

    val oldPrice: String
    get() = "${getOldPrice()}"

    private fun getOldPrice(): Int {
        return (price + (price / 100) * salePercent)
    }

    override fun toString(): String {
        return "ProductDTO(id=$id, title='$title', shortDescription='$shortDescription', " +
                "imageUrl='$imageUrl', price=$price, salePercent=$salePercent, details='$details')"
    }
}
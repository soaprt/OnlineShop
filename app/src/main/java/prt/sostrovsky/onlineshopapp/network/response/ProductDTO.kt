package prt.sostrovsky.onlineshopapp.network.response

import com.google.gson.annotations.SerializedName

data class ProductDTO(val id: Int,
                      val title: String,
                      @SerializedName("short_description")
                      val shortDescription: String,
                      val image: String,
                      @SerializedName("sale_precent")
                      val salePercent: Int = 0,
                      val details: String) {
    override fun toString(): String {
        return "ProductDTO(id=$id, title='$title', shortDescription='$shortDescription', " +
                "image='$image', salePercent=$salePercent, details='$details')"
    }
}
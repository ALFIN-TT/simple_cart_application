package com.alfin.simplecartapp.data.network.response


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("product_tb")
data class Product(
    @Ignore
    @SerializedName("brand")
    var brand: String? = null,
    @Ignore
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @Ignore
    @SerializedName("discountPercentage")
    var discountPercentage: Double? = null,
    @Ignore
    @SerializedName("rating")
    var rating: Double? = null,
    @Ignore
    @SerializedName("stock")
    var stock: Int? = null,
    @Ignore
    @SerializedName("images")
    var images: List<String>? = null,

    //Cached data in db
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("thumbnail")
    var thumbnail: String? = null,
    @SerializedName("price")
    var price: Int? = null,
    var quantity: Int = 0,
)
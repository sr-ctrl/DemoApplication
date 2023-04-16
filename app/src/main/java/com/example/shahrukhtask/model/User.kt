package com.example.shahrukhtask.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class UserResponse(
    @SerializedName("page")
    var page:Int?= null,
    @SerializedName("per_page")
    var perPage: Int?  = null,
    @SerializedName("total")
    var total : Int? = null,
    @SerializedName("total_pages" )
    var totalPages : Int? = null,
    @SerializedName("data") var data : ArrayList<User> = arrayListOf(),
)


@Entity(tableName = "user_table")
@Parcelize
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int?= null,
    @SerializedName("email")
    var email:String? = null,
    @SerializedName("first_name")
    var firstName : String? = null,
    @SerializedName("last_name")
    var lastName  : String? = null,
    @SerializedName("avatar")
    var avatar    : String? = null
):Parcelable
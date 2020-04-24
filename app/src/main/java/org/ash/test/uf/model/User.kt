package org.ash.test.uf.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.ash.test.uf.util.Constants

@Entity(tableName = Constants.USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Int?,
    @ColumnInfo(name = "THUMBNAIL") val thumbnail: String,
    @ColumnInfo(name = "TITLE") val title: String,
    @ColumnInfo(name = "FIRST_NAME") val firstName: String,
    @ColumnInfo(name = "LAST_NAME") val lastName: String,
    @ColumnInfo(name = "GENEDER") val gender: String,
    @ColumnInfo(name = "DOB") val dob: String,
    @ColumnInfo(name = "LARGE") val large: String,
    @ColumnInfo(name = "COUNTRY") val country: String,
    @ColumnInfo(name = "STATE") val state: String,
    @ColumnInfo(name = "CITY") val city: String,
    @ColumnInfo(name = "STREET") val street: String,
    @ColumnInfo(name = "STREET_NO") val streetNumber: Int,
    @ColumnInfo(name = "POSTCODE") val postcode: String,
    @ColumnInfo(name = "EMAIL") val email: String,
    @ColumnInfo(name = "CELL") val cell: String
) {
    fun getDOBString(): String {
        val t = dob.indexOf('T', ignoreCase = true)
        return dob.substring(0, t)
    }

    fun getAddressString() = "$streetNumber $street, $postcode $city $state $country"

    fun getFullname() = "$firstName $lastName"
}
package com.cirogg.conexa.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "users")
@Serializable
data class UsersEntity(
    @PrimaryKey val id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    @Embedded val address: AddressEntity
){
    fun toUsers() = Users(id, firstname, lastname, email, address.toModel())
}

@Entity
@Serializable
data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded val geo: GeoEntity
)

@Entity
@Serializable
data class GeoEntity(
    val lat: String,
    val lng: String
)

// Conversion functions
fun Users.toEntity() = UsersEntity(id, firstname, lastname, email, address.toEntity())
fun UsersEntity.toModel() = Users(id, firstname, lastname, email, address.toModel())

fun Address.toEntity() = AddressEntity(street, suite, city, zipcode, geo.toEntity())
fun AddressEntity.toModel() = Address(street, suite, city, zipcode, geo.toModel())

fun Geo.toEntity() = GeoEntity(lat, lng)
fun GeoEntity.toModel() = Geo(lat, lng)
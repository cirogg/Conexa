package com.cirogg.conexa.data.model

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
class Users(
    val id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    @Embedded val address: Address
){

}

@Serializable
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded val geo: Geo
)

@Serializable
data class Geo(
    val lat: String,
    val lng: String
)
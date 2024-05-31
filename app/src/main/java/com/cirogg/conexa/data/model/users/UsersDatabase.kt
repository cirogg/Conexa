package com.cirogg.conexa.data.model.users

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UsersEntity::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}
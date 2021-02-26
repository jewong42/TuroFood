package com.jewong.turofood.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jewong.turofood.api.data.Businesses

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE userId == 0")
    fun loadUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM user WHERE userId == 0")
    fun deleteUser()

    @Query("UPDATE user SET pizzaBusinesses=:businesses WHERE userId == 0")
    fun updatePizzaBusinesses(businesses: Businesses)

    @Query("UPDATE user SET beerBusinesses=:businesses WHERE userId == 0")
    fun updateBeerBusinesses(businesses: Businesses)
}
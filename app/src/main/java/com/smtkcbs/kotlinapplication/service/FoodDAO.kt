package com.smtkcbs.kotlinapplication.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smtkcbs.kotlinapplication.model.Food

@Dao
interface FoodDAO {

    // Data Access Object

    @Insert
    suspend fun insertAll(vararg food : Food) : List<Long>
    // insert  insert into in room
    // suspend is  in coroutine scope
    // vararg more than one

    @Query("SELECT * FROM food")
    suspend fun getAllFoods() : List<Food>

    @Query("SELECT * FROM food WHERE foodID= :foodID")
    suspend fun getFood(foodID : Int) : Food

    @Query("DELETE FROM food")
    suspend fun deleteAllFoods()

}
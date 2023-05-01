package br.com.fiap25mob.mbamobile.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity

@Dao
interface GuitarsDAO {

    @Insert
    suspend fun insert(guitarsEntity: GuitarsEntity): Long

    @Update
    suspend fun update(guitarsEntity: GuitarsEntity)

    @Query("DELETE FROM guitar_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM guitar_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM guitar_table")
    suspend fun getAllGuitars(): List<GuitarsEntity>

}
package br.com.fiap25mob.mbamobile.repository

import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity

interface GuitarsRepository {
    suspend fun insertGuitar(brand: String, model: String): Long
    suspend fun updateGuitar(id: Long, brand: String, model: String)
    suspend fun getAllGuitars(): List<GuitarsEntity>
    suspend fun deleteGuitar(id: Long)
}
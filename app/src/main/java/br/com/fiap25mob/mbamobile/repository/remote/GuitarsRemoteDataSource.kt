package br.com.fiap25mob.mbamobile.repository.remote

import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity

interface GuitarsRemoteDataSource {
    suspend fun insertGuitar(id: Long, brand: String, model: String)
    suspend fun getAllGuitars(): List<GuitarsEntity>
    suspend fun deleteGuitar(id: Long)
}
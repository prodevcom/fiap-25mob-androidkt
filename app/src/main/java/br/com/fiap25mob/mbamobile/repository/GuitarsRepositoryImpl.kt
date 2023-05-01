package br.com.fiap25mob.mbamobile.repository

import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity
import br.com.fiap25mob.mbamobile.repository.local.GuitarsLocalDataSource
import br.com.fiap25mob.mbamobile.repository.remote.GuitarsRemoteDataSource

class GuitarsRepositoryImpl(
    private val remoteDataSource: GuitarsRemoteDataSource,
    private val localDataSource: GuitarsLocalDataSource
) : GuitarsRepository {
    override suspend fun insertGuitar(brand: String, model: String): Long {
        val id = localDataSource.insertGuitar(brand, model)
        if (id > 0) remoteDataSource.insertGuitar(id, brand, model)
        return id
    }

    override suspend fun updateGuitar(id: Long, brand: String, model: String) {
        localDataSource.updateGuitar(id, brand, model)
        remoteDataSource.insertGuitar(id, brand, model)
    }

    override suspend fun getAllGuitars(): List<GuitarsEntity> {
        return localDataSource.getAllGuitars()
    }

    override suspend fun deleteGuitar(id: Long) {
        localDataSource.deleteGuitar(id)
        remoteDataSource.deleteGuitar(id)
    }
}
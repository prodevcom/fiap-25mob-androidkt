package br.com.fiap25mob.mbamobile.repository.remote

import android.util.Log
import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity
import br.com.fiap25mob.mbamobile.utils.FirebaseUtils

class GuitarsRemoteDataSourceImpl(
    private var firebaseConnection: FirebaseUtils
) : GuitarsRemoteDataSource {
    override suspend fun insertGuitar(id: Long, brand: String, model: String) {
        firebaseConnection.saveValue(id, brand, model)
    }

    override suspend fun getAllGuitars(): List<GuitarsEntity> {
        return firebaseConnection.readValues()
    }

    override suspend fun deleteGuitar(id: Long) {
        firebaseConnection.deleteValue(id)
    }

}
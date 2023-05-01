package br.com.fiap25mob.mbamobile.repository.local

import br.com.fiap25mob.mbamobile.data.dao.GuitarsDAO
import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity

class GuitarsLocalDataSourceImpl(private val guitarsDAO: GuitarsDAO): GuitarsLocalDataSource {

    override suspend fun insertGuitar(brand: String, model: String): Long {
        val guitar = GuitarsEntity(brand = brand, model = model)
        return guitarsDAO.insert(guitar)
    }

    override suspend fun updateGuitar(id: Long, brand: String, model: String) {
        val guitar = GuitarsEntity(id = id, brand = brand, model = model)
        guitarsDAO.update(guitar)
    }

    override suspend fun getAllGuitars(): List<GuitarsEntity> = guitarsDAO.getAllGuitars()

    override suspend fun deleteGuitar(id: Long) = guitarsDAO.delete(id)

}
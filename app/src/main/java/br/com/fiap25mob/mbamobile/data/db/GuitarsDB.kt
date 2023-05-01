package br.com.fiap25mob.mbamobile.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap25mob.mbamobile.data.dao.GuitarsDAO

@Database(entities = [GuitarsEntity::class], version = 1)
abstract class GuitarsDB: RoomDatabase() {

    abstract val guitarsDAO: GuitarsDAO
    companion object{
        @Volatile
        private var INSTANCE: GuitarsDB? = null

        fun getInstance(context: Context): GuitarsDB {
            synchronized(this) {
                var instance: GuitarsDB? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        GuitarsDB::class.java,
                        "guitars_database"
                    ).build()
                }
                return instance
            }
        }
    }
}
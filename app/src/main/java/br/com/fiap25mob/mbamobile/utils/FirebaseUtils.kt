package br.com.fiap25mob.mbamobile.utils

import android.content.Context
import android.util.Log
import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity
import br.com.fiap25mob.mbamobile.repository.local.GuitarsLocalDataSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import java.lang.Exception

private const val TAG = "FirebaseUtils"

class FirebaseUtils(private val context: Context,
                    private val localDataSource: GuitarsLocalDataSource
) {

    fun saveValue(id: Long, brand: String, model: String) {
        val database = Firebase.database
        val pref = context.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)
        val myRef = pref.getString(USER_ID_KEY, "")?.let { database.getReference(it) }
        myRef!!.child(GUITAR_PATH).child(id.toString()).setValue(GuitarsEntity(id, brand, model))
    }

    fun deleteValue(id: Long) {
        val database = Firebase.database
        val pref = context.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)
        val myRef = pref.getString(USER_ID_KEY, "")?.let { database.getReference(it) }
        myRef!!.child(GUITAR_PATH).child(id.toString()).removeValue()
    }

    fun readValues(): List<GuitarsEntity> {
        val list = mutableListOf<GuitarsEntity>()
        val database = Firebase.database
        val pref = context.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)
        val myRef = pref.getString(USER_ID_KEY, "")?.let { database.getReference(it) }

        myRef!!.child(GUITAR_PATH).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    child.getValue(GuitarsEntity::class.java)?.let { list.add(it) }
                }
                Log.d(TAG, "Value has ${list.size} amount of elements on cloud")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        return list
    }
}
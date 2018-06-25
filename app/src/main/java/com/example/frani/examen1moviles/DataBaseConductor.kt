package com.example.frani.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseConductor {
    companion object {
        val DB_NAME = "conductorAuto"
        val TABLE_NAME = "conductor"
        val NUMERO_ID = "id"
        val NOMBRES = "nombre"
        val APELLIDOS = "apellido"
        val FECHA_NACIMIENTO = "fecha"
        val NUMERO_AUTOS = "numeroAutos"
        val LICENCIA_VALIDA = "licenciaValida"
    }
}

class DBConductorHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBaseConductor.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBaseConductor.TABLE_NAME} (${DataBaseConductor.NUMERO_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${DataBaseConductor.NOMBRES} VARCHAR(50),${DataBaseConductor.APELLIDOS} VARCHAR(50),${DataBaseConductor.FECHA_NACIMIENTO} VARCHAR(20), ${DataBaseConductor.NUMERO_AUTOS} INTEGER, ${DataBaseConductor.LICENCIA_VALIDA} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarConductor(conductor: Conductor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseConductor.NOMBRES, conductor.nombre)
        cv.put(DataBaseConductor.APELLIDOS, conductor.apellido)
        cv.put(DataBaseConductor.FECHA_NACIMIENTO, conductor.fechaNacimiento)
        cv.put(DataBaseConductor.NUMERO_AUTOS, conductor.numeroAutos)
        cv.put(DataBaseConductor.LICENCIA_VALIDA, conductor.licenciaValida)

        val resultado = dbWriteable.insert(DataBaseConductor.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updateCondcutor(conductor: Conductor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseConductor.NOMBRES, conductor.nombre)
        cv.put(DataBaseConductor.APELLIDOS, conductor.apellido)
        cv.put(DataBaseConductor.FECHA_NACIMIENTO, conductor.fechaNacimiento)
        cv.put(DataBaseConductor.NUMERO_AUTOS, conductor.numeroAutos)
        cv.put(DataBaseConductor.LICENCIA_VALIDA, conductor.licenciaValida)

        val whereClause = "${DataBaseConductor.NUMERO_ID} = ${conductor.id}"
        val resultado = dbWriteable.update(DataBaseConductor.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deleteConductor(id: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBaseConductor.NUMERO_ID} = $id"
        return dbWriteable.delete(DataBaseConductor.TABLE_NAME, whereClause, null) > 0
    }

    fun getConductoresList(): ArrayList<Conductor> {
        var lista = ArrayList<Conductor>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBaseConductor.TABLE_NAME}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val id = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val apellido = resultado.getString(2)
                val fechaNacimiento = resultado.getString(3)
                val numeroAutos = resultado.getString(4).toInt()
                val licenciaValida = resultado.getString(5).toInt()

                lista.add(Conductor(id, nombre, apellido, fechaNacimiento, numeroAutos, licenciaValida))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}

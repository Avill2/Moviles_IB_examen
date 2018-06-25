package com.example.frani.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseAuto {
    companion object {
        val DB_NAME = "Libros"
        val TABLE_NAME = "auto"
        val CHASIS = "chasis"
        val NOMBRE_MARCA = "nombre"
        val COLOR_UNO = "colorUno"
        val COLOR_DOS = "colorDos"
        val NOMBRE_MODELO = "nombreModelo"
        val ANIO = "anio"
        val ID_CONDUCTOR = "idConductor"
    }
}

class DBAutoHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBaseAuto.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBaseAuto.TABLE_NAME} (${DataBaseAuto.CHASIS} INTEGER PRIMARY KEY, ${DataBaseAuto.NOMBRE_MARCA} VARCHAR(50),${DataBaseAuto.COLOR_UNO} VARCHAR(50),${DataBaseAuto.COLOR_DOS} VARCHAR(50), ${DataBaseAuto.NOMBRE_MODELO} VARCHAR(20), ${DataBaseAuto.ANIO} VARCHAR(20),  ${DataBaseAuto.ID_CONDUCTOR} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarAuto(auto: Autoc) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseAuto.CHASIS, auto.chasis)
        cv.put(DataBaseAuto.NOMBRE_MARCA, auto.nombre)
        cv.put(DataBaseAuto.COLOR_UNO, auto.colorUno)
        cv.put(DataBaseAuto.COLOR_DOS, auto.colorDos)
        cv.put(DataBaseAuto.NOMBRE_MODELO, auto.nombreModelo)
        cv.put(DataBaseAuto.ANIO, auto.anio)
        cv.put(DataBaseAuto.ID_CONDUCTOR, auto.idConductor)

        val resultado = dbWriteable.insert(DataBaseAuto.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updateAuto(auto: Autoc) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseAuto.CHASIS, auto.chasis)
        cv.put(DataBaseAuto.NOMBRE_MARCA, auto.nombre)
        cv.put(DataBaseAuto.COLOR_UNO, auto.colorUno)
        cv.put(DataBaseAuto.COLOR_DOS, auto.colorDos)
        cv.put(DataBaseAuto.NOMBRE_MODELO, auto.nombreModelo)
        cv.put(DataBaseAuto.ANIO, auto.anio)
        cv.put(DataBaseAuto.ID_CONDUCTOR, auto.idConductor)

        val whereClause = "${DataBaseAuto.CHASIS} = ${auto.chasis}"
        val resultado = dbWriteable.update(DataBaseAuto.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deleteAuto(icbn: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBaseAuto.CHASIS} = $icbn"
        return dbWriteable.delete(DataBaseAuto.TABLE_NAME, whereClause, null) > 0
    }

    fun getAutosList(idAuto: Int): ArrayList<Autoc> {
        var lista = ArrayList<Autoc>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBaseAuto.TABLE_NAME}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val chasis = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val colorUno = resultado.getString(2)
                val colorDos = resultado.getString(3)
                val nombreModelo = resultado.getString(4)
                val anio = resultado.getString(5)
                val idConductor = resultado.getString(6).toInt()

                lista.add(Autoc(chasis, nombre, colorUno, colorDos, nombreModelo, anio, idConductor))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}

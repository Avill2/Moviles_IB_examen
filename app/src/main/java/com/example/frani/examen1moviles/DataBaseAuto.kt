package com.example.frani.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.StrictMode
import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPatch
import com.github.kittinunf.fuel.httpPost

class DataBaseAuto {

companion object {
    fun insertarAuto(auto: Autoc) {

        "http://172.29.64.230:1337/Auto".httpPost(listOf("isbn" to auto.isbn,
                "nombre" to auto.nombre,
                "chasis" to auto.chasis,
                "colorDos" to auto.colorDos,
                "colorUno" to auto.colorUno,
                "anio" to auto.anio,
                "nombreModelo" to auto.nombreModelo,
                "latitud" to auto.latitud,
                "longitud" to auto.longitud,
                "idConductor" to auto.idConductor))
                .responseString { request, _, result ->
                    Log.d("http-ejemplo", request.toString())
                }

    }

    fun updateAuto(auto: Autoc) {
        "http://172.29.64.230:1337/Auto/${auto.id}".httpPatch(listOf("isbn" to auto.isbn,
                "nombre" to auto.nombre,
                "chasis" to auto.chasis,
                "colorDos" to auto.colorDos,
                "colorUno" to auto.colorUno,
                "anio" to auto.anio,
                "nombreModelo" to auto.nombreModelo,
                "latitud" to auto.latitud,
                "longitud" to auto.longitud,
                "idConductor" to auto.idConductor))
                .responseString { request, _, result ->
                    Log.d("http-ejemplo", request.toString())
                }
    }

    fun deleteAuto(id: Int) {
        "http://172.29.64.230:1337/Auto/$id".httpDelete()
                .responseString { request, response, result ->
                    Log.d("http-ejemplo", request.toString())
                }
    }

    fun getAutosList(conductorId: Int): ArrayList<Autoc> {
        val autos: ArrayList<Autoc> = ArrayList()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val (request, response, result) = "http://172.29.64.230:1337/Auto?autorId=$conductorId".httpGet().responseString()
        val jsonStringLibro = result.get()

        val parser = Parser()
        val stringBuilder = StringBuilder(jsonStringLibro)
        val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

        array.forEach {
            val id = it["id"] as Int
            val isbn = it["isbn"] as String
            val nombre = it["nombre"] as String
            val chasis = it["chasis"] as Int
            val colorDos = it["colorDos"] as String
            val colorUno = it["colorUno"] as String
            val anio = it["anio"] as String
            val nombreModelo = it["nombreModelo"] as String
            val latitud = it["latitud"] as Double
            val longitud = it["longitud"] as Double
            val auto = Autoc(id, isbn, chasis,nombre, colorUno,colorDos,nombreModelo, latitud, longitud,anio, conductorId, 0, 0)
            autos.add(auto)
        }
        return autos
    }

}

    /*companion object {
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
   */


}

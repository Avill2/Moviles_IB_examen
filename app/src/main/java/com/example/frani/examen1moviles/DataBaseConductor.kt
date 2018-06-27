package com.example.frani.examen1moviles

import android.os.StrictMode
import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPatch
import com.github.kittinunf.fuel.httpPost

class DataBaseConductor {
    fun insertarConductor(conductor: Conductor) {
        "http://172.29.64.230:1337/Conductor".httpPost(listOf("nombre" to conductor.nombre,
                "apellido" to conductor.apellido,
                "fechaNacimiento" to conductor.fechaNacimiento,
                "numeroAutos" to conductor.numeroAutos,
                "licenciaValida" to conductor.licenciaValida))
                .responseString { request, _, result ->
                    Log.d("http-ejemplo", request.toString())
                }
    }

    fun updateConductor(conductor: Conductor) {
        "http://172.29.64.230:1337/Conductor/${conductor.id}".httpPatch(listOf("nombre" to conductor.nombre,
                "apellido" to conductor.apellido,
                "fechaNacimiento" to conductor.fechaNacimiento,
                "numeroAutos" to conductor.numeroAutos,
                "licenciaValida" to conductor.licenciaValida))
                .responseString { request, _, result ->
                    Log.d("http-ejemplo", request.toString())
                }
    }

    fun deleteConductor(id: Int) {
        "http://172.29.64.230:1337/Conductor/$id".httpDelete()
                .responseString { request, response, result ->
                    Log.d("http-ejemplo", request.toString())
                }
    }

    fun getListConductor(): ArrayList<Conductor> {
        val autores: ArrayList<Conductor> = ArrayList()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://172.29.64.230:1337/Conductor".httpGet().responseString()
        val jsonStringAutor = result.get()

        val parser = Parser()
        val stringBuilder = StringBuilder(jsonStringAutor)
        val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

        array.forEach {
            val id = it["id"] as Int
            val nombre = it["nombre"] as String
            val apellido = it["apellido"] as String
            val fechaNacimiento = it["fechaNacimiento"] as String
            val numeroLibros = it["numeroAutos"] as Int
            val ecuatoriano = it["licenciaValida"] as Int
            val conductor = Conductor(id, nombre, apellido, fechaNacimiento, numeroLibros, ecuatoriano, 0, 0)
            autores.add(conductor)
        }
        return autores
    }
}


    /*companion object {
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
    }*/


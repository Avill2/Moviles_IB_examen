package com.example.frani.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.util.*

class Http_FuelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http__fuel)

        "htpp://172.31.104.27:1337/Conductor".httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-ejemplo", "Error ${ex.response}")
                        }
                        is Result.Success -> {
                            val jsonStringConductor = result.get()
                            Log.i("http-ejemplo", "Exito ${jsonStringConductor}")

                            val conductor: Conductores? = Klaxon()
                                    .parse<Conductores>(jsonStringConductor)

                            if (conductor != null) {
                                Log.i("http-ejemplo", "Nombre: ${conductor.nombre}")
                                Log.i("http-ejemplo", "Apellido: ${conductor.apellido}")
                                Log.i("http-ejemplo", "Id: ${conductor.id}")
                                Log.i("http-ejemplo", "Medallas: ${conductor.fechaNacimiento}")
                                Log.i("http-ejemplo", "Edad: ${conductor.numeroAutos}")
                                Log.i("http-ejemplo", "Edad: ${conductor.licenciaValida}")
                                Log.i("http-ejemplo", "Creado: ${conductor.createdAtDate}")
                                Log.i("http-ejemplo", "Actualizado: ${conductor.updatedAtDate}")

                                conductor.autos.forEach { auto: Autoa ->
                                    Log.i("http-ejemplo", "Nombre ${auto.nombre}")
                                    Log.i("http-ejemplo", "Tipo ${auto.colorUno}")
                                    Log.i("http-ejemplo", "Numero ${auto.chasis}")
                                    Log.i("http-ejemplo", "Numero ${auto.nombreModelo}")
                                    Log.i("http-ejemplo", "Numero ${auto.anio}")
                                }



                            } else {
                                Log.i("http-ejemplo", "Conductor nulo")
                            }


                        }
                    }
                }
    }
}

class Conductores(var nombre: String,
                 var apellido: String,
                 var fechaNacimiento: String,
                 var numeroAutos: Int,
                var licenciaValida: Boolean,
                 var createdAt: Long,
                 var updatedAt: Long,
                 var id: Int,
                 var autos: List<Autoa>) {
    var createdAtDate = Date(updatedAt)
    var updatedAtDate = Date(createdAt)

}

class Autoa(var nombre: String,
              var colorUno: String,
              var chasis: Int,
              var nombreModelo: String,
              var anio: Int,
              var updatedAt: Long,
              var createdAt :Long,
              var id: Int,
              var idConductor: Int) {
    var createdAtDate = Date(updatedAt)
    var updatedAtDate = Date(createdAt)
}

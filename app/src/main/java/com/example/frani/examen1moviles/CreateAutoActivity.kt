package com.example.frani.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_crear_auto.*

class CreateAutoActivity : AppCompatActivity() {

    lateinit var dbHandler: DBAutoHandlerAplicacion
    var idConductor: Int = 0
    var auto: Autoc? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_auto)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewLibro.text = getString(R.string.edit_book)
            auto = intent.getParcelableExtra("auto")
            fillFields()
            tipo = true
        }

        idConductor = intent.getIntExtra("idConductor", 0)

        dbHandler = DBAutoHandlerAplicacion(this)

        btnCrearLibro.setOnClickListener{
            v: View? ->  crearAuto()
        }
    }

    fun fillFields() {
        txtIChasis.setText(auto?.chasis.toString())
        txtNombreAuto.setText(auto?.nombre)
        txtNColorUno.setText(auto?.colorUno.toString())
        txtColorDos.setText(auto?.colorDos.toString())
        txtNomModelo.setText(auto?.nombreModelo)
        txtAnio.setText(auto?.anio)
    }

    fun crearAuto() {
        var chasis = txtIChasis.text.toString().toInt()
        var nombre = txtNombreAuto.text.toString()
        var colorUno = txtNColorUno.text.toString()
        var colorDos = txtColorDos.text.toString()
        var nombreModelo = txtNomModelo.text.toString()
        var anio = txtAnio.text.toString()
        var auto = Autoc(chasis, nombre, colorUno, colorDos, nombreModelo, anio, idConductor)

        if (!tipo) {
            dbHandler.insertarAuto(auto)
        } else {
            dbHandler.updateAuto(auto)
        }

        finish()
    }
}

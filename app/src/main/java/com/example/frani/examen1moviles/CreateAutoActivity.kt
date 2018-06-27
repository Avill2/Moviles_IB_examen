package com.example.frani.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_crear_auto.*

class CreateAutoActivity : AppCompatActivity() {

    //lateinit var dbHandler: DBAutoHandlerAplicacion
    var idConductor: Int = 0
    var idAuto: Int = 0
    var auto: Autoc? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_auto)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewLibro.text = getString(R.string.edit_book)
            auto = intent.getParcelableExtra("auto")
            idAuto = auto!!.id
            fillFields()
            tipo = true
        }

        idConductor = intent.getIntExtra("idConductor", 0)

        //dbHandler = DBAutoHandlerAplicacion(this)

        btnCrearLibro.setOnClickListener{
            v: View? ->  crearAuto()
        }
    }

    fun fillFields() {
        //txtISBNAuto.setText(auto?.isbn)
        txtIChasis.setText(auto?.chasis.toString())
        txtNombreAuto.setText(auto?.nombre)
        txtNColorUno.setText(auto?.colorUno)
        txtColorDos.setText(auto?.colorDos)
        txtNomModelo.setText(auto?.nombreModelo)
        txtAnio.setText(auto?.anio)
    }

    fun crearAuto() {
        var isbn = txtISBNAuto.text.toString()
        var chasis = txtIChasis.text.toString().toInt()
        var nombre = txtNombreAuto.text.toString()
        var colorUno = txtNColorUno.text.toString()
        var colorDos = txtColorDos.text.toString()
        var nombreModelo = txtNomModelo.text.toString()
        var anio = txtAnio.text.toString()
        var latitud = txtLatitutdAuto.text.toString().toDouble()
        var longitud = txtLongitudAuto.text.toString().toDouble()
        var auto = Autoc(this.idAuto, isbn, chasis,nombre, colorUno,colorDos,nombreModelo, latitud, longitud,anio, idAuto, 0, 0)

        if (!tipo) {
            DataBaseAuto.insertarAuto(auto)
        } else {
            DataBaseAuto.updateAuto(auto)
        }

        finish()
    }
}

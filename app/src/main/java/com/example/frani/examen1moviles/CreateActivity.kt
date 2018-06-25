package com.example.frani.examen1moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    lateinit var dbHandler: DBConductorHandlerAplicacion
    var conductor: Conductor? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewAutor.text = getString(R.string.edit_autor)
            conductor = intent.getParcelableExtra("conductor")
            fillFields()
            tipo = true
        }

        dbHandler = DBConductorHandlerAplicacion(this)

        btnCrearConductor.setOnClickListener{
            v: View? -> crearCondutor()
        }
    }

    fun fillFields() {
        txtNombreConductor.setText(conductor?.nombre)
        txtApellidoConductor.setText(conductor?.apellido)
        txtFechaConductor.setText(conductor?.fechaNacimiento)
        txtNumeroAutos.setText(conductor?.numeroAutos.toString())
        if (conductor?.licenciaValida == 1) {
            switchEcAutor.toggle()
        }
    }

    fun crearCondutor() {
        var nombre = txtNombreConductor.text.toString()
        var apellido = txtApellidoConductor.text.toString()
        var fecha = txtFechaConductor.text.toString()
        var numeroAutos = txtNumeroAutos.text.toString().toInt()
        var licenciaValida = if (switchEcAutor.isChecked) 1 else 0

        if (!tipo) {
            var autor = Conductor(0, nombre, apellido, fecha, numeroAutos, licenciaValida)
            dbHandler.insertarConductor(autor)
        } else {
            var autor = Conductor(conductor?.id!!, nombre, apellido, fecha, numeroAutos, licenciaValida)
            dbHandler.updateCondcutor(autor)
        }

        irAListView()
    }

    fun irAListView() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}

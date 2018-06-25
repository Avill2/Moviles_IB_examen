package com.example.frani.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detalle_auto.*

class DetalleAutoActivity : AppCompatActivity() {

    var auto: Autoc? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_auto)

        auto = intent.getParcelableExtra("auto")

        txtShowChasis.text = auto?.chasis.toString()
        txtShowNombreMarca.text = auto?.nombre
        txtShowColorUnos.text = auto?.colorUno.toString()
        txtShowColorDos.text = auto?.colorDos.toString()
        txtShowModelo.text = auto?.nombreModelo
        txtShowAnio.text = auto?.anio
    }
}

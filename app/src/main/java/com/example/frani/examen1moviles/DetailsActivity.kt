package com.example.frani.examen1moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    var conductor: Conductor? = null
    lateinit var adaptador: AdaptadorAuto
    lateinit var autos: ArrayList<Autoc>
    lateinit var dbHandler: DBAutoHandlerAplicacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        conductor = intent.getParcelableExtra("conductor")

        txtShowNombreCondutor.text = conductor?.nombre
        txtShowApellidoConductor.text = conductor?.apellido
        txtShowFechaConductor.text = conductor?.fechaNacimiento
        txtShowNumAutos.text = conductor?.numeroAutos.toString()
        txtShowLicValida.text = if(conductor?.licenciaValida == 1) getString(R.string.yes) else getString(R.string.no)

        dbHandler = DBAutoHandlerAplicacion(this)
        autos = dbHandler.getAutosList(conductor?.id!!)

        val layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorAuto(autos)
        recycler_view_auto.layoutManager = layoutManager
        recycler_view_auto.itemAnimator = DefaultItemAnimator()
        recycler_view_auto.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view_auto)

        btnNuevoAuto.setOnClickListener{
            v: View? ->  crearAuto()
        }
    }

    fun crearAuto() {
        val intent = Intent(this, CreateAutoActivity::class.java)
        intent.putExtra("tipo", "Create")
        intent.putExtra("idConductor", conductor?.id!!)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var auto = autos[position]

        when (item.itemId) {
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "${"CONDUCTORES"} - ${"AUTOS"}")
                intent.putExtra(Intent.EXTRA_TEXT, "${"CHASIS"} ${auto.chasis}\n${"NOMBRE MARCA:"} ${auto.nombre}\n${"COLOR:"} ${auto.colorDos}\n${"AÑO:"} ${auto.anio}")
                startActivity(intent)
                return true
            }
            R.id.item_menu_editar -> {
                val intent = Intent(this, CreateAutoActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("auto", auto)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.confirmation)
                        .setPositiveButton(R.string.yes, { dialog, which ->
                            dbHandler.deleteAuto(auto.chasis)
                            finish()
                            startActivity(intent)
                        }
                        )
                        .setNegativeButton(R.string.no, null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }
}

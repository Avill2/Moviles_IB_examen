package com.example.frani.examen1moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    lateinit var adaptador: AdaptadorConductor
    lateinit var conductores: ArrayList<Conductor>
   // lateinit var dbHandler: DBConductorHandlerAplicacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        dbHandler = DBConductorHandlerAplicacion(this)
        conductores = dbHandler.getConductoresList()

        val layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorConductor(conductores)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var conductor = conductores[position]

        when (item.itemId) {
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "${"CONDUCTOR"} - ${"CONDUCTORES Y AUTOS"}")
                intent.putExtra(Intent.EXTRA_TEXT, "${getString(R.string.name)} ${conductor.nombre} ${conductor.apellido}\n${"NUMERO DE AUTOS:"} ${conductor.numeroAutos}\n${getString(R.string.fecha_nacimiento)} ${conductor.fechaNacimiento}")
                startActivity(intent)
                return true
            }
            R.id.item_menu_editar -> {
                val intent = Intent(this, CreateActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("conductor", conductor)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.confirmation)
                        .setPositiveButton(R.string.yes, { dialog, which ->
                            dbHandler.deleteConductor(conductor.id)
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

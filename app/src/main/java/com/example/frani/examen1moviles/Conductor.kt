package com.example.frani.examen1moviles

import android.os.Parcel
import android.os.Parcelable

class Conductor(var id: Int, var nombre: String, var apellido: String, var fechaNacimiento: String, var numeroAutos: Int, var licenciaValida: Int): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(id)
        destino?.writeString(nombre)
        destino?.writeString(apellido)
        destino?.writeString(fechaNacimiento)
        destino?.writeInt(numeroAutos)
        destino?.writeInt(licenciaValida)
    }

    companion object CREATOR : Parcelable.Creator<Conductor> {
        override fun createFromParcel(parcel: Parcel): Conductor {
            return Conductor(parcel)
        }

        override fun newArray(size: Int): Array<Conductor?> {
            return arrayOfNulls(size)
        }
    }

}
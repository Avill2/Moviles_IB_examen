package com.example.frani.examen1moviles

import android.os.Parcel
import android.os.Parcelable

class Autoc(var chasis: Int, var nombre: String, var colorUno: String, var colorDos: String, var nombreModelo: String, var anio: String, var idConductor: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(chasis)
        destino?.writeString(nombre)
        destino?.writeString(colorUno)
        destino?.writeString(colorDos)
        destino?.writeString(nombreModelo)
        destino?.writeString(anio)
        destino?.writeInt(idConductor)
    }

    companion object CREATOR : Parcelable.Creator<Autoc> {
        override fun createFromParcel(parcel: Parcel): Autoc {
            return Autoc(parcel)
        }

        override fun newArray(size: Int): Array<Autoc?> {
            return arrayOfNulls(size)
        }
    }

}
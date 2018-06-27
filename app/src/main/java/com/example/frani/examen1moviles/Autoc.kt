package com.example.frani.examen1moviles

import android.os.Parcel
import android.os.Parcelable

class Autoc(var id:Int,
            var isbn: String,
            var chasis: Int,
            var nombre: String,
            var colorUno: String,
            var colorDos: String,
            var nombreModelo: String,
            var latitud: Double,
            var longitud: Double,
            var anio: String,
            var idConductor: Int,
            var createdAt: Long,
            var updatedAt: Long) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(id)
        destino?.writeString(isbn)
        destino?.writeInt(chasis)
        destino?.writeString(nombre)
        destino?.writeString(colorUno)
        destino?.writeString(colorDos)
        destino?.writeString(nombreModelo)
        destino?.writeDouble(latitud)
        destino?.writeDouble(longitud)
        destino?.writeString(anio)
        destino?.writeInt(idConductor)
        destino?.writeLong(createdAt)
        destino?.writeLong(updatedAt)
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
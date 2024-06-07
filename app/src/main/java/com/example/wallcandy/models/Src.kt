package com.example.wallcandy.models

import android.os.Parcel
import android.os.Parcelable

data class Src(
    val landscape: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val original: String,
    val portrait: String,
    val small: String,
    val tiny: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(landscape)
        parcel.writeString(large)
        parcel.writeString(large2x)
        parcel.writeString(medium)
        parcel.writeString(original)
        parcel.writeString(portrait)
        parcel.writeString(small)
        parcel.writeString(tiny)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Src> {
        override fun createFromParcel(parcel: Parcel): Src {
            return Src(parcel)
        }

        override fun newArray(size: Int): Array<Src?> {
            return arrayOfNulls(size)
        }

        fun default(): Src {
            return Src("", "", "", "", "", "", "", "")
        }
    }


}
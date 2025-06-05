package com.example.wallcandy.models

import android.os.Parcel
import android.os.Parcelable

data class Wallpaper(
    val alt: String,
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Long,
    val photographer_url: String,
    val src: Src,
    val url: String,
    val width: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readParcelable(Src::class.java.classLoader) ?: Src.default(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alt)
        parcel.writeString(avg_color)
        parcel.writeInt(height)
        parcel.writeInt(id)
        parcel.writeByte(if (liked) 1 else 0)
        parcel.writeString(photographer)
        parcel.writeLong(photographer_id)
        parcel.writeString(photographer_url)
        parcel.writeParcelable(src, flags)
        parcel.writeString(url)
        parcel.writeInt(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wallpaper> {
        override fun createFromParcel(parcel: Parcel): Wallpaper {
            return Wallpaper(parcel)
        }

        override fun newArray(size: Int): Array<Wallpaper?> {
            return arrayOfNulls(size)
        }
    }
}
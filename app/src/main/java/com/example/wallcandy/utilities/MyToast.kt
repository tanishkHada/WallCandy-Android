package com.example.wallcandy.utilities

import android.content.Context
import android.widget.Toast

object MyToast {
    fun showToastShort(message : String, context : Context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToastLong(message: String, context : Context){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
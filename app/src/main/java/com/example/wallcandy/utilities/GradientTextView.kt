package com.example.wallcandy.utilities

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.wallcandy.R

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            applyGradient()
        }
    }

    private fun applyGradient() {
        val paint = paint
        val width = paint.measureText(text.toString())
        val textShader = LinearGradient(
            0f, 0f, width, textSize,
            intArrayOf(
                ContextCompat.getColor(context, R.color.gradient_start2),
                ContextCompat.getColor(context, R.color.gradient_end2)
            ),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = textShader
    }
}

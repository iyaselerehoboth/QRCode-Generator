package com.iyaselerehoboth.qrgeneratorsample

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class MainActivity : AppCompatActivity() {
    private lateinit var imgVQrCode: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgVQrCode = findViewById(R.id.imageView)
        imgVQrCode.setImageBitmap(encodeAsBitmap("This is a test qr code"))
    }

    private fun encodeAsBitmap(input: String): Bitmap? {
        val result: BitMatrix

        try {
            result = MultiFormatWriter().encode(input, BarcodeFormat.QR_CODE, 1000, 1000, null)
        } catch (ex: IllegalArgumentException){
            ex.printStackTrace()
            return null
        }

        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        for(y in 0 until height){
            val offset = y * width
            for (x in 0 until width){
                pixels[offset + x] = if(result.get(x,y)) -0x1000000 else -0x1
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }
}
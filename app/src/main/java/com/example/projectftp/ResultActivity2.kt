package com.example.projectftp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.projectftp.databinding.ActivityResult2Binding
import java.io.File


class ResultActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityResult2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResult2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var bitmap : Bitmap? =null
        if (intent.hasExtra("image")){
            //convert to bitmap
            val byteArray = intent.getByteArrayExtra("image")
            if (byteArray != null) {
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
        }
        val bright = intent.getFloatExtra("bright",0f)
        val cont = intent.getFloatExtra("cont",0f)
        val sat = intent.getFloatExtra("sat",0f)
        binding.picture.setImageBitmap(bitmap)
        binding.picture.brightness = bright
        binding.picture.contrast = cont
        binding.picture.saturation = sat
        binding.back.setOnClickListener {
            finish()
        }
        binding.home.setOnClickListener {
            val i = Intent(this@ResultActivity2, HomepageActivity2::class.java)
            startActivity(i)
        }
        binding.share.setOnClickListener {
            val outputFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath,
                "scanresult.pdf"
            )
            val imageUri = FileProvider.getUriForFile(
                this@ResultActivity2,
                "takepdf",  //(use your app signature + ".provider" )
                outputFile
            )
            val share = Intent()
            share.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            share.action = Intent.ACTION_SEND
            share.type = "application/pdf"
            share.putExtra(Intent.EXTRA_STREAM, imageUri)
            startActivity(Intent.createChooser(share, "Share"))
        }
        binding.save.setOnClickListener {
            if (bitmap != null) {
                createPDF(bitmap, this@ResultActivity2, binding.title1.text.toString())
            }
        }
    }
}
package com.example.projectftp

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.example.projectftp.data.ScanResponse
import com.example.projectftp.databinding.ActivityResultBinding
import java.io.File


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photo = intent.getStringExtra("HASIL")
        val bitmap = BitmapFactory.decodeFile(photo)
        binding.picture.setImageBitmap(bitmap)
        binding.back.setOnClickListener {
            val i = Intent(this@ResultActivity, Preview1::class.java)
            startActivity(i)
        }
        binding.home.setOnClickListener {
            val i = Intent(this@ResultActivity, HomepageActivity::class.java)
            startActivity(i)
        }
        binding.share.setOnClickListener {
            val outputFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString(),
                "scanresult.pdf"
            )
            val uri = Uri.fromFile(outputFile)
            val share = Intent()
            share.action = Intent.ACTION_SEND
            share.type = "application/pdf"
            share.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(share)
        }
        binding.save.setOnClickListener {
            createPDF(ArrayList<ScanResponse>(), photo.toString(), this@ResultActivity)
        }

    }
}
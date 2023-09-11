package com.example.projectftp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.projectftp.databinding.ActivityPreview1Binding
import com.example.projectftp.ml.BestFloat321
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.common.ops.QuantizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.TransformToGrayscaleOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class Preview1 : AppCompatActivity() {
    private lateinit var binding: ActivityPreview1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreview1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var bitmap = BitmapFactory.decodeFile(intent.getStringExtra("HASIL"))
        binding.picture.setImageBitmap(bitmap)
        binding.back.setOnClickListener {
            val i = Intent(this@Preview1, HomepageActivity::class.java)
            startActivity(i)
        }

        binding.calculate.setOnClickListener {
            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)
            bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true)
            val model = BestFloat321.newInstance(this@Preview1)
            val image = TensorImage.fromBitmap(bitmap)

            val outputs = model.process(image)
            outputs.outputAsCategoryList
            model.close()
        }
    }
}
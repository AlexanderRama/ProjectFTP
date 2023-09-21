package com.example.projectftp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.example.projectftp.databinding.ActivityPreview1Binding
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.ByteArrayOutputStream


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
        val MyImageFilterView =
            findViewById<ImageFilterView>(R.id.picture)

        val MySeekBarSat = findViewById<SeekBar>(R.id.seekBarSat)
        val MyTextViewSat = findViewById<TextView>(R.id.sat)
        val MySeekBarCont = findViewById<SeekBar>(R.id.seekBarCont)
        val MyTextViewCont = findViewById<TextView>(R.id.cont)
        val MySeekBarBright = findViewById<SeekBar>(R.id.seekBarBright)
        val MyTextViewBright = findViewById<TextView>(R.id.bright)

        //Get the first-run setting of saturation, contrast and warmth
        MyTextViewSat.text= MyImageFilterView.saturation.toString()
        MyTextViewCont.text= MyImageFilterView.contrast.toString()
        MyTextViewBright.text= MyImageFilterView.warmth.toString()

        binding.btnCont.setOnClickListener {
            MySeekBarSat.isEnabled = false
            MySeekBarBright.isEnabled = false
            MySeekBarCont.isEnabled = true
            if(binding.bright.alpha == 0f && binding.sat.alpha == 0f && binding.cont.alpha == 0f) {
                binding.cont.alpha = 1f
                binding.seekBarCont.alpha = 1f
                binding.txtCont.alpha = 1f
            } else {
                binding.cont.alpha = 0f
                binding.seekBarCont.alpha = 0f
                binding.txtCont.alpha = 0f
            }
        }

        binding.btnBright.setOnClickListener {
            MySeekBarSat.isEnabled = false
            MySeekBarBright.isEnabled = true
            MySeekBarCont.isEnabled = false
            if(binding.bright.alpha == 0f && binding.sat.alpha == 0f && binding.cont.alpha == 0f) {
                binding.bright.alpha = 1f
                binding.seekBarBright.alpha = 1f
                binding.txtBright.alpha = 1f
            } else {
                binding.bright.alpha = 0f
                binding.seekBarBright.alpha = 0f
                binding.txtBright.alpha = 0f
            }
        }

        binding.btnSat.setOnClickListener {
            MySeekBarSat.isEnabled = true
            MySeekBarBright.isEnabled = false
            MySeekBarCont.isEnabled = false
            if(binding.bright.alpha == 0f && binding.sat.alpha == 0f && binding.cont.alpha == 0f) {
                binding.sat.alpha = 1f
                binding.seekBarSat.alpha = 1f
                binding.txtSat.alpha = 1f
            } else {
                binding.sat.alpha = 0f
                binding.seekBarSat.alpha = 0f
                binding.txtSat.alpha = 0f
            }
        }

        MySeekBarSat.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?,
                                               progress: Int, fromUser: Boolean) {
                    //update saturation
                    MyImageFilterView.saturation = (progress / 100.0f) * 2
                    //read back the saturation
                    MyTextViewSat.text= MyImageFilterView.saturation.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

        MySeekBarCont.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?,
                                               progress: Int, fromUser: Boolean) {
                    MyImageFilterView.contrast = (progress / 100.0f) * 2
                    MyTextViewCont.text= MyImageFilterView.contrast.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

        MySeekBarBright.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?,
                                               progress: Int, fromUser: Boolean) {
                    MyImageFilterView.brightness = (progress / 100.0f) * 2
                    MyTextViewBright.text= MyImageFilterView.brightness.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

        var imageProcessor = ImageProcessor.Builder().add(ResizeOp(640, 640, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0.0f, 255.0f)).add(CastOp(DataType.FLOAT32))
            .build()

        binding.calculate.setOnClickListener {
            try {
                val bm = binding.picture.getDrawable() as BitmapDrawable
                val i = Intent(this@Preview1, ResultActivity::class.java)
                var bStream  =  ByteArrayOutputStream()
                bm.bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bStream)
                val byteArray = bStream.toByteArray()
                i.putExtra("image", byteArray)
                i.putExtra("bright", MyImageFilterView.brightness)
                i.putExtra("cont", MyImageFilterView.contrast)
                i.putExtra("sat", MyImageFilterView.saturation)
                startActivity(i)
            } catch (e : Exception) {

            }
//            val i = Intent(this@Preview1, ResultActivity::class.java)
//            i.putExtra("FINAL", bm.bitmap)
//            startActivity(i)
//            val inputImage = TensorImage.fromBitmap(imageProcessor)
//            val model = BestFloat323.newInstance(this@Preview1)
//            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 640, 640, 3), DataType.FLOAT32)
//            val byteBuffer = ByteBuffer.allocateDirect(4 * 640 * 640 * 3)
//            inputFeature0.loadBuffer(byteBuffer)
//
//            val outputs = model.process(inputFeature0)
//            val outputFeature0 = outputs.outputAsCategoryList.get(0).label
        }
    }
}
package com.testes.projetoimc

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private lateinit var btnMale: RadioButton
    private lateinit var btnFemale: RadioButton
    private lateinit var seekBarHeight: SeekBar
    private lateinit var seekBarIdade: SeekBar
    private lateinit var seekBarPeso: SeekBar
    private lateinit var txtHeight: TextView
    private lateinit var btnAgeMinus: Button
    private lateinit var btnAgePlus: Button
    private lateinit var btnWeightMinus: Button
    private lateinit var btnWeightPlus: Button
    private lateinit var txtAge: TextView
    private lateinit var txtWeight: TextView
    private lateinit var btnCalculate: Button
    private lateinit var txtResult: TextView

    private var height: Int = 0
    private var weight: Double = 0.0
    private var age: Int = 0
    private var imc: Double = 0.0
    private val FEMALE_CORRECTION_FACTOR = 1.1
    private val HEIGHT_CONVERSION_FACTOR = 0.01

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnMale = findViewById(R.id.btn_male)
        btnFemale = findViewById(R.id.btn_female)
        seekBarHeight = findViewById(R.id.seekBar_height)
        seekBarIdade = findViewById(R.id.seekBar_idade)
        seekBarPeso  = findViewById(R.id.seekBar_peso)
        txtHeight = findViewById(R.id.txt_height)
        txtAge = findViewById(R.id.txt_idade)
        txtWeight = findViewById(R.id.txt_peso)
        btnCalculate = findViewById(R.id.btn_calcular)
        txtResult = findViewById(R.id.txt_resultado)

        btnMale.isChecked = false
        btnFemale.isChecked = false


        seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                height = progress
                txtHeight.text = "$height cm"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarIdade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                age = progress
                txtAge.text = age.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        seekBarPeso.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                weight = progress.toDouble()
                txtWeight.text = weight.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        fun calculateIMC() {
            val heightInMeters = height * HEIGHT_CONVERSION_FACTOR
            var imc: Double? = null

            if (height == 0 || weight == 0.0 || age == 0) {
                txtResult.text = "Por favor, preencha todos os campos"
                txtResult.setTextColor(getColor(R.color.red))
                return
            }

            if (btnMale.isChecked) {
                imc = weight / (heightInMeters * heightInMeters)
            } else if (btnFemale.isChecked) {
                imc = weight / (heightInMeters * heightInMeters * FEMALE_CORRECTION_FACTOR)
            }

            if (imc == null) {
                txtResult.text = "Por favor, selecione o gênero"
                txtResult.setTextColor(getColor(R.color.red))
                return
            }

            // Define a cor do resultado de acordo com o valor do IMC
            val color = when {
                imc < 18.5 -> R.color.red // abaixo do peso
                imc < 25.0 -> R.color.green // peso normal
                imc < 30.0 -> R.color.yellow // sobrepeso
                imc < 35.0 -> R.color.orange // obesidade grau 1
                imc < 40.0 -> R.color.red // obesidade grau 2
                else -> R.color.dark_red // obesidade grau 3
            }

            // Exibe o resultado com a cor correspondente
            txtResult.text = "Seu IMC é: %.2f".format(imc)
            txtResult.setTextColor(getColor(color))
        }

        btnMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btnFemale.isChecked = false
                calculateIMC()
            } else if (!btnFemale.isChecked) {
                txtResult.text = ""
            }
        }

        btnFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btnMale.isChecked = false
                calculateIMC()
            } else if (!btnMale.isChecked) {
                txtResult.text = ""
            }
        }


    }
}
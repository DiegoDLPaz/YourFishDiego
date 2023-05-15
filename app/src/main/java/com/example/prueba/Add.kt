package com.example.prueba

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prueba.interfaces.PescadoAPI
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Add : AppCompatActivity() {

    lateinit var nombre: TextInputEditText
    lateinit var tipo: TextInputEditText
    lateinit var imageUrl: TextInputEditText
    lateinit var radioGroup: RadioGroup
    lateinit var espinadoSi: RadioButton
    lateinit var espinadoNo: RadioButton
    lateinit var insertar: Button
    var ip: String? = ""

    var espinado: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        val intent = intent

        ip = intent.getStringExtra("ip")

        println(ip)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://$ip:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        nombre = findViewById(R.id.nombre)
        tipo = findViewById(R.id.tipo)
        imageUrl = findViewById(R.id.imageUrl)
        espinadoSi = findViewById(R.id.espinado)
        espinadoNo = findViewById(R.id.noEspinado)
        radioGroup = findViewById(R.id.radioGroup)
        insertar = findViewById(R.id.insertar)

        espinadoSi.isChecked = true

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton: RadioButton = findViewById(checkedId)
            val selectedOption: String = selectedRadioButton.text.toString()

            when (selectedOption) {
                "Espinado" -> {
                    espinado = true
                }

                "No espinado" -> {

                }
            }
        }

        insertar.setOnClickListener {

            val nombreJ: String = nombre.text.toString()
            val tipo: String = tipo.text.toString()
            val imageUrl: String = imageUrl.text.toString()

            if (nombreJ == "" || tipo == "" || imageUrl == "") {
                Toast.makeText(
                    applicationContext,
                    "No deje campos vacíos por favor",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                val pescado = Pescado(
                    idPez = 0,
                    nombre = nombreJ,
                    espinado = espinado,
                    tipo = tipo,
                    imageUrl = imageUrl
                )

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response =
                            retrofit.create(PescadoAPI::class.java).createPescado(pescado).execute()
                        if (response.isSuccessful) {
                            // Show created successfully
                            println("El pescado se creó con éxito")
                            println(response.code())
                        } else {
                            // Show creation failed
                            println("ERROR " + response.code())
                        }
                    } catch (e: Exception) {
                        // Network request failed
                    }
                }
                finish()
            }
        }
    }
}
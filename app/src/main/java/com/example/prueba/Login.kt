package com.example.prueba

import android.bluetooth.le.AdvertiseSettings
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Login : AppCompatActivity() {
    lateinit var ip: String
    lateinit var nocuenta: TextView
    lateinit var login: TextView
    lateinit var settings: ImageView
    lateinit var entrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ip = "1020210120112"

        login = findViewById(R.id.LoginText)
        nocuenta = findViewById(R.id.crear)
        settings = findViewById(R.id.settings)
        entrar = findViewById(R.id.entrar)

        login.paintFlags = login.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        nocuenta.paintFlags = nocuenta.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val settingsClickListener = View.OnClickListener {
            showPopup()
        }

        settings.setOnClickListener(settingsClickListener)

        entrar.setOnClickListener{
            if (ip.trim().length > 15){
                Toast.makeText(applicationContext, "La ip no puede ocupar más de 15 carácteres", Toast.LENGTH_SHORT).show()
            }else if (ip.trim().length < 7){
                Toast.makeText(applicationContext, "La ip no puede ocupar menos de 7 carácteres", Toast.LENGTH_SHORT).show()
            }else{
            val miIntent = Intent(this, MainActivity::class.java)
            miIntent.putExtra("ip",ip)
            startActivity(miIntent)
            }
        }
    }

    private fun showPopup() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.popup_layout, null)
        val editText = dialogView.findViewById<EditText>(R.id.ip)
        editText.setText(ip)
        builder.setView(dialogView).setPositiveButton("Confirmar") { dialog, which ->
            val inputText = editText.text.toString().trim()
            if (inputText.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter an IP address", Toast.LENGTH_SHORT).show()
            } else {
                ip = inputText
                dialog.dismiss()
            }
            }.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}
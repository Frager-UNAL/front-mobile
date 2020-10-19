package com.example.frager

import android.app.VoiceInteractor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Registro : AppCompatActivity()  {
    var aux=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val nombre : EditText = findViewById<EditText>(R.id.userRegistro)
        val contraseña : EditText = findViewById<EditText>(R.id.passwordRegistro)
        val emailr : EditText = findViewById<EditText>(R.id.emailAddressR)
        val bRegistro : Button = findViewById<Button>(R.id.buttonR)
        bRegistro.setOnClickListener{
            postUser(emailr.text.toString().trim(),nombre.text.toString(),contraseña.text.toString())

            if (aux){
            showError("Bienvenido")
                val intent: Intent = Intent (this, principal ::class.java)
                startActivity(intent)}
        }
    }
    fun showError(msgError: String){
        Toast.makeText(this,msgError,Toast.LENGTH_SHORT).show()
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://192.168.0.6:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    private fun postUser(email:String,name:String,pass:String){
        val queue = Volley.newRequestQueue(this)
        val url = "http://3.238.113.217:8080/users"

        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("name", name)
        jsonObject.put("password", pass)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,url, jsonObject,
                {response ->
                    Log.i(null,"Response is $response")
                    aux = true
                },
                { error ->
                    error.printStackTrace()
                    showError("algo malio sal")
                    println("Error $error.message")
                })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)



    }
}
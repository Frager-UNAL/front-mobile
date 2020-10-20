package com.example.frager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var aux = false
    var userToValid : User? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text: TextView = findViewById<TextView>(R.id.textView2)
        text.setOnClickListener {
            val intent:Intent = Intent (this, Registro ::class.java)
            startActivity(intent)
        }
        val email : EditText = findViewById<EditText>(R.id.userName)
        val contraseña : EditText = findViewById<EditText>(R.id.TextPassword)
        val bLogin : Button = findViewById<Button>(R.id.login_b)
        bLogin.setOnClickListener {
            getUsers(email.text.toString())
            val gson=Gson()
            userToValid = gson.fromJson(hu,User::class.java)
            println(hu)
            validar(userToValid,contraseña.text.toString())
            if (aux){
                showError("Bienvenido")
                val intent: Intent = Intent (this, principal ::class.java)
                startActivity(intent)}
        }
    }
    fun showError(msgError: String){
        Toast.makeText(this,msgError,Toast.LENGTH_SHORT).show()
    }
    var hu:String=""
    private fun getUsers(id:String){
        val queue = Volley.newRequestQueue(this)
        val url = "http://3.238.113.217:8080/users/".plus(id)
        val jsonObjectRequest = StringRequest(url,
                {response ->
                    Log.i(null,"Response is $response")
                   hu= "Response: %s".format(response.toString())
                },
                { error ->
                    error.printStackTrace()
                    showError("error de conexion")
                })

        queue.add(jsonObjectRequest)
    }
    private fun validar(user: User?,contraseña:String){
        if((user!=null)&&(user.password==contraseña)){
            aux=true
        }else{
            showError("Email o contraseña incorrectos")
        }

    }


}
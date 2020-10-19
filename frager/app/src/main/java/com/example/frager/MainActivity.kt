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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text: TextView = findViewById<TextView>(R.id.textView2)
        text.setOnClickListener {
            val intent:Intent = Intent (this, Registro ::class.java)
            startActivity(intent)
        }
        val nombre : EditText = findViewById<EditText>(R.id.userName)
        val contraseña : EditText = findViewById<EditText>(R.id.TextPassword)
        val bLogin : Button = findViewById<Button>(R.id.login_b)
        bLogin.setOnClickListener {
        }
    }
    fun showError(msgError: String){
        Toast.makeText(this,msgError,Toast.LENGTH_SHORT).show()
    }
    private fun getRetrofit(): Retrofit {
        val interceptor=HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
                .baseUrl("http://192.168.0.6:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    private fun getUsers(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.6:8080/users/"
        val JsonArrayRequest = JsonArrayRequest(url,
                {response ->
                    Log.i(null,"Response is $response")
                },
                { error ->
                    error.printStackTrace()
                })
        queue.add(JsonArrayRequest)
    }
    private fun searchById(query: String) {
        doAsync {
            val call = getRetrofit().create(ApiService::class.java).getUserById(query).execute()
            val puppies = call.body() as User
            uiThread {
                    showError(puppies.name)
            }
        }
    }

}
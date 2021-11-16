package com.example.navigate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var infoList: MutableList<Informacion>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listViewData) as ListView
        infoList = mutableListOf<Informacion>()
        loadArtists()
    }

    private fun loadArtists() {
        val stringRequest = StringRequest(Request.Method.GET,
            "https://targetgsm.000webhostapp.com/v1/?op=getdata",
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("data")

                        for (i in 0..array.length() - 1) {
                            val objectData = array.getJSONObject(i)
                            val data = Informacion(
                                objectData.getInt("id"),
                                objectData.getString("latitud"),
                                objectData.getString("longitud"),
                                objectData.getString("presion"),
                                objectData.getString("humedad"),
                                objectData.getString("temperatura"),
                                objectData.getString("coordenadas"),
                                objectData.getString("giroscopio"),
                                objectData.getString("fecha")
                            )
                            infoList!!.add(data)
                            val adapter = InformacionAdapter(this@MainActivity, infoList!!)
                            listView!!.adapter = adapter
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }

}
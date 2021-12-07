package com.example.navigate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.navigate.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var infoList: MutableList<Informacion>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
        listView = findViewById(R.id.listViewData) as ListView
        infoList = mutableListOf<Informacion>()
        loadInfo()

        swipe.setOnRefreshListener {
            infoList!!.clear()
            val adapter = InformacionAdapter(this@MainActivity, infoList!!)
            listView!!.adapter = adapter
            loadInfo()
            swipe.isRefreshing = false
        }



    }

    private fun loadInfo() {
        val stringRequest = StringRequest(Request.Method.GET,
            "https://targetgsm.000webhostapp.com/v1/?op=getdata",
            { s ->
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
                                objectData.getString("fecha"),
                                objectData.getString("dt_created")
                            )
                            infoList!!.add(data)
                            val adapter = InformacionAdapter(this@MainActivity, infoList!!)
                            listView!!.adapter = adapter
                            listView!!.setOnItemClickListener { parent, view, position, id ->
                                val myItem = parent.getItemAtPosition(position) as Informacion
                                val latitud = myItem.latitud
                                val longitud = myItem.longitud
                                val intento1 = Intent(this, MapsActivity::class.java)
                                val extras = Bundle()
                                extras.putString("lat", latitud)
                                extras.putString("long", longitud)
                                intento1.putExtras(extras)
                                startActivity(intento1)
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }



}
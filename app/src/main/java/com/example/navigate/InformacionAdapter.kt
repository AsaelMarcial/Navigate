package com.example.navigate

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class InformacionAdapter(private val context: Activity, internal var datas: List<Informacion>) : ArrayAdapter<Informacion>(context, R.layout.item_informacion, datas) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.item_informacion, null, true)

        val txtlatitud = listViewItem.findViewById(R.id.txtLatitud) as TextView
        val txtlongitud = listViewItem.findViewById(R.id.txtLongitud) as TextView
        val txtPresion = listViewItem.findViewById(R.id.txtPresion) as TextView
        val txtHumedad = listViewItem.findViewById(R.id.txtHumedad) as TextView
        val txtTemperatura = listViewItem.findViewById(R.id.txtTemperatura) as TextView
        val txtCoordenadas = listViewItem.findViewById(R.id.txtCoordenadas) as TextView
        val txtFecha = listViewItem.findViewById(R.id.txtFecha) as TextView


        val data = datas[position]
        txtlatitud.text = data.latitud
        txtlongitud.text = data.longitud
        txtPresion.text = data.presion + "mb"
        txtHumedad.text = data.humedad + "%"
        txtTemperatura.text = data.temperatura + "°C"
        txtCoordenadas.text = data.coordenadas
        txtFecha.text = data.dt_created + " UTC"

        return listViewItem
    }
}
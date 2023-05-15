package com.example.prueba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.Pescado
import com.example.prueba.R
import com.squareup.picasso.Picasso


class Adaptador(var listaPescados: ArrayList<Pescado>,context: Context?) :
    RecyclerView.Adapter<Adaptador.layout_pescado>() {

    var mcontext = context

    inner class layout_pescado(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var tipo: TextView
        var espinado: TextView
        var nombre: TextView
        var imagen: ImageView

        init {
            tipo = itemView.findViewById(R.id.tipo)
            nombre = itemView.findViewById(R.id.nombre)
            espinado = itemView.findViewById(R.id.espinado)
            imagen = itemView.findViewById(R.id.imagen)

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): layout_pescado {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_pescado, parent, false)
        return layout_pescado(item)
    }

    override fun onBindViewHolder(holder: layout_pescado, position: Int) {
        val pescadoActual = listaPescados[position]

        val imageUrl = pescadoActual.imageUrl

        holder.nombre.text = pescadoActual.nombre
        if (pescadoActual.espinado == true){
            holder.espinado.text = "Si"
        }else{
            holder.espinado.text = "No"
        }
        holder.tipo.text = pescadoActual.tipo
        Picasso.with(mcontext).load(imageUrl).fit().centerInside().into(holder.imagen)

    }

    override fun getItemCount() = listaPescados.size


}
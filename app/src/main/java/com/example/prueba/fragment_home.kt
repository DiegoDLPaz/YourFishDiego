package com.example.prueba

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.adapter.Adaptador
import com.example.prueba.interfaces.PescadoAPI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class fragment_home : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorPescado: Adaptador
    private lateinit var listaPescado: ArrayList<Pescado>
    private lateinit var fab: FloatingActionButton
    private lateinit var sharedViewModel: SharedViewModel
    var ip: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        listaPescado = ArrayList()

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.ip.observe(viewLifecycleOwner, Observer { ip ->

            this.ip = ip

        })
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val itemDecorator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                recyclerView.context,
                R.drawable.divider
            )!!
        )
        recyclerView.addItemDecoration(itemDecorator)
        getData()
        val adapter = Adaptador(listaPescado, context) // Replace Adaptador with your adapter class
        recyclerView.adapter = adapter
    }

    fun fetchData(){
        getData()
    }

    private fun getData(): ArrayList<Pescado> {
    if (ip!=null){
        var antes = listaPescado.size
        listaPescado.clear()

        listaPescado.add(
            Pescado(
                1,
                "Vieja",
                true,
                "Agua salada",
                "https://marcacanaria.com/wp-content/uploads/2020/10/vieja-pescado-canario.jpg"
            )
        )

        val retrofit = Retrofit.Builder()
            .baseUrl("http://$ip:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var interfaz = retrofit.create(PescadoAPI::class.java)

        interfaz.obetenerPescados().enqueue(object : retrofit2.Callback<List<Pescado>> {

            override fun onResponse(call: Call<List<Pescado>>, response: Response<List<Pescado>>) {

                if (response.code() == 200) {
                    println("HEY")
                    val r = response.body()
                    if (r != null) {

                        for (pescado in r) {
                            listaPescado.add(pescado)
                            println(pescado.nombre.toString())
                        }
                    }
                } else {
                    println("HAY ALGO MAL")
                }

                adaptadorPescado = Adaptador(listaPescado, context)
                recyclerView.adapter = adaptadorPescado

                adaptadorPescado.notifyItemRangeRemoved(0, antes);
                //tell the recycler view how many new items we added
                adaptadorPescado.notifyItemRangeInserted(0, listaPescado.size);
            }

            override fun onFailure(call: Call<List<Pescado>>, t: Throwable) {
                println(t.message)
            }
        })
    }else{
        Handler().postDelayed({
            getData()
        }, 2000)
    }
     return listaPescado
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_home().apply {
            }
    }
}
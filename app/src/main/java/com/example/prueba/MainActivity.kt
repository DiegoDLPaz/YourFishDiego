package com.example.prueba

import android.annotation.SuppressLint
import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.prueba.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var drawer: DrawerLayout
    lateinit var toolbar: MaterialToolbar
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var bottom_nav: BottomNavigationView
    lateinit var boton_anyadir: FloatingActionButton
    lateinit var boton_refresh: FloatingActionButton
    var ip : String? = ""
    var isAdmin: Boolean = false
    private lateinit var sharedViewModel: SharedViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Tomamos el modelo compartido
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        val intent = intent

        //Tomamos el ip del Login y Comprobamos si es admin
        if (intent.getStringExtra("ip") != null) {
            ip = intent.getStringExtra("ip")
            isAdmin = intent.getBooleanExtra("admin",false)
            print(isAdmin)
            sharedViewModel.setIp(ip)
        }

        //Inicializamos el manejador de fragmentos
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //Inicializamos las variables
        drawer = binding.drawerLayout
        toolbar = binding.materialToolbar
        bottom_nav = binding.bottomNavigationView
        boton_anyadir = binding.floatingActionButton
        boton_refresh = binding.refresh

        //Escondemos los botones si no es admin
        if (!isAdmin){
            boton_anyadir.isVisible = false
            boton_refresh.isVisible = false
        }

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fragment_home2),
            drawer
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_nav.setupWithNavController(navController)

        //Bindeamos el Listener de la navegación
        binding.navView.setNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //Viajamos a la pantalla de añadir
        boton_anyadir.setOnClickListener {
            var miIntent = Intent(this,Add::class.java)
            miIntent.putExtra("ip",ip)
            startActivityForResult(miIntent,1)
        }

        boton_refresh.setOnClickListener{
            actualizarDatos()
        }

    }

    private val onNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home2 -> {
                    navController.navigate(R.id.fragment_home2)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.fragment_cart2 -> {
                    navController.navigate(R.id.fragment_cart2)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_LogOut -> {
                    finish()
                }
            }
            false
        }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Usamos la función del fragmento home para actualizar los datos
    private fun actualizarDatos() {
        val fragmentoHome = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val fragmentHome = fragmentoHome?.childFragmentManager?.primaryNavigationFragment as? fragment_home
        fragmentHome?.fetchData()
    }

}


package com.juanalberticohf.apis

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Favoritos : AppCompatActivity() {
    private lateinit var recyclerViewFavoritos: RecyclerView
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        // bind
        recyclerViewFavoritos = findViewById(R.id.rvFavoritos)
        val adaptadorFavoritos = AdaptadorFavoritos()
        recyclerViewFavoritos.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL, false)
        recyclerViewFavoritos.adapter = adaptadorFavoritos
        var datosFavoritos:MutableList<Autores>

        lifecycleScope.launch(Dispatchers.IO) {
            val database = AutoresBBDD.getInstance(applicationContext)
            datosFavoritos = database.autoresDAO().selectAll() as MutableList<Autores>
            withContext(Dispatchers.Main) {
                adaptadorFavoritos.changelist(datosFavoritos)
                adaptadorFavoritos.notifyDataSetChanged()
            }
        }

        adaptadorFavoritos.setAdaptadorCallBack(object : AdaptadorFavoritos.AdaptadorCallBack {
            override fun onDeleteFavorito(aut: Autores) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val database = AutoresBBDD.getInstance(applicationContext)
                    datosFavoritos = database.autoresDAO().selectAll() as MutableList<Autores>
                    database.autoresDAO().delete(aut)
                    withContext(Dispatchers.Main) {
                        adaptadorFavoritos.changelist(datosFavoritos)
                        adaptadorFavoritos.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
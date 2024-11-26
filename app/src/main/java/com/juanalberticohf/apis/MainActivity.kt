package com.juanalberticohf.apis

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var recy: RecyclerView
    val adaptadorRecyclerView = AdaptadorAutores()
    private lateinit var buscador: SearchView

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_favoritos -> {
                val intent = Intent(this, Favoritos::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBarPrincipal))

        //bind
        buscador=findViewById(R.id.svAutores)
        buscador.setOnQueryTextListener(this)
        recy = findViewById(R.id.rvAutores)
        recy.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL, false)
        recy.adapter = adaptadorRecyclerView

        adaptadorRecyclerView.setAdaptadorCallBack(object : AdaptadorAutores.AdaptadorCallBack {
            override fun onSaveAutor(aut: Autores) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val database = AutoresBBDD.getInstance(applicationContext)
                    database.autoresDAO().insert(aut)
                }
            }
        })
    }
    private fun getRetrofit(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl("https://openlibrary.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun busquedaAutores(query:String){
        lifecycleScope.launch(Dispatchers.IO) {
            val call = getRetrofit().create(APIservice::class.java).getAutores(
                "/search/authors.json?q=$query")
            val autoresAPI = call.body()
            if(call.isSuccessful){
                val autores = autoresAPI?. autores ?: emptyList()
                withContext(Dispatchers.Main) {
                    adaptadorRecyclerView.changelist(autores)
                    adaptadorRecyclerView.notifyDataSetChanged()
                }
            }else{
                Toast.makeText(applicationContext, "error API", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            busquedaAutores(query.lowercase())
        }
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.buscador.windowToken, 0)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}
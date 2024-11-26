package com.juanalberticohf.apis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdaptadorFavoritos : RecyclerView.Adapter<AutoresViewHolder>() {
    private var aut: List<Autores> = ArrayList()
    private lateinit var listener: AdaptadorCallBack
    interface AdaptadorCallBack {
        fun onDeleteFavorito(aut:Autores)
    }
    fun setAdaptadorCallBack (listener:AdaptadorCallBack){
        this.listener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var viewholder= AutoresViewHolder(layoutInflater.inflate(R.layout.fila_autores, parent, false))
        viewholder.toolbarAutores.inflateMenu(R.menu.toolbar_favoritos)
        return viewholder
    }

    override fun getItemCount(): Int = aut.size
    override fun onBindViewHolder(holder: AutoresViewHolder, position: Int) {
        val item = aut[position]
        holder.nombre.text = item.name
        holder.toolbarAutores.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_eliminar -> {
                    listener.onDeleteFavorito(item)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
    fun changelist(autores: List<Autores>) {
        aut=autores
    }
}
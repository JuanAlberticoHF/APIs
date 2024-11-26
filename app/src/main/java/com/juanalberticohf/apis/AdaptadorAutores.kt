package com.juanalberticohf.apis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdaptadorAutores : RecyclerView.Adapter<AutoresViewHolder>() {
    private var aut: List<Autores> = ArrayList()
    private lateinit var listener: AdaptadorCallBack
    interface AdaptadorCallBack {
        fun onSaveAutor(aut:Autores)
    }
    fun setAdaptadorCallBack (listener:AdaptadorCallBack){
        this.listener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var viewholder= AutoresViewHolder(layoutInflater.inflate(R.layout.fila_autores, parent, false))
        viewholder.toolbarAutores.inflateMenu(R.menu.toolbar_autores)
        return viewholder
    }

    override fun getItemCount(): Int = aut.size
    override fun onBindViewHolder(holder: AutoresViewHolder, position: Int) {
        val item = aut[position]
        holder.nombre.text = item.name
        holder.toolbarAutores.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_guardar -> {
                    listener.onSaveAutor(item)
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
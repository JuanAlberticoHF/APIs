package com.juanalberticohf.apis
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class AutoresViewHolder (view:View): RecyclerView.ViewHolder(view) {
    var nombre:TextView
    var toolbarAutores:Toolbar
    init {
        nombre=view.findViewById(R.id.tvAutor)
        toolbarAutores=view.findViewById(R.id.toolbarAutores)
    }
}
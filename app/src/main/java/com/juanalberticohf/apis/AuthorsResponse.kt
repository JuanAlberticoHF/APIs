package com.juanalberticohf.apis

import com.google.gson.annotations.SerializedName

data class AuthorsResponse (var numFound: Int, @SerializedName("docs") var autores: List<Autores>){
}
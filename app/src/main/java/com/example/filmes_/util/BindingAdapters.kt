package com.example.filmes_.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmes_.R
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.filmes.FilmesAdapter
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.netWork.model.ListaFilmes

@BindingAdapter("dataRecyclerView")
fun bindRecyclerViewData(recyclerView: RecyclerView, data : List<FilmeModel>?){
    //val adapter = recyclerView.adapter as FilmesAdapter
    //adapter.submitList(data)

    //app:dataRecyclerView="@{viewModel.listaFilmes}"
}

@BindingAdapter("getImageFromUrl")
fun bindImage(imgView : ImageView, imgUrl : String){
    val url = ("image.tmdb.org/t/p/w500" + imgUrl).toUri().buildUpon().scheme("https").build()
    Glide.with(imgView.context)
        .load(url)
        .apply(RequestOptions()
            .placeholder(R.drawable.ic_baseline_autorenew_24))
        .into(imgView)
}

@BindingAdapter("isFavorite")
fun bindIsFavorite(imgView: ImageView, isFavorite : Boolean){
    println("Chamado ___" + isFavorite.toString())
    if(isFavorite) imgView.setImageResource(R.drawable.ic_baseline_star_24)
    else           imgView.setImageResource(R.drawable.ic_baseline_star_border_24)
}

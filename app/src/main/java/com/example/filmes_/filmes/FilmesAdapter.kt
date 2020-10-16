package com.example.filmes_.filmes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes_.databinding.FilmesItemBinding
import com.example.filmes_.netWork.model.Filme
import kotlinx.android.synthetic.main.filmes_item.view.*

class FilmesAdapter(private val onClickListener: OnClickListener, private val lifecycle: LifecycleOwner) : ListAdapter<Filme,FilmesAdapter.FilmeViewHolder>(DiffCallback){

    class FilmeViewHolder(val binding : FilmesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(filme : Filme){
            binding.filme = filme
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Filme>() {
        override fun areItemsTheSame(oldItem: Filme , newItem: Filme): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Filme, newItem: Filme): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FilmeViewHolder {
        var binding = FilmesItemBinding.inflate(LayoutInflater.from(parent.context))

        return FilmeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = getItem(position)

        holder.itemView.imageViewFotoCelular.setOnClickListener {
            onClickListener.onClick(filme)
        }
        holder.itemView.imageViewFavoriteRecy.setOnClickListener {
            onClickListener.onClickFavorite(filme)
        }

        holder.bind(filme)
    }

    class OnClickListener(val clickListener: (filme:Filme) -> Unit, val clickFavotite : (filme : Filme) -> Unit) {
        fun onClick(filme:Filme) = clickListener(filme)
        fun onClickFavorite(filme: Filme) = clickFavotite(filme)
    }
}
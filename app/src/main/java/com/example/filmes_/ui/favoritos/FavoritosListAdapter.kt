package com.example.filmes_.ui.favoritos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes_.databinding.FavoritoItemBinding
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.util.ParseFilme
import kotlinx.android.synthetic.main.filmes_item.view.*

class FavoritosListAdapter(private val onClickListener: OnClickListener) : ListAdapter<FilmeModel, FavoritosListAdapter.FavoritosViewHolder>(DiffCallback){

    class FavoritosViewHolder(val binding : FavoritoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(filmeModel : FilmeModel){
            binding.filmeModel = filmeModel
            binding.lifecycleOwner = itemView.context as LifecycleOwner
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FilmeModel>() {
        override fun areItemsTheSame(oldItem: FilmeModel, newItem: FilmeModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FilmeModel, newItem: FilmeModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FavoritosViewHolder {
        val binding = FavoritoItemBinding.inflate(LayoutInflater.from(parent.context))

        return FavoritosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val filmeModel = getItem(position)

        holder.itemView.imageViewFotoCelular.setOnClickListener {
            ParseFilme.parseModelToFilme(filmeModel!!)?.let { it -> onClickListener.onClick(it) }
        }
        holder.itemView.imageViewFavoriteRecy.setOnClickListener {
            onClickListener.onClickFavorite(filmeModel!!)
        }

        holder.bind(filmeModel!!)
    }

    class OnClickListener(val clickListener: (filme: Filme) -> Unit, val clickFavotite : (filmeModel : FilmeModel) -> Unit) {
        fun onClick(filme: Filme) = clickListener(filme)
        fun onClickFavorite(filmeModel: FilmeModel) = clickFavotite(filmeModel)
    }
}
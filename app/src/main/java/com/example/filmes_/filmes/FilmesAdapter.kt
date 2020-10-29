package com.example.filmes_.filmes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes_.databinding.FilmesItemBinding
import com.example.filmes_.domain.FilmeModel
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.util.ParseFilme
import kotlinx.android.synthetic.main.filmes_item.view.*

class FilmesAdapter(private val onClickListener: OnClickListener,private val lifecycle: LifecycleOwner) : PagingDataAdapter<FilmeModel,FilmesAdapter.FilmeViewHolder>(DiffCallback){

    class FilmeViewHolder(val binding : FilmesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(filmeModel : FilmeModel,lifecycle: LifecycleOwner){
            binding.filmeModel = filmeModel
            binding.lifecycleOwner = lifecycle
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FilmeModel>() {
        override fun areItemsTheSame(oldItem: FilmeModel , newItem: FilmeModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FilmeModel, newItem: FilmeModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FilmeViewHolder {
        var binding = FilmesItemBinding.inflate(LayoutInflater.from(parent.context))

        return FilmeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filmeModel = getItem(position)

        holder.itemView.imageViewFotoCelular.setOnClickListener {
            onClickListener.onClick(ParseFilme.parseModelToFilme(filmeModel!!))
        }
        holder.itemView.imageViewFavoriteRecy.setOnClickListener {
            onClickListener.onClickFavorite(filmeModel!!)
        }

        holder.bind(filmeModel!!,lifecycle)
    }

    class OnClickListener(val clickListener: (filme:Filme) -> Unit, val clickFavotite : (filmeModel : FilmeModel) -> Unit) {
        fun onClick(filme:Filme) = clickListener(filme)
        fun onClickFavorite(filmeModel: FilmeModel) = clickFavotite(filmeModel)
    }
}
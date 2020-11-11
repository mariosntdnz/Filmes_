package com.example.filmes_.detalhes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.filmes_.databinding.FragmentDetalhesBinding
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class DetalhesFragment : Fragment() {

    private val viewModel : DetalhesViewModel by lazy {
        ViewModelProvider(this).get(DetalhesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetalhesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        var filmeModel = ParseFilme.parseFilmeToModel(DetalhesFragmentArgs.fromBundle(requireArguments()).filme)
        binding.filmeModel = filmeModel

        binding.imageViewFavorito.setOnClickListener {
            if (filmeModel != null) {
                filmeModel.favorite.value = !filmeModel.favorite.value!!
            }
            setFilmeFavorite()
        }

        return binding.root
    }
    fun setFilmeFavorite(){
        DetalhesFragmentArgs.fromBundle(requireArguments()).filme.favorite = !DetalhesFragmentArgs.fromBundle(requireArguments()).filme.favorite!!
    }
}
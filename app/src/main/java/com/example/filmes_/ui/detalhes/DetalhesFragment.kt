package com.example.filmes_.ui.detalhes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.filmes_.databinding.FragmentDetalhesBinding
import com.example.filmes_.ui.detalhes.DetalhesFragmentArgs
import com.example.filmes_.util.ParseFilme

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
        var filmeModel = ParseFilme.parseFilmeToModel(
            DetalhesFragmentArgs.fromBundle(
                requireArguments()
            ).filme)
        binding.filmeModel = filmeModel

        binding.imageViewFavorito.setOnClickListener {
            filmeModel?.favorite?.value = !filmeModel?.favorite?.value!!
            DetalhesFragmentArgs.fromBundle(requireArguments()).filme.favorite = filmeModel.favorite.value
            setFragmentResult("filme_KEY", bundleOf("filme" to DetalhesFragmentArgs.fromBundle(
                requireArguments()
            ).filme))
        }

        return binding.root
    }
}
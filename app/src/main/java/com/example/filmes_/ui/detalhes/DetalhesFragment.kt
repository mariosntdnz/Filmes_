package com.example.filmes_.ui.detalhes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmes_.databinding.FragmentDetalhesBinding
import com.example.filmes_.ui.filmes.FilmesViewModel
import com.example.filmes_.util.Parse

class DetalhesFragment : Fragment() {

    /*private val viewModel : DetalhesViewModel by lazy {
        ViewModelProvider(this).get(DetalhesViewModel::class.java)
    }*/

    lateinit var viewModel : DetalhesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this,DetalhesViewModelFactory(requireActivity().application)).get(DetalhesViewModel::class.java)

        val binding = FragmentDetalhesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val filmeModel = Parse.parseFilmeToModel(
            DetalhesFragmentArgs.fromBundle(
                requireArguments()
            ).filme)
        binding.filmeModel = filmeModel

        binding.imageViewFavorito.setOnClickListener {
            viewModel.updateFavorite(filmeModel!!)
        }

        viewModel.responseGeneros.observe(viewLifecycleOwner, Observer {
            binding.textViewGeneros.text = viewModel.getGenerosFormatados(binding.filmeModel!!)
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}
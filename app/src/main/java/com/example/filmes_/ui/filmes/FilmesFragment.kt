package com.example.filmes_.ui.filmes

import android.os.Bundle
import android.view.*
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.filmes_.R
import com.example.filmes_.databinding.FragmentFilmesBinding
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilmesFragment : Fragment() {

    private val viewModel : FilmesViewModel by lazy {
        ViewModelProvider(this).get(FilmesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding =  FragmentFilmesBinding.inflate(inflater,container,false)
        val adapter = FilmesAdapter(FilmesAdapter.OnClickListener(
            {viewModel.setFilmeClicado(it)},
            {viewModel.updateFavorite(it)}
        ))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerViewFilmes.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataFilmes.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        viewModel.filmeClicado.observe(viewLifecycleOwner, Observer{
            it?.let {
                this.findNavController().navigate(FilmesFragmentDirections.actionFilmesFragmentToDetalhesFragment(it,it.title!!))
                viewModel.nagationTelaDetalhes()
            }
        })

        viewModel.updateData()

        return binding.root
    }

}
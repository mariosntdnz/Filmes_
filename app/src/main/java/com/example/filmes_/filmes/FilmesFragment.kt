package com.example.filmes_.filmes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.example.filmes_.databinding.FragmentFilmesBinding
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerViewFilmes.adapter = FilmesAdapter(FilmesAdapter.OnClickListener(
            {viewModel.setFilmeClicado(it)},
            {viewModel.updateFavorite(it)}
        ))

        lifecycleScope.launch {
            viewModel.dataFilmes.collect {
                (binding.recyclerViewFilmes.adapter as FilmesAdapter).submitData(it)
            }
        }

        viewModel.filmeClicado.observe(viewLifecycleOwner, Observer{
            it?.let {
                this.findNavController().navigate(FilmesFragmentDirections.actionFilmesFragmentToDetalhesFragment(it))
                viewModel.nagationTelaDetalhes()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}
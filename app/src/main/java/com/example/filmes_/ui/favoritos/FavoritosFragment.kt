package com.example.filmes_.ui.favoritos

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.filmes_.databinding.FragmentFavoritosBinding
import com.example.filmes_.netWork.model.Filme
import com.example.filmes_.ui.detalhes.DetalhesFragmentArgs
import com.example.filmes_.util.ParseFilme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritosFragment : Fragment() {

    private val viewModel : FavoritosViewModel by lazy {
        ViewModelProvider(this).get(FavoritosViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFavoritosBinding.inflate(inflater,container,false)

        val adapter = FavoritosListAdapter(
            FavoritosListAdapter.OnClickListener(
            {viewModel.setFilmeClicado(it)},
            {viewModel.updateFavorite(it)}
        ))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerViewFavoritos.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataLisFilmes.collect { filmes ->
                adapter.submitList(filmes)
            }
        }

        viewModel.filmeClicado.observe(viewLifecycleOwner, Observer{
            it?.let {
                this.findNavController().navigate(FavoritosFragmentDirections.actionFavoritosFragmentToDetalhesFragment(it,it.title!!))
                viewModel.nagationTelaDetalhes()
            }
        })

        setFragmentResultListener("filme_KEY"){key,bundle->
            val filmeNaVoltaDetalhes = bundle.getParcelable<Filme>("filme")!!
            filmeNaVoltaDetalhes.favorite = !filmeNaVoltaDetalhes.favorite!!
            viewModel.updateFavoriteBD(ParseFilme.parseFilmeToModel(filmeNaVoltaDetalhes)!!)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}
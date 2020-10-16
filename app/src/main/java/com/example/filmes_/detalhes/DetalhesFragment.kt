package com.example.filmes_.detalhes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.filmes_.databinding.FragmentDetalhesBinding
import com.example.filmes_.netWork.model.Filme

class DetalhesFragment : Fragment() {

    private val viewModel : DetalhesViewModel by lazy {
        ViewModelProvider(this).get(DetalhesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetalhesBinding.inflate(inflater,container,false)

        binding.apply {
            lifecycleOwner = this@DetalhesFragment
            viewModel = this@DetalhesFragment.viewModel
            filme = DetalhesFragmentArgs.fromBundle(requireArguments()).filme
        }

        return binding.root
    }

}
package com.example.practicabooksoffline.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practicabooksoffline.databinding.FragmentSearchBinding
import com.example.practicabooksoffline.ui.adapters.MovieAdapter
import com.example.practicabooksoffline.ui.viewmodels.GridSpacingItemDecoration
import com.example.practicabooksoffline.ui.viewmodels.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.post {
            binding.searchView.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.searchView, InputMethodManager.SHOW_IMPLICIT)
        }

        setupAdapter()
        setupSearchView()
        observeViewModel()
    }

    private fun setupAdapter() {
        adapter = MovieAdapter(
            onMovieClick = { movie ->
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailMovieFragment(movie.id)
                )
            }
        )

        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvMovies.adapter = adapter

        val spacing = resources.getDimensionPixelSize(com.example.practicabooksoffline.R.dimen.grid_spacing)
        binding.rvMovies.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 3,
                spacing = spacing,
                includeEdge = true
            )
        )
    }

    private fun setupSearchView() {
        binding.searchView.apply {
            queryHint = getString(com.example.practicabooksoffline.R.string.search_hint)
            isIconified = false // Asegura que esté expandido
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.searchMovies(it) }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchMovies(newText ?: "")
                    return false // Cambia esto a false
                }
            })

            requestFocus()

            post {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
    private fun observeViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
            binding.tvEmpty.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
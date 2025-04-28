package com.example.practicabooksoffline.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicabooksoffline.databinding.FragmentListBinding
import com.example.practicabooksoffline.ui.adapters.MyListAdapter
import com.example.practicabooksoffline.ui.viewmodels.MyListViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyListViewModel by viewModels()
    private lateinit var adapter: MyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupTabs()
        observeViewModel()
    }

    private fun setupAdapter() {
        adapter = MyListAdapter(
            onFavoriteClick = { movieId ->
                viewModel.toggleFavorite(movieId)
            },
            onWatchedClick = { movieId, isChecked ->
                viewModel.toggleWatched(movieId, isChecked)
            },
            onItemClick = { movie ->
                val action = MyListFragmentDirections.actionListFragmentToDetailMovieFragment(movie.id)
                findNavController().navigate(action)
            }
        )

        binding.recyclerViewMyList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMyList.adapter = adapter
    }

    private fun setupTabs() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Favorites"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Watched"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.loadFavorites()
                    1 -> viewModel.loadWatched()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
            binding.recyclerViewMyList.visibility = if (movies.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
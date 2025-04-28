package com.example.practicabooksoffline.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.practicabooksoffline.R
import com.example.practicabooksoffline.databinding.FragmentDetailMovieBinding
import com.example.practicabooksoffline.db.models.Movie
import com.example.practicabooksoffline.ui.viewmodels.DetailMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val args: DetailMovieFragmentArgs by navArgs()
    private val viewModel: DetailMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.loadMovie(args.movieId)

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            movie?.let { bindMovieData(it) }
        }

        binding.fabFavorite.setOnClickListener {
            viewModel.toggleFavorite()
            viewModel.movie.value?.let { currentMovie ->
                updateFavoriteButton(currentMovie.isFavorite)
            }
        }

        binding.checkBoxWatchedAll.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleWatched(isChecked)
        }
    }

    private fun bindMovieData(movie: Movie) {
        with(binding) {
            Glide.with(requireContext())
                .load(movie.imageUrl)
                .into(imageViewPoster)

            textViewTitle.text = movie.title
            textViewRating.text = getString(R.string.rating_format, movie.rating)
            textViewInfo.text = getString(R.string.movie_info_format, movie.year ?: 0, movie.genre ?: "")
            textViewAboutDescription.text = movie.plot ?: ""

            checkBoxWatchedAll.isChecked = movie.isWatched
            updateFavoriteButton(movie.isFavorite)
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val icon = if (isFavorite) {
            R.drawable.ic_favorite_filled
        } else {
            R.drawable.ic_favorite_border
        }
        binding.fabFavorite.setImageResource(icon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
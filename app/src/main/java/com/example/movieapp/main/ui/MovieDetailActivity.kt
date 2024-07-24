package com.example.movieapp.main.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.MovieDetailRes
import com.example.movieapp.data.model.SearchItem
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.main.viewmodel.MovieVM
import com.example.movieapp.util.ApiResult
import com.example.movieapp.util.AppConstance.IntentConstant.DATA_TO_DETAIL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val vm: MovieVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        showProgressBar()

        val intent = intent.getSerializableExtra(DATA_TO_DETAIL) as? SearchItem

        intent.let {
            vm.getMovieDetail(id = it?.imdbID ?: "", type = it?.type ?: "")
        }

        lifecycleScope.launch {
            vm.movieDetail.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val data = result.data
                        Log.d("Api Response", "SUCCESS -> ${data}")
                        displayData(data)
                    }

                    is ApiResult.Error -> {
                        val errorMessage = result.message
                        Log.d("Api Response", "ERROR -> $errorMessage")
                    }
                }
                showProgressBar(false)
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun displayData(data: MovieDetailRes) {
        binding.apply {
            tvTitle.text = title
            tvReleaseDate.text = data.released
            tvMovieType.text = data.type
            tvIdbmRating.text = data.imdbRating
            tvRunTime.text = data.runtime
            tvMovieGenre.text = data.genre
            tvDirector.text = data.director
            tvWriter.text = data.writer
            tvActor.text = data.actors
            tvPlot.text = data.plot
            tvLanguage.text = data.language

            Glide.with(this@MovieDetailActivity)
                .load(data.poster)
                .placeholder(R.drawable.placeholder_image)
                .into(ivPoster)

        }
    }

    private fun showProgressBar(isShow: Boolean = true) {
        binding.pb.isVisible = isShow
    }
}
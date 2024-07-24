package com.example.movieapp.main.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.ActivityMovieListingBinding
import com.example.movieapp.main.viewmodel.MovieVM
import com.example.movieapp.util.ApiResult
import com.example.movieapp.util.AppConstance.IntentConstant.DATA_TO_DETAIL
import com.example.movieapp.util.Debounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListingBinding
    private val vm: MovieVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_listing)

        binding.noData = true
        val debounce = Debounce(300)

        binding.edtMovieName.doOnTextChanged { text, _, _, _ ->
            debounce.debounce {
                vm.searchMovies(text.toString())
            }
        }

        lifecycleScope.launch {
            vm.movieList.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        binding.noData = false
                        val data = result.data
                        Log.d("Api Response", "SUCCESS -> ${data.search}")
                        binding.rvMovieList.adapter = MovieAdapter(data.search, click = {

                            Log.d("RCV CLICK", "DATA -> $it")

                            val intent =
                                Intent(this@MovieListingActivity, MovieDetailActivity::class.java)
                            intent.putExtra(DATA_TO_DETAIL, it)
                            startActivity(intent)
                        })
                    }

                    is ApiResult.Error -> {
                        binding.noData = true
                        val errorMessage = result.message
                        Log.d("Api Response", "ERROR -> $errorMessage")
                    }
                }
            }
        }
    }
}
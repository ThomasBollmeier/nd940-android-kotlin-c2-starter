package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repo.AsteroidFilterType
import java.lang.RuntimeException
import java.lang.System.load

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        val adapter = AsteroidAdapter(AsteroidClickListener { asteroid ->
            val action = MainFragmentDirections.actionShowDetail(asteroid)
            this.findNavController().navigate(action)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.imageOfDayUrl.observe(viewLifecycleOwner, Observer { url ->
            if (!url.isNullOrEmpty()) {
                Picasso.with(context).load(url).into(binding.activityMainImageOfTheDay)
            }
        })

        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            asteroids?.let {
                adapter.submitList(asteroids)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.setFilter(when (item.itemId) {
            R.id.menu_item_week -> AsteroidFilterType.WEEK
            R.id.menu_item_today -> AsteroidFilterType.TODAY
            R.id.menu_item_saved -> AsteroidFilterType.SAVED
            else -> throw IllegalArgumentException("Unknown filter type")
        })

        return true
    }
}

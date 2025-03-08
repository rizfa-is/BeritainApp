package com.issog.beritainapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.issog.beritainapp.databinding.FragmentHomeBinding
import com.issog.beritainapp.utils.DataGenerator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initCategoryAdapters()
    }

    private fun initCategoryAdapters() {
        val categories = DataGenerator.generateCategoryData()
        val catAdapter = HomeCategoryAdapter()
        catAdapter.initData(categories)
        catAdapter.initClick { cat ->
            Toast.makeText(activity, cat.category, Toast.LENGTH_SHORT).show()
        }

        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = catAdapter
        }
    }
}
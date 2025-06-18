package com.issog.beritainapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.issog.beritainapp.R
import com.issog.beritainapp.databinding.ActivityBeritainBinding
import com.issog.core.domain.model.ArticleModel

class BeritainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeritainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBeritainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initStartDestination()
    }

    private fun initStartDestination() {
        val news = intent.extras?.getParcelable<ArticleModel>("news")
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val startDestination =
            news?.let {
                R.id.newsDetailFragment
            } ?: run {
                R.id.homeFragment
            }

        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph_home)
        navGraph.setStartDestination(startDestination)
        navController.setGraph(navGraph, bundleOf("news" to news))
    }
}

package com.issog.beritainapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.issog.beritainapp.R
import com.issog.beritainapp.databinding.FragmentNewsDetailBinding
import com.issog.core.domain.model.ArticleModel
import com.issog.core.utils.ImageUtils.loadImage
import com.issog.core.utils.NavigationUtils.safeNavigate

class NewsDetailFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        binding.apply {
            val news = arguments?.getParcelable<ArticleModel>("news")

            ivClose.setOnClickListener { actionBack() }
            btnReadFullNews.setOnClickListener {
                findNavController().safeNavigate(
                    R.id.newsWebViewFragment,
                    bundleOf("url" to news?.url)
                )
            }

            news?.let {
                imgNews.loadImage(it.urlToImage)
                tvTitle.text = it.title
                tvAuthor.text = it.author
                tvDesc.text = it.content
            }
        }
    }

    private fun actionBack() {
        if (!findNavController().popBackStack()) {
            activity?.finish()
        }
    }
}
package com.issog.beritainapp.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.issog.beritainapp.R
import com.issog.beritainapp.databinding.FragmentNewsWebViewBinding
import com.issog.core.utils.gone
import com.issog.core.utils.visible

class NewsWebViewFragment : Fragment() {
    private lateinit var binding: FragmentNewsWebViewBinding
    private lateinit var mWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleBackPressed()
    }

    private fun handleBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (mWebView.canGoBack()) {
                    mWebView.goBack()
                } else {
                    findNavController().popBackStack()
                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        binding.apply {
            ivClose.setOnClickListener { findNavController().popBackStack() }

            val url = arguments?.getString("url")
            mWebView = webview
            mWebView.settings.javaScriptEnabled = true
            mWebView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.pbWebview.visible()
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.tvUrl.text = view?.url?.substringAfter("://")?.split('/')?.get(0).orEmpty()
                    binding.pbWebview.gone()
                }
            }
            mWebView.webChromeClient = WebChromeClient()
            mWebView.loadUrl(url.orEmpty())
        }
    }
}
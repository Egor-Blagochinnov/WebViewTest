package com.egorblagochinnov.webview.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.egorblagochinnov.webview.R
import com.egorblagochinnov.webview.databinding.MainFragmentBinding
import com.egorblagochinnov.webviewfilechooserconfig.WebViewConfig

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    private val webView: WebView by lazy {
        requireView().findViewById<WebView>(R.id.web_view)
    }

    private val webViewConfigurator = WebViewConfig()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = requireView().findViewById<WebView>(R.id.web_view)

        webViewConfigurator.configure(this, binding.webView)


        webViewConfigurator.setupWebViewSettings(binding.webView)
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                return webViewConfigurator.handleFileChooser(
                    this@MainFragment,
                    webView,
                    filePathCallback,
                    fileChooserParams
                )
            }
        }


        binding.webView.loadUrl("https://sandbox.wert.io/")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        webViewConfigurator.onActivityResult(requestCode, resultCode, data)
    }

    fun loadPage() {
        binding.webView.loadUrl("https://sandbox.wert.io/")
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
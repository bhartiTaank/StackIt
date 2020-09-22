package com.bharti.stackit.listing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.bharti.stackit.R
import kotlinx.android.synthetic.main.dialog_web_view.*

class WebViewDialogFragment : DialogFragment() {

    private var webViewUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)
        arguments?.let {
            webViewUrl = it.getString(WEB_VIEW_URL_TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_web_view, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.settings.javaScriptEnabled = true
        webView.apply {
            webViewClient = WebViewClient()
            webViewUrl?.let { loadUrl(it) }
        }
    }

    companion object {
        const val WEB_VIEW_URL_TAG = "WebViewUrl"

        fun newInstance(url: String) =
            WebViewDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(WEB_VIEW_URL_TAG, url)
                }
            }
    }
}
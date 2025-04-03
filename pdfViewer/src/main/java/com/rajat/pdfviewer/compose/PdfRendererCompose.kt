package com.rajat.pdfviewer.compose

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.rajat.pdfviewer.HeaderData
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.util.CacheStrategy
import java.io.File

@Composable
fun PdfRendererViewCompose(
    url: String,
    modifier: Modifier = Modifier,
    headers: HeaderData = HeaderData(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    cacheStrategy: CacheStrategy = if ( Build.VERSION.SDK_INT < 24 ) CacheStrategy.MAXIMIZE_PERFORMANCE else CacheStrategy.MINIMIZE_CACHE,
    statusCallBack: PdfRendererView.StatusCallBack? = null,
) {
    AndroidView(
        factory = { context: Context -> PdfRendererView(context) },
        update = { pdfRendererView: PdfRendererView ->
            if (statusCallBack != null) {
                pdfRendererView.statusListener = statusCallBack
            }

            pdfRendererView.initWithUrl(
                url = url,
                headers = headers,
                lifecycleCoroutineScope = lifecycleOwner.lifecycleScope,
                lifecycle = lifecycleOwner.lifecycle,
                cacheStrategy = cacheStrategy,
            )
        },
        modifier = modifier,
    )
}

@Composable
fun PdfRendererViewCompose(
    file: File,
    modifier: Modifier = Modifier,
    cacheStrategy: CacheStrategy = if ( Build.VERSION.SDK_INT < 24 ) CacheStrategy.MAXIMIZE_PERFORMANCE else CacheStrategy.MINIMIZE_CACHE,
    statusCallBack: PdfRendererView.StatusCallBack? = null,
) {
    AndroidView(
        factory = { context -> PdfRendererView(context) },
        update = { pdfRendererView: PdfRendererView ->
            if (statusCallBack != null) {
                pdfRendererView.statusListener = statusCallBack
            }

            pdfRendererView.initWithFile(file = file, cacheStrategy = cacheStrategy)
        },
        modifier = modifier
    )
}

@Composable
fun PdfRendererViewCompose(
    uri: Uri,
    modifier: Modifier = Modifier,
    statusCallBack: PdfRendererView.StatusCallBack? = null,
) {
    AndroidView(
        factory = { context -> PdfRendererView(context) },
        update = { pdfRendererView: PdfRendererView ->
            if (statusCallBack != null) {
                pdfRendererView.statusListener = statusCallBack
            }

            pdfRendererView.initWithUri(uri = uri)
        },
        modifier = modifier,
    )
}

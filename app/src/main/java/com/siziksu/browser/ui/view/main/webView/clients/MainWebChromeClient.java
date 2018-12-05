package com.siziksu.browser.ui.view.main.webView.clients;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;

import javax.inject.Inject;

public class MainWebChromeClient extends WebChromeClient {

    @Inject
    Context context;

    private Consumer<Integer> progress;
    private Consumer<String> blankListener;
    private Consumer<View> videoViewShowingListener;
    private Consumer<View> videoViewHidingListener;
    private View videoView;
    private View videoProgressView;
    private CustomViewCallback callback;

    @Inject
    public MainWebChromeClient() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {
        WebView.HitTestResult result = view.getHitTestResult();
        String data = result.getExtra();
        blankListener.accept(data);
        return false;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (progress != null) {
            progress.accept(newProgress);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        onShowCustomView(view, callback);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (videoView != null) {
            callback.onCustomViewHidden();
            return;
        }
        videoView = view;
        videoViewShowingListener.accept(videoView);
        this.callback = callback;
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        if (videoView == null) { return; }
        videoView.setVisibility(View.GONE);
        videoViewHidingListener.accept(videoView);
        videoView = null;
        callback.onCustomViewHidden();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getVideoLoadingProgressView() {
        if (videoProgressView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            videoProgressView = inflater.inflate(R.layout.video_progress, null);
        }
        return videoProgressView;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        result.confirm();
        return true;
    }

    public void setProgressListener(Consumer<Integer> progress) {
        this.progress = progress;
    }

    public void setOnBlankLinkListener(Consumer<String> blankListener) {
        this.blankListener = blankListener;
    }

    public void setVideoViewShowingListener(Consumer<View> videoViewShowingListener) {
        this.videoViewShowingListener = videoViewShowingListener;
    }

    public void setVideoViewHidingListener(Consumer<View> videoViewHidingListener) {
        this.videoViewHidingListener = videoViewHidingListener;
    }

    public boolean isShowingVideoView() {
        return videoView != null;
    }

    public void hideVideoView() {
        onHideCustomView();
    }
}

package com.siziksu.browser.ui.common.manager;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloaderManager {

    private static final String USER_AGENT_KEY = "User-Agent";
    private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36";
    private static final String COOKIE_KEY = "cookie";

    private DownloaderManager() {}

    public static void download(Context context, String url, Consumer<Boolean> listener) {
        Log.i(Constants.TAG, "URL to download: " + url);
        if (url.startsWith("data:")) {
            createAndSaveFileFromBase64Url(context, url);
        }
        if (URLUtil.isValidUrl(url)) {
            String contentDisposition = "";
            String mimeType = getMimeType(url);
            String cookies = CookieManager.getInstance().getCookie(url);
            DownloadManager.Request request = getRequest(url, contentDisposition, mimeType, cookies);
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (manager != null) {
                manager.enqueue(request);
                if (listener != null) {
                    listener.accept(true);
                }
            } else {
                if (listener != null) {
                    listener.accept(false);
                }
            }
        } else {
            if (listener != null) {
                listener.accept(false);
            }
        }
    }

    @NonNull
    private static DownloadManager.Request getRequest(String url, String contentDisposition, String mimeType, String cookies) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.addRequestHeader(COOKIE_KEY, cookies);
        request.addRequestHeader(USER_AGENT_KEY, USER_AGENT_VALUE);
        request.setDescription("Downloading file...");
        request.setMimeType(mimeType);
        request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
        request.allowScanningByMediaScanner();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
        return request;
    }

    private static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createAndSaveFileFromBase64Url(Context context, String url) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileType = url.substring(url.indexOf("/") + 1, url.indexOf(";"));
        String fileName = System.currentTimeMillis() + "." + fileType;
        File file = new File(path, fileName);
        try {
            if (!path.exists()) { path.mkdirs(); }
            if (!file.exists()) { file.createNewFile(); }
            String base64EncodedString = url.substring(url.indexOf(",") + 1);
            byte[] decodedBytes = Base64.decode(base64EncodedString, Base64.DEFAULT);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(decodedBytes);
            outputStream.close();
            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, null);
            NotificationManager.sendSimpleNotification(context, fileName, getPendingIntent(context, url, file));
        } catch (IOException e) {
            Toast.makeText(context, "Error downloading file", Toast.LENGTH_SHORT).show();
        }
    }

    private static PendingIntent getPendingIntent(Context context, String url, File file) {
        String mimeType = url.substring(url.indexOf(":") + 1, url.indexOf("/"));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = Uri.parse(file.getPath());
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, (mimeType + "/*"));
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}

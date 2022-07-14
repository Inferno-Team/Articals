package com.inferno.mobile.articals.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.activities.MainActivity;
import com.inferno.mobile.articals.databinding.ArticleDetailsLayoutBinding;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class PDFDownloaderThread extends Thread {
    final private String url;
    private final ArticleDetailsLayoutBinding binding;
    private final Context context;
    private File pdfFileInCache = null;
    private final String title;
    public String localPath;

    public PDFDownloaderThread(String url,
                               ArticleDetailsLayoutBinding binding, Context context) {
        this.url = url;
        this.binding = binding;
        this.context = context;
        this.title = binding.toolbar.getTitle().toString();
    }

    @Override
    public void run() {
        super.run();
        pdfFileInCache = new File(context.getCacheDir(), "pdf_file");
        if (pdfFileInCache.exists()) {
            pdfFileInCache.delete();
        }
        try {
            URL _url = new URL(url);
            URLConnection connection = _url.openConnection();
//            int lengthOfFile = connection.getContentLength();
            int count;
            InputStream input = new BufferedInputStream(_url.openStream(),
                    8192);
            OutputStream output = new FileOutputStream(pdfFileInCache);
            byte[] data = new byte[1024];

//            long total = 0;
            while ((count = input.read(data)) != -1) {
//                total += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            localPath = pdfFileInCache.getAbsolutePath();

            ((MainActivity) context).runOnUiThread(() -> {
                binding.pdfDownloader.setEnabled(true);
                binding.pdfView.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.pdfView.fromFile(pdfFileInCache)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .enableAnnotationRendering(false)
                        .password(null)
                        .scrollHandle(null)
                        .enableAntialiasing(true)
                        .spacing(0)
                        .onPageChange(this::onPageChange)
                        .pageFitPolicy(FitPolicy.WIDTH)
                        .load();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPageChange(int page, int pageCount) {
        String fullTitle = context.getString(R.string.page_number, page + "/" + pageCount);
        binding.toolbar.setSubtitle(fullTitle);
    }

    public void removePDF() {
        Log.d("PDF", "removePDF: " + pdfFileInCache.exists());
        if (pdfFileInCache.exists()) {
            boolean isDeleted = pdfFileInCache.delete();
            Log.d("PDF", "is removed PDF: " + isDeleted);
        }
        pdfFileInCache = null;
    }

    public void downloadToFile() {
        if (pdfFileInCache != null) {
            File downloadedFile = new File(Environment.getExternalStorageDirectory() + "/Download/articles");
            if (!downloadedFile.isDirectory())
                downloadedFile.mkdir();
            String name = "/" + title + ".pdf";
            downloadedFile = new File(downloadedFile.getAbsolutePath() + name);
            try {
                copy(pdfFileInCache, downloadedFile);
                Toast.makeText(context, "file saved as :\n" + downloadedFile.getAbsolutePath()
                        , Toast.LENGTH_LONG).show();
                showNotification(downloadedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showNotification(String path) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("file_path", path);
        intent.putExtra("is_moving", true);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notificationRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final String channelId = "channelIdString";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentText("File Downloaded in path : " + path)
                .setContentTitle("File Downloaded")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setVibrate(new long[]{300, 300, 300, 300, 300, 300})
                .setSound(notificationRingtone)
                //.setColor(context.getColor(R.color.schwarz))
                .setOngoing(false)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setFullScreenIntent(pendingIntent, false);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        AudioAttributes att = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioManager.STREAM_ALARM)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        NotificationChannelCompat notificationChannel =
                new NotificationChannelCompat.Builder(channelId, NotificationManagerCompat.IMPORTANCE_HIGH)
                        .setSound(notificationRingtone, att)
                        .build();
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(0, builder.build());

    }

    private void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}

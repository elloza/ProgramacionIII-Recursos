package com.elloza.youtubemetadata;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;

public class VideoInfoExtractor {

    public static VideoInfo getVideoInfoFromUrl(String url) {
        String videoId = extractVideoId(url);
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo request = new RequestVideoInfo(videoId);

        Response<VideoInfo> response = downloader.getVideoInfo(request);

        if (response.ok()) {
            return response.data();
        } else {
            throw new RuntimeException("Error fetching video info: " + response.error().getMessage());
        }
    }

    private static String extractVideoId(String url) {
        // Simple extractor for YouTube video ID from URL
        String[] parts = url.split("v=");
        if (parts.length > 1) {
            return parts[1].split("&")[0];
        } else {
            throw new IllegalArgumentException("Invalid YouTube URL");
        }
    }
}
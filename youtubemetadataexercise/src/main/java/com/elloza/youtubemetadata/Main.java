package com.elloza.youtubemetadata;

import com.github.kiulian.downloader.model.videos.VideoInfo;

public class Main {
    public static void main(String[] args) {
        String url = "https://www.youtube.com/watch?v=6iRLDa0LQNM";
        try {
            VideoInfo videoInfo = VideoInfoExtractor.getVideoInfoFromUrl(url);
            System.out.println("Title: " + videoInfo.details().title());
            System.out.println("Views: " + videoInfo.details().viewCount());
            System.out.println("Author: " + videoInfo.details().author());
            System.out.println("Keywords: " + videoInfo.details().keywords());
            System.out.println("Description: " + videoInfo.details().description());
            System.out.println("Duration: " + videoInfo.details().lengthSeconds() + " seconds"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//package com.example.doctorapp.ui.activities;
//
//import com.example.doctorapp.utility.NetworkClient;
//
//public final class DataDownloadManager {
//    private static DataDownloadManager instance;
//
//    private NetworkClient client = new NetworkClient();
//
//    private DataDownloadManager() {
//    }
//
//     public static DataDownloadManager getInstance() {
//    }
//
//    public void downloadEvents() {
//        client.getRetrofitClient().getEvents().enqueue(new EventListResponseProcessor());
//        NetworkClient.getOpenEventAPI().getEvents().enqueue(new EventListResponseProcessor());
//    }
//
//    public void downloadSpeakers() {
//        client.getOpenEventAPI().getSpeakers().enqueue(new SpeakerListResponseProcessor());
//        NetworkClient.getOpenEventAPI().getSpeakers().enqueue(new SpeakerListResponseProcessor());
//    }
//
//    public void downloadSponsors() {
//        client.getOpenEventAPI().getSponsors().enqueue(new SponsorListResponseProcessor());
//        APIClient.getOpenEventAPI().getSponsors().enqueue(new SponsorListResponseProcessor());
//    }
//
//    public void downloadSession() {
//        client.getOpenEventAPI().getSessions("start_time.asc").enqueue(new SessionListResponseProcessor());
//        APIClient.getOpenEventAPI().getSessions("start_time.asc").enqueue(new SessionListResponseProcessor());
//    }
//
//    public void downloadTracks() {
//        client.getOpenEventAPI().getTracks().enqueue(new TrackListResponseProcessor());
//        APIClient.getOpenEventAPI().getTracks().enqueue(new TrackListResponseProcessor());
//    }
//
//    public void downloadMicrolocations() {
//        client.getOpenEventAPI().getMicrolocations().enqueue(new MicrolocationListResponseProcessor());
//        APIClient.getOpenEventAPI().getMicrolocations().enqueue(new MicrolocationListResponseProcessor());
//    }
//
//
//}
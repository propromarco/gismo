package com.github.propromarco.gismo.data;

public enum Radio {
    EinsLive("https://wdr-1live-live.icecastssl.wdr.de/wdr/1live/live/mp3/128/stream.mp3");

    private final String url;

    Radio(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

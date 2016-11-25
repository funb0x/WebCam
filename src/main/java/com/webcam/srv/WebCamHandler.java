package com.webcam.srv;

import com.github.sarxos.webcam.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WebCamHandler implements WebcamUpdater.DelayCalculator, WebcamListener {

    public static final WebCamHandler instance = new WebCamHandler();
    public static final int DELAY = 50;
    private Webcam webcam;
    private List<ImageListener> imageListeners = new ArrayList<>();

    public WebCamHandler() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.addWebcamListener(this);
        webcam.open(true, this);
    }

    public void webcamOpen(WebcamEvent we) {
        System.out.println(we.getSource().getName() + " opened");
    }

    public void webcamClosed(WebcamEvent we) {
        System.out.println(we.getSource().getName() + " closed");
    }

    public void webcamDisposed(WebcamEvent we) {
        System.out.println(we.getSource().getName() + " disposed");
    }

    public void webcamImageObtained(final WebcamEvent we) {
        imageListeners.forEach(imageListener -> imageListener.onImage(we.getImage()));
    }

    public long calculateDelay(long snapshotDuration, double deviceFps) {
        return Math.max(DELAY - snapshotDuration, 0);
    }

    public void subscribe(ImageListener listener) {
        imageListeners.add(listener);
    }

    public void unSubscribe(ImageListener listener) {
        imageListeners.remove(listener);
    }

}

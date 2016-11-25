package com.webcam.utils;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.*;

public class WebCamTest {


    @Test
    public void showVideoTest() {
        Dimension dimension = WebcamResolution.VGA.getSize();
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(dimension);
        webcam.open();

        JFrame frame = new JFrame();
        frame.setSize(dimension);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        VideoPanel panel = new VideoPanel();
        frame.add(panel);

        //TODO refactor this
        for(;; panel.updateImage(webcam.getImage())) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class VideoPanel extends JPanel {

        private BufferedImage image;
        private final ExecutorService worker = Executors.newSingleThreadExecutor();

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            if (image != null) {
                int width = image.getWidth();
                int height = image.getHeight();
                g2.clearRect(0, 0, width, height);
                g2.drawImage(image, 0, 0, width, height, null);
                setSize(width, height);
            }
        }

        public void updateImage(final BufferedImage update) {
            worker.execute(new Runnable() {

                public void run() {
                    image = update;
                    repaint();
                }
            });
        }

        public void close() {
            worker.shutdown();
        }

    }
}



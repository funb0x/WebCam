package com.webcam.srv;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebSocket
public class WebCamWebSocketHandler implements ImageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Session session;

    private void setup(Session session) {
        this.session = session;
        WebCamHandler.instance.subscribe(this);
    }

    private void tearDown() {
        try {
            session.close();
            session = null;
        } finally {
            WebCamHandler.instance.unSubscribe(this);
        }
    }

    @OnWebSocketClose
    public void onClose(int status, String reason) {
        System.out.println("WebSocket closed, status = " + status + ", reason = " + reason);
        tearDown();
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("WebSocket error " + t);
        tearDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connect, from = " + session.getRemoteAddress().getAddress());
        setup(session);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("WebSocket message, text = " + message);
    }

    @Override
    public void onImage(BufferedImage image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPG", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String base64 = null;
        try {
            base64 = new String(Base64.getEncoder().encode(baos.toByteArray()), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, Object> message = new HashMap<String, Object>();
        message.put("type", "image");
        message.put("image", base64);

        send(message);
    }

    private void send(String message) {
        if (session.isOpen()) {
            try {
                session.getRemote().sendStringByFuture(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void send(Object object) {
        try {
            send(MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
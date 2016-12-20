package com.webcam.srv;

public class CarController {

    public static final CarController carController = new CarController();

    public void onControl(String key) {
        switch (key) {
            case "UP_P" : {
                onUpPressed(); break;}
            case "DOWN_P" : {
                onDownPressed(); break;}
            case "LEFT_P" : {
                onLeftPressed(); break;}
            case "RIGHT_P" : {
                onRightPressed(); break;}
            case "UP_R" : {
                onUpReleased(); break;}
            case "DOWN_R" : {
                onDownReleased(); break;}
            case "LEFT_R" : {
                onLeftReleased(); break;}
            case "RIGHT_R" : {
                onRightReleased(); break;}
            default: throw new RuntimeException("Unknown Control");
        }
    }

    private void onUpPressed() {
        System.out.println("MOVING FORWARD");
    }

    private void onUpReleased() {
        System.out.println("STOP MOVING FORWARD");
    }

    private void onDownPressed() {
        System.out.println("MOVING BACKWARD");
    }

    private void onDownReleased() {
        System.out.println("STOP MOVING BACKWARD");
    }

    private void onLeftPressed() {
        System.out.println("TURNING LEFT");
    }

    private void onLeftReleased() {
        System.out.println("STOP TURNING LEFT");
    }

    private void onRightPressed() {
        System.out.println("TURNING RIGHT");
    }

    private void onRightReleased() {
        System.out.println("STOP TURNING RIGHT");
    }
}

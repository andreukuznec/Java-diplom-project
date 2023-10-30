package ru.netology.graphics.image;


public class BadImageSizeException extends Exception {
    public BadImageSizeException(double ratio, double maxRatio) {
        super("Maximum image aspect ratio " + maxRatio + ", but this has " + ratio);
    }
}

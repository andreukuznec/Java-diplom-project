package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    protected int maxWidth;
    protected int maxHeight;
    protected double maxRatio;
    protected double ratio;
    protected int color;
    private boolean setTextColorSchemaUse;
    protected TextColorSchema schema;


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));
        if ((img.getWidth() / img.getHeight()) > maxRatio) {
            throw new BadImageSizeException(maxRatio, ratio);
        }
        int newWidth = img.getWidth();
        int newHeight = img.getHeight();


        if (maxWidth < img.getWidth()) {
            newWidth = img.getWidth() / (img.getWidth() / maxWidth);
            newHeight = img.getHeight() / (img.getWidth() / maxWidth);
        }
        if (maxHeight < img.getHeight()) {
            newWidth = img.getWidth() / (img.getHeight() / maxHeight);
            newHeight = img.getHeight() / (img.getHeight() / maxHeight);
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        TextColorSchema converter;
        if (setTextColorSchemaUse) {
            converter  = schema;
            } else {
            converter = new ColorSchema();}

        int w;
        int h;
        int colorIntense[] = new int[3];
        char[][] charsCord = new char[newHeight][newWidth];
        for (h = 0; h < newHeight; h++) {
            for (w = 0; w < newWidth; w++) {
                color = bwRaster.getPixel(w, h, colorIntense)[0];
                char c = converter.convert(color);
                charsCord[h][w] = c;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (h = 0; h < newHeight; h++) {
            sb.append("\n");
            for (w = 0; w < newWidth; w++) {
                sb.append(charsCord[h][w]);
                sb.append(charsCord[h][w]);
            }
        }
        return sb.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        maxWidth = width;
        System.out.println("Установлена максимальная ширина: " + width + "пикселей");
    }

    @Override
    public void setMaxHeight(int height) {
        maxHeight = height;
        System.out.println("Установлена максимальная высота: " + height + "пикселей");

    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
        setTextColorSchemaUse = true;

    }

}

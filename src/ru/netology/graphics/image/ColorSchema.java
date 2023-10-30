package ru.netology.graphics.image;
public class ColorSchema implements TextColorSchema {
    char[] chars = new char[]{'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};

    @Override
    public char convert(int color) {

        return chars[(int) Math.floor(color / 256. * chars.length)];
    }
}




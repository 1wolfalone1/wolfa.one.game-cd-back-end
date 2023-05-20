package com.wolfalone.gamecdbackend.util;


import java.util.Random;

public class MyRamdom {
    public final static String generateString(int size) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = size;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateString(10));
        System.out.println(generateString(10));
        System.out.println(generateString(10));
        System.out.println(generateString(10));

    }
}

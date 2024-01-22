package com.htb_kg.ctf;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import java.security.SecureRandom;

public class Test {
    public static void main(String[] args) {
        int passwordLength = 12; // Specify the desired length of the password

        String randomPassword = generateRandomPassword(passwordLength);
        System.out.println("Random Password: " + randomPassword);
    }

    private static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }
}


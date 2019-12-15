package faf.labs.si.encryption.services;

import org.springframework.stereotype.Service;

@Service
public class Encoder {

    private static char SEPARATOR = ' ';

    public String encryption(String input, int key) {
        StringBuilder encryptedString = new StringBuilder();

        for (char c : input.toCharArray()) {
            encryptedString.append(((int) c) + key).append(((int) SEPARATOR) + key);
        }

        return encryptedString.toString();
    }

    public String decryption(String input, int key) {
        StringBuilder decryptedString = new StringBuilder();

        String[] str = input.split(String.valueOf(((int) SEPARATOR) + key));
        for (String s : str) {
            decryptedString.append((char) (Integer.valueOf(s) - key));
        }

        return decryptedString.toString();
    }

}

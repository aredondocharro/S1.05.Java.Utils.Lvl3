package Encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

public class KeyManager {
    private static final int AES_KEY_SIZE = 128;

    public SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE);
        return keyGen.generateKey();
    }

    public IvParameterSpec generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public void saveKeyAndIV(SecretKey key, IvParameterSpec iv, String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(Base64.getEncoder().encodeToString(key.getEncoded()) + "\n");
            writer.write(Base64.getEncoder().encodeToString(iv.getIV()));
        }
    }

    public SecretKey loadKey(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String keyLine = reader.readLine();
            byte[] decodedKey = Base64.getDecoder().decode(keyLine);
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        }
    }

    public IvParameterSpec loadIV(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine(); // skip key line
            String ivLine = reader.readLine();
            byte[] decodedIV = Base64.getDecoder().decode(ivLine);
            return new IvParameterSpec(decodedIV);
        }
    }
}
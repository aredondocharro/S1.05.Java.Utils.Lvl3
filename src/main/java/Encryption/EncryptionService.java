package Encryption;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;

public class EncryptionService {
    private final KeyManager keyManager;
    private final FileEncryptor encryptor;

    public EncryptionService(String keyFilePath) throws Exception {
        this.keyManager = new KeyManager();
        SecretKey key;
        IvParameterSpec iv;

        File file = new File(keyFilePath);
        if (file.exists()) {
            key = keyManager.loadKey(keyFilePath);
            iv = keyManager.loadIV(keyFilePath);
        } else {
            key = keyManager.generateKey();
            iv = keyManager.generateIV();
            keyManager.saveKeyAndIV(key, iv, keyFilePath);
        }

        this.encryptor = new FileEncryptor(key, iv);
    }

    public void encrypt(String inputPath, String outputPath) throws Exception {
        encryptor.encryptFile(new File(inputPath), new File(outputPath));
    }

    public void decrypt(String inputPath, String outputPath) throws Exception {
        encryptor.decryptFile(new File(inputPath), new File(outputPath));
    }
}
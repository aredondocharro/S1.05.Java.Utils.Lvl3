import Encryption.EncryptionService;

public class Main {
    public static void main(String[] args) {
        try {
            EncryptionService service = new EncryptionService("secret.key");
            service.encrypt("DirectoryTree.txt", "DirectoryTree.dat");
            service.decrypt("DirectoryTree.dat", "DirectoryTree_decrypted.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
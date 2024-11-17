import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtil {
    private static final String AES = "AES";
    private static final String SECRET_KEY = "mySuperSecretKey";
    private static final String SALT = "mySaltValue";

    // public static SecretKey generateAESKey(String password) throws Exception {
    //     MessageDigest digest = MessageDigest.getInstance("SHA-256");
    //     byte[] key = digest.digest(password.getBytes("UTF-8"));
    //     return new SecretKeySpec(key, AES);
    // }

    // public static String encrypt(String value, SecretKey secretKey) throws Exception {
    //     Cipher cipher = Cipher.getInstance(AES);
    //     cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    //     byte[] encrypted = cipher.doFinal(value.getBytes());
    //     return Base64.getEncoder().encodeToString(encrypted);
    // }

    // public static String decrypt(String encryptedValue, SecretKey secretKey) throws Exception {
    //     Cipher cipher = Cipher.getInstance(AES);
    //     cipher.init(Cipher.DECRYPT_MODE, secretKey);
    //     byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
    //     return new String(decrypted);
    // }

    // public static String hashPassword(String password) {
    //     try {
    //         MessageDigest md = MessageDigest.getInstance("SHA-256");
    //         byte[] hashedPassword = md.digest(password.getBytes());
    //         return Base64.getEncoder().encodeToString(hashedPassword);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
    // Derive a secret key from the given password
    protected static SecretKey getSecretKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    // Encrypt the password
    protected static String encrypt(String password) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the password
    protected static String decrypt(String encryptedPassword) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }
}

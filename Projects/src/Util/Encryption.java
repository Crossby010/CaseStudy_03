package Util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Encryption {
    private static final String Algo = "AES";
    private static SecretKey sK;

    static {
        try {
            KeyGenerator kG = KeyGenerator.getInstance(Algo);
            kG.init(128);
            sK = kG.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String Encrypt(String Data) {
        try {
            Cipher c = Cipher.getInstance(Algo);
            c.init(Cipher.DECRYPT_MODE,sK);
            byte[] eD = c.doFinal(Data.getBytes());
            return Base64.getEncoder().encodeToString(eD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String Decrypt(String eD) {
        try {
            Cipher c = Cipher.getInstance(Algo);
            c.init(Cipher.DECRYPT_MODE, sK);
            byte[] decodedData = Base64.getDecoder().decode(eD);
            byte[] decryptedData = c.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

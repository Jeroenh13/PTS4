/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.IOException;
import java.security.SecureRandom;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Bas
 */
public class Coder {

    private SecureRandom random;

    public String encrypt(String text) {
        BASE64Encoder encoder = new BASE64Encoder();

        // let's create some dummy salt
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return encoder.encode(salt) + encoder.encode(text.getBytes());
    }
    
       public String decrypt(String encryptKey) {
        if (encryptKey.length() > 12) {
            String cipher = encryptKey.substring(12);
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                return new String(decoder.decodeBuffer(cipher));
            } catch (IOException e) {
            }
        }
        return null;
    }
}

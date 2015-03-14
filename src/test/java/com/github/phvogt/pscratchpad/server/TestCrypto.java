// (c) 2014 by Philipp Vogt
package com.github.phvogt.pscratchpad.server;

import java.security.spec.KeySpec;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

/**
 * Tests for crypto.
 */
public class TestCrypto {

    /** logger. */
    private final Logger logger = Logger.getLogger(TestCrypto.class.getName());

    /**
     * Tests decryption.
     * @throws Exception if an error occurs
     */
    @Test
    public void testDecrypt() throws Exception {

        final String methodname = "testDecrypt(): ";
        final String[][] duts = new String[][] { {
            "test",
        "{\"key\":{\"size\":128,\"iter\":1,\"salt\":\"e6fb7d7485b6200979d336bc6cdad8f2\"},\"iv\":\"0e5551c91471339bc0ab1a0c764f3b19\",\"ciphertext\":\"d2KUTipGHzFD8XBn1GhdJA==\"}" } };
        for (final String[] dut : duts) {
            final String passphrase = dut[0];
            logger.info(methodname + "passphrase    = " + passphrase);
            logger.info(methodname + "encryptedData = " + dut[1]);

            final JSONParser jsonParser = new JSONParser();
            final JSONObject encryptedData = (JSONObject) jsonParser.parse(dut[1]);

            final JSONObject keyData = ((JSONObject) encryptedData.get("key"));
            final String keySaltHex = (String) keyData.get("salt");
            final long keySize = (Long) keyData.get("size");
            final long keyIter = (Long) keyData.get("iter");
            final String ivHex = (String) encryptedData.get("iv");
            final String msg64 = (String) encryptedData.get("ciphertext");
            logger.info(methodname + "keySaltHex = " + keySaltHex);
            logger.info(methodname + "ivHex   = " + ivHex);
            logger.info(methodname + "msg64   = " + msg64);

            final byte[] iv = Hex.decodeHex(ivHex.toCharArray());
            final byte[] keySalt = Hex.decodeHex(keySaltHex.toCharArray());
            final byte[] msg = Base64.decodeBase64(msg64);

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            final KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), keySalt, (int) keyIter, (int) keySize);
            final SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            final String plaintext = new String(cipher.doFinal(msg), "UTF-8");

            logger.info(methodname + "plaintext = " + plaintext);

        }
    }
}

package io.natix.slix.core.util;

import io.natix.slix.core.helper.LogHelper;
import io.natix.slix.core.payload.KeyPair;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.Objects;

public class CryptoUtil {

    public static String sha256(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            return StringUtil.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.error("sha256", e.getMessage());
            return StringUtils.EMPTY;
        }
    }

    public static String md5(String message) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] digest = m.digest();
            return StringUtil.encodeHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.error("md5", e.getMessage());
            return "";
        }
    }

    public static KeyPair generateEddsa() {
        Curve25519KeyPair keyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
        return new KeyPair(StringUtil.encodeHexString(keyPair.getPrivateKey()), StringUtil.encodeHexString(keyPair.getPublicKey()));
    }

    public static String signEddsa(String privateKey, String message) {
        return StringUtil.encodeHexString(signEddsa(privateKey, message.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] signEddsa(String privateKey, byte[] message) {
        return Curve25519.getInstance(Curve25519.JAVA).calculateSignature(StringUtil.decodeHex(privateKey), message);
    }

    public static boolean verifyEddsa(String publicKey, String signature, String message) {
        return verifyEddsa(publicKey, StringUtil.decodeHex(signature), message.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean verifyEddsa(String publicKey, byte[] signature, byte[] message) {
        return Curve25519.getInstance(Curve25519.JAVA).verifySignature(Objects.requireNonNull(StringUtil.decodeHex(publicKey)), message, signature);
    }

    public static KeyPair generateEcdh() {
        Curve25519KeyPair keyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
        return new KeyPair(StringUtil.encodeHexString(keyPair.getPrivateKey()), StringUtil.encodeHexString(keyPair.getPublicKey()));
    }

    public static String getSharedSecret(String privateKey, String receiverPublicKey) {
        return StringUtil.encodeHexString(Curve25519.getInstance(Curve25519.JAVA).calculateAgreement(Objects.requireNonNull(StringUtil.decodeHex(receiverPublicKey)), Objects.requireNonNull(StringUtil.decodeHex(privateKey))));
    }

    public static byte[] ecdhEncrypt(String privateKey, String receiverPublicKey, String value) {
        String secret = getSharedSecret(privateKey, receiverPublicKey);
        return StringUtils.isEmpty(secret) ? null : aesEncrypt(secret, value);
    }

    public static byte[] ecdhEncrypt(String privateKey, String receiverPublicKey, byte[] value) {
        String secret = getSharedSecret(privateKey, receiverPublicKey);
        return StringUtils.isEmpty(secret) ? null : aesEncrypt(secret, value);
    }

    public static byte[] ecdhDecrypt(String privateKey, String receiverPublicKey, String value) {
        String secret = getSharedSecret(privateKey, receiverPublicKey);
        return StringUtils.isEmpty(secret) ? null : aesDecrypt(secret, value);
    }

    public static byte[] ecdhDecrypt(String privateKey, String receiverPublicKey, byte[] value) {
        String secret = getSharedSecret(privateKey, receiverPublicKey);
        return StringUtils.isEmpty(secret) ? null : aesDecrypt(secret, value);
    }


    public static byte[] aesEncrypt(String key, String value) {
        return aesEncrypt(key, StringUtils.isBlank(value) ? StringUtils.EMPTY : value, "AES/CTR/NoPadding");
    }

    public static byte[] aesEncrypt(String key, byte[] value) {
        byte[] initVector = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5};
        return aesEncrypt(key,initVector, value, "AES/CTR/NoPadding");
    }

    public static byte[] aesEncrypt(String key, String value, String cipherValue) {
        byte[] initVector = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5};
        return CryptoUtil.aesEncrypt(key, initVector, StringUtils.isBlank(value) ? StringUtils.EMPTY : value, cipherValue);
    }

    public static byte[] aesEncrypt(String key, byte[] initVector, String value, String cipherValue) {
        return aesEncrypt(key, initVector, value.getBytes(StandardCharsets.UTF_8), cipherValue);
    }

    public static byte[] aesEncrypt(String key, byte[] initVector, byte[] value, String cipherValue) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec secretKeySpec = new SecretKeySpec(Objects.requireNonNull(StringUtil.decodeHex(key)), cipherValue);
            Cipher cipher = Cipher.getInstance(cipherValue);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(value);
            return encrypted;
        } catch (Exception ex) {
            LogHelper.error("aes-encryption", ex.getMessage());
            return null;
        }
    }

    public static byte[] aesDecrypt(String key, String value) {
        return aesDecrypt(key, StringUtils.isBlank(value) ? StringUtils.EMPTY : value, "AES/CTR/NoPadding");
    }

    public static byte[] aesDecrypt(String key, byte[] value) {
        return aesDecrypt(key, value, "AES/CTR/NoPadding");
    }

    public static byte[] aesDecrypt(String key, String value, String cipherValue) {
        byte[] initVector = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5};
        return aesDecrypt(key, initVector, StringUtils.isBlank(value) ? StringUtils.EMPTY : value, cipherValue);
    }

    public static byte[] aesDecrypt(String key, byte[] value, String cipherValue) {
        byte[] initVector = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5};
        return aesDecrypt(key, initVector, value, cipherValue);
    }

    public static byte[] aesDecrypt(String key, byte[] initVector, String value, String cipherValue) {
        return aesDecrypt(key, initVector, Base64.getDecoder().decode(value), cipherValue);
    }

    public static byte[] aesDecrypt(String key, byte[] initVector, byte[] value, String cipherValue) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec secretKeySpec = new SecretKeySpec(Objects.requireNonNull(StringUtil.decodeHex(key)), cipherValue);
            Cipher cipher = Cipher.getInstance(cipherValue);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(value);
            return encrypted;
        } catch (Exception ex) {
            LogHelper.error("aes-encryption", ex.getMessage());
            return null;
        }
    }
}

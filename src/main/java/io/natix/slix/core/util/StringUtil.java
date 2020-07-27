package io.natix.slix.core.util;

import io.natix.slix.core.helper.LogHelper;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class StringUtil {

    public static String convertToUTF8(String msg) {
        if (StandardCharsets.ISO_8859_1.newEncoder().canEncode(msg)) {
            return new String(msg.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        return msg;
    }

    public static byte[] decodeHex(String hex) {
        try {
            return Hex.decodeHex(hex);
        } catch (DecoderException e) {
            LogHelper.error("DecodeException", e.getMessage());
            return null;
        }
    }

    public static String encodeHexString(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public static String getRandom(int characterLength) {
        return UUID.randomUUID().toString().substring(0, characterLength);
    }

}

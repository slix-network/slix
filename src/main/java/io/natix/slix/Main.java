package io.natix.slix;

import io.natix.slix.core.payload.KeyPair;
import io.natix.slix.core.util.CryptoUtil;
import io.natix.slix.core.util.StringUtil;

public class Main {
    public static void main(String[] args) {

        KeyPair keyPair = CryptoUtil.generateEcdh();

        String msg = "hello";
        String jsPrv = "e8ebc08d9b47e0b89439d6325c393e178c1b50b5a0e2b96505ac44570bcda055";
        String jsPub = "4213c86ada39d804caf07aa4d3ec9b6b1ebd8e72aeb9fd7b8349735c98c6731c";

        String jPrv = "e82c96fe2a7c69faa276c8e6389b00d422e1f8bbd894d3fc41a7ba4a1ae79f6c";
        String jPub = "cd069214f2af4117230595693461b8cd6e50fdd8044eb05b6e3c30914979b249";

        String secret =  CryptoUtil.getSharedSecret(jsPrv, jPub);
        String secret2 =  CryptoUtil.getSharedSecret(jPrv, jsPub);
        String encrypted = StringUtil.encodeHexString(CryptoUtil.ecdhEncrypt(jPrv, jsPub, msg));
//        String pk = "376fadeb96efe12bf92fddd813cbff58cc137d5420aafbef46e4532d944f94a0";
        String pk2 = "b8a1d3a09cc9ebca57ed33034ac81f05676df2bb05dbb4e80ee5ccec70cbb44d";
//        String pubk = "b2ab2ed91c776c942698dee95cbfb73e69d0d5643a2e18ac16714d8d9e414755";
        String pubk2 = "74c045b8da3565f195135f7c7704be79d9a2a379eaebda081238877a8d79ce34";


    }
}

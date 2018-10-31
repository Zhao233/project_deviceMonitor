package com.zx.demo.util;

public class DecodeUtil {
    public static String  decodeAsBase64(String strBefore) throws Exception {
        String strResult = new String();

        // Decoder as Base64
//		byte[] byteList = Base64.getDecoder().decode(strBefore);				// Uncomment by James

        byte[] byteList = strBefore.getBytes("GBK");									// add by James

        for (int i = 0; i != byteList.length; ++i) {
            int nCur = byteList[i];
            if (nCur < 0) {
                nCur += 256;
            }

            char cA = decodeAsHex(nCur / 16);
            char cB = decodeAsHex(nCur % 16);
            strResult = strResult + cA + cB;
        }

        return strResult;
    }

    public static char decodeAsHex(int n) throws Exception {
        if (n >= 10) {
            return (char)(n - 10 + 'A');
        }
        if (n >=0) {
            return (char)(n + '0');
        }
        throw new Exception();
    }

    public static int convertToInt(char c) throws Exception {
        int n = 0;
        if (c >= '0' && c <= '9') {
            n = c - '0';
        } else if (c >= 'A' && c <= 'F') {
            n = c - 'A' + 10;
        } else if (c >= 'a' && c <= 'f') {
            n = c - 'a' + 10;
        } else {
            throw new Exception();
        }
        return n;
    }
}

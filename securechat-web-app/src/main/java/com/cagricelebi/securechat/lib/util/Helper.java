/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Cagri Celebi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.cagricelebi.securechat.lib.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 *
 * @author azureel
 */
public class Helper {

    private final static String ENCRYPTED_MESSAGE_FILTER = "[^a-zA-Z0-9 =\\?\\+\\/]";
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Helper for eliminationg some characters.
     * Encrypted messages contain only these characters.
     *
     * before: These are valid= /azAZ09=?+// But these are not= /\.*!<>&;{}[]/
     * after : These are valid= /azAZ09=?+// But these are not= //
     *
     * @param userInput
     * @return
     */
    public static String filterEncryptedUserInput(String userInput) {
        return userInput.replaceAll(ENCRYPTED_MESSAGE_FILTER, "");
    }

    public static SecureRandom createFixedRandom() {
        SecureRandom secure = new SecureRandom();
        return secure;
    }

    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Beware, the valid word "null" is also rendered as empty.
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".contentEquals(str) || "null".contentEquals(str);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(List<T> l) {
        return l == null || l.isEmpty();
    }

    /*public static void main(String[] args) {
     String userString = "These are valid= /azAZ09=?+// But these are not= /\\.*!<>&;{}[]/";
     System.out.println("before: " + userString);
     userString = userString.replaceAll(ENCRYPTED_MESSAGE_FILTER, "");
     System.out.println("after : " + userString);
     }*/
}

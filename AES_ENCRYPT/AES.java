package AES_ENCRYPT;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author lvfanrui
 * @description
 * @Date 2018/4/8
 */
public class AES {
    private static byte[] keyBytes;

    static {
        try {
            String encryptKey = "aTheNaseCretkeY";
            keyBytes = Arrays.copyOf(encryptKey.getBytes("ASCII"), 16);
        } catch (Exception e) {
            throw new IllegalArgumentException("获取加密key失败", e);
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("请选择加密(e)还是解密(d)：");
            String type = sc.nextLine();
            if (type.equals("d")) {
                // 解密
                System.out.print("请输入要解密的密文：");
                String de_str = sc.nextLine();
                System.out.println(decrypt(de_str));
            } else if (type.equals("e")) {
                // 加密
                System.out.print("请输入要解密的密文：");
                String en_str = sc.nextLine();
                System.out.println(encrypt(en_str));
            } else if (type.equals("exit")){
                break;
            }

        }
    }

    public static String encrypt(String data) {
        if (StringUtils.isEmpty(data)) {
            return data;
        } else if (!data.startsWith("Encrypt")) {
            byte[] encryptBytes = encrypt(data, keyBytes);
            return encryptBytes == null ? data
                    : "Encrypt" + Base64.encodeBase64String(encryptBytes);
        } else {
            return data;
        }
    }

    public static String decrypt(String encryptData) {
        if (StringUtils.isEmpty(encryptData)) {
            return encryptData;
        } else if (encryptData.startsWith("Encrypt")) {
            String mobile = decrypt(
                    Base64.decodeBase64(encryptData.substring("Encrypt".length())),
                    keyBytes);
            return mobile == null ? encryptData : mobile;
        } else {
            return encryptData;
        }
    }
    private static byte[] encrypt(String clearText, byte[] keyBytes) {
        try {
            SecretKeySpec e = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, e);
            byte[] cleartext = clearText.getBytes("UTF-8");
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            return ciphertextBytes;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    private static String decrypt(byte[] encryptBytes, byte[] keyBytes) {
        try {
            SecretKeySpec e = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, e);
            byte[] ciphertextBytes = cipher.doFinal(encryptBytes);
            return new String(ciphertextBytes, "UTF-8");
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }
}

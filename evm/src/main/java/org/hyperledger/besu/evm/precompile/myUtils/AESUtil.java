package org.hyperledger.besu.evm.precompile.myUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Closeable;
import java.io.IOException;

import java.security.SecureRandom;

public class AESUtil {
    //密钥长度：128,192 or 256
    private static final int KEY_SIZE = 128;

    // 加密/解密算法名称
    private static final String ALGORITHM = "AES";

    // 随机数生成器(RNG)算法名称
    private static final String RNG_ALGORITHM = "SHA1PRNG";

    @SuppressWarnings(value = {"DoNotCreateSecureRandomDirectly"})
    private static SecretKey generateKey(final byte[] key) throws Exception{
        SecureRandom random = SecureRandom.getInstance(RNG_ALGORITHM);

        random.setSeed(key);

        KeyGenerator gen = KeyGenerator.getInstance(ALGORITHM);

        gen.init(KEY_SIZE, random);

        return gen.generateKey();
    }
    /**
     * 数据加密 明文-> 密文
     */
    @SuppressWarnings(value = {"DoNotCreateSecureRandomDirectly", "InsecureCryptoUsage"})
    public static byte[] encrypt(final byte[] plainBytes, final byte[] key) throws Exception{
        SecretKey secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] cipherBytes = cipher.doFinal(plainBytes);
        return cipherBytes;
    }

    @SuppressWarnings(value = {"DoNotCreateSecureRandomDirectly", "InsecureCryptoUsage"})
    public static byte[] decrypt(final byte[] cipherBytes, final byte[] key) throws Exception{
        SecretKey secretKey = generateKey(key);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] plainBytes = cipher.doFinal(cipherBytes);

        return plainBytes;
    }

    public static void encryptFile(final File plainIn, final File cipherOut, final byte[] key) throws Exception{
        aesFile(plainIn, cipherOut, key, true);
    }

    public static void decryptFile(final File cipherIn, final File plainOut, final byte[] key) throws Exception{
        aesFile(plainOut, cipherIn, key, false);
    }

    @SuppressWarnings(value = {"DoNotCreateSecureRandomDirectly", "InsecureCryptoUsage"})
    private static void aesFile(final File plainFile, final File cipherFile, final byte[] key, final boolean isEncrypt) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = generateKey(key);

        cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);

        InputStream in = null;
        OutputStream out = null;

        try{
            if(isEncrypt){
                //加密， 明文文件为输入， 密文文件为输出
                in = new FileInputStream(plainFile);
                out = new FileOutputStream(cipherFile);
            }else{
                in = new FileInputStream(cipherFile);
                out = new FileOutputStream(plainFile);
            }

            byte[] buf = new byte[1024];
            int len = -1;

            //循环读取数据 加密/解密
            while ((len = in.read(buf)) != -1){
                out.write(cipher.update(buf, 0, len));
            }
            out.write(cipher.doFinal());

            out.flush();
        }finally {
            close(in);
            close(out);
        }
    }

    private static void close(final Closeable c){
        if(c != null){
            try {
                c.close();
            }catch (IOException e){
                //nothing
            }
        }
    }
}

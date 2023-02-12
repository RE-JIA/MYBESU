package org.hyperledger.besu.evm.precompile.myUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {
    @SuppressWarnings(value = {"DoNotInvokeMessageDigestDirectly", "InsecureCryptoUsage"})
    public static byte[] SHA256(final byte[] bytes){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
            byte[] byteBuffer = messageDigest.digest();
            return byteBuffer;
        }catch (NoSuchAlgorithmException e){
            System.out.println(e);
        }
        return null;
    }

    @SuppressWarnings(value = {"DoNotInvokeMessageDigestDirectly", "InsecureCryptoUsage"})
    public static String SHA(final String strText){
        String strResult = null;
        if(strText != null && strText.length() > 0){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(strText.getBytes(StandardCharsets.UTF_8));
                byte[] byteBuffer = messageDigest.digest();
                strResult = Utils.bytes2String(byteBuffer);
            }catch (NoSuchAlgorithmException e){
                System.out.println(e);
            }
        }
        return strResult;
    }

    public static String shaHashCode(final String filePath) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        return  shaHashCode(fis);
    }

    @SuppressWarnings(value = {"DoNotInvokeMessageDigestDirectly", "InsecureCryptoUsage"})
    public static String shaHashCode(final InputStream fis){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[1024];
            int length = -1;
            while((length = fis.read(buffer, 0, 1024)) != -1){
                md.update(buffer,0,length);
            }
            fis.close();
            byte[] shaBytes = md.digest();
            BigInteger bigInt = new BigInteger(1,shaBytes);
            return bigInt.toString(16);
        }catch (Exception e){
            System.out.println(e);
            return "";
        }
    }
}

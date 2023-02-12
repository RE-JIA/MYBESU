package org.hyperledger.besu.evm.precompile.myUtils;

public class Utils {

    //将int转为byte[]
    public static byte[] int2Bytes(final int n){
        byte[] result = new byte[4];
        for(int i = 4; i > 0; i--){
            result[i - 1] = (byte) (n >> (8 *(4 - i)) & 0xff);
        }
        return result;
    }

    //将两个字节数组拼接成一个
    public static byte[] combineTwoBytes2One(final byte[] a, final byte[] b){
        byte[] res = new byte[a.length + b.length];
        for(int i = 0; i < a.length; i++){
            res[i] = a[i];
        }
        for(int i = 0; i < b.length; i++){
            res[i + a.length] = b[i];
        }
        return res;
    }

    //将byte转为String
    public static String bytes2String(final byte[] byteBuffer){
        StringBuilder strHexString = new StringBuilder();
        for(int i = 0; i < byteBuffer.length; i++){
            String hex = Integer.toHexString(0xff & byteBuffer[i]);
            if(hex.length() == 1){
                strHexString.append('0');
            }
            strHexString.append(hex);
        }
        return strHexString.toString();
    }
}

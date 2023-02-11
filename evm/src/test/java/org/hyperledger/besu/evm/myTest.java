package org.hyperledger.besu.evm;

import org.apache.tuweni.bytes.Bytes;
import org.junit.Test;

import java.util.Arrays;

public class myTest {
    @Test
    public void test(){
        byte[] a = {81, 109, 98, 89, 53, 117, 83, 90, 70, 55, 119, 65, 65, 86, 75, 88, 70, 69, 74, 99, 114, 118, 82, 77, 114, 102, 80, 118, 76, 118, 109, 70, 53, 102, 74, 102, 70, 65, 85, 97, 57, 111, 80, 119, 78, 50};
        Bytes b = Bytes.wrap(a);
        System.out.println(b);
        System.out.println(Arrays.toString(b.toArray()) );
    }
}

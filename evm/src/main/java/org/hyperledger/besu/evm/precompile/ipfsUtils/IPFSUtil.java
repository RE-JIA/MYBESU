package org.hyperledger.besu.evm.precompile.ipfsUtils;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class IPFSUtil {
    public String upload(final IPFS ipfs, final String filename) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(filename));
        MerkleNode addResult = ipfs.add(file).get(0);
        System.out.println(addResult.largeSize);
        return addResult.hash.toString();
    }

    public String upload(final IPFS ipfs, final byte[] data) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }


    public Map<String, Object> stat(final IPFS ipfs, final String hash)throws IOException{
        return ipfs.object.stat(Multihash.fromBase58(hash));
    }

    public byte[] download(final IPFS ipfs, final String hash){
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }

    public void download(final IPFS ipfs, final String hash, final String destFile){
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        }catch (Exception e){
            System.out.println(e);
        }
        if(data != null && data.length > 0){
            File file = new File(destFile);
            if(file.exists()){
                file.delete();
            }

            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
            }catch (IOException e){
                System.out.println(e);
            }finally {
                try{
                    fos.close();
                }catch (IOException e){
                    System.out.println(e);
                }
            }
        }
    }
}
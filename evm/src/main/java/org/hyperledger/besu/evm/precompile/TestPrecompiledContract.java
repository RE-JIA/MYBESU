/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.evm.precompile;

import io.ipfs.api.IPFS;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.gascalculator.GasCalculator;

import javax.annotation.Nonnull;

import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.evm.precompile.myUtils.AESUtil;
import org.hyperledger.besu.evm.precompile.myUtils.SHAUtil;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestPrecompiledContract extends AbstractPrecompiledContract {

    public TestPrecompiledContract(final GasCalculator gasCalculator) {
        super("RJTEST", gasCalculator);
        System.out.println("my pre contract start");
    }

    @Override
    public long gasRequirement(final Bytes input) {
        return gasCalculator().idPrecompiledContractGasCost(input);
    }

    @Nonnull
    @Override
    public PrecompileContractResult computePrecompile(
            final Bytes input, @Nonnull final MessageFrame messageFrame) {

        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));

            //1 构建一个新的 ipfs对象
            String ipfsAddress = (String) prop.get("ipfsAddress");
            MultiAddress multiAddress = new MultiAddress(ipfsAddress);
            IPFS ipfs = new IPFS(multiAddress);


            //2 将输入的input转为String地址
            byte[] inputBytes = input.toArray();
            //前32字节表示输入的长度
            int len = 0;
            for(int i = 0; i < 32; i++){
                len *= 2;
                len += inputBytes[i];
            }
            //实际输入的字节
            byte[] myBytes = new byte[len];
            for(int i = 0; i < len; i++){
                myBytes[i] = inputBytes[i + 32];
            }
            String cid = new String(myBytes, StandardCharsets.US_ASCII);

            //根据cid获得存在IPFS中数据
            byte[] ipfsBytes;
            ipfsBytes = ipfs.cat(Multihash.fromBase58(cid));

            //3 将获得到的IPFS数据解密并计算hash
            String aesKey = (String) prop.get("aesKey");
            byte[] plainIpfsBytes = AESUtil.decrypt(ipfsBytes, aesKey.getBytes(StandardCharsets.UTF_8));
            byte[] ipfsHash = SHAUtil.SHA256(plainIpfsBytes);

            Bytes ipfsHash2 = Bytes.wrap(ipfsBytes);
            System.out.println(ipfsHash2);
            System.out.println(new String(ipfsHash, StandardCharsets.UTF_8));

            Bytes32 res = Bytes32.fromHexString("0x0001");
            return PrecompileContractResult.success(res);
        }catch (Exception e){
            Bytes32 res = Bytes32.fromHexString("0x0000");
            return PrecompileContractResult.success(res);
        }
    }
}

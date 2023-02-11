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

import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.gascalculator.GasCalculator;

import javax.annotation.Nonnull;

import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.evm.precompile.ipfsUtils.IPFSUtil;

import java.io.FileInputStream;
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

//        try {
//            Properties prop = new Properties();
//            prop.load(new FileInputStream("config.properties"));
//        }catch (Exception e){
//            System.out.println();
//            e.printStackTrace();
//        }

        byte[] bytes = input.toArray();
        try {
            String cid = new String(bytes, "UTF-8");
            System.out.println("hello rj----------------");
            System.out.println(cid);
        }catch (Exception e){
            System.out.println(e);
        }
//        IPFSUtil ipfsUtil = new IPFSUtil();
        Bytes32 res = Bytes32.fromHexString("0x0123");

        System.out.println(input.toHexString());
        return PrecompileContractResult.success(res);
    }
}

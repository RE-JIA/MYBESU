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

import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.gascalculator.GasCalculator;

import javax.annotation.Nonnull;

import org.apache.tuweni.bytes.Bytes;

public class TestPrecompiledContract extends AbstractPrecompiledContract {

    public TestPrecompiledContract(final GasCalculator gasCalculator) {
        super("RJTEST", gasCalculator);
        System.out.println("hello rj++++++++++++++");
    }

    @Override
    public long gasRequirement(final Bytes input) {
        return gasCalculator().idPrecompiledContractGasCost(input);
    }

    @Nonnull
    @Override
    public PrecompileContractResult computePrecompile(
            final Bytes input, @Nonnull final MessageFrame messageFrame) {
        Bytes res = Bytes.of(123);
        System.out.println("hello rj----------------");
        return PrecompileContractResult.success(res);
    }
}

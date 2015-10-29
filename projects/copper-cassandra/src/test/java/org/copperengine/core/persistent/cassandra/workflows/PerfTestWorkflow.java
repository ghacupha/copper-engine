/*
 * Copyright 2002-2015 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.copperengine.core.persistent.cassandra.workflows;

import java.util.concurrent.TimeUnit;

import org.copperengine.core.AutoWire;
import org.copperengine.core.Interrupt;
import org.copperengine.core.WaitMode;
import org.copperengine.core.persistent.PersistentWorkflow;
import org.copperengine.core.persistent.cassandra.DummyResponseSender;
import org.copperengine.core.persistent.cassandra.PerfTestData;
import org.copperengine.core.util.Backchannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfTestWorkflow extends PersistentWorkflow<PerfTestData> {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(PerfTestWorkflow.class);

    private transient DummyResponseSender dummyResponseSender;
    private transient Backchannel backchannel;

    @AutoWire(beanId = "backchannel")
    public void setBackchannel(Backchannel backchannel) {
        this.backchannel = backchannel;
    }

    @AutoWire(beanId = "dummyResponseSender")
    public void setDummyResponseSender(DummyResponseSender dummyResponseSender) {
        this.dummyResponseSender = dummyResponseSender;
    }

    @Override
    public void main() throws Interrupt {
        logger.info("started");

        logger.info("Testing delayed response...");
        delayedResponse();

        logger.info("Testing early response...");
        earlyResponse();

        logger.info("Testing timeout response...");
        timeoutResponse();

        backchannel.notify(getData().id, getData().id);
        logger.info("finished");
    }

    private void delayedResponse() throws Interrupt {
        final String cid = getEngine().createUUID();
        dummyResponseSender.foo(cid, 100, TimeUnit.MILLISECONDS);
        wait(WaitMode.ALL, 1000, cid);
    }

    private void earlyResponse() throws Interrupt {
        final String cid = getEngine().createUUID();
        dummyResponseSender.foo(cid, 0, TimeUnit.MILLISECONDS);
        wait(WaitMode.ALL, 1000, cid);
    }

    private void timeoutResponse() throws Interrupt {
        final String cid = getEngine().createUUID();
        wait(WaitMode.ALL, 100, cid);
    }

}

/*
 * Copyright 2019 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.steps.assertions;

import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.spring.conformance.util.security.SecurityUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.activiti.steps.assertions.ProcessInstanceMatchers.processInstance;
import static org.activiti.steps.assertions.SequenceFlowMatchers.sequenceFlow;
import static org.activiti.steps.assertions.StartEventMatchers.startEvent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class ActivitiAssertionsTest {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ProcessOperations processOperations;

    @Before
    public void setUp() {
        securityUtil.logInAs("user1");
    }

    @Test
    public void shouldExecuteGenericProcess() {

        processOperations.start(ProcessPayloadBuilder
                                        .start()
                                        .withProcessDefinitionKey("processwit-c6fd1b26-0d64-47f2-8d04-0b70764444a7")
                                        .withBusinessKey("my-business-key")
                                        .withName("my-process-instance-name")
                                        .build())
                .expect(
                        processInstance()
                                .hasBeenStarted(),
                        startEvent("StartEvent_1")
                                .hasBeenCompleted(),
                        sequenceFlow("SequenceFlow_108momn")
                                .hasBeenTaken()
                )
                .hasBusinessKey("my-business-key")
                .hasName("my-process-instance-name");
    }
}
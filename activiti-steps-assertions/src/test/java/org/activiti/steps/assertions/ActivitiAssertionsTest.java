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

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.spring.conformance.util.security.SecurityUtil;
import org.activiti.steps.operations.ProcessOperations;
import org.activiti.steps.operations.TaskOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.activiti.steps.matchers.BPMNStartEventMatchers.startEvent;
import static org.activiti.steps.matchers.EndEventMatchers.endEvent;
import static org.activiti.steps.matchers.ManualTaskMatchers.manualTask;
import static org.activiti.steps.matchers.ProcessInstanceMatchers.processInstance;
import static org.activiti.steps.matchers.ProcessTaskMatchers.task;
import static org.activiti.steps.matchers.SequenceFlowMatchers.sequenceFlow;
import static org.activiti.steps.matchers.TaskMatchers.task;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class ActivitiAssertionsTest {

    private static final int MAX_ITEMS = 100;
    private static final String USERNAME = "user1";
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ProcessOperations processOperations;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private TaskOperations taskOperations;

    @Before
    public void setUp() {
        securityUtil.logInAs(USERNAME);
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
                                .hasBeenTaken(),
                        manualTask("Task_1dk1vp6")
                                .hasBeenCompleted(),
                        sequenceFlow("SequenceFlow_1lit3dy")
                                .hasBeenTaken(),
                        endEvent("EndEvent_0lms8y3")
                                .hasBeenCompleted(),
                        processInstance()
                                .hasBeenCompleted()

                )
                .expect(
                        processInstance()
                                .hasBusinessKey("my-business-key"),
                        processInstance()
                                .hasName("my-process-instance-name")

                );
    }

    @Test
    public void shouldExecuteProcessWithUserTask() {

        ProcessInstance processInstance = processOperations.start(ProcessPayloadBuilder
                                                                          .start()
                                                                          .withProcessDefinitionKey("usertaskgr-1a8cdf77-0981-45d4-8080-7cf1a80c973b")
                                                                          .withBusinessKey("my-business-key")
                                                                          .withName("my-process-instance-name")
                                                                          .build())
                .expect(
                        processInstance()
                                .hasBeenStarted(),
                        startEvent("StartEvent_1")
                                .hasBeenCompleted(),
                        sequenceFlow("SequenceFlow_052072h")
                                .hasBeenTaken(),
                        task("Task Group 1")
                                .hasBeenCreated()

                )
                .expect(processInstance()
                                .hasBusinessKey("my-business-key"),
                        processInstance()
                                .hasName("my-process-instance-name"))
                .andReturn();

        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0,
                                                         MAX_ITEMS),
                                             TaskPayloadBuilder
                                                     .tasks()
                                                     .withProcessInstanceId(processInstance.getId())
                                                     .build());

        assertThat(tasks.getContent()).hasSize(1);

        Task task = tasks.getContent().get(0);
        taskOperations.claim(TaskPayloadBuilder
                                     .claim()
                                     .withTaskId(
                                             task.getId())
                                     .build())
                .expect(task()
                                .hasBeenAssigned())
                .expect(task()
                                .hasAssignee(USERNAME));

        taskOperations.complete(TaskPayloadBuilder
                                        .complete()
                                        .withTaskId(task.getId())
                                        .build())
                .expect(
                        task().hasBeenCompleted(),
                        task("Task Group 2").hasBeenCreated()
                );
    }
}
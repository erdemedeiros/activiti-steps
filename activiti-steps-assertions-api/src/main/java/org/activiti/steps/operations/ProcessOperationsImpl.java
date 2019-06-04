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

package org.activiti.steps.operations;

import java.util.List;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.steps.EventProvider;
import org.activiti.steps.TaskProvider;
import org.activiti.steps.assertions.AwaitProcessInstanceAssertions;
import org.activiti.steps.assertions.ProcessInstanceAssertions;
import org.activiti.steps.assertions.ProcessInstanceAssertionsImpl;
import org.activiti.steps.assertions.SignalAssertions;

public class ProcessOperationsImpl implements ProcessOperations {

    private ProcessRuntime processRuntime;

    private EventProvider eventProvider;
    private List<TaskProvider> taskProviders;
    private boolean awaitEnabled;

    public ProcessOperationsImpl(ProcessRuntime processRuntime,
                                 EventProvider eventProvider,
                                 List<TaskProvider> taskProviders,
                                 boolean awaitEnabled) {
        this.processRuntime = processRuntime;
        this.eventProvider = eventProvider;
        this.taskProviders = taskProviders;
        this.awaitEnabled = awaitEnabled;
    }

    @Override
    public ProcessInstanceAssertions start(StartProcessPayload startProcessPayload)  {
        ProcessInstance processInstance = processRuntime.start(startProcessPayload);
        ProcessInstanceAssertions processInstanceAssertions = new ProcessInstanceAssertionsImpl(eventProvider,
                                                                                                    taskProviders,
                                                                                                    processInstance);
        if (awaitEnabled){
            processInstanceAssertions = new AwaitProcessInstanceAssertions(processInstanceAssertions);
        }
        return processInstanceAssertions;
    }

    @Override
    public SignalAssertions signal(SignalPayload signalPayload) {
        processRuntime.signal(signalPayload);
        return new SignalAssertions(eventProvider);
    }


}

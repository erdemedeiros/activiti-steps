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

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.steps.assertions.EventsProvider;
import org.activiti.steps.assertions.SignalAssertions;
import org.activiti.steps.assertions.ProcessInstanceAssertions;

public class ProcessOperations {

    private ProcessRuntime processRuntime;

    private EventsProvider eventsProvider;

    public ProcessOperations(ProcessRuntime processRuntime,
                             EventsProvider eventsProvider) {
        this.processRuntime = processRuntime;
        this.eventsProvider = eventsProvider;
    }

    public ProcessInstanceAssertions start(StartProcessPayload startProcessPayload)  {
        ProcessInstance processInstance = processRuntime.start(startProcessPayload);
        return new ProcessInstanceAssertions(eventsProvider, processInstance);
    }

    public SignalAssertions signal(SignalPayload signalPayload) {
        processRuntime.signal(signalPayload);
        return new SignalAssertions(eventsProvider);
    }


}

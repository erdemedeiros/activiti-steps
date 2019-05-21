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

import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.model.shared.event.VariableDeletedEvent;
import org.activiti.api.model.shared.event.VariableUpdatedEvent;
import org.activiti.api.process.model.events.BPMNActivityCancelledEvent;
import org.activiti.api.process.model.events.BPMNActivityCompletedEvent;
import org.activiti.api.process.model.events.BPMNActivityStartedEvent;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.api.process.model.events.BPMNSignalReceivedEvent;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.events.ProcessCancelledEvent;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.ProcessCreatedEvent;
import org.activiti.api.process.runtime.events.ProcessResumedEvent;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;
import org.activiti.api.process.runtime.events.ProcessSuspendedEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;
import org.activiti.api.task.runtime.events.TaskSuspendedEvent;
import org.activiti.api.task.runtime.events.TaskUpdatedEvent;
import org.activiti.api.task.runtime.events.listener.TaskEventListener;
import org.activiti.steps.operations.ProcessOperations;
import org.activiti.steps.operations.TaskOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiAssertionsConfiguration {

    private final HandledEvents handledEvents = new HandledEvents();

    @Bean
    public HandledEvents handledEvents() {
        return handledEvents;
    }

    @Bean
    public ProcessOperations processOperations(ProcessRuntime processRuntime,
                                               EventsProvider eventsProvider) {
        return new ProcessOperations(processRuntime,
                                     eventsProvider);
    }

    @Bean
    public TaskOperations taskOperations(TaskRuntime taskRuntime,
                                         EventsProvider eventsProvider) {
        return new TaskOperations(taskRuntime,
                                  eventsProvider);
    }

    @Bean
    public BPMNElementEventListener<BPMNActivityStartedEvent> bpmnActivityStartedListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNActivityCompletedEvent> bpmnActivityCompletedListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNActivityCancelledEvent> bpmnActivityCancelledListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNSequenceFlowTakenEvent> bpmnSequenceFlowTakenListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCreatedEvent> processCreatedListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessStartedEvent> processStartedListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCompletedEvent> processCompletedListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessResumedEvent> processResumedListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessSuspendedEvent> processSuspendedListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCancelledEvent> processCancelledListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public VariableEventListener<VariableCreatedEvent> variableCreatedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public VariableEventListener<VariableDeletedEvent> variableDeletedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public VariableEventListener<VariableUpdatedEvent> variableUpdatedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskCreatedEvent> taskCreatedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskUpdatedEvent> taskUpdatedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskCompletedEvent> taskCompletedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskSuspendedEvent> taskSuspendedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskAssignedEvent> taskAssignedEventListener() {
        return handledEvents::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNSignalReceivedEvent> bpmnSignalReceivedListener() {
        return handledEvents::addCollectedEvents;
    }

}

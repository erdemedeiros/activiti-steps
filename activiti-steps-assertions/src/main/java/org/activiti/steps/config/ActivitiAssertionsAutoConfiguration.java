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

package org.activiti.steps.config;

import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.model.shared.event.VariableDeletedEvent;
import org.activiti.api.model.shared.event.VariableUpdatedEvent;
import org.activiti.api.process.model.events.BPMNActivityCancelledEvent;
import org.activiti.api.process.model.events.BPMNActivityCompletedEvent;
import org.activiti.api.process.model.events.BPMNActivityStartedEvent;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.api.process.model.events.BPMNSignalReceivedEvent;
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
import org.activiti.steps.LocalEventProvider;
import org.activiti.steps.LocalTaskProvider;
import org.activiti.steps.TaskProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiAssertionsAutoConfiguration {

    private final LocalEventProvider localEventProvider = new LocalEventProvider();

    @Bean
    public LocalEventProvider handledEvents() {
        return localEventProvider;
    }

    @Bean
    public TaskProvider localTaskProvider(TaskRuntime taskRuntime) {
        return new LocalTaskProvider(taskRuntime);
    }

    @Bean
    public BPMNElementEventListener<BPMNActivityStartedEvent> keepInMemoryBpmnActivityStartedListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNActivityCompletedEvent> keepInMemoryBpmnActivityCompletedListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNActivityCancelledEvent> keepInMemoryBpmnActivityCancelledListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNSequenceFlowTakenEvent> keepInMemoryBpmnSequenceFlowTakenListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCreatedEvent> keepInMemoryProcessCreatedListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessStartedEvent> keepInMemoryProcessStartedListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCompletedEvent> keepInMemoryProcessCompletedListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessResumedEvent> keepInMemoryProcessResumedListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessSuspendedEvent> keepInMemoryProcessSuspendedListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public ProcessRuntimeEventListener<ProcessCancelledEvent> keepInMemoryProcessCancelledListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public VariableEventListener<VariableCreatedEvent> keepInMemoryVariableCreatedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public VariableEventListener<VariableDeletedEvent> keepInMemoryVariableDeletedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public VariableEventListener<VariableUpdatedEvent> keepInMemoryVariableUpdatedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskCreatedEvent> keepInMemoryTaskCreatedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskUpdatedEvent> keepInMemoryTaskUpdatedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskCompletedEvent> keepInMemoryTaskCompletedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskSuspendedEvent> keepInMemoryTaskSuspendedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public TaskEventListener<TaskAssignedEvent> keepInMemoryTaskAssignedEventListener() {
        return localEventProvider::addCollectedEvents;
    }

    @Bean
    public BPMNElementEventListener<BPMNSignalReceivedEvent> keepInMemoryBpmnSignalReceivedListener() {
        return localEventProvider::addCollectedEvents;
    }

}

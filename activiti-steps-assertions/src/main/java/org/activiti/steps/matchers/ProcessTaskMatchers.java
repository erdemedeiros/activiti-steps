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

package org.activiti.steps.matchers;

import java.util.List;
import java.util.stream.Collectors;

import org.activiti.api.task.model.events.TaskRuntimeEvent;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;

import static org.assertj.core.api.Assertions.*;

public class ProcessTaskMatchers {

    private String taskName;

    private ProcessTaskMatchers(String taskName) {
        this.taskName = taskName;
    }

    public static ProcessTaskMatchers task(String taskName) {
        return new ProcessTaskMatchers(taskName);
    }

    public ProcessResultMatcher hasBeenCreated() {

        return (processInstance, eventProvider) -> {
            List<TaskCreatedEvent> taskCreatedEvents = eventProvider.getEvents()
                    .stream()
                    .filter(event -> TaskRuntimeEvent.TaskEvents.TASK_CREATED.equals(event.getEventType()))
                    .map(TaskCreatedEvent.class::cast)
                    .collect(Collectors.toList());
            assertThat(taskCreatedEvents)
                    .filteredOn(event -> event.getEntity().getProcessInstanceId().equals(processInstance.getId()))
                    .extracting(event -> event.getEntity().getName())
                    .contains(taskName);

        };
    }

}
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
import org.activiti.api.task.runtime.events.TaskAssignedEvent;

import static org.assertj.core.api.Assertions.*;

public class TaskMatchers {

    private TaskMatchers() {
    }

    public static TaskMatchers task() {
        return new TaskMatchers();
    }

    public TaskResultMatcher hasBeenAssigned() {
        return (task, eventsProvider) -> {
            List<TaskAssignedEvent> taskAssignedEvents = eventsProvider.getEvents()
                    .stream()
                    .filter(event -> TaskRuntimeEvent.TaskEvents.TASK_ASSIGNED.equals(event.getEventType()))
                    .map(TaskAssignedEvent.class::cast)
                    .collect(Collectors.toList());
            assertThat(taskAssignedEvents)
                    .filteredOn(event -> event.getEntity().getId().equals(task.getId()))
                    .extracting(event -> event.getEntity().getName())
                    .as("Unable to find event " + TaskRuntimeEvent.TaskEvents.TASK_ASSIGNED + " for task " + task.getId())
                    .contains(task.getName());
        };
    }

    public TaskResultMatcher hasAssignee(String assignee) {
        return (task, eventsProvider) -> assertThat(task.getAssignee()).isEqualTo(assignee);
    }
}
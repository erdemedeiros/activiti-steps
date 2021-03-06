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

import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.steps.EventProvider;
import org.activiti.steps.TaskProvider;
import org.activiti.steps.assertions.TaskAssertions;
import org.activiti.steps.assertions.TaskAssertionsImpl;

public class TaskRuntimeOperations implements TaskOperations {

    private TaskRuntime taskRuntime;

    private EventProvider eventProvider;

    private List<TaskProvider> taskProviders;

    public TaskRuntimeOperations(TaskRuntime taskRuntime,
                                 EventProvider eventProvider,
                                 List<TaskProvider> taskProviders) {
        this.taskRuntime = taskRuntime;
        this.eventProvider = eventProvider;
        this.taskProviders = taskProviders;
    }

    @Override
    public TaskAssertions claim(ClaimTaskPayload claimTaskPayload) {
        Task task = taskRuntime.claim(claimTaskPayload);
        return buildTaskAssertions(task);
    }

    private TaskAssertions buildTaskAssertions(Task task) {
        return new TaskAssertionsImpl(task,
                                      taskProviders,
                                      eventProvider);
    }

    @Override
    public TaskAssertions complete(CompleteTaskPayload completeTaskPayload) {
        Task task = taskRuntime.task(completeTaskPayload.getTaskId());
        taskRuntime.complete(completeTaskPayload);
        return buildTaskAssertions(task);
    }
}

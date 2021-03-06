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

import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.steps.assertions.AwaitTaskAssertions;
import org.activiti.steps.assertions.TaskAssertions;

public class AwaitableTaskOperations implements TaskOperations {

    private TaskOperations taskOperations;
    private boolean awaitEnabled;

    public AwaitableTaskOperations(TaskOperations taskOperations,
                                   boolean awaitEnabled) {
        this.taskOperations = taskOperations;
        this.awaitEnabled = awaitEnabled;
    }

    @Override
    public TaskAssertions claim(ClaimTaskPayload claimTaskPayload) {
        TaskAssertions taskAssertions = taskOperations.claim(claimTaskPayload);
        return awaitableAssertions(taskAssertions);
    }

    private TaskAssertions awaitableAssertions(TaskAssertions taskAssertions) {
        if (awaitEnabled) {
            return new AwaitTaskAssertions(taskAssertions);
        }
        return taskAssertions;
    }

    @Override
    public TaskAssertions complete(CompleteTaskPayload completeTaskPayload) {
        TaskAssertions taskAssertions = taskOperations.complete(completeTaskPayload);
        return awaitableAssertions(taskAssertions);
    }
}

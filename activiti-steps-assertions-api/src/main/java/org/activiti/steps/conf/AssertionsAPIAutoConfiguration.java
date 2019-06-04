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

package org.activiti.steps.conf;

import java.util.List;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.steps.EventProvider;
import org.activiti.steps.TaskProvider;
import org.activiti.steps.operations.ProcessOperations;
import org.activiti.steps.operations.ProcessOperationsImpl;
import org.activiti.steps.operations.TaskOperations;
import org.activiti.steps.operations.TaskOperationsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssertionsAPIAutoConfiguration {

    @Bean
    public ProcessOperations processOperations(ProcessRuntime processRuntime,
                                               EventProvider eventProvider,
                                               List<TaskProvider> taskProviders,
                                               @Value("${activiti.assertions.await.enabled:false}") boolean awaitEnabled) {
        return new ProcessOperationsImpl(processRuntime,
                                         eventProvider,
                                         taskProviders,
                                         awaitEnabled);
    }

    @Bean
    public TaskOperations taskOperations(TaskRuntime taskRuntime,
                                         EventProvider eventProvider,
                                         List<TaskProvider> taskProviders,
                                         @Value("${activiti.assertions.await.enabled:false}") boolean awaitEnabled) {
        return new TaskOperationsImpl(taskRuntime,
                                      eventProvider,
                                      taskProviders,
                                      awaitEnabled);
    }

}

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
import org.activiti.steps.matchers.OperationScopeMatcher;
import org.activiti.steps.matchers.ProcessResultMatcher;
import org.activiti.steps.matchers.ProcessTaskMatcher;

import static org.awaitility.Awaitility.await;

public class AwaitProcessInstanceAssertions implements ProcessInstanceAssertions {

    private ProcessInstanceAssertions processInstanceAssertions;

    public AwaitProcessInstanceAssertions(ProcessInstanceAssertions processInstanceAssertions) {
        this.processInstanceAssertions = processInstanceAssertions;
    }

    @Override
    public ProcessInstanceAssertions expect(ProcessResultMatcher... matchers) {
        await().untilAsserted(() -> processInstanceAssertions.expect(matchers));
        return this;
    }

    @Override
    public ProcessInstanceAssertions expect(OperationScopeMatcher... matchers) {
        await().untilAsserted(() -> processInstanceAssertions.expect(matchers));
        return this;
    }

    @Override
    public ProcessInstanceAssertions expect(ProcessTaskMatcher... matchers) {
        await().untilAsserted(() -> processInstanceAssertions.expect(matchers));
        return this;
    }

    @Override
    public ProcessInstance andReturn() {
        return processInstanceAssertions.andReturn();
    }
}

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
import org.activiti.steps.EventProvider;
import org.activiti.steps.matchers.OperationScopeMatcher;

import static org.activiti.steps.matchers.OperationScopeImpl.processInstanceScope;

public class SignalAssertions {

    private EventProvider eventProvider;

    public SignalAssertions(EventProvider eventProvider) {

        this.eventProvider = eventProvider;
    }

    public SignalAssertions expectOn(ProcessInstance processInstance,
                                     OperationScopeMatcher... matchers) {
        for (OperationScopeMatcher matcher : matchers) {
            matcher.match(processInstanceScope(processInstance.getId()),
                          eventProvider);
        }
        return this;
    }
}

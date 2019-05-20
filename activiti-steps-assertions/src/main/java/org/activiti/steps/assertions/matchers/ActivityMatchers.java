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

package org.activiti.steps.assertions.matchers;

import java.util.List;
import java.util.stream.Collectors;

import org.activiti.api.process.model.events.BPMNActivityCompletedEvent;
import org.activiti.api.process.model.events.BPMNActivityEvent;
import org.activiti.api.process.model.events.BPMNActivityStartedEvent;

import static org.assertj.core.api.Assertions.*;

public abstract class ActivityMatchers {

    private String definitionKey;

    protected ActivityMatchers(String definitionKey) {
        this.definitionKey = definitionKey;
    }

    public abstract String getActivityType();

    public ResultMatcher hasBeenCompleted() {

        return (processInstance, eventProvider) -> {
            List<BPMNActivityStartedEvent> startedEvents = eventProvider.getEvents()
                    .stream()
                    .filter(event -> BPMNActivityEvent.ActivityEvents.ACTIVITY_STARTED.equals(event.getEventType()))
                    .map(BPMNActivityStartedEvent.class::cast)
                    .collect(Collectors.toList());
            assertThat(startedEvents)
                    .filteredOn(event -> event.getEntity().getProcessInstanceId().equals(processInstance.getId()))
                    .extracting(event -> event.getEntity().getActivityType(),
                                event -> event.getEntity().getElementId())
                    .contains(tuple(getActivityType(),
                                    definitionKey));

            List<BPMNActivityCompletedEvent> completedEvents = eventProvider.getEvents()
                    .stream()
                    .filter(event -> BPMNActivityEvent.ActivityEvents.ACTIVITY_COMPLETED.equals(event.getEventType()))
                    .map(BPMNActivityCompletedEvent.class::cast)
                    .collect(Collectors.toList());

            assertThat(completedEvents)
                    .filteredOn(event -> event.getEntity().getProcessInstanceId().equals(processInstance.getId()))
                    .extracting(event -> event.getEntity().getActivityType(),
                                event -> event.getEntity().getElementId())
                    .contains(tuple(getActivityType(),
                                    definitionKey));
        };
    }

}

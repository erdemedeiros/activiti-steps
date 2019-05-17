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

import java.util.List;
import java.util.stream.Collectors;

import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.runtime.events.ProcessCreatedEvent;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;

import static org.activiti.api.process.model.events.ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED;
import static org.assertj.core.api.Assertions.*;

public class ProcessInstanceMatchers {

    private ProcessInstanceMatchers() {
    }

    public static ProcessInstanceMatchers processInstance() {
        return new ProcessInstanceMatchers();
    }

    public ResultMatcher hasBeenStarted() {
        return (processInstance, eventsProvider) -> {
            List<ProcessCreatedEvent> processCreatedEvents = eventsProvider.getEvents()
                    .stream()
                    .filter(event -> ProcessRuntimeEvent.ProcessEvents.PROCESS_CREATED.equals(event.getEventType()))
                    .map(ProcessCreatedEvent.class::cast)
                    .collect(Collectors.toList());
            assertThat(processCreatedEvents)
                    .extracting(event -> event.getEntity().getId())
                    .contains(processInstance.getId());

            List<ProcessStartedEvent> processStartedEvents = eventsProvider.getEvents()
                    .stream()
                    .filter(event -> ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED.equals(event.getEventType()))
                    .map(ProcessStartedEvent.class::cast)
                    .collect(Collectors.toList());

            assertThat(processStartedEvents)
                    .extracting(event -> event.getEntity().getId())
                    .as("Unable to find related " + PROCESS_STARTED.name()  + " event!")
                    .contains(processInstance.getId());

        };
    }

}

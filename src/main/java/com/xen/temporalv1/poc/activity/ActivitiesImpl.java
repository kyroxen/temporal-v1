/*
 *  Copyright (c) 2020 Temporal Technologies, Inc. All Rights Reserved
 *
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.xen.temporalv1.poc.activity;

import static com.xen.temporalv1.poc.Queues.WORKFLOW_QUEUE;

import io.temporal.spring.boot.ActivityImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@ActivityImpl(taskQueues = WORKFLOW_QUEUE)
public class ActivitiesImpl implements Activities {

    @Override
    public String activityFunction1(String leadId) {
        sleep(3000);
        return "activityFunction1 Done! ";
    }

    @Override
    public String activityFunction2(String startProcessingResult) {
        sleep(2000);
        return startProcessingResult + "activityFunction2 Done! ";
    }

    @Override
    public String activityFunction3(String processPaymentResult) {
        sleep(1000);
        return processPaymentResult + "activityFunction3 Done! ";
    }

    private void sleep(long milliseconds) {
        try {
            log.info(
                    "Thread {} Sleeping for {}",
                    Thread.currentThread().getName(),
                    milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

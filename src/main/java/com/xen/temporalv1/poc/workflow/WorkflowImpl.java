package com.xen.temporalv1.poc.workflow;

import com.xen.temporalv1.poc.activity.Activities;

import com.xen.temporalv1.poc.Queues;
import io.temporal.activity.ActivityCancellationType;
import io.temporal.activity.ActivityOptions;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@io.temporal.spring.boot.WorkflowImpl(taskQueues = Queues.WORKFLOW_QUEUE)
public class WorkflowImpl implements Workflow {

    private final Activities activities =
            io.temporal.workflow.Workflow.newActivityStub(
                    Activities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(4))
                            .setScheduleToCloseTimeout(Duration.ofSeconds(4))
                            .setCancellationType(ActivityCancellationType.TRY_CANCEL)
                            .build());

    @Override
    public String run(String id) {
        String result1 = activities.activityFunction1(id);
        String result2 = activities.activityFunction2(result1);
        String result3 = activities.activityFunction3(result2);
        log.info("Workflow complete!");
        return result3;
    }
}

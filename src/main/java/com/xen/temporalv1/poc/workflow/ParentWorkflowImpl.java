package com.xen.temporalv1.poc.workflow;

import com.xen.temporalv1.poc.Queues;
import com.xen.temporalv1.poc.activity.Activities;
import io.temporal.activity.ActivityCancellationType;
import io.temporal.activity.ActivityOptions;
import io.temporal.api.enums.v1.ParentClosePolicy;
import io.temporal.workflow.ChildWorkflowOptions;
import io.temporal.workflow.ChildWorkflowStub;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@io.temporal.spring.boot.WorkflowImpl(taskQueues = Queues.WORKFLOW_QUEUE)
public class ParentWorkflowImpl implements ParentWorkflow {

    private final Activities activities =
            io.temporal.workflow.Workflow.newActivityStub(
                    Activities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(4))
                            .setScheduleToCloseTimeout(Duration.ofSeconds(4))
                            .setCancellationType(ActivityCancellationType.TRY_CANCEL)
                            .build());

    /**
     * Workflow function
     *
     * @param id
     * @return
     */
    @Override
    public String run(String id) {
        final String result1 = activities.activityFunction1(id);
        final String result2 = activities.activityFunction2(id, result1);
        final String childResult = executeChildWorkflowAsync(id);
        final String finalResult = activities.activityFunction3(id, childResult);
        return finalResult;
    }

    /**
     * Goal: Spawn a child workflow that lives in a different microservice
     *
     * @param id to be passed in the child workflow activuty
     * @return result of the workflow
     */
    private String executeChildWorkflowAsync(String id) {
        // Define options
        final ChildWorkflowOptions options =
                ChildWorkflowOptions.newBuilder()
                        .setWorkflowId("child-workflow-" + id)
                        .setTaskQueue("child-workflow-queue")
                        .setParentClosePolicy(ParentClosePolicy.PARENT_CLOSE_POLICY_ABANDON)
                        .build();

        // Create an untyped stub
        final ChildWorkflowStub childUntyped =
                Workflow.newUntypedChildWorkflowStub("ChildWorkflow", options);

        // Execute async
        final Promise<String> childResult = childUntyped.executeAsync(String.class, id);

        // This blocking call ensures child workflow has been scheduled
        childUntyped.getExecution().get();

        // This blocking call is to get the result from workflow
        return childResult.get();
    }
}

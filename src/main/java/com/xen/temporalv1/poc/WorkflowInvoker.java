package com.xen.temporalv1.poc;

import static com.xen.temporalv1.poc.Queues.WORKFLOW_QUEUE;

import com.xen.temporalv1.poc.workflow.Workflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Service
@Slf4j
public class WorkflowInvoker {

    private final WorkflowClient client;

    public String invoke(@PathVariable String id) {
        WorkflowOptions workflowOptions =
                WorkflowOptions.newBuilder()
                        .setWorkflowId("workflow-parent-" + Thread.currentThread().getName())
                        .setTaskQueue(WORKFLOW_QUEUE)
                        .build();
        Workflow workflow = client.newWorkflowStub(Workflow.class, workflowOptions);
        WorkflowClient.start(workflow::run, id);
        return "Done!";
    }
}

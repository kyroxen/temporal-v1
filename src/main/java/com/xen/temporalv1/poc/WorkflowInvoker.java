package com.xen.temporalv1.poc;

import com.xen.temporalv1.poc.workflow.ParentWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import static com.xen.temporalv1.poc.Queues.WORKFLOW_QUEUE;

@RequiredArgsConstructor
@Service
@Slf4j
public class WorkflowInvoker {

    private final WorkflowClient client;

    public String invoke(@PathVariable Integer numOfWorkflows) {
        //        WorkflowOptions workflowOptions =
        //                WorkflowOptions.newBuilder()
        //                        .setWorkflowId("workflow-parent-" +
        // Thread.currentThread().getName())
        //                        .setTaskQueue(WORKFLOW_QUEUE)
        //                        .build();
        //        ParentWorkflow parentWorkflow = client.newWorkflowStub(ParentWorkflow.class,
        // workflowOptions);
        //        WorkflowClient.start(parentWorkflow::run, id);

        for (int i = 0; i < numOfWorkflows; i++) {
            WorkflowClient.start(
                    client.newWorkflowStub(
                                    ParentWorkflow.class,
                                    WorkflowOptions.newBuilder()
                                            .setWorkflowId("workflow-parent-" + i)
                                            .setTaskQueue(WORKFLOW_QUEUE)
                                            .build())
                            ::run,
                    "id_" + i);
        }

        return "Done!";
    }
}

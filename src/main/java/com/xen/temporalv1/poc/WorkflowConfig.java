package com.xen.temporalv1.poc;

import com.xen.temporalv1.poc.activity.ActivitiesImpl;
import com.xen.temporalv1.poc.workflow.ParentWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.xen.temporalv1.poc.Queues.WORKFLOW_QUEUE;

@Configuration
@RequiredArgsConstructor
public class WorkflowConfig {

    private final WorkflowClient workflowClient;

    @Bean
    public WorkerFactory workerFactory() {
        WorkerFactoryOptions workerFactoryOptions =
                WorkerFactoryOptions.newBuilder().setMaxWorkflowThreadCount(3).build();
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient, workerFactoryOptions);
        Worker worker =
                factory.newWorker(
                        WORKFLOW_QUEUE,
                        WorkerOptions.newBuilder()
                                .setMaxConcurrentActivityExecutionSize(2)
                                .setMaxConcurrentWorkflowTaskExecutionSize(2)
                                .build());
        worker.registerWorkflowImplementationTypes(ParentWorkflowImpl.class);
        worker.registerActivitiesImplementations(new ActivitiesImpl());
        factory.start();
        return factory;
    }
}

package com.xen.temporalv1.poc;

import static com.xen.temporalv1.poc.Queues.WORKFLOW_QUEUE;

import com.xen.temporalv1.poc.activity.ActivitiesImpl;
import com.xen.temporalv1.poc.workflow.WorkflowImpl;

import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WorkflowConfig {

    private final WorkflowClient workflowClient;

    @Bean
    public WorkerFactory workerFactory() {
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(WORKFLOW_QUEUE);
        worker.registerWorkflowImplementationTypes(WorkflowImpl.class);
        worker.registerActivitiesImplementations(new ActivitiesImpl());
        factory.start();
        return factory;
    }
}


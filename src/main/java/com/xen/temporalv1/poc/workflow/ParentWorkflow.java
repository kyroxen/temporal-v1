package com.xen.temporalv1.poc.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ParentWorkflow {
    @WorkflowMethod
    String run(String id);
}

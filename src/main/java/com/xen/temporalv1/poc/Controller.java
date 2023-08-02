package com.xen.temporalv1.poc;

import com.xen.temporalv1.poc.WorkflowInvoker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temporal-v1")
@RequiredArgsConstructor
public class Controller {

    private final WorkflowInvoker workflowInvoker;

    @GetMapping("/{numOfWorkflows}")
    public String triggerWorkflow(@PathVariable Integer numOfWorkflows) {
        return workflowInvoker.invoke(numOfWorkflows);
    }
}

package com.xen.temporalv1;

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

    @GetMapping("/{id}")
    public String triggerWorkflow(@PathVariable String id) {
        return workflowInvoker.invoke(id);
    }
}

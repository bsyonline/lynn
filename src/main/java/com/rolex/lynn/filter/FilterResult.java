package com.rolex.lynn.filter;

import lombok.Data;

/**
 * @author rolex
 * @Since 30/09/2019
 */
@Data
public class FilterResult {
    Object result;
    ExecutionStatus status;
    Throwable exception;
    
    public FilterResult() {
        this.status = ExecutionStatus.DISABLED;
    }
    
    public FilterResult(Object result, ExecutionStatus status) {
        this.result = result;
        this.status = status;
    }
    
    public enum ExecutionStatus {
        
        SUCCESS(1), SKIPPED(-1), DISABLED(-2), FAILED(-3);
        
        private int status;
        
        ExecutionStatus(int status) {
            this.status = status;
        }
    }
}

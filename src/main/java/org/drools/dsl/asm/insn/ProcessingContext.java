package org.drools.dsl.asm.insn;

import java.util.Map;
import java.util.Stack;

public class ProcessingContext {
    final Stack<String> stack = new Stack<String>();

    private final Map<String, String> vars;

    private String result;

    public ProcessingContext(Map<String, String> vars) {
        this.vars = vars;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

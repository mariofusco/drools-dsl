package org.drools.dsl.asm.insn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ProcessingContext {
    final List<Stack<String>> liveStacks = new ArrayList<Stack<String>>();
    final List<Stack<String>> terminatedStacks = new ArrayList<Stack<String>>();
    final Map<String, List<Stack<String>>> suspendedStacks = new HashMap<String, List<Stack<String>>>();

    private final Map<String, String> vars;

    public ProcessingContext(Map<String, String> vars) {
        this.vars = vars;
        liveStacks.add(new Stack<String>());
    }

    public List<Stack<String>> getTerminatedStacks() {
        return terminatedStacks;
    }
}

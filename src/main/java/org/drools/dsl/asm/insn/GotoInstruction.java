package org.drools.dsl.asm.insn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GotoInstruction implements Instruction  {

    private final String label;

    public GotoInstruction(String label) {
        this.label = label;
    }

    public void process(ProcessingContext ctx) {
        List<Stack<String>> stacks = ctx.suspendedStacks.get(label);
        if (stacks == null) {
            stacks = new ArrayList<Stack<String>>();
            ctx.suspendedStacks.put(label, stacks);
        }
        stacks.addAll(ctx.liveStacks);
        ctx.liveStacks.clear();
    }

    @Override
    public String toString() {
        return "GOTO " + label;
    }
}
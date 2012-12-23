package org.drools.dsl.asm.insn;

import java.util.List;
import java.util.Stack;

public class LabelInstruction implements Instruction  {

    private final String label;

    public LabelInstruction(String label) {
        this.label = label;
    }

    public void process(ProcessingContext ctx) {
        List<Stack<String>> stacks = ctx.suspendedStacks.remove(label);
        if (stacks != null) {
            ctx.liveStacks.addAll(stacks);
        }
    }

    @Override
    public String toString() {
        return "LABEL " + label;
    }
}

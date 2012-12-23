package org.drools.dsl.asm.insn;

import java.util.Stack;

public class LoadInstruction implements Instruction {
    private final int index;

    public LoadInstruction(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "LOAD " + index;
    }

    public void process(ProcessingContext ctx) {
        for (Stack<String> stack : ctx.liveStacks) {
            if (index == 0) {
                stack.push("this");
            }
        }
    }
}

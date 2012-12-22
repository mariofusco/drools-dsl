package org.drools.dsl.asm.insn;

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
        if (index == 0) {
            ctx.stack.push("this");
        }
    }
}

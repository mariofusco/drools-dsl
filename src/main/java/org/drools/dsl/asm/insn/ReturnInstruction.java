package org.drools.dsl.asm.insn;

public class ReturnInstruction implements Instruction {

    @Override
    public String toString() {
        return "RETURN";
    }

    public void process(ProcessingContext ctx) {
        ctx.terminatedStacks.addAll(ctx.liveStacks);
        ctx.liveStacks.clear();
    }
}

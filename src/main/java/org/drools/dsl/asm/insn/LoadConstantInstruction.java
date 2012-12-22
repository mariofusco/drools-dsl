package org.drools.dsl.asm.insn;

public class LoadConstantInstruction implements Instruction {

    private final Object cst;

    public LoadConstantInstruction(Object cst) {
        this.cst = cst;
    }

    @Override
    public String toString() {
        return "LOAD CONST " + cst;
    }

    public void process(ProcessingContext ctx) {
        if (cst instanceof String) {
            ctx.stack.push("\"" + cst + "\"");
        } else {
            ctx.stack.push(cst.toString());
        }
    }
}

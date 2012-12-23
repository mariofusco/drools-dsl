package org.drools.dsl.asm.insn;

import java.util.Stack;

public class LoadConstantInstruction implements Instruction {

    private final Object cst;

    public LoadConstantInstruction(Object cst) {
        this.cst = cst;
    }

    @Override
    public String toString() {
        return "CONST " + cst;
    }

    public void process(ProcessingContext ctx) {
        for (Stack<String> stack : ctx.liveStacks) {
            if (cst instanceof String) {
                stack.push("\"" + cst + "\"");
            } else if (cst instanceof Character) {
                stack.push("'" + cst + "'");
            } else {
                stack.push(cst.toString());
            }
        }
    }
}

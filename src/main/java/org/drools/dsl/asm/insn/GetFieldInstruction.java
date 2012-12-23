package org.drools.dsl.asm.insn;

import java.util.Stack;

public class GetFieldInstruction implements Instruction {

    private final String name;
    private final String owner;

    public GetFieldInstruction(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public void process(ProcessingContext ctx) {
        for (Stack<String> stack : ctx.liveStacks) {
            String owner = stack.pop();
            if (owner.equals("this")) {
                stack.push(name);
            } else {
                stack.push(owner + "." + name);
            }
        }
    }

    @Override
    public String toString() {
        return "GET " + owner + "." + name;
    }
}

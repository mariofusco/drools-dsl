package org.drools.dsl.asm.insn;

public class GetFieldInstruction implements Instruction {

    private final String name;
    private final String owner;

    public GetFieldInstruction(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public void process(ProcessingContext ctx) {
        String owner = ctx.stack.pop();
        if (owner.equals("this")) {
            ctx.stack.push(name);
        } else {
            ctx.stack.push(owner + "." + name);
        }
    }

    @Override
    public String toString() {
        return "GET " + owner + "." + name;
    }
}

package org.drools.dsl.asm;

import org.drools.dsl.asm.insn.GetFieldInstruction;
import org.drools.dsl.asm.insn.Instruction;
import org.drools.dsl.asm.insn.LoadConstantInstruction;
import org.drools.dsl.asm.insn.LoadInstruction;
import org.drools.dsl.asm.insn.MethodInvocationInstruction;
import org.drools.dsl.asm.insn.ReturnInstruction;
import org.mvel2.asm.AnnotationVisitor;
import org.mvel2.asm.Attribute;
import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.mvel2.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

import static org.mvel2.asm.Type.getObjectType;

public class LhsVisitor implements MethodVisitor, Opcodes {

    private final String desc;

    private final List<Instruction> instructions = new ArrayList<Instruction>();

    public LhsVisitor(String desc) {
        this.desc = desc;
    }

    public AnnotationVisitor visitAnnotationDefault() {
        return null;
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        return null;
    }

    public void visitAttribute(Attribute attr) {
    }

    public void visitCode() {
    }

    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
    }

    public void visitInsn(int opcode) {
        switch (opcode) {
            case IRETURN:
                instructions.add(new ReturnInstruction());
        }
    }

    public void visitIntInsn(int opcode, int operand) {
        System.out.println("visitIntInsn " + opcode + ", " + operand);
    }

    public void visitVarInsn(int opcode, int var) {
        switch (opcode) {
            case ALOAD:
                instructions.add(new LoadInstruction(var));
        }
        // System.out.println("visitVarInsn " + opcode + ", " + var);
    }

    public void visitTypeInsn(int opcode, String type) {
        System.out.println("visitTypeInsn " + opcode + ", " + type);
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if (opcode == GETFIELD) {
            instructions.add(new GetFieldInstruction(name, getObjectType(owner).getClassName()));
        }
        // System.out.println("visitFieldInsn " + opcode + ", " + owner + ", " + name + ", " + desc);
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        instructions.add(new MethodInvocationInstruction(getObjectType(owner).getClassName(), name, new MethodArgsDescr(desc)));
    }

    public void visitJumpInsn(int opcode, Label label) {
        System.out.println("visitJumpInsn " + opcode + ", " + label);
    }

    public void visitLabel(Label label) {
        // System.out.println("visitLabel " + label);
    }

    public void visitLdcInsn(Object cst) {
        instructions.add(new LoadConstantInstruction(cst));
    }

    public void visitIincInsn(int var, int increment) {
    }

    public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
    }

    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
    }

    public void visitMultiANewArrayInsn(String desc, int dims) {
    }

    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
    }

    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
    }

    public void visitLineNumber(int line, Label start) {
    }

    public void visitMaxs(int maxStack, int maxLocals) {
    }

    public void visitEnd() {
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}

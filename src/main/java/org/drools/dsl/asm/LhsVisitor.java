package org.drools.dsl.asm;

import org.drools.dsl.asm.insn.Comparison;
import org.drools.dsl.asm.insn.GetFieldInstruction;
import org.drools.dsl.asm.insn.GotoInstruction;
import org.drools.dsl.asm.insn.Instruction;
import org.drools.dsl.asm.insn.JumpInstruction;
import org.drools.dsl.asm.insn.LabelInstruction;
import org.drools.dsl.asm.insn.LoadConstantInstruction;
import org.drools.dsl.asm.insn.LoadInstruction;
import org.drools.dsl.asm.insn.MethodInvocationInstruction;
import org.drools.dsl.asm.insn.ReturnInstruction;
import org.drools.dsl.asm.insn.StoreInstruction;
import org.mvel2.asm.AnnotationVisitor;
import org.mvel2.asm.Attribute;
import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.mvel2.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

import static org.mvel2.asm.Type.getObjectType;
import static org.mvel2.asm.Type.getType;

public class LhsVisitor implements MethodVisitor, Opcodes {

    private final Class<?> ruleClass;

    private final List<Instruction> instructions = new ArrayList<Instruction>();

    public LhsVisitor(Class<?> ruleClass) {
        this.ruleClass = ruleClass;
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
                break;
            case ICONST_0:
                instructions.add(new LoadConstantInstruction(0));
                break;
            case ICONST_1:
                instructions.add(new LoadConstantInstruction(1));
                break;
            case ICONST_2:
                instructions.add(new LoadConstantInstruction(2));
                break;
            case ICONST_3:
                instructions.add(new LoadConstantInstruction(3));
                break;
            case ICONST_4:
                instructions.add(new LoadConstantInstruction(4));
                break;
            case ICONST_5:
                instructions.add(new LoadConstantInstruction(5));
                break;
        }
    }

    public void visitIntInsn(int opcode, int operand) {
        switch (opcode) {
            case BIPUSH:
                instructions.add(new LoadConstantInstruction((char)operand));
        }
        // System.out.println("visitIntInsn " + opcode + ", " + operand);
    }

    public void visitVarInsn(int opcode, int var) {
        switch (opcode) {
            case ILOAD:
            case LLOAD:
            case FLOAD:
            case DLOAD:
            case ALOAD:
                instructions.add(new LoadInstruction(ruleClass, var));
                break;
            case ISTORE:
            case LSTORE:
            case FSTORE:
            case DSTORE:
            case ASTORE:
                instructions.add(new StoreInstruction(var));
                break;
        }
        // System.out.println("visitVarInsn " + opcode + ", " + var);
    }

    public void visitTypeInsn(int opcode, String type) {
        System.out.println("visitTypeInsn " + opcode + ", " + type);
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if (opcode == GETFIELD) {
            instructions.add(new GetFieldInstruction(name, getObjectType(owner).getClassName(), getType(desc).getClassName()));
        }
        // System.out.println("visitFieldInsn " + opcode + ", " + owner + ", " + name + ", " + desc);
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        instructions.add(new MethodInvocationInstruction(getObjectType(owner).getClassName(), name, new MethodArgsDescr(desc)));
    }

    public void visitJumpInsn(int opcode, Label label) {
        switch (opcode) {
            case IFEQ:
                instructions.add(new LoadConstantInstruction(0));
            case IF_ICMPEQ:
            case IF_ACMPEQ:
                instructions.add(new JumpInstruction(Comparison.EQ, label.toString()));
                break;
            case IFNE:
                instructions.add(new LoadConstantInstruction(0));
            case IF_ICMPNE:
            case IF_ACMPNE:
                instructions.add(new JumpInstruction(Comparison.NE, label.toString()));
                break;
            case IFGT:
                instructions.add(new LoadConstantInstruction(0));
            case IF_ICMPGT:
                instructions.add(new JumpInstruction(Comparison.GT, label.toString()));
                break;
            case IFGE:
                instructions.add(new LoadConstantInstruction(0));
            case IF_ICMPGE:
                instructions.add(new JumpInstruction(Comparison.GE, label.toString()));
                break;
            case IFLT:
                instructions.add(new LoadConstantInstruction(0));
            case IF_ICMPLT:
                instructions.add(new JumpInstruction(Comparison.LT, label.toString()));
                break;
            case IFLE:
                instructions.add(new LoadConstantInstruction(0));
            case IF_ICMPLE:
                instructions.add(new JumpInstruction(Comparison.LE, label.toString()));
                break;
            case GOTO:
                instructions.add(new GotoInstruction(label.toString()));
                break;
        }
        // System.out.println("visitJumpInsn " + opcode + ", " + label);
    }

    public void visitLabel(Label label) {
        instructions.add(new LabelInstruction(label.toString()));
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

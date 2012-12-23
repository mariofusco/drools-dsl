package org.drools.dsl.asm;

import org.drools.dsl.DslPattern;
import org.drools.dsl.asm.insn.Instruction;
import org.drools.dsl.asm.insn.ProcessingContext;
import org.mvel2.asm.AnnotationVisitor;
import org.mvel2.asm.Attribute;
import org.mvel2.asm.ClassReader;
import org.mvel2.asm.ClassVisitor;
import org.mvel2.asm.FieldVisitor;
import org.mvel2.asm.MethodVisitor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.mvel2.asm.Type.getType;

public class RuleIntrospector implements ClassVisitor {

    private final Class<?> ruleClass;

    private LhsVisitor lhsVisitor;

    private final Map<String, String> vars = new HashMap<String, String>();

    private DslPattern dslPattern;

    public RuleIntrospector(Class<?> ruleClass) {
        this.ruleClass = ruleClass;
        introspect();
    }

    private void introspect() {
        ClassReader cr = null;
        try {
            cr = new ClassReader(ruleClass.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cr.accept(this, 0);
    }

    public DslPattern getDslPattern() {
        return dslPattern;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    }

    public void visitSource(String source, String debug) { }

    public void visitOuterClass(String owner, String name, String desc) { }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    public void visitAttribute(Attribute attr) { }

    public void visitInnerClass(String name, String outerName, String innerName, int access) { }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        vars.put(name, getType(desc).getClassName());
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals("lhs")) {
            lhsVisitor = new LhsVisitor(desc);
            return lhsVisitor;
        }
        return null;
    }

    public void visitEnd() {
        ProcessingContext processingContext = new ProcessingContext(vars);
        for (Instruction instruction : lhsVisitor.getInstructions()) {
            instruction.process(processingContext);
        }

        String result = null;
        for (Stack<String> stack : processingContext.getTerminatedStacks()) {
            result = stack.pop();
            if (result.equals("1")) {
                result = stack.pop();
                break;
            } else if (result.equals("0")) {
                result = null;
                continue;
            }
        }

        System.out.println(result);

        int firstDot = result.indexOf('.');
        String id = result.substring(0, firstDot);
        String constraint = result.substring(firstDot+1);
        String type = vars.get(id);

        dslPattern = new DslPattern(type, id, constraint);
    }
}

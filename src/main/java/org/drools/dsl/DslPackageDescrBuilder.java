package org.drools.dsl;

import org.drools.dsl.asm.RuleIntrospector;
import org.drools.lang.api.PackageDescrBuilder;
import org.drools.lang.descr.PackageDescr;

import static org.drools.lang.api.DescrFactory.newPackage;

public class DslPackageDescrBuilder {

    private final PackageDescrBuilder packageDescrBuilder = newPackage();

    private int counter = 0;

    public PackageDescr getPackageDescr() {
        return packageDescrBuilder.getDescr();
    }

    public void addRule(Class<?> ruleClass) {
        RuleIntrospector ruleIntrospector = new RuleIntrospector(ruleClass);
        DslPattern dslPattern = ruleIntrospector.getDslPattern();

        packageDescrBuilder.newRule()
            .name("R" + counter++)
                .lhs()
                    .and()
                        .pattern(dslPattern.getType()).id(dslPattern.getId(), false).constraint(dslPattern.getConstraint()).end()
                    .end()
                .end()
                .rhs(writeRhs(ruleClass, dslPattern))
            .end();
    }

    private String writeRhs(Class<?> ruleClass, DslPattern dslPattern) {
        return ruleClass.getName() + " obj = new " + ruleClass.getName() + "();\n" +
                "obj.setDrools(drools);\n" +
                "obj.setValue(\"" + dslPattern.getId() + "\", " + dslPattern.getId() + ");" +
                "obj.rhs();";
    }
}

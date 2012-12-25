package org.drools.dsl;

import org.drools.dsl.asm.RuleIntrospector;
import org.drools.lang.api.CEDescrBuilder;
import org.drools.lang.api.PackageDescrBuilder;
import org.drools.lang.api.PatternDescrBuilder;
import org.drools.lang.api.RuleDescrBuilder;
import org.drools.lang.descr.AndDescr;
import org.drools.lang.descr.ExistsDescr;
import org.drools.lang.descr.NotDescr;
import org.drools.lang.descr.PackageDescr;

import java.util.Collection;

import static org.drools.lang.api.DescrFactory.newPackage;

public class DslPackageDescrBuilder {

    private final PackageDescrBuilder packageDescrBuilder = newPackage();

    public PackageDescr getPackageDescr() {
        return packageDescrBuilder.getDescr();
    }

    public void addRule(Class<? extends AbstractJavaRule> ruleClass) {
        RuleIntrospector ruleIntrospector = new RuleIntrospector(ruleClass);

        CEDescrBuilder<CEDescrBuilder<RuleDescrBuilder, AndDescr>, AndDescr> ceDescrBuilder =
        packageDescrBuilder.newRule()
            .name(ruleClass.getName())
                .lhs()
                    .and();

        for (DslPattern dslPattern : ruleIntrospector.getDslPatterns()) {
            if (!dslPattern.isBound()) {
                switch (dslPattern.getModifier()) {
                    case NOT:
                        PatternDescrBuilder<CEDescrBuilder<CEDescrBuilder<CEDescrBuilder<RuleDescrBuilder,AndDescr>,AndDescr>,NotDescr>> notDescrBuilder =
                                ceDescrBuilder.not().pattern(dslPattern.getType());

                        for (String constraint : dslPattern.getConstraints()) {
                            notDescrBuilder.constraint(constraint);
                        }
                        notDescrBuilder.end();
                        break;
                    case EXISTS:
                        PatternDescrBuilder<CEDescrBuilder<CEDescrBuilder<CEDescrBuilder<RuleDescrBuilder,AndDescr>,AndDescr>,ExistsDescr>> existsDescrBuilder =
                                ceDescrBuilder.exists().pattern(dslPattern.getType());

                        for (String constraint : dslPattern.getConstraints()) {
                            existsDescrBuilder.constraint(constraint);
                        }
                        existsDescrBuilder.end();
                        break;
                }

            } else {
                PatternDescrBuilder<CEDescrBuilder<CEDescrBuilder<RuleDescrBuilder, AndDescr>, AndDescr>> patternDescrBuilder =
                        ceDescrBuilder.pattern(dslPattern.getType()).id(dslPattern.getId(), false);

                for (String constraint : dslPattern.getConstraints()) {
                    patternDescrBuilder.constraint(constraint);
                }
                patternDescrBuilder.end();
            }
        }

        ceDescrBuilder.end()
                .end()
                .rhs(writeRhs(ruleClass, ruleIntrospector.getBoundVars()))
            .end();
    }

    private String writeRhs(Class<?> ruleClass, Collection<String> ids) {
        StringBuilder sb = new StringBuilder();
        sb.append(ruleClass.getName()).append(" obj = new ").append(ruleClass.getName()).append("();\n")
                .append("obj.setDrools(drools);\n");
        for (String id : ids) {
            sb.append("obj.setValue(\"").append(id).append("\", ").append(id).append(");\n");
        }
        sb.append("obj.rhs();");
        return sb.toString();
    }
}

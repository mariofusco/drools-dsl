package org.drools.dsl;

import org.drools.compiler.PackageBuilder;
import org.drools.definitions.impl.KnowledgePackageImp;
import org.drools.lang.descr.PackageDescr;
import org.kie.definition.KnowledgePackage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.isStatic;

public class DslPackageBuilder {
    private final DslPackageDescrBuilder dslPackageDescrBuilder = new DslPackageDescrBuilder();

    public DslPackageBuilder addRule(Class<?>... ruleClasses) {
        for (Class<?> ruleClass : ruleClasses) {
            if (AbstractJavaRule.class.isAssignableFrom(ruleClass)) {
                dslPackageDescrBuilder.addRule((Class<? extends AbstractJavaRule>)ruleClass);
            } else {
                for (Class<?> innerClass : ruleClass.getDeclaredClasses()) {
                    if (isStatic(innerClass.getModifiers())) {
                        addRule(innerClass);
                    }
                }
            }
        }
        return this;
    }

    public List<KnowledgePackage> getKnowledgePackages() {
        PackageDescr packageDescr = dslPackageDescrBuilder.getPackageDescr();

        PackageBuilder packageBuilder = new PackageBuilder();
        packageBuilder.addPackage(packageDescr);
        org.drools.rule.Package[] pkgs = packageBuilder.getPackages();

        List<KnowledgePackage> list = new ArrayList<KnowledgePackage>( pkgs.length );
        for ( org.drools.rule.Package pkg : pkgs ) {
            list.add( new KnowledgePackageImp( pkg ) );
        }
        return list;
    }
}

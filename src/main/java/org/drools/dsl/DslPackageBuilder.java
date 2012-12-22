package org.drools.dsl;

import org.drools.compiler.PackageBuilder;
import org.drools.definitions.impl.KnowledgePackageImp;
import org.drools.lang.descr.PackageDescr;
import org.kie.definition.KnowledgePackage;

import java.util.ArrayList;
import java.util.List;

public class DslPackageBuilder {
    private final DslPackageDescrBuilder dslPackageDescrBuilder = new DslPackageDescrBuilder();

    public void addRule(Class<?> ruleClass) {
        dslPackageDescrBuilder.addRule(ruleClass);
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

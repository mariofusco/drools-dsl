A DSL to author Drools rules in pure Java
=========================================

The purpose of this project is provide a straightforward way to write Drools rules in pure Java.

Each rule is defined by a class extending AbstractJavaRule as in the following example:

        public class HelloWorldRule extends AbstractJavaRule {

            private Message $msg;

            public boolean lhs() {
                return $msg.getMessage().equals("Hello World");
            }

            public void rhs() {
                drools.insert(new Message("Hi Universe"));
            }
        }

This class is basically composed by 3 parts: the fields, representing the bound variables, plus the lhs() and rhs()
methods (declared abstract in AbstractJavaRule). The lhs() is never invoked. Instead its bytecode is parsed with ASM
in order to try to "guess" what the user actually wanted to do and generate the actual rule's lhs patterns accordingly.
Conversely the rhs() method is invoked as it is when the rule fires, after the values bound during the pattern matching
have been properly injected in the object's fields. Also the drools object is available in the rhs(), being a protected
field of AbstractJavaRule.

At the moments it is necessary to manually add the classes defining the rules (but I think it will possible to add
in a second time an automatic mechanism to discover these classes) as done in [this example](https://github.com/mariofusco/drools-dsl/blob/master/src/test/java/org/drools/dsl/DroolsDslTest.java).
It is also possible to define more rules in a single file as static inner classes of an outer class has done
[here](https://github.com/mariofusco/drools-dsl/blob/master/src/test/java/org/drools/dsl/firealarm/rules/FireAlarmRules.java)
and then add only the outer class to the DslPackageBuilder.
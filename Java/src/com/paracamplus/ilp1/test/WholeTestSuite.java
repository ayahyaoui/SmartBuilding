/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={
        com.paracamplus.ilp1.tools.test.FileToolTest.class,
        com.paracamplus.ilp1.tools.test.ProgramCallerTest.class,
        com.paracamplus.ilp1.interpreter.test.InterpreterTest.class,
})
public class WholeTestSuite {}

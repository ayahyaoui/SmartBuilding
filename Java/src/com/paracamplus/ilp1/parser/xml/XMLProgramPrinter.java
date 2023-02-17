package com.paracamplus.ilp1.parser.xml;

import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTassignment;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocSensor;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTreadField;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp1.tools.XMLPrinter;

/* 
 * Affichage d'un AST sous forme XML, en utilisant un visiteur.
 */
public class XMLProgramPrinter 
	implements IASTvisitor<Object,XMLPrinter,Exception> {

	protected IASTprogram input;

	public void setInput(IASTprogram input) {
		this.input = input;
	}
	
	public String getString() throws Exception {
		XMLPrinter printer = new XMLPrinter();
		visit(input,printer);
		return printer.getContents();
	}
		
	
	// Visite des déclarations de haut niveau

	public Object visit(IASTprogram iast, XMLPrinter printer) throws Exception {
		printer.openTag("program");
		iast.getBody().accept(this,printer);
		printer.closeTag();
		return null;
	}
	
	
	// Implantation des méthodes du visiteur IASTvisitable
	
	@Override
	public Object visit(IASTalternative iast, XMLPrinter printer) throws Exception {
		printer.openTag("alternative");
		printer.openTag("condition");
		iast.getCondition().accept(this,printer);
		printer.closeTag();
		/*
		 * printer.openTag("consequence");
		 
		iast.getConsequence().accept(this,printer);
		printer.closeTag();
		if (iast.isTernary()) {
			printer.openTag("alternant");
			iast.getAlternant().accept(this,printer);
			printer.closeTag();
		}*/
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTbinaryOperation iast, XMLPrinter printer) throws Exception {
		printer.openTag("binaryOperation",  "operator", iast.getOperator().getName());
		printer.openTag("leftOperand");
		iast.getLeftOperand().accept(this, printer);
		printer.closeTag();
		printer.openTag("rightOperand");
		iast.getRightOperand().accept(this, printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTblock iast, XMLPrinter printer) throws Exception {
		printer.openTag("block");
		printer.openTag("bindings");
		for (IASTblock.IASTbinding x : iast.getBindings()) {
			printer.openTag("binding");
			x.getVariable().accept(this,printer);
			IASTexpression e = x.getInitialisation();
			if (e != null) {
				printer.openTag("initialisation");
				e.accept(this, printer);
				printer.closeTag();				
			}
			printer.closeTag();
		}
		printer.closeTag();
		printer.openTag("body");
		iast.getBody().accept(this,printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTboolean iast, XMLPrinter printer) throws Exception {
		printer.clopenTag("boolean",  "value", iast.getDescription());
		return null;
	}

	@Override
	public Object visit(IASTfloat iast, XMLPrinter printer) throws Exception {
		printer.clopenTag("float", "value", iast.getDescription());
		return null;
	}

	@Override
	public Object visit(IASTinteger iast, XMLPrinter printer) throws Exception {
		printer.clopenTag("integer", "value", iast.getDescription());
		return null;
	}

	@Override
	public Object visit(IASTinvocation iast, XMLPrinter printer) throws Exception {
		printer.openTag("invocation");
		printer.openTag("function");
		iast.getFunction().accept(this, printer);
		printer.closeTag();
		printer.openTag("arguments");
		for (IASTexpression e : iast.getArguments()) {
			e.accept(this,printer);
		}
		printer.closeTag();		
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTsequence iast, XMLPrinter printer) throws Exception {
		boolean singleton = iast.getExpressions().length == 1;
		if (!singleton) printer.openTag("sequence");
		for (IASTexpression e : iast.getExpressions()) {
			e.accept(this,printer);
		}
		if (!singleton) printer.closeTag();		
		return null;
	}

	@Override
	public Object visit(IASTstring iast, XMLPrinter printer) throws Exception {
		printer.clopenTagText("string", iast.getValue());
		return null;
	}

	@Override
	public Object visit(IASTunaryOperation iast, XMLPrinter printer) throws Exception {
		printer.openTag("unaryOperation",  "operator", iast.getOperator().getName());
		printer.openTag("operand");
		iast.getOperand().accept(this, printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTvariable iast, XMLPrinter printer) throws Exception {
		printer.clopenTag("variable", "name", iast.getName());
		return null;
	}

	@Override
	public Object visit(IASTinvocSensor iast, XMLPrinter printer) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IASTfunctionDefinition iast, XMLPrinter printer) throws Exception {
		printer.clopenTagText("string", iast.getName());
		return null;
	}

	@Override
	public Object visit(IASTassignment iast, XMLPrinter printer) throws Exception {
		printer.openTag("assignment", "name", iast.getVariable().getName());
		printer.openTag("value");
		iast.getExpression().accept(this,printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTvariableAssign asTassignment, XMLPrinter data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IASTreadField asTreadFiekd, XMLPrinter data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

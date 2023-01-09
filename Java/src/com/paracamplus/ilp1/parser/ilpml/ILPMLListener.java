package com.paracamplus.ilp1.parser.ilpml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.stringtemplate.v4.ST;

import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;


import antlr4.ILPMLgrammar1Listener;
import antlr4.ILPMLgrammar1Parser.GlobalFunDefContext;
import antlr4.ILPMLgrammar1Parser.ReadFieldContext;
import antlr4.ILPMLgrammar1Parser.VariableAssignContext;


import static antlr4.ILPMLgrammar1Parser.*;

/**
 * Cette classe décrit à ANTRL comment construire un AST.
 * C'est un Listener : à chaque pas de l'analyse syntaxique, ANTRL
 * appelle cette classe, qui crée un ASTNode correspondant à ce qui
 * a été reconnu. 
 * Un Listener ne peut pas renvoyer de valeur, nous utilisons donc des
 * champs du contexte ctx pour transferer l'information d'une règle
 * de grammaire à l'autre.
 * 
 */
public class ILPMLListener implements ILPMLgrammar1Listener {
	// add to all the methods of ILPMLListener printout the name of the method:

	/*
	 * Le parseur est paramétré par une fabrique qui indique comment
	 * créer les instances concrètes d'AST.
	 */
	protected IASTfactory factory;
	
	public ILPMLListener(IASTfactory factory) {
		super();
		this.factory = factory;		
	}
	
	
	/*
	 * ANTLRGrammarBaseListener, automatiquement généré, fournit
	 * un squelette d'objet "Listener".
	 * Il suffit de redéinir les méthodes qui nous intéressent : celles de la
	 * forme "exit<règle>".
	 */
	
	@Override 
	public void exitVariable(VariableContext ctx) { 
		//System.out.println("exitVariable");
		ctx.node = factory.newVariable(ctx.getText());
	}

	@Override 
	public void exitInvocation(
			InvocationContext ctx) { 
		ctx.node = factory.newInvocation(
				ctx.fun.node, 
				toExpressions(ctx.args));
	}

	@Override 
	public void exitConstFloat(
			ConstFloatContext ctx) { 
		ctx.node = factory.newFloatConstant(ctx.floatConst.getText());
	}

	@Override 
	public void	exitConstInteger(
			ConstIntegerContext ctx) { 
		ctx.node = factory.newIntegerConstant(ctx.intConst.getText());
	}
/*
	@Override 
	public void exitBinding(BindingContext ctx) { 
		ctx.node = factory.newBlock(
				toBindings(ctx.vars, ctx.vals),
				ctx.body.node);
	}
*/
	@Override 
	public void exitAlternative(AlternativeContext ctx) { 
		ctx.node = factory.newAlternative(ctx.condition.node);
		//System.out.println("exitAlternative");
	}

	@Override 
	public void exitSequence(SequenceContext ctx) {
		ctx.node = factory.newSequence(toExpressions(ctx.exprs));
		//System.out.println("exitSequence");
		//ctx.node.show("[SHOW] exitSequence");
	}

	@Override 
	public void exitConstFalse(
			ConstFalseContext ctx) { 
		ctx.node = factory.newBooleanConstant("false");
	}

	@Override 
	public void exitProg(ProgContext ctx) {
		IASTfunctionDefinition f = toFunction(ctx.defs);
		IASTexpression e = factory.newSequence(toExpressions(ctx.exprs));
		//e.show("[SHOW] exitProg exp ");
		
		ctx.node = factory.newProgram(f, e);

	}

	@Override 
	public void exitUnary(UnaryContext ctx) { 
		ctx.node = factory.newUnaryOperation(
				factory.newOperator(ctx.op.getText()),
				ctx.arg.node);
	}

	@Override 
	public void exitConstTrue(
			ConstTrueContext ctx) {
		ctx.node = factory.newBooleanConstant("true");
	}

	@Override 
	public void exitConstString(
			ConstStringContext ctx) { 
		/*
		 * On enlève le " initial et final, et on interprète les codes
		 * d'échapement \n, \r, \t, \"
		 */
		String s = ctx.getText();
		StringBuilder buf = new StringBuilder();
		for (int i = 1; i < s.length() - 1; i++) {
			if (s.charAt(i) == '\\' && i < s.length() - 2) {
				switch (s.charAt(i+1)) {
				case 'n': buf.append('\n'); i++; break;
				case 'r': buf.append('\r'); i++; break;
				case 't': buf.append('\t'); i++; break;
				case '"': buf.append('\"'); i++; break;
				default: buf.append(s.charAt(i));
				}
			}
			else buf.append(s.charAt(i));
		}
		ctx.node = factory.newStringConstant(buf.toString());
	}

	@Override 
	public void exitBinary(BinaryContext ctx) { 
		ctx.node = factory.newBinaryOperation(
				factory.newOperator(ctx.op.getText()),
				ctx.arg1.node,
				ctx.arg2.node);				
	}

		
	
	/* Utilitaires de conversion ANTLR vers AST */
	protected IASTfunctionDefinition toFunction(List<GlobalFunDefContext> defs)
	{
		//System.out.println("============================================");
		if (defs == null || defs.isEmpty()) {
			System.out.println("global function null");
			return null;
		}
		//System.out.println("size " + defs.size());
		IASTfunctionDefinition result = defs.get(0).node;
		//result.show("[SHOW] toFunction: ");
		return result;

	}
	protected IASTexpression[] toExpressions(
			List<ExprContext> ctxs) {
		if (ctxs == null) return new IASTexpression[0];
		IASTexpression[] r = new IASTexpression[ctxs.size()];
		int pos = 0;
		for (ExprContext e : ctxs) {
			r[pos++] = e.node;
		}
		return r;
	}
	
	protected IASTvariable[] toVariables(
			List<Token> vars, boolean addSelf) {
		if (vars == null) return new IASTvariable[0];
		IASTvariable[] r = new IASTvariable[vars.size() + (addSelf ? 1 : 0)];
		int pos = 0;
		if (addSelf) {
			// Les déclarations de méthodes ont une variable "self" implicite
			r[pos++] = factory.newVariable("self");
		}
		for (Token v : vars) {
			r[pos++] = factory.newVariable(v.getText());
		}
		return r;
	}

	protected String[] toStrings(List<Token> vars) {
		if (vars == null) return new String[0];
		String[] r = new String[vars.size()];
		int pos = 0;
		for (Token v : vars) {
			r[pos++] = v.getText();
		}
		return r;
	}
/*
	protected IASTblock.IASTbinding[] toBindings(
			List<Token> vars, 
			List<ExprContext> exprs) {
		if (vars == null) return new IASTblock.IASTbinding[0];
		// par construction, vars et ctxs ont la même taille 
		assert(vars.size() == exprs.size());
		IASTblock.IASTbinding[] r = new IASTblock.IASTbinding[exprs.size()];
		int pos = 0;
		for (Token v : vars) {
			r[pos] = factory.newBinding(
					factory.newVariable(v.getText()),
					exprs.get(pos).node
					);
			pos++;
		}
		return r;			
	}
*/
	@Override	public void visitErrorNode(ErrorNode arg0) {}
	@Override	public void visitTerminal(TerminalNode arg0) {}
	@Override	public void enterEveryRule(ParserRuleContext arg0) {
		//System.out.println("enterEveryRule ilpml");
	}
	@Override	public void exitEveryRule(ParserRuleContext arg0) {
		//System.out.println("exitEveryRule ilpml");
	}
	@Override	public void enterConstInteger(ConstIntegerContext ctx) {
		//System.out.println("enterConstInteger ilpml");
	}
	@Override	public void enterProg(ProgContext ctx) {
		//System.out.println("enterProg ilpml");
	}
	@Override	public void enterConstFloat(ConstFloatContext ctx) {
		//System.out.println("enterConstFloat ilpml");
	}
	@Override	public void enterVariable(VariableContext ctx) {
		//System.out.println("enterVariable ilpml");
	}
	@Override	public void enterBinary(BinaryContext ctx) {
		//System.out.println("enterBinary ilpml");
	}
	@Override	public void enterAlternative(AlternativeContext ctx) {
		//System.out.println("enterAlternative ilpml");
	}
	@Override	public void enterConstFalse(ConstFalseContext ctx) {
		//System.out.println("enterConstFalse ilpml");
	}
	@Override	public void enterSequence(SequenceContext ctx) {
		//System.out.println("enterSequence ilpml");
	}
	@Override	public void enterConstTrue(ConstTrueContext ctx) {
		//System.out.println("enterConstTrue ilpml");
	}
	//@Override	public void enterBinding(BindingContext ctx) {}
	@Override	public void enterConstString(ConstStringContext ctx) {
		//System.out.println("enterConstString ilpml");
	}
	@Override	public void enterUnary(UnaryContext ctx) {
		//System.out.println("enterUnary ilpml");
	}
	@Override	public void enterInvocation(InvocationContext ctx) {
		//System.out.println("enterInvocation ilpml");
	}

	// TODO: à compléter
	@Override public void enterGlobalFunDef(GlobalFunDefContext ctx) {
		//System.out.println("enterGlobalFunct ilpml");
		
	}
	@Override public void enterReadField(ReadFieldContext ctx) {
		//System.out.println("enterReadField ilpml");
		
	}
	@Override public void enterVariableAssign(VariableAssignContext ctx) {
		//System.out.println("enterVariableAssign ilpml");
	}

	@Override
	public void exitGlobalFunDef(GlobalFunDefContext ctx) {
		//System.out.println("exitGlobalFunct ilpml");
		ctx.node = factory.newFunctionDefinition(
				factory.newVariable(ctx.name.getText()),toVariables(ctx.vars, false),ctx.body.node);
		//ctx.node.show("[SHOW] exitGlobalFunct ilpml");
	}



	@Override
	public void exitVariableAssign(VariableAssignContext ctx) {
		ctx.node = factory.newAssignment(
				factory.newVariable(ctx.var.getText()),
				ctx.val.node);
		//System.out.println("exitVariableAssign ilpml");
	}




	// TODO changer factorynewStringConst
	@Override
	public void exitReadField(ReadFieldContext ctx) {
		ctx.node = factory.newReadField(ctx.obj.getText(), factory.newStringConstant(ctx.field.getText()));		
		//System.out.println("exitReadField ilpml");
	}

}

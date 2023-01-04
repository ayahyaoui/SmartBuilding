// Generated from ILPMLgrammar2.g4 by ANTLR 4.4

    package antlr4;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ILPMLgrammar2Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__32=1, T__31=2, T__30=3, T__29=4, T__28=5, T__27=6, T__26=7, T__25=8, 
		T__24=9, T__23=10, T__22=11, T__21=12, T__20=13, T__19=14, T__18=15, T__17=16, 
		T__16=17, T__15=18, T__14=19, T__13=20, T__12=21, T__11=22, T__10=23, 
		T__9=24, T__8=25, T__7=26, T__6=27, T__5=28, T__4=29, T__3=30, T__2=31, 
		T__1=32, T__0=33, IDENT=34, INT=35, FLOAT=36, STRING=37, ESC=38, LINE_COMMENT=39, 
		COMMENT=40, SPACE=41;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'def'", "'true'", "'!='", "'while'", "'{'", "';'", 
		"'='", "'}'", "'if'", "'^'", "'<='", "'&'", "'do'", "'('", "'*'", "','", 
		"'false'", "'>='", "'|'", "'=='", "'<'", "'>'", "'!'", "'let'", "'%'", 
		"'else'", "'in'", "')'", "'and'", "'then'", "'+'", "'-'", "IDENT", "INT", 
		"FLOAT", "STRING", "ESC", "LINE_COMMENT", "COMMENT", "SPACE"
	};
	public static final int
		RULE_prog = 0, RULE_globalFunDef = 1, RULE_expr = 2;
	public static final String[] ruleNames = {
		"prog", "globalFunDef", "expr"
	};

	@Override
	public String getGrammarFileName() { return "ILPMLgrammar2.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ILPMLgrammar2Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public com.paracamplus.ilp2.interfaces.IASTprogram node;
		public GlobalFunDefContext globalFunDef;
		public List<GlobalFunDefContext> defs = new ArrayList<GlobalFunDefContext>();
		public ExprContext expr;
		public List<ExprContext> exprs = new ArrayList<ExprContext>();
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public List<GlobalFunDefContext> globalFunDef() {
			return getRuleContexts(GlobalFunDefContext.class);
		}
		public TerminalNode EOF() { return getToken(ILPMLgrammar2Parser.EOF, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GlobalFunDefContext globalFunDef(int i) {
			return getRuleContext(GlobalFunDefContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__31) {
				{
				{
				setState(6); ((ProgContext)_localctx).globalFunDef = globalFunDef();
				((ProgContext)_localctx).defs.add(((ProgContext)_localctx).globalFunDef);
				setState(8);
				_la = _input.LA(1);
				if (_la==T__26) {
					{
					setState(7); match(T__26);
					}
				}

				}
				}
				setState(14);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__28) | (1L << T__23) | (1L << T__18) | (1L << T__15) | (1L << T__9) | (1L << T__8) | (1L << T__0) | (1L << IDENT) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
				{
				{
				setState(15); ((ProgContext)_localctx).expr = expr(0);
				((ProgContext)_localctx).exprs.add(((ProgContext)_localctx).expr);
				setState(17);
				_la = _input.LA(1);
				if (_la==T__26) {
					{
					setState(16); match(T__26);
					}
				}

				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(24); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalFunDefContext extends ParserRuleContext {
		public com.paracamplus.ilp2.interfaces.IASTfunctionDefinition node;
		public Token name;
		public Token IDENT;
		public List<Token> vars = new ArrayList<Token>();
		public ExprContext body;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENT(int i) {
			return getToken(ILPMLgrammar2Parser.IDENT, i);
		}
		public List<TerminalNode> IDENT() { return getTokens(ILPMLgrammar2Parser.IDENT); }
		public GlobalFunDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalFunDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterGlobalFunDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitGlobalFunDef(this);
		}
	}

	public final GlobalFunDefContext globalFunDef() throws RecognitionException {
		GlobalFunDefContext _localctx = new GlobalFunDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_globalFunDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26); match(T__31);
			setState(27); ((GlobalFunDefContext)_localctx).name = match(IDENT);
			setState(28); match(T__18);
			setState(30);
			_la = _input.LA(1);
			if (_la==IDENT) {
				{
				setState(29); ((GlobalFunDefContext)_localctx).IDENT = match(IDENT);
				((GlobalFunDefContext)_localctx).vars.add(((GlobalFunDefContext)_localctx).IDENT);
				}
			}

			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(32); match(T__16);
				setState(33); ((GlobalFunDefContext)_localctx).IDENT = match(IDENT);
				((GlobalFunDefContext)_localctx).vars.add(((GlobalFunDefContext)_localctx).IDENT);
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39); match(T__4);
			setState(40); match(T__27);
			setState(41); ((GlobalFunDefContext)_localctx).body = expr(0);
			setState(42); match(T__24);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public com.paracamplus.ilp1.interfaces.IASTexpression node;
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
			this.node = ctx.node;
		}
	}
	public static class BindingContext extends ExprContext {
		public Token IDENT;
		public List<Token> vars = new ArrayList<Token>();
		public ExprContext expr;
		public List<ExprContext> vals = new ArrayList<ExprContext>();
		public ExprContext body;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IDENT(int i) {
			return getToken(ILPMLgrammar2Parser.IDENT, i);
		}
		public List<TerminalNode> IDENT() { return getTokens(ILPMLgrammar2Parser.IDENT); }
		public BindingContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterBinding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitBinding(this);
		}
	}
	public static class LoopContext extends ExprContext {
		public ExprContext condition;
		public ExprContext body;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public LoopContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitLoop(this);
		}
	}
	public static class VariableContext extends ExprContext {
		public Token var;
		public TerminalNode IDENT() { return getToken(ILPMLgrammar2Parser.IDENT, 0); }
		public VariableContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitVariable(this);
		}
	}
	public static class AlternativeContext extends ExprContext {
		public ExprContext condition;
		public ExprContext consequence;
		public ExprContext alternant;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public AlternativeContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterAlternative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitAlternative(this);
		}
	}
	public static class InvocationContext extends ExprContext {
		public ExprContext fun;
		public ExprContext expr;
		public List<ExprContext> args = new ArrayList<ExprContext>();
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public InvocationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitInvocation(this);
		}
	}
	public static class ConstFloatContext extends ExprContext {
		public Token floatConst;
		public TerminalNode FLOAT() { return getToken(ILPMLgrammar2Parser.FLOAT, 0); }
		public ConstFloatContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterConstFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitConstFloat(this);
		}
	}
	public static class VariableAssignContext extends ExprContext {
		public Token var;
		public ExprContext val;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(ILPMLgrammar2Parser.IDENT, 0); }
		public VariableAssignContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterVariableAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitVariableAssign(this);
		}
	}
	public static class SequenceContext extends ExprContext {
		public ExprContext expr;
		public List<ExprContext> exprs = new ArrayList<ExprContext>();
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public SequenceContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitSequence(this);
		}
	}
	public static class ConstFalseContext extends ExprContext {
		public ConstFalseContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterConstFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitConstFalse(this);
		}
	}
	public static class UnaryContext extends ExprContext {
		public Token op;
		public ExprContext arg;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitUnary(this);
		}
	}
	public static class ConstTrueContext extends ExprContext {
		public ConstTrueContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterConstTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitConstTrue(this);
		}
	}
	public static class ConstIntegerContext extends ExprContext {
		public Token intConst;
		public TerminalNode INT() { return getToken(ILPMLgrammar2Parser.INT, 0); }
		public ConstIntegerContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterConstInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitConstInteger(this);
		}
	}
	public static class ConstStringContext extends ExprContext {
		public Token stringConst;
		public TerminalNode STRING() { return getToken(ILPMLgrammar2Parser.STRING, 0); }
		public ConstStringContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterConstString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitConstString(this);
		}
	}
	public static class BinaryContext extends ExprContext {
		public ExprContext arg1;
		public Token op;
		public ExprContext arg2;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BinaryContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).enterBinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ILPMLgrammar2Listener ) ((ILPMLgrammar2Listener)listener).exitBinary(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				_localctx = new UnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(45);
				((UnaryContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__0) ) {
					((UnaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(46); ((UnaryContext)_localctx).arg = expr(17);
				}
				break;
			case 2:
				{
				_localctx = new BindingContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(47); match(T__8);
				setState(48); ((BindingContext)_localctx).IDENT = match(IDENT);
				((BindingContext)_localctx).vars.add(((BindingContext)_localctx).IDENT);
				setState(49); match(T__25);
				setState(50); ((BindingContext)_localctx).expr = expr(0);
				((BindingContext)_localctx).vals.add(((BindingContext)_localctx).expr);
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(51); match(T__3);
					setState(52); ((BindingContext)_localctx).IDENT = match(IDENT);
					((BindingContext)_localctx).vars.add(((BindingContext)_localctx).IDENT);
					setState(53); match(T__25);
					setState(54); ((BindingContext)_localctx).expr = expr(0);
					((BindingContext)_localctx).vals.add(((BindingContext)_localctx).expr);
					}
					}
					setState(59);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(60); match(T__5);
				setState(61); ((BindingContext)_localctx).body = expr(4);
				}
				break;
			case 3:
				{
				_localctx = new VariableAssignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(63); ((VariableAssignContext)_localctx).var = match(IDENT);
				setState(64); match(T__25);
				setState(65); ((VariableAssignContext)_localctx).val = expr(2);
				}
				break;
			case 4:
				{
				_localctx = new LoopContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(66); match(T__28);
				setState(67); ((LoopContext)_localctx).condition = expr(0);
				setState(68); match(T__19);
				setState(69); ((LoopContext)_localctx).body = expr(1);
				}
				break;
			case 5:
				{
				_localctx = new SequenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(71); match(T__18);
				setState(72); ((SequenceContext)_localctx).expr = expr(0);
				((SequenceContext)_localctx).exprs.add(((SequenceContext)_localctx).expr);
				setState(79);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(74);
						_la = _input.LA(1);
						if (_la==T__26) {
							{
							setState(73); match(T__26);
							}
						}

						setState(76); ((SequenceContext)_localctx).expr = expr(0);
						((SequenceContext)_localctx).exprs.add(((SequenceContext)_localctx).expr);
						}
						} 
					}
					setState(81);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				}
				setState(83);
				_la = _input.LA(1);
				if (_la==T__26) {
					{
					setState(82); match(T__26);
					}
				}

				setState(85); match(T__4);
				}
				break;
			case 6:
				{
				_localctx = new ConstTrueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(87); match(T__30);
				}
				break;
			case 7:
				{
				_localctx = new ConstFalseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(88); match(T__15);
				}
				break;
			case 8:
				{
				_localctx = new ConstIntegerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(89); ((ConstIntegerContext)_localctx).intConst = match(INT);
				}
				break;
			case 9:
				{
				_localctx = new ConstFloatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90); ((ConstFloatContext)_localctx).floatConst = match(FLOAT);
				}
				break;
			case 10:
				{
				_localctx = new ConstStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(91); ((ConstStringContext)_localctx).stringConst = match(STRING);
				}
				break;
			case 11:
				{
				_localctx = new VariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(92); ((VariableContext)_localctx).var = match(IDENT);
				}
				break;
			case 12:
				{
				_localctx = new AlternativeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(93); match(T__23);
				setState(94); ((AlternativeContext)_localctx).condition = expr(0);
				setState(95); match(T__2);
				setState(96); ((AlternativeContext)_localctx).consequence = expr(0);
				setState(99);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(97); match(T__6);
					setState(98); ((AlternativeContext)_localctx).alternant = expr(0);
					}
					break;
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(136);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(134);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).arg1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(103);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(104);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__32) | (1L << T__17) | (1L << T__7))) != 0)) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(105); ((BinaryContext)_localctx).arg2 = expr(17);
						}
						break;
					case 2:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).arg1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(106);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(107);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__1 || _la==T__0) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(108); ((BinaryContext)_localctx).arg2 = expr(16);
						}
						break;
					case 3:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).arg1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(109);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(110);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__14) | (1L << T__11) | (1L << T__10))) != 0)) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(111); ((BinaryContext)_localctx).arg2 = expr(15);
						}
						break;
					case 4:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).arg1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(112);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(113);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__29 || _la==T__12) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(114); ((BinaryContext)_localctx).arg2 = expr(14);
						}
						break;
					case 5:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).arg1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(115);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(116); ((BinaryContext)_localctx).op = match(T__20);
						setState(117); ((BinaryContext)_localctx).arg2 = expr(13);
						}
						break;
					case 6:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).arg1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(118);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(119);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__22 || _la==T__13) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(120); ((BinaryContext)_localctx).arg2 = expr(12);
						}
						break;
					case 7:
						{
						_localctx = new InvocationContext(new ExprContext(_parentctx, _parentState));
						((InvocationContext)_localctx).fun = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(121);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(122); match(T__18);
						setState(124);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__28) | (1L << T__23) | (1L << T__18) | (1L << T__15) | (1L << T__9) | (1L << T__8) | (1L << T__0) | (1L << IDENT) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
							{
							setState(123); ((InvocationContext)_localctx).expr = expr(0);
							((InvocationContext)_localctx).args.add(((InvocationContext)_localctx).expr);
							}
						}

						setState(130);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==T__16) {
							{
							{
							setState(126); match(T__16);
							setState(127); ((InvocationContext)_localctx).expr = expr(0);
							((InvocationContext)_localctx).args.add(((InvocationContext)_localctx).expr);
							}
							}
							setState(132);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(133); match(T__4);
						}
						break;
					}
					} 
				}
				setState(138);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 16);
		case 1: return precpred(_ctx, 15);
		case 2: return precpred(_ctx, 14);
		case 3: return precpred(_ctx, 13);
		case 4: return precpred(_ctx, 12);
		case 5: return precpred(_ctx, 11);
		case 6: return precpred(_ctx, 18);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3+\u008e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\3\2\3\2\5\2\13\n\2\7\2\r\n\2\f\2\16\2\20\13\2\3\2\3\2\5"+
		"\2\24\n\2\7\2\26\n\2\f\2\16\2\31\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3!\n\3"+
		"\3\3\3\3\7\3%\n\3\f\3\16\3(\13\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4:\n\4\f\4\16\4=\13\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4M\n\4\3\4\7\4P\n\4\f\4\16\4S\13"+
		"\4\3\4\5\4V\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\5\4f\n\4\5\4h\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\177\n\4\3\4\3\4\7\4\u0083\n\4\f"+
		"\4\16\4\u0086\13\4\3\4\7\4\u0089\n\4\f\4\16\4\u008c\13\4\3\4\2\3\6\5\2"+
		"\4\6\2\b\4\2\32\32##\5\2\3\3\22\22\34\34\3\2\"#\5\2\16\16\25\25\30\31"+
		"\4\2\6\6\27\27\4\2\r\r\26\26\u00a9\2\16\3\2\2\2\4\34\3\2\2\2\6g\3\2\2"+
		"\2\b\n\5\4\3\2\t\13\7\t\2\2\n\t\3\2\2\2\n\13\3\2\2\2\13\r\3\2\2\2\f\b"+
		"\3\2\2\2\r\20\3\2\2\2\16\f\3\2\2\2\16\17\3\2\2\2\17\27\3\2\2\2\20\16\3"+
		"\2\2\2\21\23\5\6\4\2\22\24\7\t\2\2\23\22\3\2\2\2\23\24\3\2\2\2\24\26\3"+
		"\2\2\2\25\21\3\2\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\32\3"+
		"\2\2\2\31\27\3\2\2\2\32\33\7\2\2\3\33\3\3\2\2\2\34\35\7\4\2\2\35\36\7"+
		"$\2\2\36 \7\21\2\2\37!\7$\2\2 \37\3\2\2\2 !\3\2\2\2!&\3\2\2\2\"#\7\23"+
		"\2\2#%\7$\2\2$\"\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\')\3\2\2\2(&\3"+
		"\2\2\2)*\7\37\2\2*+\7\b\2\2+,\5\6\4\2,-\7\13\2\2-\5\3\2\2\2./\b\4\1\2"+
		"/\60\t\2\2\2\60h\5\6\4\23\61\62\7\33\2\2\62\63\7$\2\2\63\64\7\n\2\2\64"+
		";\5\6\4\2\65\66\7 \2\2\66\67\7$\2\2\678\7\n\2\28:\5\6\4\29\65\3\2\2\2"+
		":=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=;\3\2\2\2>?\7\36\2\2?@\5\6\4"+
		"\6@h\3\2\2\2AB\7$\2\2BC\7\n\2\2Ch\5\6\4\4DE\7\7\2\2EF\5\6\4\2FG\7\20\2"+
		"\2GH\5\6\4\3Hh\3\2\2\2IJ\7\21\2\2JQ\5\6\4\2KM\7\t\2\2LK\3\2\2\2LM\3\2"+
		"\2\2MN\3\2\2\2NP\5\6\4\2OL\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2RU\3\2"+
		"\2\2SQ\3\2\2\2TV\7\t\2\2UT\3\2\2\2UV\3\2\2\2VW\3\2\2\2WX\7\37\2\2Xh\3"+
		"\2\2\2Yh\7\5\2\2Zh\7\24\2\2[h\7%\2\2\\h\7&\2\2]h\7\'\2\2^h\7$\2\2_`\7"+
		"\f\2\2`a\5\6\4\2ab\7!\2\2be\5\6\4\2cd\7\35\2\2df\5\6\4\2ec\3\2\2\2ef\3"+
		"\2\2\2fh\3\2\2\2g.\3\2\2\2g\61\3\2\2\2gA\3\2\2\2gD\3\2\2\2gI\3\2\2\2g"+
		"Y\3\2\2\2gZ\3\2\2\2g[\3\2\2\2g\\\3\2\2\2g]\3\2\2\2g^\3\2\2\2g_\3\2\2\2"+
		"h\u008a\3\2\2\2ij\f\22\2\2jk\t\3\2\2k\u0089\5\6\4\23lm\f\21\2\2mn\t\4"+
		"\2\2n\u0089\5\6\4\22op\f\20\2\2pq\t\5\2\2q\u0089\5\6\4\21rs\f\17\2\2s"+
		"t\t\6\2\2t\u0089\5\6\4\20uv\f\16\2\2vw\7\17\2\2w\u0089\5\6\4\17xy\f\r"+
		"\2\2yz\t\7\2\2z\u0089\5\6\4\16{|\f\24\2\2|~\7\21\2\2}\177\5\6\4\2~}\3"+
		"\2\2\2~\177\3\2\2\2\177\u0084\3\2\2\2\u0080\u0081\7\23\2\2\u0081\u0083"+
		"\5\6\4\2\u0082\u0080\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084"+
		"\u0085\3\2\2\2\u0085\u0087\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0089\7\37"+
		"\2\2\u0088i\3\2\2\2\u0088l\3\2\2\2\u0088o\3\2\2\2\u0088r\3\2\2\2\u0088"+
		"u\3\2\2\2\u0088x\3\2\2\2\u0088{\3\2\2\2\u0089\u008c\3\2\2\2\u008a\u0088"+
		"\3\2\2\2\u008a\u008b\3\2\2\2\u008b\7\3\2\2\2\u008c\u008a\3\2\2\2\22\n"+
		"\16\23\27 &;LQUeg~\u0084\u0088\u008a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
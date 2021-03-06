/*
 * generated by Xtext
 */
package net.certware.argument.analysis.services;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.util.List;

import org.eclipse.xtext.*;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.service.AbstractElementFinder.*;

import org.eclipse.xtext.common.services.TerminalsGrammarAccess;

@Singleton
public class AnalysisDSLGrammarAccess extends AbstractGrammarElementFinder {
	
	
	public class OutputElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.Output");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cLinesAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cLinesLineParserRuleCall_0_0 = (RuleCall)cLinesAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final RuleCall cSATISFIABLETerminalRuleCall_1_0 = (RuleCall)cAlternatives_1.eContents().get(0);
		private final RuleCall cUNSATISFIABLETerminalRuleCall_1_1 = (RuleCall)cAlternatives_1.eContents().get(1);
		
		//// first rule must be a parser rule
		//// an output file consists of some number of lines followed by SATISFIABLE or UNSATISFIABLE keyword
		//// we assume the output file is scanned as ASCII text but it need not be stored this way
		//Output:
		//	lines+=Line* (SATISFIABLE | UNSATISFIABLE);
		@Override public ParserRule getRule() { return rule; }

		//lines+=Line* (SATISFIABLE | UNSATISFIABLE)
		public Group getGroup() { return cGroup; }

		//lines+=Line*
		public Assignment getLinesAssignment_0() { return cLinesAssignment_0; }

		//Line
		public RuleCall getLinesLineParserRuleCall_0_0() { return cLinesLineParserRuleCall_0_0; }

		//(SATISFIABLE | UNSATISFIABLE)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }

		//SATISFIABLE
		public RuleCall getSATISFIABLETerminalRuleCall_1_0() { return cSATISFIABLETerminalRuleCall_1_0; }

		//UNSATISFIABLE
		public RuleCall getUNSATISFIABLETerminalRuleCall_1_1() { return cUNSATISFIABLETerminalRuleCall_1_1; }
	}

	public class LineElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.Line");
		private final Assignment cItemsAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cItemsClassicLiteralParserRuleCall_0 = (RuleCall)cItemsAssignment.eContents().get(0);
		
		//// more parser rules
		//// a line contains one or more classic literals
		//Line:
		//	items+=ClassicLiteral+;
		@Override public ParserRule getRule() { return rule; }

		//items+=ClassicLiteral+
		public Assignment getItemsAssignment() { return cItemsAssignment; }

		//ClassicLiteral
		public RuleCall getItemsClassicLiteralParserRuleCall_0() { return cItemsClassicLiteralParserRuleCall_0; }
	}

	public class ClassicLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.ClassicLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNegAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cNegHyphenMinusKeyword_0_0 = (Keyword)cNegAssignment_0.eContents().get(0);
		private final Assignment cAtomAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cAtomAtomParserRuleCall_1_0 = (RuleCall)cAtomAssignment_1.eContents().get(0);
		
		//// a classic literal is an atom with an optional negation sign
		//ClassicLiteral:
		//	neg='-'? atom=Atom;
		@Override public ParserRule getRule() { return rule; }

		//neg='-'? atom=Atom
		public Group getGroup() { return cGroup; }

		//neg='-'?
		public Assignment getNegAssignment_0() { return cNegAssignment_0; }

		//'-'
		public Keyword getNegHyphenMinusKeyword_0_0() { return cNegHyphenMinusKeyword_0_0; }

		//atom=Atom
		public Assignment getAtomAssignment_1() { return cAtomAssignment_1; }

		//Atom
		public RuleCall getAtomAtomParserRuleCall_1_0() { return cAtomAtomParserRuleCall_1_0; }
	}

	public class AtomElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.Atom");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNamePREDICATE_NAMEParserRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cTermsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cTermsTermsParserRuleCall_1_1_0 = (RuleCall)cTermsAssignment_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		
		//// an atom is a predicate name with an optional terms expression in parentheses	
		//Atom:
		//	name=PREDICATE_NAME ('(' terms=Terms? ')')?;
		@Override public ParserRule getRule() { return rule; }

		//name=PREDICATE_NAME ('(' terms=Terms? ')')?
		public Group getGroup() { return cGroup; }

		//name=PREDICATE_NAME
		public Assignment getNameAssignment_0() { return cNameAssignment_0; }

		//PREDICATE_NAME
		public RuleCall getNamePREDICATE_NAMEParserRuleCall_0_0() { return cNamePREDICATE_NAMEParserRuleCall_0_0; }

		//('(' terms=Terms? ')')?
		public Group getGroup_1() { return cGroup_1; }

		//'('
		public Keyword getLeftParenthesisKeyword_1_0() { return cLeftParenthesisKeyword_1_0; }

		//terms=Terms?
		public Assignment getTermsAssignment_1_1() { return cTermsAssignment_1_1; }

		//Terms
		public RuleCall getTermsTermsParserRuleCall_1_1_0() { return cTermsTermsParserRuleCall_1_1_0; }

		//')'
		public Keyword getRightParenthesisKeyword_1_2() { return cRightParenthesisKeyword_1_2; }
	}

	public class PREDICATE_NAMEElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.PREDICATE_NAME");
		private final RuleCall cIDENTIFIERTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//PREDICATE_NAME:
		//	IDENTIFIER;
		@Override public ParserRule getRule() { return rule; }

		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall() { return cIDENTIFIERTerminalRuleCall; }
	}

	public class TermsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.Terms");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cCarAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cCarTermParserRuleCall_0_0 = (RuleCall)cCarAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cCommaKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cCdrAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cCdrTermParserRuleCall_1_1_0 = (RuleCall)cCdrAssignment_1_1.eContents().get(0);
		
		//// a terms expression is one or more terms separated by commas
		//Terms:
		//	car=Term (',' cdr+=Term)*;
		@Override public ParserRule getRule() { return rule; }

		//car=Term (',' cdr+=Term)*
		public Group getGroup() { return cGroup; }

		//car=Term
		public Assignment getCarAssignment_0() { return cCarAssignment_0; }

		//Term
		public RuleCall getCarTermParserRuleCall_0_0() { return cCarTermParserRuleCall_0_0; }

		//(',' cdr+=Term)*
		public Group getGroup_1() { return cGroup_1; }

		//','
		public Keyword getCommaKeyword_1_0() { return cCommaKeyword_1_0; }

		//cdr+=Term
		public Assignment getCdrAssignment_1_1() { return cCdrAssignment_1_1; }

		//Term
		public RuleCall getCdrTermParserRuleCall_1_1_0() { return cCdrTermParserRuleCall_1_1_0; }
	}

	public class TermElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.Term");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cCsAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cCsCONSTANT_SYMBOLParserRuleCall_0_0 = (RuleCall)cCsAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cSignAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final Keyword cSignHyphenMinusKeyword_1_0_0 = (Keyword)cSignAssignment_1_0.eContents().get(0);
		private final Assignment cNAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cNNUMBERTerminalRuleCall_1_1_0 = (RuleCall)cNAssignment_1_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cAlternatives.eContents().get(2);
		private final Assignment cFsAssignment_2_0 = (Assignment)cGroup_2.eContents().get(0);
		private final RuleCall cFsFUNCTIONAL_SYMBOLParserRuleCall_2_0_0 = (RuleCall)cFsAssignment_2_0.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2_1 = (Keyword)cGroup_2.eContents().get(1);
		private final Assignment cTermsAssignment_2_2 = (Assignment)cGroup_2.eContents().get(2);
		private final RuleCall cTermsTermsParserRuleCall_2_2_0 = (RuleCall)cTermsAssignment_2_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2_3 = (Keyword)cGroup_2.eContents().get(3);
		
		//// a term is a constant symbol, a number with optional sign, or 
		//// a functional symbol with optional terms in parentheses
		//Term:
		//	cs=CONSTANT_SYMBOL | sign='-'? n=NUMBER | fs=FUNCTIONAL_SYMBOL '(' terms=Terms ')';
		@Override public ParserRule getRule() { return rule; }

		//cs=CONSTANT_SYMBOL | sign='-'? n=NUMBER | fs=FUNCTIONAL_SYMBOL '(' terms=Terms ')'
		public Alternatives getAlternatives() { return cAlternatives; }

		//cs=CONSTANT_SYMBOL
		public Assignment getCsAssignment_0() { return cCsAssignment_0; }

		//CONSTANT_SYMBOL
		public RuleCall getCsCONSTANT_SYMBOLParserRuleCall_0_0() { return cCsCONSTANT_SYMBOLParserRuleCall_0_0; }

		//sign='-'? n=NUMBER
		public Group getGroup_1() { return cGroup_1; }

		//sign='-'?
		public Assignment getSignAssignment_1_0() { return cSignAssignment_1_0; }

		//'-'
		public Keyword getSignHyphenMinusKeyword_1_0_0() { return cSignHyphenMinusKeyword_1_0_0; }

		//n=NUMBER
		public Assignment getNAssignment_1_1() { return cNAssignment_1_1; }

		//NUMBER
		public RuleCall getNNUMBERTerminalRuleCall_1_1_0() { return cNNUMBERTerminalRuleCall_1_1_0; }

		//fs=FUNCTIONAL_SYMBOL '(' terms=Terms ')'
		public Group getGroup_2() { return cGroup_2; }

		//fs=FUNCTIONAL_SYMBOL
		public Assignment getFsAssignment_2_0() { return cFsAssignment_2_0; }

		//FUNCTIONAL_SYMBOL
		public RuleCall getFsFUNCTIONAL_SYMBOLParserRuleCall_2_0_0() { return cFsFUNCTIONAL_SYMBOLParserRuleCall_2_0_0; }

		//'('
		public Keyword getLeftParenthesisKeyword_2_1() { return cLeftParenthesisKeyword_2_1; }

		//terms=Terms
		public Assignment getTermsAssignment_2_2() { return cTermsAssignment_2_2; }

		//Terms
		public RuleCall getTermsTermsParserRuleCall_2_2_0() { return cTermsTermsParserRuleCall_2_2_0; }

		//')'
		public Keyword getRightParenthesisKeyword_2_3() { return cRightParenthesisKeyword_2_3; }
	}

	public class CONSTANT_SYMBOLElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.CONSTANT_SYMBOL");
		private final RuleCall cIDENTIFIERTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//CONSTANT_SYMBOL:
		//	IDENTIFIER;
		@Override public ParserRule getRule() { return rule; }

		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall() { return cIDENTIFIERTerminalRuleCall; }
	}

	public class FUNCTIONAL_SYMBOLElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.FUNCTIONAL_SYMBOL");
		private final RuleCall cIDENTIFIERTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//FUNCTIONAL_SYMBOL:
		//	IDENTIFIER;
		@Override public ParserRule getRule() { return rule; }

		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall() { return cIDENTIFIERTerminalRuleCall; }
	}
	
	
	private final OutputElements pOutput;
	private final TerminalRule tNUMBER;
	private final TerminalRule tIDENTIFIER;
	private final TerminalRule tSATISFIABLE;
	private final TerminalRule tUNSATISFIABLE;
	private final LineElements pLine;
	private final ClassicLiteralElements pClassicLiteral;
	private final AtomElements pAtom;
	private final PREDICATE_NAMEElements pPREDICATE_NAME;
	private final TermsElements pTerms;
	private final TermElements pTerm;
	private final CONSTANT_SYMBOLElements pCONSTANT_SYMBOL;
	private final FUNCTIONAL_SYMBOLElements pFUNCTIONAL_SYMBOL;
	
	private final Grammar grammar;

	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public AnalysisDSLGrammarAccess(GrammarProvider grammarProvider,
		TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pOutput = new OutputElements();
		this.tNUMBER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.NUMBER");
		this.tIDENTIFIER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.IDENTIFIER");
		this.tSATISFIABLE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.SATISFIABLE");
		this.tUNSATISFIABLE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "net.certware.argument.analysis.AnalysisDSL.UNSATISFIABLE");
		this.pLine = new LineElements();
		this.pClassicLiteral = new ClassicLiteralElements();
		this.pAtom = new AtomElements();
		this.pPREDICATE_NAME = new PREDICATE_NAMEElements();
		this.pTerms = new TermsElements();
		this.pTerm = new TermElements();
		this.pCONSTANT_SYMBOL = new CONSTANT_SYMBOLElements();
		this.pFUNCTIONAL_SYMBOL = new FUNCTIONAL_SYMBOLElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("net.certware.argument.analysis.AnalysisDSL".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	

	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//// first rule must be a parser rule
	//// an output file consists of some number of lines followed by SATISFIABLE or UNSATISFIABLE keyword
	//// we assume the output file is scanned as ASCII text but it need not be stored this way
	//Output:
	//	lines+=Line* (SATISFIABLE | UNSATISFIABLE);
	public OutputElements getOutputAccess() {
		return pOutput;
	}
	
	public ParserRule getOutputRule() {
		return getOutputAccess().getRule();
	}

	//terminal NUMBER:
	//	'0' | '1'..'9' '0'..'9'*;
	public TerminalRule getNUMBERRule() {
		return tNUMBER;
	} 

	//terminal IDENTIFIER:
	//	'a'..'z' ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDENTIFIERRule() {
		return tIDENTIFIER;
	} 

	//terminal SATISFIABLE:
	//	'SATISFIABLE';
	public TerminalRule getSATISFIABLERule() {
		return tSATISFIABLE;
	} 

	//terminal UNSATISFIABLE:
	//	'UNSATISFIABLE';
	public TerminalRule getUNSATISFIABLERule() {
		return tUNSATISFIABLE;
	} 

	//// more parser rules
	//// a line contains one or more classic literals
	//Line:
	//	items+=ClassicLiteral+;
	public LineElements getLineAccess() {
		return pLine;
	}
	
	public ParserRule getLineRule() {
		return getLineAccess().getRule();
	}

	//// a classic literal is an atom with an optional negation sign
	//ClassicLiteral:
	//	neg='-'? atom=Atom;
	public ClassicLiteralElements getClassicLiteralAccess() {
		return pClassicLiteral;
	}
	
	public ParserRule getClassicLiteralRule() {
		return getClassicLiteralAccess().getRule();
	}

	//// an atom is a predicate name with an optional terms expression in parentheses	
	//Atom:
	//	name=PREDICATE_NAME ('(' terms=Terms? ')')?;
	public AtomElements getAtomAccess() {
		return pAtom;
	}
	
	public ParserRule getAtomRule() {
		return getAtomAccess().getRule();
	}

	//PREDICATE_NAME:
	//	IDENTIFIER;
	public PREDICATE_NAMEElements getPREDICATE_NAMEAccess() {
		return pPREDICATE_NAME;
	}
	
	public ParserRule getPREDICATE_NAMERule() {
		return getPREDICATE_NAMEAccess().getRule();
	}

	//// a terms expression is one or more terms separated by commas
	//Terms:
	//	car=Term (',' cdr+=Term)*;
	public TermsElements getTermsAccess() {
		return pTerms;
	}
	
	public ParserRule getTermsRule() {
		return getTermsAccess().getRule();
	}

	//// a term is a constant symbol, a number with optional sign, or 
	//// a functional symbol with optional terms in parentheses
	//Term:
	//	cs=CONSTANT_SYMBOL | sign='-'? n=NUMBER | fs=FUNCTIONAL_SYMBOL '(' terms=Terms ')';
	public TermElements getTermAccess() {
		return pTerm;
	}
	
	public ParserRule getTermRule() {
		return getTermAccess().getRule();
	}

	//CONSTANT_SYMBOL:
	//	IDENTIFIER;
	public CONSTANT_SYMBOLElements getCONSTANT_SYMBOLAccess() {
		return pCONSTANT_SYMBOL;
	}
	
	public ParserRule getCONSTANT_SYMBOLRule() {
		return getCONSTANT_SYMBOLAccess().getRule();
	}

	//FUNCTIONAL_SYMBOL:
	//	IDENTIFIER;
	public FUNCTIONAL_SYMBOLElements getFUNCTIONAL_SYMBOLAccess() {
		return pFUNCTIONAL_SYMBOL;
	}
	
	public ParserRule getFUNCTIONAL_SYMBOLRule() {
		return getFUNCTIONAL_SYMBOLAccess().getRule();
	}

	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	} 

	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	} 

	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' |
	//	"'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	} 

	//terminal ML_COMMENT:
	//	'/ *'->'* /';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	} 

	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	} 

	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	} 

	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	} 
}

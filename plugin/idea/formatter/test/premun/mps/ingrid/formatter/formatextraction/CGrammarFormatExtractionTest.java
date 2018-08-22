package premun.mps.ingrid.formatter.formatextraction;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.FormatInfoMapToDSLConvertor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.Collections;

import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;

/**
 * Format extraction of C language
 *
 * @author dkozak
 */
public class CGrammarFormatExtractionTest {

    @Test
    public void bt() {
        String cGrammar = TestGrammars.loadResource("/C.g4");
        String bt = TestGrammars.loadResource("/c/bt.c");
        GrammarParser grammarParser = new GrammarParser("compilationUnit");
        grammarParser.parseString(cGrammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        GrammarInfo withFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, cGrammar, Collections.singletonList(bt));

        FormatInfoMapToDSLConvertor.print(withFormatting);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("primaryExpression", 0,
                                handle(
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("primaryExpression", 1,
                                handle(
                                        element("Constant", newLine(false), space(true))
                                )
                        ),
                        rule("primaryExpression", 2,
                                handle(
                                        collection("StringLiteral", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("primaryExpression", 3,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("primaryExpression", 4,
                                handle(
                                        element("genericSelection", newLine(false), space(true))
                                )
                        ),
                        rule("genericAssocList", 0,
                                handle(
                                        element("genericAssociation", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression", 0,
                                handle(
                                        element("primaryExpression", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression", 2,
                                handle(
                                        element("postfixExpression", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("argumentExpressionList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression", 4,
                                handle(
                                        element("postfixExpression", newLine(false), space(false)),
                                        element("->", newLine(false), space(false)),
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression", 5,
                                handle(
                                        element("postfixExpression", newLine(false), space(false)),
                                        element("++", newLine(false), space(true))
                                )
                        ),
                        rule("argumentExpressionList", 0,
                                handle(
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("argumentExpressionList", 1,
                                handle(
                                        element("argumentExpressionList", newLine(false), space(false)),
                                        element(",", newLine(false), space(true)),
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpression", 0,
                                handle(
                                        element("postfixExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpression", 3,
                                handle(
                                        element("unaryOperator", newLine(false), space(true)),
                                        element("castExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpression", 4,
                                handle(
                                        element("sizeof", newLine(false), space(false)),
                                        element("unaryExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryOperator", 0,
                                handle(
                                        element("&", newLine(false), space(true))
                                )
                        ),
                        rule("unaryOperator", 1,
                                handle(
                                        element("*", newLine(false), space(true))
                                )
                        ),
                        rule("unaryOperator", 2,
                                handle(
                                        element("+", newLine(false), space(true))
                                )
                        ),
                        rule("unaryOperator", 3,
                                handle(
                                        element("-", newLine(false), space(true))
                                )
                        ),
                        rule("unaryOperator", 4,
                                handle(
                                        element("~", newLine(false), space(true))
                                )
                        ),
                        rule("unaryOperator", 5,
                                handle(
                                        element("!", newLine(false), space(true))
                                )
                        ),
                        rule("castExpression", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("typeName", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("castExpression", newLine(false), space(true))
                                )
                        ),
                        rule("castExpression", 2,
                                handle(
                                        element("unaryExpression", newLine(false), space(true))
                                )
                        ),
                        rule("castExpression", 3,
                                handle(
                                        element("DigitSequence", newLine(false), space(true))
                                )
                        ),
                        rule("multiplicativeExpression", 0,
                                handle(
                                        element("castExpression", newLine(false), space(true))
                                )
                        ),
                        rule("multiplicativeExpression", 1,
                                handle(
                                        element("multiplicativeExpression", newLine(false), space(true)),
                                        element("*", newLine(false), space(true)),
                                        element("castExpression", newLine(false), space(true))
                                )
                        ),
                        rule("additiveExpression", 0,
                                handle(
                                        element("multiplicativeExpression", newLine(false), space(true))
                                )
                        ),
                        rule("shiftExpression", 0,
                                handle(
                                        element("additiveExpression", newLine(false), space(true))
                                )
                        ),
                        rule("relationalExpression", 0,
                                handle(
                                        element("shiftExpression", newLine(false), space(true))
                                )
                        ),
                        rule("relationalExpression", 1,
                                handle(
                                        element("relationalExpression", newLine(false), space(false)),
                                        element("<", newLine(false), space(false)),
                                        element("shiftExpression", newLine(false), space(true))
                                )
                        ),
                        rule("relationalExpression", 2,
                                handle(
                                        element("relationalExpression", newLine(false), space(false)),
                                        element(">", newLine(false), space(false)),
                                        element("shiftExpression", newLine(false), space(true))
                                )
                        ),
                        rule("relationalExpression", 3,
                                handle(
                                        element("relationalExpression", newLine(false), space(false)),
                                        element("<=", newLine(false), space(false)),
                                        element("shiftExpression", newLine(false), space(true))
                                )
                        ),
                        rule("equalityExpression", 0,
                                handle(
                                        element("relationalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("andExpression", 0,
                                handle(
                                        element("equalityExpression", newLine(false), space(true))
                                )
                        ),
                        rule("exclusiveOrExpression", 0,
                                handle(
                                        element("andExpression", newLine(false), space(true))
                                )
                        ),
                        rule("inclusiveOrExpression", 0,
                                handle(
                                        element("exclusiveOrExpression", newLine(false), space(true))
                                )
                        ),
                        rule("logicalAndExpression", 0,
                                handle(
                                        element("inclusiveOrExpression", newLine(false), space(true))
                                )
                        ),
                        rule("logicalOrExpression", 0,
                                handle(
                                        element("logicalAndExpression", newLine(false), space(true))
                                )
                        ),
                        rule("conditionalExpression", 0,
                                handle(
                                        element("logicalOrExpression", newLine(false), space(false)),
                                        element("conditionalExpression_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentExpression", 0,
                                handle(
                                        element("conditionalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentExpression", 1,
                                handle(
                                        element("unaryExpression", newLine(false), space(true)),
                                        element("assignmentOperator", newLine(false), space(true)),
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentExpression", 2,
                                handle(
                                        element("DigitSequence", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 0,
                                handle(
                                        element("=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 1,
                                handle(
                                        element("*=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 2,
                                handle(
                                        element("/=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 3,
                                handle(
                                        element("%=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 4,
                                handle(
                                        element("+=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 5,
                                handle(
                                        element("-=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 6,
                                handle(
                                        element("<<=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 7,
                                handle(
                                        element(">>=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 8,
                                handle(
                                        element("&=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 9,
                                handle(
                                        element("^=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 10,
                                handle(
                                        element("|=", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 1,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        element(",", newLine(false), space(true)),
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("constantExpression", 0,
                                handle(
                                        element("conditionalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("declaration", 0,
                                handle(
                                        element("declarationSpecifiers", newLine(false), space(true)),
                                        element("initDeclaratorList", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("declaration", 1,
                                handle(
                                        element("declarationSpecifiers", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("declaration", 2,
                                handle(
                                        element("staticAssertDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("declarationSpecifiers", 0,
                                handle(
                                        collection("declarationSpecifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("declarationSpecifiers2", 0,
                                handle(
                                        collection("declarationSpecifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("declarationSpecifier", 0,
                                handle(
                                        element("storageClassSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("declarationSpecifier", 1,
                                handle(
                                        element("typeSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("declarationSpecifier", 2,
                                handle(
                                        element("typeQualifier", newLine(false), space(true))
                                )
                        ),
                        rule("declarationSpecifier", 3,
                                handle(
                                        element("functionSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("declarationSpecifier", 4,
                                handle(
                                        element("alignmentSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("initDeclaratorList", 0,
                                handle(
                                        element("initDeclarator", newLine(false), space(true))
                                )
                        ),
                        rule("initDeclarator", 0,
                                handle(
                                        element("declarator", newLine(false), space(true))
                                )
                        ),
                        rule("storageClassSpecifier", 0,
                                handle(
                                        element("typedef", newLine(false), space(true))
                                )
                        ),
                        rule("storageClassSpecifier", 1,
                                handle(
                                        element("extern", newLine(false), space(true))
                                )
                        ),
                        rule("storageClassSpecifier", 2,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("storageClassSpecifier", 3,
                                handle(
                                        element("_Thread_local", newLine(false), space(true))
                                )
                        ),
                        rule("storageClassSpecifier", 4,
                                handle(
                                        element("auto", newLine(false), space(true))
                                )
                        ),
                        rule("storageClassSpecifier", 5,
                                handle(
                                        element("register", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier", 0,
                                handle(
                                        element("typeSpecifier_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier", 2,
                                handle(
                                        element("atomicTypeSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier", 3,
                                handle(
                                        element("structOrUnionSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier", 4,
                                handle(
                                        element("enumSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier", 5,
                                handle(
                                        element("typedefName", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier", 7,
                                handle(
                                        element("typeSpecifier", newLine(false), space(true)),
                                        element("pointer", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 0,
                                handle(
                                        element("void", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 1,
                                handle(
                                        element("char", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 2,
                                handle(
                                        element("short", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 3,
                                handle(
                                        element("int", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 4,
                                handle(
                                        element("long", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 5,
                                handle(
                                        element("float", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 6,
                                handle(
                                        element("double", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 7,
                                handle(
                                        element("signed", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 8,
                                handle(
                                        element("unsigned", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 9,
                                handle(
                                        element("_Bool", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 10,
                                handle(
                                        element("_Complex", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 11,
                                handle(
                                        element("__m128", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 12,
                                handle(
                                        element("__m128d", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_1_1", 13,
                                handle(
                                        element("__m128i", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_2_1", 0,
                                handle(
                                        element("__m128", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_2_1", 1,
                                handle(
                                        element("__m128d", newLine(false), space(true))
                                )
                        ),
                        rule("typeSpecifier_block_2_1", 2,
                                handle(
                                        element("__m128i", newLine(false), space(true))
                                )
                        ),
                        rule("structOrUnionSpecifier", 0,
                                handle(
                                        element("structOrUnion", newLine(false), space(true)),
                                        element("Identifier", newLine(false), space(true)),
                                        element("{", newLine(true), space(false)),
                                        element("structDeclarationList", newLine(true), space(false)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("structOrUnionSpecifier", 1,
                                handle(
                                        element("structOrUnion", newLine(false), space(true)),
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("structOrUnion", 0,
                                handle(
                                        element("struct", newLine(false), space(true))
                                )
                        ),
                        rule("structOrUnion", 1,
                                handle(
                                        element("union", newLine(false), space(true))
                                )
                        ),
                        rule("structDeclarationList", 0,
                                handle(
                                        element("structDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("structDeclarationList", 1,
                                handle(
                                        element("structDeclarationList", newLine(true), space(false)),
                                        element("structDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("structDeclaration", 0,
                                handle(
                                        element("specifierQualifierList", newLine(false), space(true)),
                                        element("structDeclaratorList", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("structDeclaration", 1,
                                handle(
                                        element("staticAssertDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("specifierQualifierList", 0,
                                handle(
                                        element("typeSpecifier", newLine(false), space(true)),
                                        element("specifierQualifierList", newLine(false), space(true))
                                )
                        ),
                        rule("structDeclaratorList", 0,
                                handle(
                                        element("structDeclarator", newLine(false), space(true))
                                )
                        ),
                        rule("structDeclaratorList", 1,
                                handle(
                                        element("structDeclaratorList", newLine(false), space(false)),
                                        element(",", newLine(false), space(true)),
                                        element("structDeclarator", newLine(false), space(true))
                                )
                        ),
                        rule("structDeclarator", 0,
                                handle(
                                        element("declarator", newLine(false), space(true))
                                )
                        ),
                        rule("enumeratorList", 0,
                                handle(
                                        element("enumerator", newLine(false), space(true))
                                )
                        ),
                        rule("enumerator", 0,
                                handle(
                                        element("enumerationConstant", newLine(false), space(true))
                                )
                        ),
                        rule("enumerationConstant", 0,
                                handle(
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("typeQualifier", 0,
                                handle(
                                        element("const", newLine(false), space(true))
                                )
                        ),
                        rule("typeQualifier", 1,
                                handle(
                                        element("restrict", newLine(false), space(true))
                                )
                        ),
                        rule("typeQualifier", 2,
                                handle(
                                        element("volatile", newLine(false), space(true))
                                )
                        ),
                        rule("typeQualifier", 3,
                                handle(
                                        element("_Atomic", newLine(false), space(true))
                                )
                        ),
                        rule("functionSpecifier", 0,
                                handle(
                                        element("functionSpecifier_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("functionSpecifier", 1,
                                handle(
                                        element("gccAttributeSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("functionSpecifier_block_1_1", 0,
                                handle(
                                        element("inline", newLine(false), space(true))
                                )
                        ),
                        rule("functionSpecifier_block_1_1", 1,
                                handle(
                                        element("_Noreturn", newLine(false), space(true))
                                )
                        ),
                        rule("functionSpecifier_block_1_1", 2,
                                handle(
                                        element("__inline__", newLine(false), space(true))
                                )
                        ),
                        rule("functionSpecifier_block_1_1", 3,
                                handle(
                                        element("__stdcall", newLine(false), space(true))
                                )
                        ),
                        rule("declarator", 0,
                                handle(
                                        element("pointer", newLine(false), space(true)),
                                        element("directDeclarator", newLine(false), space(false)),
                                        collection("gccDeclaratorExtension", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("directDeclarator", 0,
                                handle(
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("directDeclarator", 6,
                                handle(
                                        element("directDeclarator", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameterTypeList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("directDeclarator", 7,
                                handle(
                                        element("directDeclarator", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("identifierList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("directDeclarator", 9,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("typeSpecifier", newLine(false), space(true)),
                                        element("pointer", newLine(false), space(true)),
                                        element("directDeclarator", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("gccDeclaratorExtension", 1,
                                handle(
                                        element("gccAttributeSpecifier", newLine(false), space(true))
                                )
                        ),
                        rule("gccAttributeList", 1,
                                handle(

                                )
                        ),
                        rule("gccAttribute", 0,
                                handle(
                                        element("gccAttribute_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("gccAttribute", 1,
                                handle(

                                )
                        ),
                        rule("nestedParenthesesBlock", 0,
                                handle(
                                        collection("nestedParenthesesBlock_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("nestedParenthesesBlock_block_1_1", 0,
                                handle(

                                )
                        ),
                        rule("pointer", 0,
                                handle(
                                        element("*", newLine(false), space(false)),
                                        element("typeQualifierList", newLine(false), space(true))
                                )
                        ),
                        rule("typeQualifierList", 0,
                                handle(
                                        element("typeQualifier", newLine(false), space(true))
                                )
                        ),
                        rule("parameterTypeList", 0,
                                handle(
                                        element("parameterList", newLine(false), space(true))
                                )
                        ),
                        rule("parameterList", 0,
                                handle(
                                        element("parameterDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("parameterList", 1,
                                handle(
                                        element("parameterList", newLine(false), space(false)),
                                        element(",", newLine(false), space(true)),
                                        element("parameterDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("parameterDeclaration", 0,
                                handle(
                                        element("declarationSpecifiers", newLine(false), space(true)),
                                        element("declarator", newLine(false), space(true))
                                )
                        ),
                        rule("identifierList", 0,
                                handle(
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("typeName", 0,
                                handle(
                                        element("specifierQualifierList", newLine(false), space(false)),
                                        element("abstractDeclarator", newLine(false), space(true))
                                )
                        ),
                        rule("abstractDeclarator", 0,
                                handle(
                                        element("pointer", newLine(false), space(true))
                                )
                        ),
                        rule("typedefName", 0,
                                handle(
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("initializer", 0,
                                handle(
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("designatorList", 0,
                                handle(
                                        element("designator", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("labeledStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 1,
                                handle(
                                        element("compoundStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("expressionStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("selectionStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("iterationStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 5,
                                handle(
                                        element("jumpStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement_block_7_1", 0,
                                handle(
                                        element("__asm", newLine(false), space(true))
                                )
                        ),
                        rule("statement_block_7_1", 1,
                                handle(
                                        element("__asm__", newLine(false), space(true))
                                )
                        ),
                        rule("statement_block_7_2", 0,
                                handle(
                                        element("volatile", newLine(false), space(true))
                                )
                        ),
                        rule("statement_block_7_2", 1,
                                handle(
                                        element("__volatile__", newLine(false), space(true))
                                )
                        ),
                        rule("compoundStatement", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        element("blockItemList", newLine(true), space(false)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("blockItemList", 0,
                                handle(
                                        element("blockItem", newLine(false), space(true))
                                )
                        ),
                        rule("blockItemList", 1,
                                handle(
                                        element("blockItemList", newLine(true), space(false)),
                                        element("blockItem", newLine(false), space(true))
                                )
                        ),
                        rule("blockItem", 0,
                                handle(
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("blockItem", 1,
                                handle(
                                        element("declaration", newLine(false), space(true))
                                )
                        ),
                        rule("expressionStatement", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("selectionStatement", 0,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(true), space(true)),
                                        element("statement", newLine(true), space(false)),
                                        element("selectionStatement_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("selectionStatement_block_1_1", 0,
                                handle(
                                        element("else", newLine(false), space(true)),
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("iterationStatement", 2,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("forCondition", newLine(false), space(false)),
                                        element(")", newLine(false), space(true)),
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("forCondition", 1,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(false)),
                                        element("forExpression", newLine(false), space(false)),
                                        element(";", newLine(false), space(false)),
                                        element("forExpression", newLine(false), space(true))
                                )
                        ),
                        rule("forDeclaration", 1,
                                handle(
                                        element("declarationSpecifiers", newLine(false), space(true))
                                )
                        ),
                        rule("forExpression", 0,
                                handle(
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("jumpStatement", 3,
                                handle(
                                        element("return", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("compilationUnit", 0,
                                handle(
                                        element("translationUnit", newLine(false), space(true))
                                )
                        ),
                        rule("translationUnit", 0,
                                handle(
                                        element("externalDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("translationUnit", 1,
                                handle(
                                        element("translationUnit", newLine(true), space(false)),
                                        element("externalDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("externalDeclaration", 0,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("externalDeclaration", 1,
                                handle(
                                        element("declaration", newLine(false), space(true))
                                )
                        ),
                        rule("externalDeclaration", 2,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("declarationSpecifiers", newLine(false), space(true)),
                                        element("declarator", newLine(false), space(false)),
                                        element("declarationList", newLine(false), space(false)),
                                        element("compoundStatement", newLine(false), space(true))
                                )
                        ),
                        rule("declarationList", 0,
                                handle(
                                        element("declaration", newLine(false), space(true))
                                )
                        )
                )
        );
    }

}

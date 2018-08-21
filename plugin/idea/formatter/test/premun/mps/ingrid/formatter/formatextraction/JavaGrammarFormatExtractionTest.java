package premun.mps.ingrid.formatter.formatextraction;


import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.FormatInfoMapToDSLConvertor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.Collections;
import java.util.List;

import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;

/**
 * These tests are specific from the others, because they process a grammar,
 * whose lexer and grammar parts are in two separated files.
 *
 * @author dkozak
 */
public class JavaGrammarFormatExtractionTest {
    @Test
    public void animalClass() {
        List<String> grammars = TestGrammars.loadJava();

        String input = "package foo;\nimport java.util.*;\n" +
                "\n" +
                "\n" +
                "import static java.util.stream.Collectors.toList;\n" +
                "\n" +
                "\n" +
                "/**\n" +
                " * Computes how the parse tree passed in corresponds to the Ingrid rule that matched it.\n" +
                " * <p>\n" +
                " * The algorithm works as follows.\n" +
                " * <p>\n" +
                " * It first have to expand the alternatives into separate rules.\n" +
                " * If it encounters a block rule, it also separates each of it's alternatives into rules and\n" +
                " * it adds a special SerializedParserRule  as the wrapper of the content of the block rule\n" +
                " * to clearly separate it from other kind of rules. If there are any inner blocks such as\n" +
                " * (a | (b | c)), this algorithm generates two SerializedParserRules on the route from root to b or c.\n" +
                " * As this is not necessary, a flattening happens afterwards that removes these unnecessary layers.\n" +
                " * <p>\n" +
                " * When the rule in expanded, it uses the following algorithm to figure out which of the\n" +
                " * alternatives matches the ast.\n" +
                " * Foreach ruleReference in rule.handle:\n" +
                " * consume as much of the input ast as possible\n" +
                " * if you did not manage to consume token/rule that was obligatory:\n" +
                " * return error\n" +
                " * save information about what you matched\n" +
                " * <p>\n" +
                " * if whole ast was matched:\n" +
                " * return information about matching\n" +
                " * else:\n" +
                " * return error\n" +
                " *\n" +
                " * @author dkozak\n" +
                " */\n" +
                "public class Animal {\n" +
                "\n" +
                "    private final String name;\n" +
                "    \n" +
                "    public static int age;\n" +
                "    \n" +
                "\n" +
                "    public Animal(String name){\n" +
                "        this.name = name;\n" +
                "    }\n" +
                "    \n" +
                "    public int count(){\n" +
                "        if(age == 0){\n" +
                "            return -1;\n" +
                "        }\n" +
                "        for(int i = 1 ; i <= age; i++){\n" +
                "            System.out.println(i);\n" +
                "        }\n" +
                "        return age + 1;\n" +
                "    }\n" +
                "    \n" +
                "    public static void main(String[] args){\n" +
                "        Animal animal = new Animal(\"Bobik\");\n" +
                "        Animal.age = 42;\n" +
                "        animal.count();\n" +
                "    }\n" +
                "}\n";


        GrammarParser grammarParser = new GrammarParser();
        for (String grammar : grammars) {
            grammarParser.parseString(grammar);
        }
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();


        GrammarInfo withFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammars.get(0), grammars.get(1), Collections.singletonList(input));

        FormatInfoMapToDSLConvertor.print(withFormatting);

        verifyFormatInfo(
                withFormatting,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("packageDeclaration", newLine(true), space(false)),
                                        collection("importDeclaration", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null)),
                                        collection("typeDeclaration", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("packageDeclaration", 0,
                                handle(
                                        collection("annotation", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("package", newLine(false), space(true)),
                                        element("qualifiedName", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("importDeclaration", 0,
                                handle(
                                        element("import", newLine(false), space(true)),
                                        element("static", newLine(false), space(true)),
                                        element("qualifiedName", newLine(false), space(false)),
                                        element("importDeclaration_block_1_1", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("importDeclaration_block_1_1", 0,
                                handle(
                                        element(".", newLine(false), space(false)),
                                        element("*", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration", 0,
                                handle(
                                        collection("classOrInterfaceModifier", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null)),
                                        element("typeDeclaration_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration", 1,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration_block_1_1", 0,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration_block_1_1", 1,
                                handle(
                                        element("enumDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration_block_1_1", 2,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration_block_1_1", 3,
                                handle(
                                        element("annotationTypeDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("modifier", 0,
                                handle(
                                        element("classOrInterfaceModifier", newLine(false), space(true))
                                )
                        ),
                        rule("modifier", 1,
                                handle(
                                        element("native", newLine(false), space(true))
                                )
                        ),
                        rule("modifier", 2,
                                handle(
                                        element("synchronized", newLine(false), space(true))
                                )
                        ),
                        rule("modifier", 3,
                                handle(
                                        element("transient", newLine(false), space(true))
                                )
                        ),
                        rule("modifier", 4,
                                handle(
                                        element("volatile", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 2,
                                handle(
                                        element("protected", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 3,
                                handle(
                                        element("private", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 4,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 5,
                                handle(
                                        element("abstract", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 6,
                                handle(
                                        element("final", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceModifier", 7,
                                handle(
                                        element("strictfp", newLine(false), space(true))
                                )
                        ),
                        rule("variableModifier", 0,
                                handle(
                                        element("final", newLine(false), space(true))
                                )
                        ),
                        rule("variableModifier", 1,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("classDeclaration", 0,
                                handle(
                                        element("class", newLine(false), space(true)),
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("typeParameters", newLine(false), space(false)),
                                        element("classDeclaration_block_1_1", newLine(false), space(false)),
                                        element("classDeclaration_block_1_2", newLine(false), space(false)),
                                        element("classBody", newLine(false), space(true))
                                )
                        ),
                        rule("classBody", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("classBodyDeclaration", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("classBodyDeclaration", 0,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("classBodyDeclaration", 2,
                                handle(
                                        collection("modifier", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("memberDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 0,
                                handle(
                                        element("methodDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 1,
                                handle(
                                        element("genericMethodDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 2,
                                handle(
                                        element("fieldDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 3,
                                handle(
                                        element("constructorDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 4,
                                handle(
                                        element("genericConstructorDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 5,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 6,
                                handle(
                                        element("annotationTypeDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 7,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("memberDeclaration", 8,
                                handle(
                                        element("enumDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("methodDeclaration", 0,
                                handle(
                                        element("typeTypeOrVoid", newLine(false), space(true)),
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("formalParameters", newLine(false), space(false)),
                                        collection("methodDeclaration_block_1_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("methodDeclaration_block_1_2", newLine(false), space(false)),
                                        element("methodBody", newLine(false), space(true))
                                )
                        ),
                        rule("methodBody", 0,
                                handle(
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("methodBody", 1,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("typeTypeOrVoid", 0,
                                handle(
                                        element("typeType", newLine(false), space(true))
                                )
                        ),
                        rule("typeTypeOrVoid", 1,
                                handle(
                                        element("void", newLine(false), space(true))
                                )
                        ),
                        rule("constructorDeclaration", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("formalParameters", newLine(false), space(false)),
                                        element("constructorDeclaration_block_1_1", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("fieldDeclaration", 0,
                                handle(
                                        element("typeType", newLine(false), space(true)),
                                        element("variableDeclarators", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceBodyDeclaration", 1,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 0,
                                handle(
                                        element("constDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 1,
                                handle(
                                        element("interfaceMethodDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 2,
                                handle(
                                        element("genericInterfaceMethodDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 3,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 4,
                                handle(
                                        element("annotationTypeDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 5,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 6,
                                handle(
                                        element("enumDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodDeclaration_block_1_1", 0,
                                handle(
                                        element("typeTypeOrVoid", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 2,
                                handle(
                                        element("abstract", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 3,
                                handle(
                                        element("default", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 4,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 5,
                                handle(
                                        element("strictfp", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclarators", 0,
                                handle(
                                        element("variableDeclarator", newLine(false), space(false)),
                                        collection("variableDeclarators_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("variableDeclarator", 0,
                                handle(
                                        element("variableDeclaratorId", newLine(false), space(true)),
                                        element("variableDeclarator_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclarator_block_1_1", 0,
                                handle(
                                        element("=", newLine(false), space(true)),
                                        element("variableInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclaratorId", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        collection("variableDeclaratorId_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("variableInitializer", 0,
                                handle(
                                        element("arrayInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("variableInitializer", 1,
                                handle(
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("arrayInitializer_block_1_3", 0,
                                handle(
                                        element(",", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceType", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("typeArguments", newLine(false), space(false)),
                                        collection("classOrInterfaceType_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("typeArgument", 0,
                                handle(
                                        element("typeType", newLine(false), space(true))
                                )
                        ),
                        rule("typeArgument_block_2_2", 0,
                                handle(
                                        element("extends", newLine(false), space(true))
                                )
                        ),
                        rule("typeArgument_block_2_2", 1,
                                handle(
                                        element("super", newLine(false), space(true))
                                )
                        ),
                        rule("formalParameters", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("formalParameterList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("formalParameterList", 0,
                                handle(
                                        element("formalParameter", newLine(false), space(false)),
                                        collection("formalParameterList_block_1_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("formalParameterList_block_1_2", newLine(false), space(true))
                                )
                        ),
                        rule("formalParameterList", 1,
                                handle(
                                        element("lastFormalParameter", newLine(false), space(true))
                                )
                        ),
                        rule("formalParameter", 0,
                                handle(
                                        collection("variableModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("typeType", newLine(false), space(true)),
                                        element("variableDeclaratorId", newLine(false), space(true))
                                )
                        ),
                        rule("qualifiedName", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        collection("qualifiedName_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("qualifiedName_block_1_1", 0,
                                handle(
                                        element(".", newLine(false), space(false)),
                                        element("IDENTIFIER", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 0,
                                handle(
                                        element("integerLiteral", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 1,
                                handle(
                                        element("floatLiteral", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 2,
                                handle(
                                        element("CHAR_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 3,
                                handle(
                                        element("STRING_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 4,
                                handle(
                                        element("BOOL_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 5,
                                handle(
                                        element("null", newLine(false), space(true))
                                )
                        ),
                        rule("integerLiteral", 0,
                                handle(
                                        element("DECIMAL_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("integerLiteral", 1,
                                handle(
                                        element("HEX_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("integerLiteral", 2,
                                handle(
                                        element("OCT_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("integerLiteral", 3,
                                handle(
                                        element("BINARY_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("floatLiteral", 0,
                                handle(
                                        element("FLOAT_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("floatLiteral", 1,
                                handle(
                                        element("HEX_FLOAT_LITERAL", newLine(false), space(true))
                                )
                        ),
                        rule("annotation_block_1_2", 0,
                                handle(
                                        element("elementValuePairs", newLine(false), space(true))
                                )
                        ),
                        rule("annotation_block_1_2", 1,
                                handle(
                                        element("elementValue", newLine(false), space(true))
                                )
                        ),
                        rule("elementValue", 0,
                                handle(
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("elementValue", 1,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("elementValue", 2,
                                handle(
                                        element("elementValueArrayInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("elementValueArrayInitializer_block_1_3", 0,
                                handle(
                                        element(",", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeBody_block_1_1", 0,
                                handle(
                                        element("annotationTypeElementDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeElementDeclaration", 1,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("annotationMethodOrConstantRest", 0,
                                handle(
                                        element("annotationMethodRest", newLine(false), space(true))
                                )
                        ),
                        rule("annotationMethodOrConstantRest", 1,
                                handle(
                                        element("annotationConstantRest", newLine(false), space(true))
                                )
                        ),
                        rule("annotationConstantRest", 0,
                                handle(
                                        element("variableDeclarators", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("blockStatement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("blockStatement", 0,
                                handle(
                                        element("localVariableDeclaration", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("blockStatement", 1,
                                handle(
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("blockStatement", 2,
                                handle(
                                        element("localTypeDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("localVariableDeclaration", 0,
                                handle(
                                        collection("variableModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("typeType", newLine(false), space(true)),
                                        element("variableDeclarators", newLine(false), space(true))
                                )
                        ),
                        rule("localTypeDeclaration", 1,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("localTypeDeclaration_block_1_1", 0,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("localTypeDeclaration_block_1_1", 1,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("parExpression", newLine(false), space(false)),
                                        element("statement", newLine(false), space(false)),
                                        element("statement_block_3_1", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("forControl", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 10,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 14,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 15,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement_block_7_1", 1,
                                handle(
                                        element("finallyBlock", newLine(false), space(true))
                                )
                        ),
                        rule("switchLabel_block_1_1", 0,
                                handle(
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("switchLabel_block_1_1", 1,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(true))
                                )
                        ),
                        rule("forControl", 0,
                                handle(
                                        element("enhancedForControl", newLine(false), space(true))
                                )
                        ),
                        rule("forControl", 1,
                                handle(
                                        element("forInit", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expressionList", newLine(false), space(true))
                                )
                        ),
                        rule("forInit", 0,
                                handle(
                                        element("localVariableDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("forInit", 1,
                                handle(
                                        element("expressionList", newLine(false), space(true))
                                )
                        ),
                        rule("parExpression", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("expressionList", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("expressionList_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("methodCall", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expressionList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("primary", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 1,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("expression_block_2_1", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 3,
                                handle(
                                        element("methodCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 4,
                                handle(
                                        element("new", newLine(false), space(true)),
                                        element("creator", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        element("expression_block_7_1", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 7,
                                handle(
                                        element("expression_block_8_1", newLine(false), space(false)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 10,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("expression_block_11_1", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 12,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("expression_block_13_1", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 14,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("expression_block_15_1", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 21,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("expression_block_22_1", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 22,
                                handle(
                                        element("lambdaExpression", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_2_1", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_2_1", 1,
                                handle(
                                        element("methodCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_2_1", 2,
                                handle(
                                        element("this", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_2_1", 5,
                                handle(
                                        element("explicitGenericInvocation", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_7_1", 0,
                                handle(
                                        element("++", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_7_1", 1,
                                handle(
                                        element("--", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_8_1", 0,
                                handle(
                                        element("+", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_8_1", 1,
                                handle(
                                        element("-", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_8_1", 2,
                                handle(
                                        element("++", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_8_1", 3,
                                handle(
                                        element("--", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_9_1", 0,
                                handle(
                                        element("~", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_9_1", 1,
                                handle(
                                        element("!", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_10_1", 0,
                                handle(
                                        element("*", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_10_1", 1,
                                handle(
                                        element("/", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_10_1", 2,
                                handle(
                                        element("%", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_11_1", 0,
                                handle(
                                        element("+", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_11_1", 1,
                                handle(
                                        element("-", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_13_1", 0,
                                handle(
                                        element("<=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_13_1", 1,
                                handle(
                                        element(">=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_13_1", 2,
                                handle(
                                        element(">", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_13_1", 3,
                                handle(
                                        element("<", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_15_1", 0,
                                handle(
                                        element("==", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_15_1", 1,
                                handle(
                                        element("!=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 0,
                                handle(
                                        element("=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 1,
                                handle(
                                        element("+=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 2,
                                handle(
                                        element("-=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 3,
                                handle(
                                        element("*=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 4,
                                handle(
                                        element("/=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 5,
                                handle(
                                        element("&=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 6,
                                handle(
                                        element("|=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 7,
                                handle(
                                        element("^=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 8,
                                handle(
                                        element(">>=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 9,
                                handle(
                                        element(">>>=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 10,
                                handle(
                                        element("<<=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_22_1", 11,
                                handle(
                                        element("%=", newLine(false), space(true))
                                )
                        ),
                        rule("expression_block_25_1", 1,
                                handle(
                                        element("new", newLine(false), space(true))
                                )
                        ),
                        rule("lambdaParameters", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(true))
                                )
                        ),
                        rule("lambdaBody", 0,
                                handle(
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("lambdaBody", 1,
                                handle(
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("primary", 1,
                                handle(
                                        element("this", newLine(false), space(true))
                                )
                        ),
                        rule("primary", 2,
                                handle(
                                        element("super", newLine(false), space(true))
                                )
                        ),
                        rule("primary", 3,
                                handle(
                                        element("literal", newLine(false), space(true))
                                )
                        ),
                        rule("primary", 4,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(true))
                                )
                        ),
                        rule("primary_block_7_1", 0,
                                handle(
                                        element("explicitGenericInvocationSuffix", newLine(false), space(true))
                                )
                        ),
                        rule("creator", 1,
                                handle(
                                        element("createdName", newLine(false), space(false)),
                                        element("creator_block_2_1", newLine(false), space(true))
                                )
                        ),
                        rule("creator_block_2_1", 0,
                                handle(
                                        element("arrayCreatorRest", newLine(false), space(true))
                                )
                        ),
                        rule("creator_block_2_1", 1,
                                handle(
                                        element("classCreatorRest", newLine(false), space(true))
                                )
                        ),
                        rule("createdName", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("typeArgumentsOrDiamond", newLine(false), space(false)),
                                        collection("createdName_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("createdName", 1,
                                handle(
                                        element("primitiveType", newLine(false), space(true))
                                )
                        ),
                        rule("classCreatorRest", 0,
                                handle(
                                        element("arguments", newLine(false), space(false)),
                                        element("classBody", newLine(false), space(true))
                                )
                        ),
                        rule("typeArgumentsOrDiamond", 1,
                                handle(
                                        element("typeArguments", newLine(false), space(true))
                                )
                        ),
                        rule("nonWildcardTypeArgumentsOrDiamond", 1,
                                handle(
                                        element("nonWildcardTypeArguments", newLine(false), space(true))
                                )
                        ),
                        rule("typeType", 0,
                                handle(
                                        element("annotation", newLine(false), space(false)),
                                        element("typeType_block_1_1", newLine(false), space(false)),
                                        collection("typeType_block_1_2", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("typeType_block_1_1", 0,
                                handle(
                                        element("classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("typeType_block_1_1", 1,
                                handle(
                                        element("primitiveType", newLine(false), space(true))
                                )
                        ),
                        rule("typeType_block_1_2", 0,
                                handle(
                                        element("[", newLine(false), space(false)),
                                        element("]", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 0,
                                handle(
                                        element("boolean", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 1,
                                handle(
                                        element("char", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 2,
                                handle(
                                        element("byte", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 3,
                                handle(
                                        element("short", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 4,
                                handle(
                                        element("int", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 5,
                                handle(
                                        element("long", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 6,
                                handle(
                                        element("float", newLine(false), space(true))
                                )
                        ),
                        rule("primitiveType", 7,
                                handle(
                                        element("double", newLine(false), space(true))
                                )
                        ),
                        rule("superSuffix", 0,
                                handle(
                                        element("arguments", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expressionList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );

    }
}

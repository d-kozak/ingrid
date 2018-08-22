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
                                        collection("classOrInterfaceModifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
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
                                        collection("modifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
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

    @Test
    public void javaMoreComplex() {
        String input = "package premun.mps.ingrid.formatter;\n" +
                "\n" +
                "import org.antlr.v4.runtime.CommonToken;\n" +
                "import org.antlr.v4.runtime.CommonTokenStream;\n" +
                "import org.antlr.v4.runtime.ParserRuleContext;\n" +
                "import org.antlr.v4.runtime.Token;\n" +
                "import org.antlr.v4.runtime.tree.ParseTree;\n" +
                "import org.antlr.v4.runtime.tree.TerminalNode;\n" +
                "import org.antlr.v4.runtime.tree.TerminalNodeImpl;\n" +
                "import premun.mps.ingrid.formatter.model.MatchInfo;\n" +
                "import premun.mps.ingrid.model.Quantity;\n" +
                "import premun.mps.ingrid.model.format.SimpleFormatInfo;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Collections;\n" +
                "import java.util.List;\n" +
                "import java.util.function.Function;\n" +
                "import java.util.stream.Collectors;\n" +
                "\n" +
                "/**\n" +
                " * Extracts format information from list of MatchInfo objects\n" +
                " *\n" +
                " * @author dkozak\n" +
                " */\n" +
                "class FormatInfoExtractor {\n" +
                "\n" +
                "    /**\n" +
                "     * Extracts format information from the list of MatchInfo objects\n" +
                "     */\n" +
                "    static List<SimpleFormatInfo> extractFormatInfo(List<MatchInfo> matchInfos, CommonTokenStream allTokens) {\n" +
                "        if (matchInfos.isEmpty())\n" +
                "            return new ArrayList<>();\n" +
                "\n" +
                "        int originalMatchInfoSize = matchInfos.size();\n" +
                "        matchInfos = new ArrayList<>(matchInfos);\n" +
                "        matchInfos.add(createDummyNextTokenMatchInfo(matchInfos, allTokens));\n" +
                "\n" +
                "        List<SimpleFormatInfo> result = new ArrayList<>();\n" +
                "\n" +
                "        for (int i = 0; i < originalMatchInfoSize; i++) {\n" +
                "            MatchInfo currentMatchInfo = matchInfos.get(i);\n" +
                "            MatchInfo nextMatchInfo = matchInfos.get(i + 1);\n" +
                "            if (currentMatchInfo.isNotEmpty() && nextMatchInfo.isNotEmpty()) {\n" +
                "                result.add(extractFormatInfoFor(currentMatchInfo, nextMatchInfo, allTokens, i == 0));\n" +
                "            } else {\n" +
                "                result.add(SimpleFormatInfo.UNKNOWN);\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * @param currentMatchInfo currently analysed matched info\n" +
                "     * @param nextMatchInfo    next match info\n" +
                "     * @param isFirst specifies whether this is the first matchInfo from the matched region, used children newline detection\n" +
                "     * @return formatInfo extracted from the input\n" +
                "     */\n" +
                "    private static SimpleFormatInfo extractFormatInfoFor(MatchInfo currentMatchInfo, MatchInfo nextMatchInfo, CommonTokenStream tokens, boolean isFirst) {\n" +
                "        ParseTree rightmostNode = currentMatchInfo.getRightMostParseTree();\n" +
                "        ParseTree leftmostNode = nextMatchInfo.getLeftmostParseTree();\n" +
                "        Token currentToken = extractRightmostToken(rightmostNode);\n" +
                "        Token nextToken = extractLeftmostToken(leftmostNode);\n" +
                "\n" +
                "        boolean appendedNewLine = nextToken.getLine() - currentToken.getLine() > 0;\n" +
                "        boolean appendSpace = !appendedNewLine && (nextToken.getCharPositionInLine() - (currentToken.getCharPositionInLine() + currentToken.getText()\n" +
                "                                                                                                                                           .length())) > 0;\n" +
                "\n" +
                "        boolean childrenOnNewLine = false;\n" +
                "        boolean childrenIndented = false;\n" +
                "\n" +
                "        boolean isMultipleCardinality = (currentMatchInfo.quantity == Quantity.AT_LEAST_ONE || currentMatchInfo.quantity == Quantity.ANY) && currentMatchInfo.times() > 0;\n" +
                "        if (isMultipleCardinality) {\n" +
                "            childrenOnNewLine = checkIfChildrenAreOnNewLine(currentMatchInfo, tokens, isFirst);\n" +
                "\n" +
                "\n" +
                "            childrenIndented = checkIfChildrenAreIndented(currentMatchInfo, childrenOnNewLine, tokens);\n" +
                "        }\n" +
                "        return new SimpleFormatInfo(appendedNewLine, appendSpace, childrenOnNewLine, childrenIndented);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    private static boolean checkIfChildrenAreOnNewLine(MatchInfo matchInfo, CommonTokenStream tokenStream, boolean isFirst) {\n" +
                "        //  one match only is an edge case\n" +
                "        if (matchInfo.matched.size() == 1) {\n" +
                "            if (isFirst) {\n" +
                "                // in this case we cannot check for newlines, because they could simply be newlines added by rules before this one\n" +
                "                return false;\n" +
                "            }\n" +
                "            List<Token> tokens = extractTokens(matchInfo.getMatchedRegion());\n" +
                "            // all we can check is whether there in newline before the first element of this region\n" +
                "            boolean newlineBeforeElement = false;\n" +
                "            Token firstToken = tokens.get(0);\n" +
                "            if (firstToken.getTokenIndex() > 0) {\n" +
                "                Token previousToken = tokenStream.get(firstToken.getTokenIndex() - 1);\n" +
                "                newlineBeforeElement = previousToken.getLine() < firstToken.getLine();\n" +
                "            }\n" +
                "            return newlineBeforeElement;\n" +
                "        }\n" +
                "\n" +
                "        // skip the last element, the formatting of the last token after the matched region is not a reliable source of information\n" +
                "        // so we handle one match separately\n" +
                "        List<Boolean> collect = matchInfo.matched.subList(0, matchInfo.matched.size() - 1)\n" +
                "                                                 .stream()\n" +
                "                                                 .map(\n" +
                "                                                         list -> isNewlineAtTheEnd(tokenStream, list)\n" +
                "                                                 )\n" +
                "                                                 .collect(Collectors.toList());\n" +
                "\n" +
                "        boolean allAreTrue = collect.stream()\n" +
                "                                    .allMatch(it -> it);\n" +
                "        if (allAreTrue)\n" +
                "            return true;\n" +
                "        else {\n" +
                "            boolean allAreFalse = collect.stream()\n" +
                "                                         .noneMatch(it -> it);\n" +
                "            if (allAreFalse)\n" +
                "                return false;\n" +
                "            else {\n" +
                "                throw new IllegalArgumentException(\"Inconsistent formatting\");\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Checks whether there is a newline at the end of the tokens from given part of parse tree\n" +
                "     *\n" +
                "     * @param tokenStream stream of all tokens\n" +
                "     * @param list        parse tree which is being examined\n" +
                "     * @return true if there is a newline after the last token in the parse tree\n" +
                "     */\n" +
                "    private static Boolean isNewlineAtTheEnd(CommonTokenStream tokenStream, List<ParseTree> list) {\n" +
                "        Token currentToken = extractRightmostToken(list.get(list.size() - 1));\n" +
                "        for (int i = currentToken.getTokenIndex() + 1; i < tokenStream.size(); i++) {\n" +
                "            Token token = tokenStream.get(i);\n" +
                "            boolean isNormalToken = token.getChannel() == 0;\n" +
                "            if (isNormalToken)\n" +
                "                return currentToken.getLine() < token.getLine();\n" +
                "        }\n" +
                "        return false;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * We can check for indentation by comparing all tokens from the matched region with\n" +
                "     * the leftmost token from previous line. If their position on line is bigger, then they have to be indented\n" +
                "     */\n" +
                "    private static boolean checkIfChildrenAreIndented(MatchInfo currentMatchInfo, boolean childrenOnNewLine, CommonTokenStream tokens) {\n" +
                "        if (!childrenOnNewLine)\n" +
                "            return false;\n" +
                "        Token leftmostTokenInThisRegion = extractLeftmostToken(currentMatchInfo.getLeftmostParseTree());\n" +
                "        Token leftmostTokenOnPreviousLine = getLeftmostTokenOnLine(leftmostTokenInThisRegion.getTokenIndex(), leftmostTokenInThisRegion.getLine() - 1, tokens);\n" +
                "        return extractTokens(currentMatchInfo.getMatchedRegion()).stream()\n" +
                "                                                                 .allMatch(it -> it.getCharPositionInLine() > leftmostTokenOnPreviousLine.getCharPositionInLine());\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Gets the leftmost token from token stream on specified line, starting traversing the list from index startTokenIndex.\n" +
                "     *\n" +
                "     * @param startTokenIndex index from which to start traversing the list.\n" +
                "     * @param wantedLine      line on which the token should be\n" +
                "     * @param tokens          antlr4 stream of tokens to traverse\n" +
                "     * @return the leftmost token on specified line, or dummy token with line position zero\n" +
                "     */\n" +
                "    private static Token getLeftmostTokenOnLine(int startTokenIndex, int wantedLine, CommonTokenStream tokens) {\n" +
                "        int index = startTokenIndex - 1;\n" +
                "        Token right = tokens.get(startTokenIndex);\n" +
                "        while (index >= 0) {\n" +
                "            Token left = tokens.get(index);\n" +
                "            if (left.getLine() < right.getLine() && right.getLine() == wantedLine)\n" +
                "                return right;\n" +
                "            right = left;\n" +
                "            index--;\n" +
                "        }\n" +
                "        // no token found, just return dummy one\n" +
                "        CommonToken dummy = new CommonToken(-1);\n" +
                "        dummy.setCharPositionInLine(0);\n" +
                "        return dummy;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Creates a mathInfo containing the token that follows immediately after the last token in the matched region.\n" +
                "     * It is used extract the formatting of the last element in the matched region with respect to the first element after it\n" +
                "     */\n" +
                "    private static MatchInfo createDummyNextTokenMatchInfo(List<MatchInfo> matchInfos, CommonTokenStream tokens) {\n" +
                "        for (int i = matchInfos.size() - 1; i >= 0; i--) {\n" +
                "            MatchInfo matchInfo = matchInfos.get(i);\n" +
                "            if (matchInfo.isNotEmpty()) {\n" +
                "                Token rightmostToken = extractRightmostToken(matchInfo.getRightMostParseTree());\n" +
                "                CommonToken dummyToken = new CommonToken(rightmostToken);\n" +
                "                dummyToken.setCharPositionInLine(rightmostToken.getCharPositionInLine() + rightmostToken.getText()\n" +
                "                                                                                                        .length() + 1);\n" +
                "                return new MatchInfo(null, null, Collections.singletonList(Collections.singletonList(new TerminalNodeImpl(dummyToken))));\n" +
                "            }\n" +
                "        }\n" +
                "        return new MatchInfo(null, null, Collections.emptyList());\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * @param nodes list of AST nodes to traverse\n" +
                "     * @return list of all Tokens in the order in which they occur in the input\n" +
                "     */\n" +
                "    private static List<Token> extractTokens(List<ParseTree> nodes) {\n" +
                "        List<Token> result = new ArrayList<>();\n" +
                "        for (ParseTree node : nodes) {\n" +
                "            if (node instanceof TerminalNode) {\n" +
                "                result.add(((TerminalNode) node).getSymbol());\n" +
                "            } else if (node instanceof ParserRuleContext) {\n" +
                "                result.addAll(extractTokens(((ParserRuleContext) node).children));\n" +
                "            } else {\n" +
                "                throw new IllegalArgumentException(\"Unknown type of ParseTree node: \" + node.getClass()\n" +
                "                                                                                            .getName());\n" +
                "            }\n" +
                "        }\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Extracts the leftmost token in given tree\n" +
                "     */\n" +
                "    private static Token extractLeftmostToken(ParseTree tree) {\n" +
                "        return extractToken(tree, node -> node.getChild(0));\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Extracts the rightmost token in given tree\n" +
                "     */\n" +
                "    private static Token extractRightmostToken(ParseTree tree) {\n" +
                "        return extractToken(tree, node -> node.getChild(node.getChildCount() - 1));\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Extracts the first token it finds in the given parseTree, the next node to traverse is chosen by the nextNodeExtractor function\n" +
                "     *\n" +
                "     * @param tree              tree to traverse\n" +
                "     * @param nextNodeExtractor function to choose what is the next token to traverse\n" +
                "     * @return first token that is found in given parse tree when traversing using the nextNodeExtractor\n" +
                "     */\n" +
                "    private static Token extractToken(ParseTree tree, Function<ParseTree, ParseTree> nextNodeExtractor) {\n" +
                "        while (!(tree instanceof TerminalNode)) {\n" +
                "            if (tree.getChildCount() == 0) {\n" +
                "                throw new IllegalArgumentException(\"Cannot find wanted token in this tree\");\n" +
                "            }\n" +
                "            tree = nextNodeExtractor.apply(tree);\n" +
                "        }\n" +
                "        return ((TerminalNode) tree).getSymbol();\n" +
                "    }\n" +
                "}\n";

        List<String> grammars = TestGrammars.loadJava();
        GrammarParser grammarParser = new GrammarParser();
        for (String grammar : grammars) {
            grammarParser.parseString(grammar);
        }
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();


        GrammarInfo withFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammars.get(0), grammars.get(1), Collections.singletonList(input));

        FormatInfoMapToDSLConvertor.print(withFormatting);

        verifyFormatInfo(
                grammarInfo,
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
                                        element("import", newLine(false), space(false)),
                                        element("static", newLine(false), space(false)),
                                        element("qualifiedName", newLine(false), space(false)),
                                        element("importDeclaration_block_1_1", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration", 0,
                                handle(
                                        collection("classOrInterfaceModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
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
                                        collection("modifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
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
                        rule("formalParameterList_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(true)),
                                        element("formalParameter", newLine(false), space(true))
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
                                        element("if", newLine(false), space(true)),
                                        element("parExpression", newLine(true), space(true)),
                                        element("statement", newLine(true), space(true)),
                                        element("statement_block_3_1", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("for", newLine(false), space(true)),
                                        element("(", newLine(false), space(false)),
                                        element("forControl", newLine(false), space(false)),
                                        element(")", newLine(false), space(true)),
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("while", newLine(false), space(true)),
                                        element("parExpression", newLine(false), space(true)),
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
                        rule("statement", 11,
                                handle(
                                        element("throw", newLine(false), space(true)),
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
                        rule("statement_block_3_1", 0,
                                handle(
                                        element("else", newLine(false), space(true)),
                                        element("statement", newLine(false), space(true))
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
                                        element("forInit", newLine(false), space(false)),
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
                        rule("enhancedForControl", 0,
                                handle(
                                        collection("variableModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("typeType", newLine(false), space(true)),
                                        element("variableDeclaratorId", newLine(false), space(true)),
                                        element(":", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
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
                        rule("expressionList_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("methodCall", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("(", newLine(true), space(false)),
                                        element("expressionList", newLine(true), space(false)),
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
                                        element("expression", newLine(true), space(false)),
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
                        rule("expression", 5,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("typeType", newLine(false), space(false)),
                                        element(")", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
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
                        rule("expression", 8,
                                handle(
                                        element("expression_block_9_1", newLine(false), space(false)),
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
                        rule("expression", 13,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("instanceof", newLine(false), space(true)),
                                        element("typeType", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 14,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("expression_block_15_1", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 18,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("&&", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 19,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("||", newLine(false), space(true)),
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
                        rule("lambdaExpression", 0,
                                handle(
                                        element("lambdaParameters", newLine(false), space(true)),
                                        element("->", newLine(false), space(true)),
                                        element("lambdaBody", newLine(false), space(true))
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
                        rule("primary", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
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
                        rule("typeArgumentsOrDiamond", 0,
                                handle(
                                        element("<", newLine(false), space(false)),
                                        element(">", newLine(false), space(true))
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
                        rule("typeArguments", 0,
                                handle(
                                        element("<", newLine(false), space(false)),
                                        element("typeArgument", newLine(false), space(false)),
                                        collection("typeArguments_block_1_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element(">", newLine(false), space(true))
                                )
                        ),
                        rule("typeArguments_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(true)),
                                        element("typeArgument", newLine(false), space(true))
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

    @Test
    public void expressions() {
        List<String> grammars = TestGrammars.loadJava();

        String input = "class Expressions {\n" +
                "\n" +
                "            public void method() {\n" +
                "                int sum = 0;\n" +
                "                for (int i = 0; i < 42; i++) {\n" +
                "                    sum += i;\n" +
                "                }\n" +
                "                System.out.println(sum);\n" +
                "                System.out.println(sum + 25 * 8 - 13 * (-sum) * Math.pow(2, 10));\n" +
                "            }\n" +
                "        }";


        GrammarParser grammarParser = new GrammarParser();
        for (String grammar : grammars) {
            grammarParser.parseString(grammar);
        }
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();


        GrammarInfo withFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammars.get(0), grammars.get(1), Collections.singletonList(input));

        FormatInfoMapToDSLConvertor.print(withFormatting);


        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("packageDeclaration", newLine(false), space(false)),
                                        collection("importDeclaration", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        collection("typeDeclaration", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("typeDeclaration", 0,
                                handle(
                                        collection("classOrInterfaceModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
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
                                        collection("modifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
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
                        rule("formalParameterList", 1,
                                handle(
                                        element("lastFormalParameter", newLine(false), space(true))
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
                        rule("statement", 3,
                                handle(
                                        element("for", newLine(false), space(true)),
                                        element("(", newLine(false), space(false)),
                                        element("forControl", newLine(false), space(false)),
                                        element(")", newLine(false), space(true)),
                                        element("statement", newLine(false), space(true))
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
                                        element("forInit", newLine(false), space(false)),
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
                        rule("expressionList", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("expressionList_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("expressionList_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
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
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("expression_block_10_1", newLine(false), space(true)),
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
                        rule("primary", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
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
                        rule("createdName", 1,
                                handle(
                                        element("primitiveType", newLine(false), space(true))
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
                        )
                )
        );

    }

}
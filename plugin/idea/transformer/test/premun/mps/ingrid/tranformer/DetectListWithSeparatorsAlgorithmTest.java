package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.FormatInfoMapToDSLConvertor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.model.format.SimpleFormatInfo;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.tranformer.Utils.loadFileContent;

public class DetectListWithSeparatorsAlgorithmTest {

    @Test
    public void cimple__shouldFindArgsAndParameters__checkingOnlyGrammar() {
        String grammar = TestGrammars.loadCimple();

        GrammarInfo grammarInfo = processGrammar(grammar);
        assertRuleHasJustOneRuleReference(grammarInfo, "arguments", "expression");
        assertRuleHasJustOneRuleReference(grammarInfo, "parameters", "ID");
    }

    @Test
    public void args__shouldFindArgsAndIdList__checkingOnlyGrammar() throws IOException {
        String grammar = loadFileContent("/Args.g4");

        GrammarInfo grammarInfo = processGrammar(grammar);

        assertRuleHasJustOneRuleReference(grammarInfo, "args", "arg");
        assertRuleHasJustOneRuleReference(grammarInfo, "idList", "ID");
    }


    private void assertRuleHasJustOneRuleReference(GrammarInfo grammarInfo, String checkedRuleName, String referencedRuleName) {
        try {
            ParserRule parserRule = (ParserRule) grammarInfo.rules.get(checkedRuleName);
            assertEquals(1, parserRule.alternatives.size());
            Alternative alternative = parserRule.alternatives.get(0);
            List<RuleReference> elements = alternative.elements;
            assertEquals(1, elements.size());
            RuleReference ruleReference = elements.get(0);
            assertEquals(referencedRuleName, ruleReference.rule.name);
            assertEquals(Quantity.ANY, ruleReference.quantity);
        } catch (AssertionError ex) {
            throw new AssertionError("When checking '" + checkedRuleName + "' and looking for reference to '" + referencedRuleName + "' in grammar " + grammarInfo, ex);
        }
    }

    private GrammarInfo processGrammar(String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        for (RuleReference ruleReference : grammarInfo.getRuleReferences()) {
            ruleReference.formatInfo = SimpleFormatInfo.UNKNOWN;
        }

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        return detectListWithSeparatorsAlgorithm.transform(grammarInfo);

    }


    @Test
    public void cimple__shouldFindArgsAndParameters__checkingTheFormatInfoMap() throws IOException {
        String grammar = TestGrammars.loadCimple();
        String input = "fn fib(i){\n" +
                "    if(i < 2){\n" +
                "        return 1;\n" +
                "    } else {\n" +
                "        return fib(i - 1)  + fib(i - 2);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "i = 10;\n" +
                "\n" +
                "for(j = 0; j < i ; j = j+1){\n" +
                "    res = fib(j);\n" +
                "    print res;\n" +
                "}\n";

        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        GrammarInfo withFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammar, Collections.singletonList(input));

        verifyFormatInfo(
                withFormatting,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true)),
                                        element("else", newLine(false), space(true)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        )
                )
        );

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        GrammarInfo withSeparators = detectListWithSeparatorsAlgorithm.transform(withFormatting);

        verifyFormatInfo(
                withSeparators,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true)),
                                        element("else", newLine(false), space(true)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void args__shouldFindArgsAndParameters__checkingTheFormatInfoMap() throws IOException {
        String grammar = loadFileContent("/Args.g4");
        String input = "foo(a,b,c) bar(a,b)";

        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        GrammarInfo witFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammar, Collections.singletonList(input));

        verifyFormatInfo(
                witFormatting,
                rules(
                        rule("statements", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 1,
                                handle(
                                        element("idList", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("args", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("args", 0,
                                handle(
                                        element("arg", newLine(false), space(false)),
                                        collection("args_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("args_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("arg", newLine(false), space(true))
                                )
                        ),
                        rule("arg", 0,
                                handle(
                                        element("ID", newLine(false), space(true))
                                )
                        )
                )
        );


        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();

        GrammarInfo withSeparators = detectListWithSeparatorsAlgorithm.transform(witFormatting);

        verifyFormatInfo(
                withSeparators,
                rules(
                        rule("statements", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 1,
                                handle(
                                        element("idList", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("args", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("args", 0,
                                handle(
                                        collection("arg", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        ),
                        rule("args_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("arg", newLine(false), space(true))
                                )
                        ),
                        rule("arg", 0,
                                handle(
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("idList", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        )
                )
        );

    }


    @Test
    public void javaGrammar__basicTest() {
        List<String> grammarFiles = TestGrammars.loadJava();

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
                "public class Animal<T> {\n" +
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


        GrammarParser grammarParser = new GrammarParser("compilationUnit");
        for (String grammarFile : grammarFiles) {
            grammarParser.parseString(grammarFile);
        }
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        GrammarInfo withFormatInfo = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammarFiles.get(0), grammarFiles.get(1), Collections.singletonList(input));

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        GrammarInfo withSeparators = detectListWithSeparatorsAlgorithm.transform(withFormatInfo);

        FormatInfoMapToDSLConvertor.print(withSeparators);

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
                        rule("typeParameters", 0,
                                handle(
                                        element("<", newLine(false), space(false)),
                                        collection("typeParameter", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(",")),
                                        element(">", newLine(false), space(true))
                                )
                        ),
                        rule("typeBound", 0,
                                handle(
                                        collection("typeType", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator("&"))
                                )
                        ),
                        rule("enumConstants", 0,
                                handle(
                                        collection("enumConstant", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                        rule("constDeclaration", 0,
                                handle(
                                        element("typeType", newLine(false), space(false)),
                                        collection("constantDeclarator", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(",")),
                                        element(";", newLine(false), space(true))
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
                                        collection("variableDeclarator", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                        rule("arrayInitializer_block_1_1", 0,
                                handle(
                                        collection("variableInitializer", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(",")),
                                        element("arrayInitializer_block_1_3", newLine(false), space(true))
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
                        rule("qualifiedNameList", 0,
                                handle(
                                        collection("qualifiedName", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                                        collection("formalParameter", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(",")),
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
                                        collection("IDENTIFIER", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator("."))
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
                        rule("elementValuePairs", 0,
                                handle(
                                        collection("elementValuePair", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                        rule("elementValueArrayInitializer_block_1_1", 0,
                                handle(
                                        collection("elementValue", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                        rule("catchType", 0,
                                handle(
                                        collection("qualifiedName", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator("|"))
                                )
                        ),
                        rule("resources", 0,
                                handle(
                                        collection("resource", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(";"))
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
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                        rule("lambdaParameters", 2,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        collection("IDENTIFIER", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(",")),
                                        element(")", newLine(false), space(true))
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
                        rule("arrayCreatorRest_block_1_1", 0,
                                handle(
                                        collection("]", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator("[")),
                                        element("arrayInitializer", newLine(false), space(true))
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
                        rule("typeList", 0,
                                handle(
                                        collection("typeType", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                        rule("typeArguments", 0,
                                handle(
                                        element("<", newLine(false), space(false)),
                                        collection("typeArgument", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(",")),
                                        element(">", newLine(false), space(true))
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

    /**
     * Checks whether '<' and '>' stays around type parameters after
     * the transformation
     */
    @Test
    public void javaGrammar__checkTypeParameters__LT__and__GR__remains() {
        List<String> grammarFiles = TestGrammars.loadJava();

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


        GrammarParser grammarParser = new GrammarParser("compilationUnit");
        for (String grammarFile : grammarFiles) {
            grammarParser.parseString(grammarFile);
        }
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        GrammarInfo withFormatInfo = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammarFiles.get(0), grammarFiles.get(1), Collections.singletonList(input));

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        GrammarInfo withSeparators = detectListWithSeparatorsAlgorithm.transform(withFormatInfo);


        // typeParameters : '<' typeParameter (',' typeParameter)* '>' ;
        // should be transformed into typeParameters : '<' typeParameter '>' ;

        ParserRule typeParameters = (ParserRule) withSeparators.rules.get("typeParameters");
        assertEquals(1, typeParameters.alternatives.size());
        Alternative alternative = typeParameters.alternatives.get(0);
        assertEquals(3, alternative.elements.size());
        assertEquals("<", ((LiteralRule) alternative.elements.get(0).rule).value);
        assertNotNull(withSeparators.rules.get("typeParameter"));
        assertEquals(withFormatInfo.rules.get("typeParameter"), alternative.elements.get(1).rule);
        assertEquals(">", ((LiteralRule) alternative.elements.get(2).rule).value);

        // verify formatting
        FormatInfo formatInfo = alternative.elements.get(1).formatInfo;
        assertEquals(new SimpleFormatInfo(false, true, false, false, ","), formatInfo);

    }

}

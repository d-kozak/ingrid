package premun.mps.ingrid.importer.java;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.FormatInfoMapToDSLConvertor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.importer.IngridConfiguration;
import premun.mps.ingrid.model.GrammarInfo;

import java.util.Collections;
import java.util.List;

import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.importer.GrammarImporter.fullIngridPipeline;

public class JavaTests {

    @Test
    public void animalClass() {
        String grammar = TestGrammars.loadJava9();

        String animalClass = "import java.util.*;\n" +
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

        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(grammar), Collections.singletonList(animalClass), Collections.emptyList(), false, "compilationUnit");
        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

        FormatInfoMapToDSLConvertor.print(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("ordinaryCompilation", newLine(false), space(true))
                                )
                        ),
                        rule("compilationUnit", 1,
                                handle(
                                        element("modularCompilation", newLine(false), space(true))
                                )
                        ),
                        rule("ordinaryCompilation", 0,
                                handle(
                                        element("packageDeclaration", newLine(false), space(false)),
                                        collection("importDeclaration", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null)),
                                        collection("typeDeclaration", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("importDeclaration", 0,
                                handle(
                                        element("singleTypeImportDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("importDeclaration", 1,
                                handle(
                                        element("typeImportOnDemandDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("importDeclaration", 2,
                                handle(
                                        element("singleStaticImportDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("importDeclaration", 3,
                                handle(
                                        element("staticImportOnDemandDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration", 0,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration", 1,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("typeDeclaration", 2,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("singleStaticImportDeclaration", 0,
                                handle(
                                        element("import", newLine(false), space(true)),
                                        element("static", newLine(false), space(true)),
                                        element("typeName", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("identifier", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("typeImportOnDemandDeclaration", 0,
                                handle(
                                        element("import", newLine(false), space(true)),
                                        element("packageOrTypeName", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("*", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("annotation", 0,
                                handle(
                                        element("normalAnnotation", newLine(false), space(true))
                                )
                        ),
                        rule("annotation", 1,
                                handle(
                                        element("markerAnnotation", newLine(false), space(true))
                                )
                        ),
                        rule("annotation", 2,
                                handle(
                                        element("singleElementAnnotation", newLine(false), space(true))
                                )
                        ),
                        rule("moduleName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("open", 0,
                                handle(
                                        element("open", newLine(false), space(true))
                                )
                        ),
                        rule("packageName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("packageModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceDeclaration", 0,
                                handle(
                                        element("normalInterfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceDeclaration", 1,
                                handle(
                                        element("annotationTypeDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("classDeclaration", 0,
                                handle(
                                        element("normalClassDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("classDeclaration", 1,
                                handle(
                                        element("enumDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("typeName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("typeName", 1,
                                handle(
                                        element("packageOrTypeName", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 0,
                                handle(
                                        element("Identifier", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 1,
                                handle(
                                        element("to", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 2,
                                handle(
                                        element("module", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 3,
                                handle(
                                        element("open", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 4,
                                handle(
                                        element("with", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 5,
                                handle(
                                        element("provides", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 6,
                                handle(
                                        element("uses", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 7,
                                handle(
                                        element("opens", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 8,
                                handle(
                                        element("requires", newLine(false), space(true))
                                )
                        ),
                        rule("identifier", 9,
                                handle(
                                        element("exports", newLine(false), space(true))
                                )
                        ),
                        rule("packageOrTypeName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("packageOrTypeName", 1,
                                handle(
                                        element("packageOrTypeName", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("requiresModifier", 0,
                                handle(
                                        element("transitive", newLine(false), space(true))
                                )
                        ),
                        rule("requiresModifier", 1,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("normalClassDeclaration", 0,
                                handle(
                                        collection("classModifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("class", newLine(false), space(true)),
                                        element("identifier", newLine(false), space(false)),
                                        element("typeParameters", newLine(false), space(false)),
                                        element("superclass", newLine(false), space(false)),
                                        element("superinterfaces", newLine(false), space(false)),
                                        element("classBody", newLine(false), space(true))
                                )
                        ),
                        rule("elementValue", 0,
                                handle(
                                        element("conditionalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("elementValue", 1,
                                handle(
                                        element("elementValueArrayInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("elementValue", 2,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceModifier", 2,
                                handle(
                                        element("protected", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceModifier", 3,
                                handle(
                                        element("private", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceModifier", 4,
                                handle(
                                        element("abstract", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceModifier", 5,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceModifier", 6,
                                handle(
                                        element("strictfp", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 2,
                                handle(
                                        element("protected", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 3,
                                handle(
                                        element("private", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 4,
                                handle(
                                        element("abstract", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 5,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 6,
                                handle(
                                        element("final", newLine(false), space(true))
                                )
                        ),
                        rule("classModifier", 7,
                                handle(
                                        element("strictfp", newLine(false), space(true))
                                )
                        ),
                        rule("classBody", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("classBodyDeclaration", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("conditionalExpression", 0,
                                handle(
                                        element("conditionalOrExpression", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeMemberDeclaration", 0,
                                handle(
                                        element("annotationTypeElementDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeMemberDeclaration", 1,
                                handle(
                                        element("constantDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeMemberDeclaration", 2,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeMemberDeclaration", 3,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeMemberDeclaration", 4,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 0,
                                handle(
                                        element("constantDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 1,
                                handle(
                                        element("interfaceMethodDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 2,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 3,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMemberDeclaration", 4,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("comma_1", 0,
                                handle(
                                        element(",", newLine(false), space(true))
                                )
                        ),
                        rule("classBodyDeclaration", 0,
                                handle(
                                        element("classMemberDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("classBodyDeclaration", 1,
                                handle(
                                        element("instanceInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("classBodyDeclaration", 2,
                                handle(
                                        element("staticInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("classBodyDeclaration", 3,
                                handle(
                                        element("constructorDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("comma_2", 0,
                                handle(
                                        element(",", newLine(false), space(true))
                                )
                        ),
                        rule("conditionalOrExpression", 0,
                                handle(
                                        element("conditionalAndExpression", newLine(false), space(true))
                                )
                        ),
                        rule("conditionalExpression_block_2_1", 0,
                                handle(
                                        element("conditionalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("conditionalExpression_block_2_1", 1,
                                handle(
                                        element("lambdaExpression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("lambdaExpression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 1,
                                handle(
                                        element("assignmentExpression", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceType", 0,
                                handle(
                                        element("classType", newLine(false), space(true))
                                )
                        ),
                        rule("constructorDeclaration", 0,
                                handle(
                                        collection("constructorModifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("constructorDeclarator", newLine(false), space(false)),
                                        element("throws_", newLine(false), space(false)),
                                        element("constructorBody", newLine(false), space(true))
                                )
                        ),
                        rule("instanceInitializer", 0,
                                handle(
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("classMemberDeclaration", 0,
                                handle(
                                        element("fieldDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("classMemberDeclaration", 1,
                                handle(
                                        element("methodDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("classMemberDeclaration", 2,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("classMemberDeclaration", 3,
                                handle(
                                        element("interfaceDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("classMemberDeclaration", 4,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("conditionalAndExpression", 0,
                                handle(
                                        element("inclusiveOrExpression", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentExpression", 0,
                                handle(
                                        element("conditionalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentExpression", 1,
                                handle(
                                        element("assignment", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeElementModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeElementModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("annotationTypeElementModifier", 2,
                                handle(
                                        element("abstract", newLine(false), space(true))
                                )
                        ),
                        rule("dims", 0,
                                handle(
                                        collection("annotation", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("[", newLine(false), space(false)),
                                        element("]", newLine(false), space(false)),
                                        collection("dims_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("unannType", 0,
                                handle(
                                        element("unannPrimitiveType", newLine(false), space(true))
                                )
                        ),
                        rule("unannType", 1,
                                handle(
                                        element("unannReferenceType", newLine(false), space(true))
                                )
                        ),
                        rule("constantModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("constantModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("constantModifier", 2,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("constantModifier", 3,
                                handle(
                                        element("final", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclaratorList", 0,
                                handle(
                                        element("variableDeclarator", newLine(false), space(false)),
                                        collection("variableDeclaratorList_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("methodHeader", 0,
                                handle(
                                        element("result", newLine(false), space(true)),
                                        element("methodDeclarator", newLine(false), space(false)),
                                        element("throws_", newLine(false), space(true))
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
                                        element("private", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 3,
                                handle(
                                        element("abstract", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 4,
                                handle(
                                        element("default", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 5,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceMethodModifier", 6,
                                handle(
                                        element("strictfp", newLine(false), space(true))
                                )
                        ),
                        rule("typeParameterModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("enumConstantModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceType_block_1_1", 0,
                                handle(
                                        element("classType_lfno_classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceType_block_1_1", 1,
                                handle(
                                        element("interfaceType_lfno_classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceType_block_1_2", 0,
                                handle(
                                        element("classType_lf_classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("classOrInterfaceType_block_1_2", 1,
                                handle(
                                        element("interfaceType_lf_classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        element("blockStatements", newLine(true), space(false)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("constructorDeclarator", 0,
                                handle(
                                        element("typeParameters", newLine(false), space(false)),
                                        element("simpleTypeName", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("formalParameterList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("constructorModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("constructorModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("constructorModifier", 2,
                                handle(
                                        element("protected", newLine(false), space(true))
                                )
                        ),
                        rule("constructorModifier", 3,
                                handle(
                                        element("private", newLine(false), space(true))
                                )
                        ),
                        rule("constructorBody", 0,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("explicitConstructorInvocation", newLine(false), space(false)),
                                        element("blockStatements", newLine(true), space(false)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("methodDeclaration", 0,
                                handle(
                                        collection("methodModifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("methodHeader", newLine(false), space(false)),
                                        element("methodBody", newLine(false), space(true))
                                )
                        ),
                        rule("fieldDeclaration", 0,
                                handle(
                                        collection("fieldModifier", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("unannType", newLine(false), space(true)),
                                        element("variableDeclaratorList", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("inclusiveOrExpression", 0,
                                handle(
                                        element("exclusiveOrExpression", newLine(false), space(true))
                                )
                        ),
                        rule("lambdaParameters", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
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
                        rule("assignment", 0,
                                handle(
                                        element("leftHandSide", newLine(false), space(true)),
                                        element("assignmentOperator", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("unannReferenceType", 0,
                                handle(
                                        element("unannClassOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("unannReferenceType", 1,
                                handle(
                                        element("unannTypeVariable", newLine(false), space(true))
                                )
                        ),
                        rule("unannReferenceType", 2,
                                handle(
                                        element("unannArrayType", newLine(false), space(true))
                                )
                        ),
                        rule("unannPrimitiveType", 0,
                                handle(
                                        element("numericType", newLine(false), space(true))
                                )
                        ),
                        rule("unannPrimitiveType", 1,
                                handle(
                                        element("boolean", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclarator", 0,
                                handle(
                                        element("variableDeclaratorId", newLine(false), space(true)),
                                        element("variableDeclarator_block_1_1", newLine(false), space(true))
                                )
                        ),
                        rule("result", 0,
                                handle(
                                        element("unannType", newLine(false), space(true))
                                )
                        ),
                        rule("result", 1,
                                handle(
                                        element("void", newLine(false), space(true))
                                )
                        ),
                        rule("methodDeclarator", 0,
                                handle(
                                        element("identifier", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("formalParameterList", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("dims", newLine(false), space(true))
                                )
                        ),
                        rule("argumentList", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("argumentList_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("typeArgument", 0,
                                handle(
                                        element("referenceType", newLine(false), space(true))
                                )
                        ),
                        rule("typeArgument", 1,
                                handle(
                                        element("wildcard", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceType_lfno_classOrInterfaceType", 0,
                                handle(
                                        element("classType_lfno_classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceType_lf_classOrInterfaceType", 0,
                                handle(
                                        element("classType_lf_classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("blockStatements", 0,
                                handle(
                                        collection("blockStatement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null))
                                )
                        ),
                        rule("formalParameterList", 1,
                                handle(
                                        element("lastFormalParameter", newLine(false), space(true))
                                )
                        ),
                        rule("formalParameterList", 2,
                                handle(
                                        element("receiverParameter", newLine(false), space(true))
                                )
                        ),
                        rule("simpleTypeName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 2,
                                handle(
                                        element("protected", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 3,
                                handle(
                                        element("private", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 4,
                                handle(
                                        element("abstract", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 5,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 6,
                                handle(
                                        element("final", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 7,
                                handle(
                                        element("synchronized", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 8,
                                handle(
                                        element("native", newLine(false), space(true))
                                )
                        ),
                        rule("methodModifier", 9,
                                handle(
                                        element("strictfp", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 1,
                                handle(
                                        element("public", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 2,
                                handle(
                                        element("protected", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 3,
                                handle(
                                        element("private", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 4,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 5,
                                handle(
                                        element("final", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 6,
                                handle(
                                        element("transient", newLine(false), space(true))
                                )
                        ),
                        rule("fieldModifier", 7,
                                handle(
                                        element("volatile", newLine(false), space(true))
                                )
                        ),
                        rule("exclusiveOrExpression", 0,
                                handle(
                                        element("andExpression", newLine(false), space(true))
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
                                        element(">>>=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 9,
                                handle(
                                        element("&=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 10,
                                handle(
                                        element("^=", newLine(false), space(true))
                                )
                        ),
                        rule("assignmentOperator", 11,
                                handle(
                                        element("|=", newLine(false), space(true))
                                )
                        ),
                        rule("leftHandSide", 0,
                                handle(
                                        element("expressionName", newLine(false), space(true))
                                )
                        ),
                        rule("leftHandSide", 1,
                                handle(
                                        element("fieldAccess", newLine(false), space(true))
                                )
                        ),
                        rule("leftHandSide", 2,
                                handle(
                                        element("arrayAccess", newLine(false), space(true))
                                )
                        ),
                        rule("unannTypeVariable", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("unannClassOrInterfaceType", 0,
                                handle(
                                        element("unannClassOrInterfaceType_block_1_1", newLine(false), space(false)),
                                        collection("unannClassOrInterfaceType_block_1_2", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("unannArrayType", 1,
                                handle(
                                        element("unannClassOrInterfaceType", newLine(false), space(false)),
                                        element("dims", newLine(false), space(true))
                                )
                        ),
                        rule("numericType", 0,
                                handle(
                                        element("integralType", newLine(false), space(true))
                                )
                        ),
                        rule("numericType", 1,
                                handle(
                                        element("floatingPointType", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclaratorId", 0,
                                handle(
                                        element("identifier", newLine(false), space(false)),
                                        element("dims", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclarator_block_1_1", 0,
                                handle(
                                        element("=", newLine(false), space(true)),
                                        element("variableInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("referenceType", 0,
                                handle(
                                        element("classOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("referenceType", 1,
                                handle(
                                        element("typeVariable", newLine(false), space(true))
                                )
                        ),
                        rule("referenceType", 2,
                                handle(
                                        element("arrayType", newLine(false), space(true))
                                )
                        ),
                        rule("blockStatement", 0,
                                handle(
                                        element("localVariableDeclarationStatement", newLine(false), space(true))
                                )
                        ),
                        rule("blockStatement", 1,
                                handle(
                                        element("classDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("blockStatement", 2,
                                handle(
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("lastFormalParameter", 1,
                                handle(
                                        element("formalParameter", newLine(false), space(true))
                                )
                        ),
                        rule("exceptionType", 0,
                                handle(
                                        element("classType", newLine(false), space(true))
                                )
                        ),
                        rule("exceptionType", 1,
                                handle(
                                        element("typeVariable", newLine(false), space(true))
                                )
                        ),
                        rule("expressionName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("expressionName", 1,
                                handle(
                                        element("ambiguousName", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("primary", 0,
                                handle(
                                        element("primary_block_1_1", newLine(false), space(false)),
                                        collection("primary_block_1_2", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("andExpression", 0,
                                handle(
                                        element("equalityExpression", newLine(false), space(true))
                                )
                        ),
                        rule("fieldAccess", 0,
                                handle(
                                        element("primary", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("unannClassOrInterfaceType_block_1_1", 0,
                                handle(
                                        element("unannClassType_lfno_unannClassOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("unannClassOrInterfaceType_block_1_1", 1,
                                handle(
                                        element("unannInterfaceType_lfno_unannClassOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("unannClassOrInterfaceType_block_1_2", 0,
                                handle(
                                        element("unannClassType_lf_unannClassOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("unannClassOrInterfaceType_block_1_2", 1,
                                handle(
                                        element("unannInterfaceType_lf_unannClassOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("floatingPointType", 0,
                                handle(
                                        element("float", newLine(false), space(true))
                                )
                        ),
                        rule("floatingPointType", 1,
                                handle(
                                        element("double", newLine(false), space(true))
                                )
                        ),
                        rule("integralType", 0,
                                handle(
                                        element("byte", newLine(false), space(true))
                                )
                        ),
                        rule("integralType", 1,
                                handle(
                                        element("short", newLine(false), space(true))
                                )
                        ),
                        rule("integralType", 2,
                                handle(
                                        element("int", newLine(false), space(true))
                                )
                        ),
                        rule("integralType", 3,
                                handle(
                                        element("long", newLine(false), space(true))
                                )
                        ),
                        rule("integralType", 4,
                                handle(
                                        element("char", newLine(false), space(true))
                                )
                        ),
                        rule("variableInitializer", 0,
                                handle(
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("variableInitializer", 1,
                                handle(
                                        element("arrayInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("localVariableDeclarationStatement", 0,
                                handle(
                                        element("localVariableDeclaration", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("statementWithoutTrailingSubstatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 1,
                                handle(
                                        element("labeledStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("ifThenStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifThenElseStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("whileStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 5,
                                handle(
                                        element("forStatement", newLine(false), space(true))
                                )
                        ),
                        rule("variableModifier", 0,
                                handle(
                                        element("annotation", newLine(false), space(true))
                                )
                        ),
                        rule("variableModifier", 1,
                                handle(
                                        element("final", newLine(false), space(true))
                                )
                        ),
                        rule("formalParameter", 0,
                                handle(
                                        collection("variableModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("unannType", newLine(false), space(true)),
                                        element("variableDeclaratorId", newLine(false), space(true))
                                )
                        ),
                        rule("ambiguousName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("primary_block_1_1", 0,
                                handle(
                                        element("primaryNoNewArray_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primary_block_1_1", 1,
                                handle(
                                        element("arrayCreationExpression", newLine(false), space(true))
                                )
                        ),
                        rule("primary_block_1_2", 0,
                                handle(
                                        element("primaryNoNewArray_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("equalityExpression", 0,
                                handle(
                                        element("relationalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("equalityExpression", 1,
                                handle(
                                        element("equalityExpression", newLine(false), space(true)),
                                        element("==", newLine(false), space(true)),
                                        element("relationalExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unannClassType_lfno_unannClassOrInterfaceType", 0,
                                handle(
                                        element("identifier", newLine(false), space(false)),
                                        element("typeArguments", newLine(false), space(true))
                                )
                        ),
                        rule("unannInterfaceType_lfno_unannClassOrInterfaceType", 0,
                                handle(
                                        element("unannClassType_lfno_unannClassOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("unannInterfaceType_lf_unannClassOrInterfaceType", 0,
                                handle(
                                        element("unannClassType_lf_unannClassOrInterfaceType", newLine(false), space(true))
                                )
                        ),
                        rule("localVariableDeclaration", 0,
                                handle(
                                        collection("variableModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("unannType", newLine(false), space(true)),
                                        element("variableDeclaratorList", newLine(false), space(true))
                                )
                        ),
                        rule("forStatement", 0,
                                handle(
                                        element("basicForStatement", newLine(false), space(true))
                                )
                        ),
                        rule("forStatement", 1,
                                handle(
                                        element("enhancedForStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 0,
                                handle(
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 1,
                                handle(
                                        element("emptyStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 2,
                                handle(
                                        element("expressionStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 3,
                                handle(
                                        element("assertStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 4,
                                handle(
                                        element("switchStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 5,
                                handle(
                                        element("doStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 6,
                                handle(
                                        element("breakStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 7,
                                handle(
                                        element("continueStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 8,
                                handle(
                                        element("returnStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 9,
                                handle(
                                        element("synchronizedStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 10,
                                handle(
                                        element("throwStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementWithoutTrailingSubstatement", 11,
                                handle(
                                        element("tryStatement", newLine(false), space(true))
                                )
                        ),
                        rule("ifThenStatement", 0,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary", 0,
                                handle(
                                        element("literal", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary", 4,
                                handle(
                                        element("this", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary", 7,
                                handle(
                                        element("classInstanceCreationExpression_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary", 8,
                                handle(
                                        element("fieldAccess_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary", 9,
                                handle(
                                        element("arrayAccess_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary", 10,
                                handle(
                                        element("methodInvocation_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary", 11,
                                handle(
                                        element("methodReference_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary", 0,
                                handle(
                                        element("classInstanceCreationExpression_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary", 1,
                                handle(
                                        element("fieldAccess_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary", 2,
                                handle(
                                        element("arrayAccess_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary", 3,
                                handle(
                                        element("methodInvocation_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary", 4,
                                handle(
                                        element("methodReference_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("relationalExpression", 0,
                                handle(
                                        element("shiftExpression", newLine(false), space(true))
                                )
                        ),
                        rule("relationalExpression", 3,
                                handle(
                                        element("relationalExpression", newLine(false), space(true)),
                                        element("<=", newLine(false), space(true)),
                                        element("shiftExpression", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_arrayAccess", 0,
                                handle(

                                )
                        ),
                        rule("primaryNoNewArray_lfno_arrayAccess", 0,
                                handle(
                                        element("literal", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_arrayAccess", 3,
                                handle(
                                        element("this", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_arrayAccess", 6,
                                handle(
                                        element("classInstanceCreationExpression", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_arrayAccess", 7,
                                handle(
                                        element("fieldAccess", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_arrayAccess", 8,
                                handle(
                                        element("methodInvocation", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_arrayAccess", 9,
                                handle(
                                        element("methodReference", newLine(false), space(true))
                                )
                        ),
                        rule("comma_3", 0,
                                handle(
                                        element(",", newLine(false), space(true))
                                )
                        ),
                        rule("basicForStatement", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("forInit", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("forUpdate", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("statement", newLine(false), space(true))
                                )
                        ),
                        rule("emptyStatement", 0,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("tryStatement", 2,
                                handle(
                                        element("tryWithResourcesStatement", newLine(false), space(true))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expressionStatement", 0,
                                handle(
                                        element("statementExpression", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statementNoShortIf", 0,
                                handle(
                                        element("statementWithoutTrailingSubstatement", newLine(false), space(true))
                                )
                        ),
                        rule("statementNoShortIf", 1,
                                handle(
                                        element("labeledStatementNoShortIf", newLine(false), space(true))
                                )
                        ),
                        rule("statementNoShortIf", 2,
                                handle(
                                        element("ifThenElseStatementNoShortIf", newLine(false), space(true))
                                )
                        ),
                        rule("statementNoShortIf", 3,
                                handle(
                                        element("whileStatementNoShortIf", newLine(false), space(true))
                                )
                        ),
                        rule("statementNoShortIf", 4,
                                handle(
                                        element("forStatementNoShortIf", newLine(false), space(true))
                                )
                        ),
                        rule("dimExprs", 0,
                                handle(
                                        collection("dimExpr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("literal", 0,
                                handle(
                                        element("IntegerLiteral", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 1,
                                handle(
                                        element("FloatingPointLiteral", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 2,
                                handle(
                                        element("BooleanLiteral", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 3,
                                handle(
                                        element("CharacterLiteral", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 4,
                                handle(
                                        element("StringLiteral", newLine(false), space(true))
                                )
                        ),
                        rule("literal", 5,
                                handle(
                                        element("null", newLine(false), space(true))
                                )
                        ),
                        rule("classInstanceCreationExpression_lfno_primary", 0,
                                handle(
                                        element("new", newLine(false), space(false)),
                                        element("typeArguments", newLine(false), space(false)),
                                        collection("annotation", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("identifier", newLine(false), space(false)),
                                        collection("classInstanceCreationExpression_lfno_primary_block_1_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("typeArgumentsOrDiamond", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("argumentList", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("classBody", newLine(false), space(true))
                                )
                        ),
                        rule("shiftExpression", 0,
                                handle(
                                        element("additiveExpression", newLine(false), space(true))
                                )
                        ),
                        rule("methodInvocation", 1,
                                handle(
                                        element("typeName", newLine(false), space(false)),
                                        element(".", newLine(false), space(false)),
                                        element("typeArguments", newLine(false), space(false)),
                                        element("identifier", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("argumentList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("forUpdate", 0,
                                handle(
                                        element("statementExpressionList", newLine(false), space(true))
                                )
                        ),
                        rule("forInit", 0,
                                handle(
                                        element("statementExpressionList", newLine(false), space(true))
                                )
                        ),
                        rule("forInit", 1,
                                handle(
                                        element("localVariableDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("catches", 0,
                                handle(
                                        collection("catchClause", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statementExpression", 0,
                                handle(
                                        element("assignment", newLine(false), space(true))
                                )
                        ),
                        rule("statementExpression", 1,
                                handle(
                                        element("preIncrementExpression", newLine(false), space(true))
                                )
                        ),
                        rule("statementExpression", 2,
                                handle(
                                        element("preDecrementExpression", newLine(false), space(true))
                                )
                        ),
                        rule("statementExpression", 3,
                                handle(
                                        element("postIncrementExpression", newLine(false), space(true))
                                )
                        ),
                        rule("statementExpression", 4,
                                handle(
                                        element("postDecrementExpression", newLine(false), space(true))
                                )
                        ),
                        rule("statementExpression", 5,
                                handle(
                                        element("methodInvocation", newLine(false), space(true))
                                )
                        ),
                        rule("statementExpression", 6,
                                handle(
                                        element("classInstanceCreationExpression", newLine(false), space(true))
                                )
                        ),
                        rule("forStatementNoShortIf", 0,
                                handle(
                                        element("basicForStatementNoShortIf", newLine(false), space(true))
                                )
                        ),
                        rule("forStatementNoShortIf", 1,
                                handle(
                                        element("enhancedForStatementNoShortIf", newLine(false), space(true))
                                )
                        ),
                        rule("typeArgumentsOrDiamond", 0,
                                handle(
                                        element("typeArguments", newLine(false), space(true))
                                )
                        ),
                        rule("methodName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("additiveExpression", 0,
                                handle(
                                        element("multiplicativeExpression", newLine(false), space(true))
                                )
                        ),
                        rule("additiveExpression", 1,
                                handle(
                                        element("additiveExpression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("multiplicativeExpression", newLine(false), space(true))
                                )
                        ),
                        rule("statementExpressionList", 0,
                                handle(
                                        element("statementExpression", newLine(false), space(false)),
                                        collection("statementExpressionList_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("postIncrementExpression", 0,
                                handle(
                                        element("postfixExpression", newLine(false), space(false)),
                                        element("++", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary", 0,
                                handle(
                                        element("literal", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary", 4,
                                handle(
                                        element("this", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary", 7,
                                handle(
                                        element("classInstanceCreationExpression_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary", 8,
                                handle(
                                        element("fieldAccess_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary", 9,
                                handle(
                                        element("methodInvocation_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary", 10,
                                handle(
                                        element("methodReference_lfno_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary", 0,
                                handle(

                                )
                        ),
                        rule("primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary", 0,
                                handle(
                                        element("classInstanceCreationExpression_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary", 1,
                                handle(
                                        element("fieldAccess_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary", 2,
                                handle(
                                        element("methodInvocation_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary", 3,
                                handle(
                                        element("methodReference_lf_primary", newLine(false), space(true))
                                )
                        ),
                        rule("primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary", 0,
                                handle(

                                )
                        ),
                        rule("multiplicativeExpression", 0,
                                handle(
                                        element("unaryExpression", newLine(false), space(true))
                                )
                        ),
                        rule("semicolon", 0,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpression", 0,
                                handle(
                                        element("preIncrementExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpression", 1,
                                handle(
                                        element("preDecrementExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpression", 3,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("unaryExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpression", 4,
                                handle(
                                        element("unaryExpressionNotPlusMinus", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression", 0,
                                handle(
                                        element("postfixExpression_block_1_1", newLine(false), space(false)),
                                        collection("postfixExpression_block_1_2", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("switchLabels", 0,
                                handle(
                                        collection("switchLabel", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("constantExpression", 0,
                                handle(
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("enumConstantName", 0,
                                handle(
                                        element("identifier", newLine(false), space(true))
                                )
                        ),
                        rule("resource", 1,
                                handle(
                                        element("variableAccess", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpressionNotPlusMinus", 0,
                                handle(
                                        element("postfixExpression", newLine(false), space(true))
                                )
                        ),
                        rule("unaryExpressionNotPlusMinus", 3,
                                handle(
                                        element("castExpression", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression_block_1_2", 0,
                                handle(
                                        element("postIncrementExpression_lf_postfixExpression", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression_block_1_2", 1,
                                handle(
                                        element("postDecrementExpression_lf_postfixExpression", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression_block_1_1", 0,
                                handle(
                                        element("primary", newLine(false), space(true))
                                )
                        ),
                        rule("postfixExpression_block_1_1", 1,
                                handle(
                                        element("expressionName", newLine(false), space(true))
                                )
                        ),
                        rule("variableAccess", 0,
                                handle(
                                        element("expressionName", newLine(false), space(true))
                                )
                        ),
                        rule("variableAccess", 1,
                                handle(
                                        element("fieldAccess", newLine(false), space(true))
                                )
                        ),
                        rule("postDecrementExpression_lf_postfixExpression", 0,
                                handle(
                                        element("--", newLine(false), space(true))
                                )
                        ),
                        rule("postIncrementExpression_lf_postfixExpression", 0,
                                handle(
                                        element("++", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressions() {
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

        List<String> grammars = TestGrammars.loadJava();

        IngridConfiguration ingridConfiguration = new IngridConfiguration(grammars, Collections.singletonList(input), Collections.emptyList(), false, "compilationUnit");
        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

        FormatInfoMapToDSLConvertor.print(grammarInfo);

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
                        rule("static_1", 0,
                                handle(
                                        element("static", newLine(false), space(true))
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
                        rule("classBody", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("classBodyDeclaration", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("comma_1", 0,
                                handle(
                                        element(",", newLine(false), space(true))
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
                        rule("annotationTypeBody_block_1_1", 0,
                                handle(
                                        element("annotationTypeElementDeclaration", newLine(false), space(true))
                                )
                        ),
                        rule("interfaceBodyDeclaration", 1,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("typeType", 0,
                                handle(
                                        element("annotation", newLine(false), space(false)),
                                        element("typeType_block_1_1", newLine(false), space(false)),
                                        collection("typeType_block_1_2", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
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
                        rule("annotationTypeElementDeclaration", 1,
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
                        rule("static_2", 0,
                                handle(
                                        element("static", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("blockStatement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
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
                        rule("elementValueArrayInitializer_block_1_3", 0,
                                handle(
                                        element(",", newLine(false), space(true))
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
                        rule("expression_block_25_1", 1,
                                handle(
                                        element("new", newLine(false), space(true))
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
                        rule("methodCall", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expressionList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
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
                        rule("expressionList", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("expressionList_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
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
                        rule("createdName", 1,
                                handle(
                                        element("primitiveType", newLine(false), space(true))
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
                        rule("superSuffix", 0,
                                handle(
                                        element("arguments", newLine(false), space(true))
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
                        rule("primary_block_7_1", 0,
                                handle(
                                        element("explicitGenericInvocationSuffix", newLine(false), space(true))
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
                        rule("typeArgument", 0,
                                handle(
                                        element("typeType", newLine(false), space(true))
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
                        rule("semicolon_3", 0,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("semicolon_2", 0,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("semicolon", 0,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("semicolon_1", 0,
                                handle(
                                        element(";", newLine(false), space(true))
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
                        rule("formalParameters", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("formalParameterList", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("localTypeDeclaration", 1,
                                handle(
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("localVariableDeclaration", 0,
                                handle(
                                        collection("variableModifier", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("typeType", newLine(false), space(true)),
                                        element("variableDeclarators", newLine(false), space(true))
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
                        rule("variableDeclarators", 0,
                                handle(
                                        element("variableDeclarator", newLine(false), space(false)),
                                        collection("variableDeclarators_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("expressionList_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("formalParameterList", 1,
                                handle(
                                        element("lastFormalParameter", newLine(false), space(true))
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
                        rule("annotationConstantRest", 0,
                                handle(
                                        element("variableDeclarators", newLine(false), space(true))
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
                        rule("statement_block_7_1", 1,
                                handle(
                                        element("finallyBlock", newLine(false), space(true))
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
                        rule("identifier_1", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(true))
                                )
                        ),
                        rule("identifier_2", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(true))
                                )
                        ),
                        rule("variableDeclarator", 0,
                                handle(
                                        element("variableDeclaratorId", newLine(false), space(true)),
                                        element("variableDeclarator_block_1_1", newLine(false), space(true))
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
                        rule("semicolon_4", 0,
                                handle(
                                        element(";", newLine(false), space(true))
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
                        rule("variableDeclaratorId", 0,
                                handle(
                                        element("IDENTIFIER", newLine(false), space(false)),
                                        collection("variableDeclaratorId_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("variableDeclarator_block_1_1", 0,
                                handle(
                                        element("=", newLine(false), space(true)),
                                        element("variableInitializer", newLine(false), space(true))
                                )
                        ),
                        rule("arrayInitializer_block_1_3", 0,
                                handle(
                                        element(",", newLine(false), space(true))
                                )
                        )
                )
        );

    }
}

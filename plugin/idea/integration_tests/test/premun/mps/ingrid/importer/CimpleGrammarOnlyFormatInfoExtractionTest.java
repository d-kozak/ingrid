package premun.mps.ingrid.importer;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;

import java.util.Collections;

import static premun.mps.ingrid.importer.GrammarImporter.fullIngridPipeline;

public class CimpleGrammarOnlyFormatInfoExtractionTest {

    @Test
    public void cimpleFibonacci__kindaTypicalFormatting() {
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

        String cimple = TestGrammars.loadCimple();
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(cimple), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);


    }

    @Test
    public void cimpleFibonacci__eachTopLevelStatementOnOneLine() {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1)  + fib(i - 2);}}\n" +
                "i = 10;\n" +
                "for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(cimple), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);


    }


    @Test
    public void cimpleFibonacci__kindaTypicalFormatting__inIfElseThereAreSpacesAroundExpressionInIf() {
        String input = "fn fib(i){\n" +
                "    if (i < 2) {\n" +
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

        String cimple = TestGrammars.loadCimple();
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(cimple), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);


    }


    @Test
    public void cimpleFibonacci__kindaTypicalFormatting__inIfElseThereAreNoSpacesAroundElse() {
        String input = "fn fib(i){\n" +
                "    if(i < 2){\n" +
                "        return 1;\n" +
                "    }else{\n" +
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

        String cimple = TestGrammars.loadCimple();
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(cimple), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

    }

    @Test
    public void cimpleFibonacci__noFormat() {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1) + fib(i - 2);}} i = 10; for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(cimple), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);


    }

    @Test
    public void cimpleFibonacci__kindaTypicalFormatting__blockStatementLeftBracketOnNewLineInFunctionDefinition() {
        String input = "fn fib(i)\n{\n" +
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

        String cimple = TestGrammars.loadCimple();
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(cimple), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);


    }

    @Test
    public void cimpleFibonacci__kindaTypicalFormatting__blockStatementLeftBracketOnNewLineInIfElse() {
        String input = "fn fib(i){\n" +
                "    if(i < 2)\n{\n" +
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

        String cimple = TestGrammars.loadCimple();
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(cimple), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);


    }
}

package premun.mps.ingrid.importer.java;

import org.junit.Ignore;
import org.junit.Test;
import premun.mps.ingrid.formatter.utils.FormatInfoMapToDSLConvertor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.importer.IngridConfiguration;
import premun.mps.ingrid.model.GrammarInfo;

import java.util.Collections;

import static premun.mps.ingrid.importer.GrammarImporter.fullIngridPipeline;

public class Python3Tests {

    @Test
    @Ignore
    public void formatExtractionTest() {

        String python3 = TestGrammars.loadPython3();

        String input = "class Person:\n" +
                "  def __init__(self, name, age):\n" +
                "    self.name = name\n" +
                "    self.age = age\n" +
                "\n" +
                "p1 = Person(\"John\", 36)\n" +
                "\n" +
                "print(p1.name)\n" +
                "print(p1.age)";

        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(python3), Collections.singletonList(input), Collections.emptyList(), false, null);
        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

        FormatInfoMapToDSLConvertor.print(grammarInfo);

    }
}

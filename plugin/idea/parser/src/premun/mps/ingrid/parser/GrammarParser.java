package premun.mps.ingrid.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.Rule;
import premun.mps.ingrid.parser.antlr.ANTLRv4Lexer;
import premun.mps.ingrid.parser.antlr.ANTLRv4Parser;
import premun.mps.ingrid.parser.exception.IngridParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GrammarParser {
    private final String rootRule;


    private ParserResult data = new ParserResult();

    public GrammarParser() {
        this.rootRule = null;
    }

    public GrammarParser(String rootRule) {
        this.rootRule = rootRule;
    }

    public void parseFile(String fileName) {
        File file = new File(fileName);

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IngridParserException("File '" + fileName + "' not found");
        }

        byte[] data = new byte[(int) file.length()];

        try {
            fis.read(data);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IngridParserException("Error while reading the file: " + e.getMessage());
        }

        parseString(new String(data));
    }

    public void parseString(String grammar) {
        ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(grammar));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        ANTLRv4Parser parser = new ANTLRv4Parser(tokens);

        CollectionErrorListener errorListener = new CollectionErrorListener();
        parser.addErrorListener(errorListener);

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        GrammarWalker listener = new GrammarWalker();
        walker.walk(listener, parser.grammarSpec());

        List<String> errors = errorListener.getErrors();
        if (errors.size() != 0) {
            throw new IngridParserException("Couldn't parse the grammar file:\n" + String.join("\n", errors));
        }

        ParserResult parseResult = listener.getParseResult();

        // Merge results with previous results
        if (parseResult.grammarName != null) {
            this.data.grammarName = parseResult.grammarName;
        }

        if (parseResult.rootRule != null) {
            this.data.rootRule = parseResult.rootRule;
        }

        this.data.fragmentLexerRules.addAll(parseResult.fragmentLexerRules);

        for (Map.Entry<String, Rule> entry : parseResult.rules.entrySet()) {
            this.data.rules.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @return raw parser result without any post processing
     */
    public ParserResult getRawParserResult() {
        return data;
    }

    public GrammarInfo resolveGrammar() {
        GrammarInfo grammarInfo = GrammarResolver.generateGrammar(this.data);
        if (rootRule != null && !rootRule.isEmpty()) {
            Rule rootRule = grammarInfo.rules.get(this.rootRule);
            if (rootRule == null) {
                throw new IllegalStateException("Specified root rule was not found");
            }
            grammarInfo.rootRule = rootRule;
        }
        return grammarInfo;
    }
}

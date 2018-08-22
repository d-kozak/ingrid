package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;
import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author dkozak
 * @see InterpretingParser
 */
public class InterpretingParserTest {

    @Test
    public void antlrOfficialExample() throws RecognitionException {
        String startRule = "s";
        String input = "abbaaabbac";

        LexerGrammar lg = new LexerGrammar(
                "lexer grammar L;\n" +
                        "A : 'a' ;\n" +
                        "B : 'b' ;\n" +
                        "C : 'c' ;\n");
        Grammar g = new Grammar(
                "parser grammar T;\n" +
                        "s : (A|B)* C ;\n",
                lg);
        LexerInterpreter lexEngine =
                lg.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexEngine);
        ParserInterpreter parser = g.createParserInterpreter(tokens);
        ParseTree t = parser.parse(g.rules.get(startRule).index);
    }

    /**
     * Checks whether sentences in set language were parsed correctly
     */
    @Test
    public void parsingSetLanguage() throws RecognitionException {
        Grammar grammar = new Grammar(TestGrammars.setGrammar);
        String input = "{a,b,{}}";
        ParseTree parseTree = InterpretingParser.parse(grammar, input, "compilationUnit");
        assertEquals(2, parseTree.getChildCount()); // set EOF
        ParseTree set = parseTree.getChild(0);

        assertEquals(7, set.getChildCount()); // { a , b , {} } <- 7 children (empty set counts as one)

        assertTrue(set.getChild(0) instanceof TerminalNode);
        assertEquals("{", ((TerminalNode) set.getChild(0)).getSymbol()
                                                          .getText());

        assertTrue(set.getChild(1) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(1)).getRuleIndex()); // 2 == index of collection rule

        assertTrue(set.getChild(2) instanceof TerminalNode);
        assertEquals(",", (((TerminalNode) set.getChild(2)).getSymbol()
                                                           .getText()));

        assertTrue(set.getChild(3) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(3)).getRuleIndex());// 2 == index of collection rule

        assertTrue(set.getChild(4) instanceof TerminalNode);
        assertEquals(",", ((TerminalNode) set.getChild(4)).getSymbol()
                                                          .getText());

        assertTrue(set.getChild(5) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(5)).getRuleIndex());// 2 == index of set rule

        ParseTree elemAsSetWrapper = set.getChild(5); // collection
        assertEquals(1, elemAsSetWrapper.getChildCount()); // collection should have one child, the inner set
        ParseTree innerSet = elemAsSetWrapper.getChild(0);
        assertTrue(innerSet instanceof ParserRuleContext);
        assertEquals(2, innerSet.getChildCount()); // inner set has just two children, the curly brackets { }

        assertTrue(set.getChild(6) instanceof TerminalNode && "}".equals(((TerminalNode) set.getChild(6)).getSymbol()
                                                                                                         .getText()));
    }

    @Test
    public void parseTwoFiles() throws RecognitionException {
        List<String> grammars = TestGrammars.loadJava();
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
        InterpretingParser.InterpretingParserResult pair = InterpretingParser.tokenizeAndParse(grammars.get(0), grammars.get(1), animalClass, "compilationUnit");

    }

    @Test
    public void parseComplexJava() throws RecognitionException {
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
        InterpretingParser.InterpretingParserResult pair = InterpretingParser.tokenizeAndParse(grammars.get(0), grammars.get(1), input, "compilationUnit");
    }

    @Test
    public void justC() throws RecognitionException {
        String cLanguage = TestGrammars.loadResource("/C.g4");
        String minilisp = TestGrammars.loadResource("/c/bt.c");
        ParseTree parseTree = InterpretingParser.parse(cLanguage, minilisp, "compilationUnit");
    }
}

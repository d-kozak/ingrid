package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.model.Quantity;
import premun.mps.ingrid.model.format.SimpleFormatInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Extracts format information from list of MatchInfo objects
 *
 * @author dkozak
 */
class FormatInfoExtractor {

    /**
     * Extracts format information from the list of MatchInfo objects
     */
    static List<SimpleFormatInfo> extractFormatInfo(List<MatchInfo> matchInfos, CommonTokenStream allTokens) {
        if (matchInfos.isEmpty())
            return new ArrayList<>();

        int originalMatchInfoSize = matchInfos.size();
        matchInfos = new ArrayList<>(matchInfos);
        matchInfos.add(createDummyNextTokenMatchInfo(matchInfos, allTokens));

        List<SimpleFormatInfo> result = new ArrayList<>();

        for (int i = 0; i < originalMatchInfoSize; i++) {
            MatchInfo currentMatchInfo = matchInfos.get(i);
            MatchInfo nextMatchInfo = matchInfos.get(i + 1);
            if (currentMatchInfo.isNotEmpty() && nextMatchInfo.isNotEmpty()) {
                result.add(extractFormatInfoFor(currentMatchInfo, nextMatchInfo, allTokens));
            } else {
                result.add(SimpleFormatInfo.UNKNOWN);
            }
        }

        return result;
    }

    /**
     * @param currentMatchInfo currently analysed matched info
     * @param nextMatchInfo    next match info
     * @return formatInfo extracted from the input
     */
    private static SimpleFormatInfo extractFormatInfoFor(MatchInfo currentMatchInfo, MatchInfo nextMatchInfo, CommonTokenStream tokens) {
        ParseTree rightmostNode = currentMatchInfo.getRightMostParseTree();
        ParseTree leftmostNode = nextMatchInfo.getLeftmostParseTree();
        Token currentToken = extractRightmostToken(rightmostNode);
        Token nextToken = extractLeftmostToken(leftmostNode);

        boolean appendedNewLine = nextToken.getLine() - currentToken.getLine() > 0;
        boolean appendSpace = !appendedNewLine && (nextToken.getCharPositionInLine() - (currentToken.getCharPositionInLine() + currentToken.getText()
                                                                                                                                           .length())) > 0;

        boolean childrenOnNewLine = false;
        boolean childrenIndented = false;

        boolean isMultipleCardinality = (currentMatchInfo.quantity == Quantity.AT_LEAST_ONE || currentMatchInfo.quantity == Quantity.ANY) && currentMatchInfo.times() > 0;
        if (isMultipleCardinality) {
            childrenOnNewLine = checkIfChildrenAreOnNewLine(currentMatchInfo, tokens);


            childrenIndented = checkIfChildrenAreIndented(currentMatchInfo, childrenOnNewLine, tokens);
        }
        return new SimpleFormatInfo(appendedNewLine, appendSpace, childrenOnNewLine, childrenIndented);

    }

    private static boolean checkIfChildrenAreOnNewLine(MatchInfo matchInfo, CommonTokenStream tokenStream) {
        //  one match only is an edge case
        if (matchInfo.matched.size() == 1) {
            List<Token> tokens = extractTokens(matchInfo.getMatchedRegion());
            // all we can check is whether there in newline before the first element of this region
            boolean newlineBeforeElement = false;
            Token firstToken = tokens.get(0);
            if (firstToken.getTokenIndex() > 0) {
                Token previousToken = tokenStream.get(firstToken.getTokenIndex() - 1);
                newlineBeforeElement = previousToken.getLine() < firstToken.getLine();
            }
            return newlineBeforeElement;
        }

        // skip the last element, the formatting of the last token after the matched region is not a reliable source of information
        // so we handle one match separately
        List<Boolean> collect = matchInfo.matched.subList(0, matchInfo.matched.size() - 1)
                                                 .stream()
                                                 .map(
                                                         list -> isNewlineAtTheEnd(tokenStream, list)
                                                 )
                                                 .collect(Collectors.toList());

        boolean allAreTrue = collect.stream()
                                    .allMatch(it -> it);
        if (allAreTrue)
            return true;
        else {
            boolean allAreFalse = collect.stream()
                                         .noneMatch(it -> it);
            if (allAreFalse)
                return false;
            else {
                throw new IllegalArgumentException("Inconsistent formatting");
            }
        }
    }

    /**
     * Checks whether there is a newline at the end of the tokens from given part of parse tree
     *
     * @param tokenStream stream of all tokens
     * @param list        parse tree which is being examined
     * @return true if there is a newline after the last token in the parse tree
     */
    private static Boolean isNewlineAtTheEnd(CommonTokenStream tokenStream, List<ParseTree> list) {
        Token currentToken = extractRightmostToken(list.get(list.size() - 1));
        for (int i = currentToken.getTokenIndex() + 1; i < tokenStream.size(); i++) {
            Token token = tokenStream.get(i);
            boolean isNormalToken = token.getChannel() == 0;
            if (isNormalToken)
                return currentToken.getLine() < token.getLine();
        }
        return false;
    }

    /**
     * We can check for indentation by comparing all tokens from the matched region with
     * the leftmost token from previous line. If their position on line is bigger, then they have to be indented
     */
    private static boolean checkIfChildrenAreIndented(MatchInfo currentMatchInfo, boolean childrenOnNewLine, CommonTokenStream tokens) {
        if (!childrenOnNewLine)
            return false;
        Token leftmostTokenInThisRegion = extractLeftmostToken(currentMatchInfo.getLeftmostParseTree());
        Token leftmostTokenOnPreviousLine = getLeftmostTokenOnLine(leftmostTokenInThisRegion.getTokenIndex(), leftmostTokenInThisRegion.getLine() - 1, tokens);
        return extractTokens(currentMatchInfo.getMatchedRegion()).stream()
                                                                 .allMatch(it -> it.getCharPositionInLine() > leftmostTokenOnPreviousLine.getCharPositionInLine());
    }

    /**
     * Gets the leftmost token from token stream on specified line, starting traversing the list from index startTokenIndex.
     *
     * @param startTokenIndex index from which to start traversing the list.
     * @param wantedLine      line on which the token should be
     * @param tokens          antlr4 stream of tokens to traverse
     * @return the leftmost token on specified line, or dummy token with line position zero
     */
    private static Token getLeftmostTokenOnLine(int startTokenIndex, int wantedLine, CommonTokenStream tokens) {
        int index = startTokenIndex - 1;
        Token right = tokens.get(startTokenIndex);
        while (index >= 0) {
            Token left = tokens.get(index);
            if (left.getLine() < right.getLine() && right.getLine() == wantedLine)
                return right;
            right = left;
            index--;
        }
        // no token found, just return dummy one
        CommonToken dummy = new CommonToken(-1);
        dummy.setCharPositionInLine(0);
        return dummy;
    }

    /**
     * Creates a mathInfo containing the token that follows immediately after the last token in the matched region.
     * It is used extract the formatting of the last element in the matched region with respect to the first element after it
     */
    private static MatchInfo createDummyNextTokenMatchInfo(List<MatchInfo> matchInfos, CommonTokenStream tokens) {
        for (int i = matchInfos.size() - 1; i >= 0; i--) {
            MatchInfo matchInfo = matchInfos.get(i);
            if (matchInfo.isNotEmpty()) {
                Token rightmostToken = extractRightmostToken(matchInfo.getRightMostParseTree());
                CommonToken dummyToken = new CommonToken(rightmostToken);
                dummyToken.setCharPositionInLine(rightmostToken.getCharPositionInLine() + rightmostToken.getText()
                                                                                                        .length() + 1);
                return new MatchInfo(null, null, Collections.singletonList(Collections.singletonList(new TerminalNodeImpl(dummyToken))));
            }
        }
        return new MatchInfo(null, null, Collections.emptyList());
    }

    /**
     * @param nodes list of AST nodes to traverse
     * @return list of all Tokens in the order in which they occur in the input
     */
    private static List<Token> extractTokens(List<ParseTree> nodes) {
        List<Token> result = new ArrayList<>();
        for (ParseTree node : nodes) {
            if (node instanceof TerminalNode) {
                result.add(((TerminalNode) node).getSymbol());
            } else if (node instanceof ParserRuleContext) {
                result.addAll(extractTokens(((ParserRuleContext) node).children));
            } else {
                throw new IllegalArgumentException("Unknown type of ParseTree node: " + node.getClass()
                                                                                            .getName());
            }
        }
        return result;
    }

    /**
     * Extracts the leftmost token in given tree
     */
    private static Token extractLeftmostToken(ParseTree tree) {
        return extractToken(tree, node -> node.getChild(0));
    }

    /**
     * Extracts the rightmost token in given tree
     */
    private static Token extractRightmostToken(ParseTree tree) {
        return extractToken(tree, node -> node.getChild(node.getChildCount() - 1));
    }

    /**
     * Extracts the first token it finds in the given parseTree, the next node to traverse is chosen by the nextNodeExtractor function
     *
     * @param tree              tree to traverse
     * @param nextNodeExtractor function to choose what is the next token to traverse
     * @return first token that is found in given parse tree when traversing using the nextNodeExtractor
     */
    private static Token extractToken(ParseTree tree, Function<ParseTree, ParseTree> nextNodeExtractor) {
        while (!(tree instanceof TerminalNode)) {
            if (tree.getChildCount() == 0) {
                throw new IllegalArgumentException("Cannot find wanted token in this tree");
            }
            tree = nextNodeExtractor.apply(tree);
        }
        return ((TerminalNode) tree).getSymbol();
    }
}

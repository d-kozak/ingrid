package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.model.Quantity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
    static List<FormatInfo> extractFormatInfo(List<MatchInfo> matchInfos, CommonTokenStream tokens) {
        if (matchInfos.isEmpty())
            return new ArrayList<>();

        int originalMatchInfoSize = matchInfos.size();
        matchInfos = new ArrayList<>(matchInfos);
        matchInfos.add(createDummyNextTokenMatchInfo(matchInfos, tokens));

        List<FormatInfo> result = new ArrayList<>();

        for (int i = 0; i < originalMatchInfoSize; i++) {
            MatchInfo currentMatchInfo = matchInfos.get(i);
            MatchInfo nextMatchInfo = matchInfos.get(i + 1);
            if (currentMatchInfo.matched.size() > 0 && nextMatchInfo.matched.size() > 0) {
                result.add(extractFormatInfoFor(currentMatchInfo, nextMatchInfo));
            } else {
                result.add(FormatInfo.NULL_INFO);
            }
        }

        return result;
    }

    /**
     * @param currentMatchInfo currently analysed matched info
     * @param nextMatchInfo    next match info
     * @return formatInfo extracted from the input
     */
    private static FormatInfo extractFormatInfoFor(MatchInfo currentMatchInfo, MatchInfo nextMatchInfo) {
        ParseTree rightmostNode = currentMatchInfo.matched.get(currentMatchInfo.matched.size() - 1);
        ParseTree leftmostNode = nextMatchInfo.matched.get(0);
        Token currentToken = extractRightmostToken(rightmostNode);
        Token nextToken = extractLeftmostToken(leftmostNode);

        boolean appendedNewLine = nextToken.getLine() - currentToken.getLine() > 0;
        boolean appendSpace = (nextToken.getCharPositionInLine() - (currentToken.getCharPositionInLine() + currentToken.getText()
                                                                                                                       .length())) > 0;

        boolean childrenOnNewLine = false;
        boolean childrenIndented = false;

        boolean isMultipleCardinality = (currentMatchInfo.quantity == Quantity.AT_LEAST_ONE || currentMatchInfo.quantity == Quantity.ANY) && currentMatchInfo.times > 0;
        if (isMultipleCardinality) {
            Set<Integer> lineNumbers = extractTokens(currentMatchInfo.matched).stream()
                                                                              .map(Token::getLine)
                                                                              .collect(Collectors.toSet());
            childrenOnNewLine = lineNumbers.size() > 1;

            // TODO find a better way to extract children indentation
            childrenIndented = childrenOnNewLine && extractTokens(currentMatchInfo.matched).stream()
                                                                                           .allMatch(it -> it.getCharPositionInLine() > 0);
        }
        return new FormatInfo(currentMatchInfo.rule, appendedNewLine, appendSpace, childrenOnNewLine, childrenIndented);

    }

    /**
     * Creates a mathInfo containing the token that follows immediately after the last token in the matched region.
     * It is used extract the formatting of the last element in the matched region with respect to the first element after it
     */
    private static MatchInfo createDummyNextTokenMatchInfo(List<MatchInfo> matchInfos, CommonTokenStream tokens) {
        for (int i = matchInfos.size() - 1; i >= 0; i--) {
            MatchInfo matchInfo = matchInfos.get(i);
            if (!matchInfo.matched.isEmpty()) {
                Token rightmostToken = extractRightmostToken(matchInfo.matched.get(matchInfo.matched.size() - 1));
                CommonToken dummyToken = new CommonToken(rightmostToken);
                dummyToken.setCharPositionInLine(rightmostToken.getCharPositionInLine() + rightmostToken.getText()
                                                                                                        .length() + 1);
                return new MatchInfo(null, null, -1, Collections.singletonList(new TerminalNodeImpl(dummyToken)));
            }
        }
        return new MatchInfo(null, null, -1, Collections.emptyList());
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

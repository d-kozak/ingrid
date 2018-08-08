package premun.mps.ingrid.formatter;

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


    private static MatchInfo createDummyNextTokenMatchInfo(List<MatchInfo> matchInfos, CommonTokenStream tokens) {
        MatchInfo lastMatchInfo = matchInfos.get(matchInfos.size() - 1);
        if (lastMatchInfo.matched.size() > 0) {
            Token rightmostToken = extractRightmostToken(lastMatchInfo.matched.get(lastMatchInfo.matched.size() - 1));
            int rightmostTokenIndex = rightmostToken.getTokenIndex();
            if (rightmostTokenIndex < tokens.size() - 1) {
                return new MatchInfo(null, null, -1, Collections.singletonList(new TerminalNodeImpl(tokens.get(rightmostTokenIndex + 1))));
            } else
                return new MatchInfo(null, null, -1, Collections.emptyList());
        } else return new MatchInfo(null, null, -1, Collections.emptyList());
    }

    /**
     * Extract format information from list of MatchInfo objects
     * For each pair of MatchInfos it compares the rightmost
     * from the left element to the leftmost of the right element.
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
                ParseTree rightmostNode = currentMatchInfo.matched.get(currentMatchInfo.matched.size() - 1);
                ParseTree leftmostNode = nextMatchInfo.matched.get(0);
                Token currentToken = extractRightmostToken(rightmostNode);
                Token nextToken = extractLeftmostToken(leftmostNode);

                int appendedNewLines = nextToken.getLine() - currentToken.getLine();
                int indentation = Integer.max(
                        nextToken.getCharPositionInLine() - (currentToken.getCharPositionInLine() + currentToken.getText()
                                                                                                                .length()),
                        0);

                boolean childrenOnNewLine = false;
                boolean childrenIndented = false;

                boolean isMultipleCardinality = (currentMatchInfo.quantity == Quantity.AT_LEAST_ONE || currentMatchInfo.quantity == Quantity.ANY) && currentMatchInfo.times > 0;
                if (isMultipleCardinality) {
                    Set<Integer> lineNumbers = extractTokens(currentMatchInfo.matched).stream()
                                                                                      .map(Token::getLine)
                                                                                      .collect(Collectors.toSet());
                    childrenOnNewLine = lineNumbers.size() > currentMatchInfo.times;

                    // TODO verify that this works in every situation
                    childrenIndented = childrenOnNewLine && extractTokens(currentMatchInfo.matched).stream()
                                                                                                   .allMatch(it -> it.getCharPositionInLine() > 0);
                }


                result.add(new FormatInfo(currentMatchInfo.rule, appendedNewLines, indentation, childrenOnNewLine, childrenIndented));
            } else {
                result.add(FormatInfo.NULL_INFO);
            }
        }

        return result;
    }

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

    private static Token extractLeftmostToken(ParseTree leftmostNode) {
        return extractToken(leftmostNode, node -> node.getChild(0));
    }

    private static Token extractRightmostToken(ParseTree rightmostNode) {
        return extractToken(rightmostNode, node -> node.getChild(node.getChildCount() - 1));
    }

    static private Token extractToken(ParseTree parseTree, Function<ParseTree, ParseTree> nextNodeExtractor) {
        while (!(parseTree instanceof TerminalNode)) {
            if (parseTree.getChildCount() == 0) {
                throw new IllegalArgumentException("Cannot find wanted token in this tree");
            }
            parseTree = nextNodeExtractor.apply(parseTree);
        }
        return ((TerminalNode) parseTree).getSymbol();
    }
}

package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.MatchInfo;

import java.util.ArrayList;
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
     * Extract format information from list of MatchInfo objects
     * For each pair of MatchInfos it compares the rightmost
     * from the left element to the leftmost of the right element.
     */
    static List<FormatInfo> extractFormatInfo(List<MatchInfo> matchInfos) {
        if (matchInfos.size() < 2)
            return new ArrayList<>();

        List<FormatInfo> formatInfosA = new ArrayList<>();

        for (int i = 0; i < matchInfos.size() - 1; i++) {
            MatchInfo left = matchInfos.get(i);
            MatchInfo right = matchInfos.get(i + 1);
            if (left.matched.size() > 0 && right.matched.size() > 0) {
                ParseTree rightmostNode = left.matched.get(left.matched.size() - 1);
                ParseTree leftmostNode = right.matched.get(0);
                Token current = extractRightmostToken(rightmostNode);
                Token next = extractLeftmostToken(leftmostNode);

                int appendedNewLines = next.getLine() - current.getLine();
                int indentation = Integer.max(
                        next.getCharPositionInLine() - (current.getCharPositionInLine() + current.getText()
                                                                                                 .length()),
                        0);
                formatInfosA.add(new FormatInfo(left.rule, appendedNewLines, indentation, false, false));
            } else {
                formatInfosA.add(FormatInfo.NULL_INFO);
            }
        }

        List<FormatInfo> formatInfosB = new ArrayList<>();
        for (int i = 1; i < matchInfos.size(); i++) {
            MatchInfo left = matchInfos.get(i - 1);
            MatchInfo right = matchInfos.get(i);
            if (left.matched.size() > 0 && right.matched.size() > 0) {
                ParseTree rightmostNode = left.matched.get(left.matched.size() - 1);
                ParseTree leftmostNode = right.matched.get(0);
                Token previous = extractRightmostToken(rightmostNode);
                Token current = extractLeftmostToken(leftmostNode);

                int indentation = Integer.max(
                        current.getCharPositionInLine() - (previous.getCharPositionInLine() + previous.getText()
                                                                                                      .length()),
                        0);

                boolean childrenOnNewLine = false;
                boolean childrenIndented = false;

                boolean isMultipleCardinality = right.times > 1;
                if (isMultipleCardinality) {
                    Set<Integer> lineNumbers = extractTokens(right.matched).stream()
                                                                           .map(Token::getLine)
                                                                           .collect(Collectors.toSet());
                    childrenOnNewLine = lineNumbers.size() == right.times;
                    childrenIndented = extractTokens(right.matched).stream()
                                                                   .allMatch(it -> it.getCharPositionInLine() > previous.getCharPositionInLine());
                }

                formatInfosB.add(new FormatInfo(right.rule, 0, indentation, childrenOnNewLine, childrenIndented));
            } else {
                formatInfosB.add(FormatInfo.NULL_INFO);
            }
        }


        if (formatInfosA.size() != formatInfosB.size()) {
            throw new IllegalStateException("FormatInfoLists should have the same size");
        }

        List<FormatInfo> result = new ArrayList<>();
        for (int i = 0; i < matchInfos.size(); i++) {
            if (i == 0) {
                result.add(formatInfosA.get(0));
            } else if (i == matchInfos.size() - 1) {
                result.add(formatInfosB.get(i - 1));
            } else {
                result.add(formatInfosA.get(i)
                                       .merge(formatInfosB.get(i - 1)));
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

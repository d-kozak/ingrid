package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.MatchInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class FormatInfoExtractor {

    static List<FormatInfo> extractFormatInfo(List<MatchInfo> matchInfos) {
        List<FormatInfo> formatInfos = new ArrayList<>();

        for (int i = 0; i < matchInfos.size() - 1; i++) {
            MatchInfo left = matchInfos.get(i);
            MatchInfo right = matchInfos.get(i + 1);
            if (left.matched.size() > 0 && right.matched.size() > 0) {
                ParseTree rightmostNode = left.matched.get(left.matched.size() - 1);
                ParseTree leftmostNode = right.matched.get(0);
                Token current = extractToken(rightmostNode, node -> node.getChild(node.getChildCount() - 1));
                Token next = extractToken(leftmostNode, node -> node.getChild(0));

                int appendedNewLines = next.getLine() - current.getLine();
                int indentation = next.getCharPositionInLine() - (current.getCharPositionInLine() + current.getText()
                                                                                                           .length());

                formatInfos.add(new FormatInfo(appendedNewLines, indentation));
            } else {
                formatInfos.add(FormatInfo.NULL_INFO);
            }
        }
        return formatInfos;
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

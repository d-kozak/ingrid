package premun.mps.ingrid.serialization;

/**
 * @author dkozak
 */
public class RegexSerializer {

    /**
     * When serializing into Antlr4 format, regex expressions have to be tweaked.
     *
     * @param regex regex to be modified
     * @return antlr4 version of the input regex
     * TODO fix problems with java regexes
     */
    public static String serializeRegex(String regex) {
        StringBuilder stringBuilder = new StringBuilder();

        int depth = 0;
        boolean quotesStarted = false;

        for (int i = 0; i < regex.length(); i++) {
            char c = regex.charAt(i);
            if (c != '\'') {
                if (c == '[') {
                    depth++;
                } else if (c == ']')
                    depth--;

                if (!isSpecialCharacter(c) && !quotesStarted && depth == 0) {
                    stringBuilder.append("'");
                    quotesStarted = true;
                } else if (isSpecialCharacter(c) && quotesStarted) {
                    stringBuilder.append("'");
                    quotesStarted = false;
                }
            } else {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    private static boolean isSpecialCharacter(char c) {
        String specialChars = ".[]{}()*+-?^$|~";
        return specialChars.contains(c + "");
    }
}

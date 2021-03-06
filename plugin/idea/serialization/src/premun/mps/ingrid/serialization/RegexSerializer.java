package premun.mps.ingrid.serialization;

/**
 * Preprocessor for regexes that is used when they are serialize back to the g4 grammar file
 *
 * @author dkozak
 */
class RegexSerializer {

    /**
     * When serializing into Antlr4 format, regex expressions have to be tweaked.
     *
     * @param regex regex to be modified
     * @return antlr4 version of the input regex
     */
    static String serializeRegex(String regex) {
        // rewrite the syntax for negation
        regex = regex.replaceAll("\\[\\^", "~[");

        StringBuilder stringBuilder = new StringBuilder();
        int depth = 0;
        boolean quotesStarted = false;

        for (int i = 0; i < regex.length(); i++) {
            char c = regex.charAt(i);
            switch (c) {
                case '[':
                    depth++;
                    break;
                case ']':
                    depth--;
                    break;
                case '\'':
                    if (depth == 0) {
                        stringBuilder.append('\\'); // ' needs to be escaped if standing outside of [ ]
                    }
                    break;
                case '\\':
                    if (depth == 0) {
                        if (i == regex.length() - 1) {
                            throw new IllegalArgumentException("Escape without any following character");
                        }

                        if (quotesStarted) {
                            // finish previous quotes
                            quotesStarted = false;
                            stringBuilder.append("'");
                        }

                        stringBuilder.append("'");

                        char nextChar = regex.charAt(++i);
                        if (nextChar == '\\')
                            stringBuilder.append("\\\\");
                        else if (nextChar == '\'')
                            stringBuilder.append("\\'");
                        else stringBuilder.append(nextChar);

                        stringBuilder.append("'");
                        continue;
                    }
                    break;
            }

            if (!isSpecialCharacter(c) && depth == 0 && !quotesStarted) {
                stringBuilder.append("'");
                quotesStarted = true;
            } else if (isSpecialCharacter(c) && quotesStarted) {
                stringBuilder.append("'");
                quotesStarted = false;
            }

            stringBuilder.append(c);
        }

        if (quotesStarted)
            stringBuilder.append("'");

        return stringBuilder.toString();
    }

    private static boolean isSpecialCharacter(char c) {
        String specialChars = ".[]{}()*+-?^$|~";
        return specialChars.contains(c + "");
    }
}

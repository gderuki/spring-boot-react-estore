package lv.psanatovs.api.util;

public class StringUtils {

    public static String toKebabCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        StringBuilder kebabCase = new StringBuilder();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == ' ' || c == '_') {
                kebabCase.append('-');
            } else if (Character.isUpperCase(c)) {
                if (i > 0 && kebabCase.charAt(kebabCase.length() - 1) != '-') {
                    kebabCase.append('-');
                }
                kebabCase.append(Character.toLowerCase(c));
            } else {
                kebabCase.append(c);
            }
        }
        return kebabCase.toString();
    }
}
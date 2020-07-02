import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapUtils {
    private final static String allowedSymbols = "qwertyuiopasdfghjklzxcvbnm" +
            "йцукенгшщзхъфывапролджэячсмитьбюё-_1234567890";

    public static String[] findCollocation(String text) {
        text = text.toLowerCase();
        List<String> strings = new ArrayList<>();

        int firstIndex = allowedSymbols.indexOf(text.charAt(0)) != -1 ? 0 :
                skip(0, text);
        int indexOfSpaceBetweenWords = 0;
        int index = firstIndex;

        while (index < text.length()) {

            if (index == text.length() - 1) {
                if (indexOfSpaceBetweenWords != 0)
                    strings.add(text.substring(firstIndex));
                break;
            }

            if (text.charAt(index) == ' ' || text.charAt(index) == '\n') {
                if (allowedSymbols.indexOf(text.charAt(skip(index, text))) != -1 &&
                        text.charAt(skip(index, text)) != '-') {
                    if (indexOfSpaceBetweenWords == 0)
                        indexOfSpaceBetweenWords = index;
                    else {
                        strings.add(text.substring(firstIndex, index));
                        firstIndex = skip(indexOfSpaceBetweenWords, text);
                        indexOfSpaceBetweenWords = index;
                    }
                }
            }

            if (!(text.charAt(index) == ' ' || text.charAt(index) == '\n')
                    && (allowedSymbols.indexOf(text.charAt(index)) == -1 || text.charAt(index) == '-')) {
                if (indexOfSpaceBetweenWords != 0) {
                    strings.add(text.substring(firstIndex, index));
                    indexOfSpaceBetweenWords = 0;
                }
                firstIndex = allowedSymbols.indexOf(text.charAt(index + 1)) != -1 ? index + 1 :
                        skip(index + 1, text);
                index = firstIndex;
                continue;
            }

            index = skip(index, text);
        }

        String[] stringArr = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            stringArr[i] = strings.get(i);
        }
        return stringArr;
    }

    private static int skip(int index, String text) {
        if (allowedSymbols.indexOf(text.charAt(index)) != -1) {
            do {
                if (index == text.length() - 1) break;
                index++;
            } while (allowedSymbols.indexOf(text.charAt(index)) != -1);
            return index;
        } else if (allowedSymbols.indexOf(text.charAt(index)) == -1) {
            do {
                if (index == text.length() - 1) break;
                index++;
            } while (allowedSymbols.indexOf(text.charAt(index)) == -1);
            return index;
        }

        do {
            if (index == text.length() - 1) break;
            index++;
        } while (text.charAt(index) == ' ' || text.charAt(index) == '\n');
        return index;
    }

    public static Object[] choose(MyMap<String, Collocation> map) {
        Collocation[] cols = new Collocation[map.size()];
        int i = 0;
        for (Collocation c : map) {
            cols[i] = c;
            i++;
        }
        Arrays.sort(cols, cols[0].comparator());
        return cols;
    }
}

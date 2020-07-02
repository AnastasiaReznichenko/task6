import java.util.HashMap;

public class Solution {

    public interface Chooser {
        Object[] choose(MyMap<String, Collocation> map);
    }

    public interface ChooserFromSystemMap {
        Object[] choose(HashMap<String, Collocation> map);
    }

    public interface CollocationFinder {
        String[] find(String text);
    }

    public Object[] findCollocations(String text, CollocationFinder cf,Chooser chooser) {
        /*
         * Средняя длина слова в русском языке 6 букв
         * Среднее колличество слов в предложении 10 => 60 букв
         * Между каждым словом есть пробел и по 1-2 знака препинаия на предложение
         * Получается в среднем предложение состоит из 75 символов и 8 словосочетанй (с учётом знаков препинания)
         * Значит объём словаря для оптимальной работы должен быть равен 8 * l / 75, где
         * l - колличество символов в тексте
         *
         * P.s В предыдущей реализации хэш коды распределялись неравномерно, потому было приняторешение увеличить
         * объём словаря в 3 раза
         */
        MyMap<String, Collocation> map = new MyMap<>(text.length() * 8 / 25);
        String[] collocations = cf.find(text);
        for (String string : collocations) {
            string = string.toLowerCase();
            if (map.containsKey(string)) {
                map.get(string).increaseFrequency();
            } else {
                map.add(string, stringToCollocation(string));
            }
        }

        return chooser.choose(map);
    }

    public Object[] findCollocationsBySystemMap(String text, CollocationFinder cf, ChooserFromSystemMap chooser) {

        HashMap<String, Collocation> map = new HashMap<>(text.length() * 8 / 25);
        String[] collocations = cf.find(text);
        for (String string : collocations) {
            string = string.toLowerCase();
            if (map.containsKey(string)) {
                map.get(string).increaseFrequency();
            } else {
                map.put(string, stringToCollocation(string));
            }
        }

        return chooser.choose(map);
    }

    private Collocation stringToCollocation(String string) {
        return new Collocation(string, 1);
    }
}

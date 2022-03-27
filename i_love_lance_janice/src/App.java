import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        final int l = 26;

        final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final char[] cypher = "zyxwvutsrqponmlkjihgfedcba".toCharArray();

        final List<Character> alphabetList = new ArrayList<>();
        final List<Character> cypherList = new ArrayList<>();

        for (int i = 0; i < l; i++) {
            alphabetList.add(alphabet[i]);
            cypherList.add(cypher[i]);
        }

        String asd = new String("Yvzs! I xzm'g yvorvev Lzmxv olhg srh qly zg gsv xlolmb!!");
        String out = new String();
        for (int i = 0; i < asd.length(); i++) {
            if (Character.isLowerCase(asd.charAt(i))) {
                out += alphabetList.get(cypherList.indexOf(asd.charAt(i)));
            } else {
                out += asd.charAt(i);
            }
        }
        System.out.println(out);

    }

    public static int charindex(char[] arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c) {
                return i;
            }
        }
        return -1;
    }
}

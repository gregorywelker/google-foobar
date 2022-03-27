public class App {
    public static void main(String[] args) throws Exception {

        String[] l = { "1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0" };

        Solution.solution(l);

        for (int i = 0; i < l.length; i++) {
            System.out.println(l[i]);
        }
    }

    // This class solves the version ordering problem
    public class Solution {

        // Maximum number of digits by parts of the version numbers
        static int[] maxValues = { 0, 0, 0 };
        // Number of parts of the version number
        static int versionParts = maxValues.length;

        public static String[] solution(String[] l) {

            // Find the maximum digit values by part
            for (int i = 0; i < l.length; i++) {
                String[] split = l[i].split("\\.");
                for (int j = 0; j < split.length; j++) {
                    if (split[j].length() > maxValues[j]) {
                        maxValues[j] = split[j].length();
                    }
                }
            }
            // Compare version values, if the two values are equal then place the shorter
            // version number before the other, if they are not equal then swap them
            // accordingly
            for (int i = 0; i < l.length; i++) {
                for (int j = 0; j < l.length; j++) {
                    int first = getCompValue(l[i]);
                    int second = getCompValue(l[j]);
                    if (first < second) {
                        swap(l, i, j);
                    } else if (first == second && l[i].length() < l[j].length()) {
                        swap(l, i, j);
                    }
                }
            }
            return l;
        }

        // Calculate compare values of the version numbers, we add as many zeros before
        // each version section as much is missing from the maximum number of digits,
        // version numbers are assumed to be well-formed, in other cases additional
        // checking can be implemented here
        public static int getCompValue(String version) {
            String[] splitVersion = new String[versionParts];
            String[] temp = version.split("\\.");
            String ret = "";

            for (int i = 0; i < splitVersion.length; i++) {
                if (temp.length > i) {
                    splitVersion[i] = temp[i];
                } else {
                    splitVersion[i] = "0";
                }
                int len = splitVersion[i].length();
                for (int j = 0; j < maxValues[i] - len; j++) {
                    splitVersion[i] = "0" + splitVersion[i];
                }
                ret += splitVersion[i];
            }
            return Integer.parseInt(ret);
        }

        // Swap two strings
        public static void swap(String[] l, int a, int b) {
            String temp = l[a];
            l[a] = l[b];
            l[b] = temp;
        }
    }

}
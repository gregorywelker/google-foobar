import java.math.BigInteger;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(Solution.solution(
                "15"));
    }

    // This class solves the pellet division problem
    public class Solution {

        public static int solution(String n) {
            // Converting the received number to biginteger to be able to handle very large
            // numbers
            BigInteger num = new BigInteger(n);
            int counter = 0;

            // Loop till the received number is not one
            while (!num.equals(BigInteger.ONE)) {

                // Check if the number is divisible by two, the last bit needs to be 0 for that
                if (!num.testBit(0)) {
                    // If divisible, then divide it
                    num = num.shiftRight(1);
                } else {
                    // If the number is not divisible then substract one making it even, then divide
                    // it by 2
                    BigInteger temp = num.subtract(BigInteger.ONE).shiftRight(1);

                    // Check whether the temp number is divisible by two if so then substract one
                    // from the original, if it is not divisible then add one to the original number
                    // because by incrementing the original number now we will be able to do two
                    // consevutive divisions, requiring less steps overall
                    if (!temp.testBit(0)) {
                        num = num.subtract(BigInteger.ONE);
                    } else {
                        num = num.add(BigInteger.ONE);
                    }
                }
                // Number 3 is kind of a special case, if we reach 3 then simply increment the
                // counter by two and end set the number to 1
                if (num.equals(new BigInteger("3"))) {
                    counter += 2;
                    num = BigInteger.ONE;
                }
                counter++;
            }
            return counter;
        }
    }
}
import java.math.*;

public class FibonacciUpdate {

    public static void main(String[] args){

        for(int i = 1; i < 100; i++) {
            BigInteger a = FibLoopBig(i-1);
            BigInteger b = FibMatrixBig(i-1);
            long c = FibFormula(i);
            BigInteger d = FibFormulaBig(i);
            //System.out.println( i +":" + " "+ a + " " + b + " " + c + " " + d );
        }

        BigInteger a = FibLoopBig(97);
        BigInteger b = FibMatrixBig(97);
        System.out.println( "x = 97\n" + "LoopBig   " + a + "\n" + "MatrixBig " + b);

        a = FibLoopBig(301);
        b = FibMatrixBig(301);
        System.out.println( "x = 300\n" + "LoopBig   " + a + "\n" + "MatrixBig " + b);

        a = FibLoopBig(20000);
        b = FibMatrixBig(20000);
        System.out.println( "x = 2000\n" + "LoopBig   " + a + "\n" + "MatrixBig " + b);
    }


    public static BigInteger FibLoopBig(long x){

        //if x is less than 2, return 1 as answer
        if(x < 2)
            return BigInteger.ONE;
        else{
            //initialize variables
            BigInteger secondToLast = BigInteger.ONE;
            BigInteger last = BigInteger.ONE;
            BigInteger current = BigInteger.ZERO;
            //loop summing last two numbers until at x, and swapping a with b and b with c
            for(int i = 2; i <= x; i++){
                current = secondToLast.add(last);
                secondToLast = last;
                last = current;
            }
            //return answer
            return current;

        }
    }

    //wrapper function
    public static BigInteger FibMatrixBig(long x){

        //declare result variable
        BigInteger result;

        //initialize  matrices
        BigInteger originalMatrix[][] = {{BigInteger.ONE, BigInteger.ONE},
                {BigInteger.ONE, BigInteger.ZERO}};
        BigInteger lastMatrix[][] = {{BigInteger.ONE, BigInteger.ZERO},
                {BigInteger.ZERO, BigInteger.ONE}};

        //call helper function
        MatrixPower(originalMatrix, lastMatrix, x);

        //find and return result
        result = lastMatrix[0][0];
        return result;
    }

    //use matrix multiplication to find result function
    static void MatrixPower(BigInteger matrix[][], BigInteger resultMatrix[][], long x){

        //declare temp variable
        long temp;

        //create temp matrix
        BigInteger tempMatrix[][] = {{matrix[0][0], matrix[0][1]},
                {matrix[1][0], matrix[1][1]}};

        //declare variables to hold values
        BigInteger topLeft, topRight, bottomLeft, bottomRight;

        for(temp = x; temp > 0; temp = temp/2){

            //check if leftmost bit is 1
            if(temp % 2 == 1){
                //multiply matrices
                topLeft = resultMatrix[0][0].multiply(tempMatrix[0][0]).add(resultMatrix[0][1].multiply(tempMatrix[1][0]));
                topRight = resultMatrix[0][0].multiply(tempMatrix[0][1]).add(resultMatrix[0][1].multiply(tempMatrix[1][1]));
                bottomLeft = resultMatrix[1][0].multiply(tempMatrix[0][0]).add(resultMatrix[1][1].multiply(tempMatrix[1][0]));
                bottomRight = resultMatrix[1][0].multiply(tempMatrix[0][1]).add(resultMatrix[1][1].multiply(tempMatrix[1][1]));
                //set values
                resultMatrix[0][0] = topLeft;
                resultMatrix[0][1] = topRight;
                resultMatrix[1][0] = bottomLeft;
                resultMatrix[1][1] = bottomRight;
            }

            //square temp matrix
            topLeft = tempMatrix[0][0].multiply(tempMatrix[0][0]).add(tempMatrix[0][1].multiply(tempMatrix[1][0]));
            topRight = tempMatrix[0][0].multiply(tempMatrix[0][1]).add(tempMatrix[0][1].multiply(tempMatrix[1][1]));
            bottomLeft = tempMatrix[1][0].multiply(tempMatrix[0][0]).add(tempMatrix[1][1].multiply(tempMatrix[1][0]));
            bottomRight = tempMatrix[1][0].multiply(tempMatrix[0][1]).add(tempMatrix[1][1].multiply(tempMatrix[1][1]));
            //set values
            tempMatrix[0][0] = topLeft;
            tempMatrix[0][1] = topRight;
            tempMatrix[1][0] = bottomLeft;
            tempMatrix[1][1] = bottomRight;
        }

    }

    //formula from here: http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fibonacci/fibFormula.html
    public static long FibFormula(long x){
        //calculate phi and its negative using doubles
        double phi = (Math.sqrt(5) + 1) / 2;
        double negPhi = (1 - Math.sqrt(5)) / 2;
        //use formula for answer
        double answer = ((Math.pow(phi, x) - Math.pow(negPhi, x)) / Math.sqrt(5));
        //return by rounding
        return Math.round(answer);
    }

    //got help from here: https://www.tutorialspoint.com/java/math/java_math_bigdecimal.htm
    public static BigInteger FibFormulaBig(long x){

        BigInteger answer;
        //find number of digits to help calculate precision, use approx golden ratio
        int numOfDigits = (int) Math.ceil((x * Math.log10(1.6180339887498948482045868343656)) - ((Math.log10(5)) / 2));
        //set context for precision
        MathContext mc = new MathContext((int) numOfDigits + 4);
        //ensure digits is at least 1, will always be
        if(numOfDigits == 0)
            numOfDigits = 1;
        //create original variables
        BigDecimal one = new BigDecimal(1);
        BigDecimal two =  new BigDecimal(2);
        BigDecimal five = new BigDecimal(5);
        BigDecimal root = five.sqrt(mc);
        BigDecimal phi = (one.add(root)).divide(two);
        BigDecimal negPhi = (one.subtract(root).divide(two));
        //perform operations on variables
        BigDecimal first = phi.pow((int) x);
        BigDecimal second = negPhi.pow((int) x);
        BigDecimal third = first.subtract(second);
        BigDecimal four = third.divide(root, mc);

        answer = four.toBigInteger();

        return answer;








    }

}

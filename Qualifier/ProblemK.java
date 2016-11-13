package Qualifier;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Luke Craig
 * @Date: Sep 24, 2016
 */

public class ProblemK {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int x = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < x; i++) {
                int[] z = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                System.out.println(output(z));
            }
        }

    }
    
    public static String output(int[] z) {
        int a = z[0], b = z[2], c = z[4], d = z[1], e = z[3], f = z[5];
        int M = a * e - d * b;
        int xt = c * e - b * f;
        int yt = a * f - c * d;
        if (M == 0) {
            if (xt != 0)
                return "?";
            String ret = null;
            for (int x = 1; x < c/a; x++){
                if ((c - a*x) % b == 0){
                    if (ret == null)
                        ret = ""+x+" "+((c - a*x) / b);
                    else
                        return "?";
                }
            }
            if (ret == null)
            	return "?";
            return ret;
        }
        if (xt % M != 0 || yt % M != 0) {
            return "?";
        }
        if(M > 0){
        	if (xt <= 0 || yt <= 0)
        		return "?";
        }else {
        	if (xt >= 0 || yt >= 0)
        		return "?";
        }
        return ((int) (xt / M) + " " + ((int) yt / M));
    }

}
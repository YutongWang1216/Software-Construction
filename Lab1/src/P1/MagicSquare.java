package P1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MagicSquare {
    public static int N = 200;
    public static int square[][] = new int[N][N];
    public static Set<Integer> numDic = new HashSet<>();
    public static void main(String[] args) throws IOException{
        for (int i = 1; i <= 5; i++) {
            System.out.println("Square No." + i + ":");
            if (isLegalMagicSquare("./src/P1/txt/" + i + ".txt"))
                System.out.println("True.");
            else
                System.out.println("False.");
            numDic.clear();
        }
        
        System.out.print("Input n(must be a positive odd number): ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.close();
        
        if (!generateMagicSquare(n))
        	return;
        System.out.println("Square No.6:");
        if (isLegalMagicSquare("./src/P1/txt/6.txt"))
        	System.out.println("True.");
        else
        	System.out.println("False.");
    }
    
    public static boolean isLegalNumber(String word) {
    	if (word.matches("[0-9]+"))
    		return true;
    	return false;
    }
    
    public static boolean isLegalMagicSquare(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader reader = new FileReader(file);
        BufferedReader bfReader = new BufferedReader(reader);

        //Arrays.fill(flag, false);
        
        String myLine = "";
        int value;
        int i = 0, j = 0;
        int colNum = -1;
        
        while ((myLine = bfReader.readLine()) != null) {
            String[] words = myLine.split("\t");
            
            for (j = 0; j < words.length; j++) {
            	if (!isLegalNumber(words[j])) {
            		System.out.println("Wrong format!");
                    bfReader.close();
            		return false;
            	}
            	
                value = Integer.valueOf(words[j].trim());
//                if (value < 0) {
//                    System.out.println("There's a negative number!");
//                    bfReader.close();
//                    return false;
//                }
                if (numDic.contains(value)) {
                    System.out.println("The number " + value + " appears more than one time!");
                    bfReader.close();
                    return false;
                }
                
                square[i][j] = value;
                numDic.add(value);
            }

            if (colNum == -1)
            	colNum = words.length;
            else if (words.length != colNum) {
            	System.out.println("The data is not a matrix!");
                bfReader.close();
            	return false;
            }
            
            i++;
        }
        bfReader.close();
        
        if (i != j) {
            System.out.println("The number of rows and columns of the square are unequal!");
            return false;
        }
        int n = i;
        
        int row_sum = 0, col_sum = 0, diag1_sum = 0, diag2_sum = 0;
        
        for (i = 0; i < n; i++) {
            diag1_sum += square[i][i];
            diag2_sum += square[i][n - i - 1];
        }
        if (diag1_sum != diag2_sum)
            return false;
        
        int sum = diag1_sum;
        
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                row_sum += square[i][j];
                col_sum += square[j][i];
            }
            
            if (row_sum != sum || col_sum != sum)
                return false;
            
            row_sum = 0;
            col_sum = 0;
        }
        
        return true;
    }
    
    public static boolean generateMagicSquare(int n) throws IOException { //ÓÅÑÅµØÍË³ö
    	if (n < 0 || n % 2 == 0) {
    		System.out.println("False, n must be a positive odd numer!");
    		return false;
    	}
    	int magic[][] = new int[n][n];
    	int row = 0, col = n / 2, i, j, square = n * n;
    	for (i = 1; i <= square; i++) {
	    	magic[row][col] = i;
	    	if (i % n == 0)
	    		row++;
	    	else {
	    		if (row == 0)
	    			row = n - 1;
	    		else
	    			row--;
	    		if (col == (n - 1))
	    			col = 0;
	    		else
	    			col++;
	    	}
	    }
    	
    	for (i = 0; i < n; i++) {
	    	for (j = 0; j < n; j++)
	    		System.out.print(magic[i][j] + "\t");
	    	System.out.println();
    	}
    	
    	File file = new File("./src/P1/txt/6.txt");
    	PrintWriter out = new PrintWriter(file);
    	for (i = 0; i < n; i++) {
    		for (j = 0; j < n; j++)
    			out.print(magic[i][j] + "\t");
    		out.println();
    	}
    	out.close();
    	
    	return true;
    }
}

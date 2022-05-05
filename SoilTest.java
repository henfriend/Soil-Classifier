import java.util.*;
import java.io.*;

// class containing methods to determine whether soil can drain water
public class SoilTest {
   // returns true if file can drain water and false otherwise
   public static boolean drains(String f) throws FileNotFoundException {
      File file = new File(f);
      Scanner fileScan = new Scanner(file);

      // variables to store width and depth of soil file
      int depth = 0;
      int width = 0;

      // scan through file and increment width and depth to correct integers
      while(fileScan.hasNextLine()) {
         String line = fileScan.nextLine();
         if(width == 0) {
            Scanner intScan = new Scanner(line);
            while (intScan.hasNextInt()) {
               intScan.nextInt();
               width++;
            }
         }
         depth++;
      }

      // store width * depth into variable size
      int size = width * depth;

      // create a UF of the same size as the file
      WeightedQuickUnionPC uf = new WeightedQuickUnionPC(size);

      // reset the file scanner
      fileScan.reset();
      fileScan = new Scanner(file);

      // initialize auxiliary arrays to length of soil width
      int[] prev = new int[width];
      int[] firstLine = new int[width];

      // fill array with each int of first line of file
      if(fileScan.hasNextLine()) {
         String line = fileScan.nextLine();
         Scanner intScan = new Scanner(line);
         for(int i = 0; intScan.hasNextInt() && i < width; i++) {
            prev[i] = intScan.nextInt();
            firstLine[i] = prev[i];
         }
      }

      // set depth to 1
      depth = 1;

      // for every line of file (starting at second)
      while(fileScan.hasNextLine()) {
         String line = fileScan.nextLine();
         Scanner intScan = new Scanner(line);
         // for every integer in line
         for(int i = 0; intScan.hasNextInt() && i < width; i++) {
            int num = intScan.nextInt();
            // if number is 0
            if(num == 0) {
               // make corresponding array spot = 0 if not already
               if(prev[i] == 1) {
                  prev[i] = 0;
               }
            } else { // if number is 1
               if (prev[i] == 1) { // if number directly above it in file is 1
                  // union the number and the number above it in UF
                  uf.union(i + (depth * width), i + (width * (depth - 1)));// if number is 1 and number above it is 0
               } else {
                  // set corresponding array spot to 1 if it is not already 1
                  prev[i] = 1;
               }
               // if number behind it is 1
               if(i != 0 && prev[i - 1] == 1) {
                  // union the number and the number behind it in the UF
                  uf.union(i + (depth * width), i + (depth * width) - 1);
               }
            }
         }
         // increment depth by 1
         depth++;
      }

      // for every top number (first n numbers in UF if n=width)
      for(int i = 0; i < width; i++) {
         // if number is a 1
         if(firstLine[i] == 1) {
            // for every bottom number (last n numbers in UF if n=width)
            for (int j = (width * (depth - 1)); j < size; j++) {
               // test if there is a connection between any top 1 and any bottom 1
               if (prev[i] == 1 && uf.isConnected(i, j)) {
                  return true;
               }
            }
         }
      }
      // return false if no connection is found
      return false;
   }

   // main method
   public static void main(String[] args) throws FileNotFoundException {
      Scanner input = new Scanner(System.in);
      System.out.print("Type a file name(q to quit): ");
      String file = input.next();
      while(!file.equals("q")) {
         System.out.println();
         if(drains(file)) {
            System.out.println("Allow water to drain");
         } else {
            System.out.println("Don't allow water to drain");
         }
         System.out.print("Enter a file name (q to quit): ");
         file = input.next();
      }
   }
}
         
         
      
        
            
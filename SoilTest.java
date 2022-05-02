import java.util.*;
import java.io.*;

// class containing methods to determine whether or not soil can drain water
public class SoilTest {
   // returns true if file can drain water and false otherwise
   public static boolean drains(String f) throws FileNotFoundException {
      File file = new File(f);
      Scanner fileScan = new Scanner(file);
      
      int depth = 0;
      int width = 0;

      // find dimensions of the file
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
      WeightedQuickUnionPC uf = new WeightedQuickUnionPC(size);

      fileScan.reset();
      fileScan = new Scanner(file);

      // initialize auxiliary array to length of soil width
      int[] prev = new int[width];

      // fill array with first line of file
      if(fileScan.hasNextLine()) {
         String line = fileScan.nextLine();
         Scanner intScan = new Scanner(line);
         for(int i = 0; intScan.hasNextInt() && i < width; i++) {
            prev[i] = intScan.nextInt();
         }
      }

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
               // make corresponding array spot = 0
               if(prev[i] == 1) {
                  prev[i] = 0;
               }
            } else { // if number is 1
               // if number above it is 1
               if (prev[i] == 1) {
                  // union number and number above it
                  uf.union(i + (depth * width), i + (width * (depth - 1)));// if number is 1 and number above it is 0
               } else {
                  // set corresponding array spot to 1
                  prev[i] = 1;
               }
               // if number behind it is 1
               if(i != 0 && prev[i - 1] == 1) {
                  // union number and number behind it
                  uf.union(i + (depth * width), i + (depth * width) - 1);
               }
            }
         }
         depth++;
      }

      // for every top number
      for(int i = 0; i < width; i++) {
         // for every bottom number
         for(int j = (width * (depth - 1)); j < size; j++) {
            // test if there is a connection between any top number and any bottom number
            if(uf.isConnected(i, j)) {
               return true;
            }
         }
      }
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
         
         
      
        
            
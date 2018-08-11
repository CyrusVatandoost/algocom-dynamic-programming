import java.util.ArrayList;

/**
 * 
 * @author Cyrus Vatandoost
 * @version 2018.08.11
 * ID: 11538333
 * SECTION: X22
 *
 */

public class DynamicProgramming {

	public static void main(String[] args) {
		assignTables(assignTablesInput1());
		assignTables(assignTablesInput2());
		partyBudget(partyBudgetInput1());
		partyBudget(partyBudgetInput2());
		cut(100, 3, new int[] {25, 50, 75});
		cut(10, 4, new int[] {4, 5, 7, 8});
	}
	
	// matrix chain multiplication
	public static void assignTables(String[] input) {
		System.out.println("matrix chain multiplication");
		
    	Boolean debug = false;
		// split input into variables
		int numMatrices;
		if(Integer.parseInt(input[0]) >= 3 && Integer.parseInt(input[0]) <= 100) {
			numMatrices = Integer.parseInt(input[0]);
			if(debug)
				System.out.println("numMatrices: " + numMatrices);
		}
		else {
			System.out.println("ERROR: invalid matrix quantity input.");
		}
		
		ArrayList<Matrix> matrixList = new ArrayList<Matrix>();
		
		for(int i = 1; i < input.length; i++) {
			String temp[] = input[i].trim().split(" ");
			String tempLabel = temp[0];
			int tempX = Integer.parseInt(temp[1]);
			int tempY = Integer.parseInt(temp[2]);
			matrixList.add(new Matrix(tempLabel, tempX, tempY));
		}
		
		int[] array = new int[matrixList.size() + 1];
		
		if(debug) {
			for(Matrix m : matrixList) {
				System.out.println(m.getLabel() + ": " + m.getX() + "x" + m.getY());
			}
		}
		
		for(int i = 0; i < matrixList.size(); i++) {
			array[i] = matrixList.get(i).getX();
		}
		array[matrixList.size()] = matrixList.get(matrixList.size() - 1).getY();
		
    	int temp;
    	int m[][] = new int[array.length][array.length];
    	int s[][] = new int[array.length][array.length];
    	
    	for(int i = 1; i < array.length; i++) {
    		m[i][i] = 0;
    	}
    	
    	for(int length = 2; length < array.length; length++) {
    		for(int i = 1; i < array.length - length + 1; i++) {
				int j = i + length - 1;
				m[i][j] = Integer.MAX_VALUE;
				for(int k = i; k <= (j - 1); k++) {
					temp = (array[k] * array[j]) * (m[i][k] + m[k + 1][j] + array[i - 1]);
					if(temp < m[i][j]) {
						m[i][j] = temp;
						s[i][j] = k;
					}
				}
    		}
    	}
    	
    	if(debug) {
        	for(int i = 0; i < array.length; i++) {
        		for(int j = 0; j < array.length; j++) {
        			System.out.print(m[i][j] + " ");
        		}
        		System.out.println();
        	}
    	}
    	
    	// print output
    	printMatrixChainOutput(s, 1, array.length - 1,  matrixList);
    	
		System.out.println();
		System.out.println();
	}
   
    public static void printMatrixChainOutput(int array[][], int i, int j, ArrayList<Matrix> matrixList) {
    	if(i == j) {
    		System.out.print(matrixList.get(i - 1).getLabel());
    	}
    	else {
    		System.out.print("(");
    		printMatrixChainOutput(array, i, array[i][j], matrixList);
    		printMatrixChainOutput(array, array[i][j] + 1, j, matrixList);
    	    System.out.print(")");
    	}
    }
	
	public static String[] assignTablesInput1() {
		return new String[] {"3", "A 10 30", "B 30 5", "C 5 60"};
	}
	
	public static String[] assignTablesInput2() {
		return new String[] {"4", "A 4 10", "B 10 3", "C 3 12", "D 12 20", "E 20 7"};
	}
	
	public static class Matrix {
		
		private String label;
		private int x;
		private int y;
		
		public Matrix(String label, int x, int y) {
			this.label = label;
			this.x = x;
			this.y = y;
		}
		
		public String getLabel() {
			return label;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
	}
	
	// partying scheduling
	public static void partyBudget(String[] inputs) {
		System.out.println("party scheduling");
    	Boolean debug = false;
		
		int partyBudget = 0;
		int numParties = 0;
		ArrayList<Party> partyList = new ArrayList<Party>();
		
		for(int i = 0; i < inputs.length; i++) {
			String temp[] = inputs[i].trim().split(" ");
			if(i == 0) {
				partyBudget = Integer.parseInt(temp[0]);
				numParties = Integer.parseInt(temp[1]);
			}
			else {
				partyList.add(new Party(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
			}
		}
		
		if(debug) {
			System.out.println("partyBudget: " + partyBudget);
			System.out.println("numParties: " + numParties);
			for(Party p : partyList) {
				System.out.println(p.fee + " " + p.fun);
			}
		}
		
		int[] fee = new int[partyList.size()];
		int[] fun = new int[partyList.size()];
		
		for(int i = 0; i < partyList.size(); i++) {
			fee[i] = partyList.get(i).fee;
			fun[i] = partyList.get(i).fun;
		}
		
    	int c[][] = new int[partyList.size() + 1][partyBudget + 1];
    	
		for(int i = 0; i <= partyList.size(); i++) {
			for(int j = 0; j <= partyBudget; j++) {
				if(i == 0 || j == 0) {
					c[i][j] = 0;
				}
				else if(fee[i - 1] <= j) {
					c[i][j] = Math.max(fun[i - 1] + c[i - 1][j - fee[i - 1]], c[i - 1][j]);
				}
				else {
					c[i][j] = c[i-1][j];
				}
			}
		}
		
        int temp = c[partyList.size()][partyBudget];
        int costTotal = 0;
 
        int k = partyBudget;
        for(int i = partyList.size(); i > 0 && temp > 0; i--) {
            if(temp != c[i - 1][k]) {
                costTotal += fee[i - 1];
                temp = temp - fun[i - 1];
                k = k - fee[i - 1];
            }
        }
        
        System.out.println(costTotal + " " + c[partyList.size()][partyBudget]);
		System.out.println();
	}
	
	public static String[] partyBudgetInput1() {
		return new String[] {"50 10", "12 3", "15 8", "16 9", "16 6", "10 2", "21 9", "18 4", "12 4", "17 8", "18 9"};
	}
	
	public static String[] partyBudgetInput2() {
		return new String[] {"50 10", "13 8", "19 10", "16 8", "12 9", "10 2", "12 8", "13 5", "15 5", "11 7", "16 2"};
	}
	
	public static class Party {
		
		public int fee;
		public int fun;
		
		public Party(int fee, int fun) {
			this.fee = fee;
			this.fun = fun;
		}
		
	}
	
	// wood cutter
	public static void cut(int l, int cuts, int[] places) {
		System.out.println("wood cutter");
		if(l < 1000) {
	        int[] m = new int[l];
			int[][] s = new int[l][l];
	        
	        m[0] = 0;
	        m[cuts + 1] = l;
	        
	        for(int i = 1; i <= cuts; i++) {
	            m[i] = places[i - 1];	
	        }
	        
	        for(int i = 0; i <= cuts; i++) {
	            s[i][i+1]=0;	
	        }
	        
	        int minimum;
	        for(int j = 2; j <= cuts + 1; j++) {
	            for(int i = j - 2; i >= 0; i--) {
	            	minimum = Integer.MAX_VALUE;
	                for(int k = i + 1; k < j; k++) {
	                	minimum = Math.min(minimum, s[i][k] + s[k][j] + m[j] - m[i]);	
	                }
	                s[i][j] = minimum;
	            }
	        }
	        
	        System.out.println("The minimum cutting is "+(s[0][cuts+1])+".");
		}
		else {
			System.out.println("ERROR: input l length is too long");
		}
        System.out.println();
	}

}

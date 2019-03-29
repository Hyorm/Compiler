/**
*
*@author KimHyoRim
*
*@since 2019.03.29
*
*Make a program in Java for implementing the following three exercise DFAs.
*
*1. Make a DFA for accepting such strings as { ε, a, aba, ababa, abababa, ...,a, aa, aaa, ... }
*   The strings rejected include ab, aab, aaab, abaa, ababaa, etc.
*   Σ = {a, b}
*
*2. Construct a DFA that accepts such strings as {#a123, #b000, #b9, $x123, $y1075, $x9...}
*   Σ = {#, $, a, b, x, y, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
*   The illegal strings include #x, $a12, #ax1, $xa, etc.
*
*3. Make a DFA that accepts strings such as
*   {ac, abc, abbc, xz, xyxz, xyxyxz, xyxyxyxz, xyac, xyabc, xyabbc, ...} 
*   Σ = {a, b, c, x, y, z}
*/

import java.util.*;

public class dfa{

	public static void main(String[] args){

		Scanner sc = new Scanner(System.in);
		
                int apt = 0;
		int dfa_flag = 0;
		String str = "string";
		

		//DONE: Make dfa trasition table 1
		
		char[][] dfa_trs_tbl_1 = {
			{'d','a','b','e'},
			{0,1,5,'y'},
			{1,4,2,'y'},
			{2,3,5,'n'},
			{3,5,2,'y'},
			{4,4,5,'y'},
			{5,5,5,'n'}//error state
		};
	
		//DONE: Make dfa trasition table 2
	
		char[][] dfa_trs_tbl_2 = {
			{'d','#','$','a','b','x','y','0','1','2','3','4','5','6','7','8','9','e'},
			{0,1,2,5,5,5,5,5,5,5,5,5,5,5,5,5,5,'n'},
			{1,5,5,4,4,5,5,5,5,5,5,5,5,5,5,5,5,'n'},
			{2,5,5,5,5,3,3,5,5,5,5,5,5,5,5,5,5,'n'},
			{3,5,5,5,5,5,5,4,4,4,4,4,4,4,4,4,4,'n'},
			{4,5,5,5,5,5,5,4,4,4,4,4,4,4,4,4,4,'y'},
			{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,'n'}//error state
		};
	
		//DONE: Make dfa trasition table 3
			
		char[][] dfa_trs_tbl_3 = {
			{'d','s','t','u','x','y','z','e'},
			{0,1,8,8,4,8,8,'n'},
			{1,8,2,3,8,8,8,'n'},
			{2,8,1,3,8,8,8,'n'},
			{3,8,8,8,8,8,8,'y'},
			{4,8,8,8,8,5,3,'n'},
			{5,1,8,8,6,8,8,'n'},
			{6,8,8,8,8,7,3,'n'},
			{7,8,8,8,6,8,8,'n'},
			{8,8,8,8,8,8,8,'n'}//error state
		};

		//TODO: Make dfa

		System.out.println("java dfa\nType E or e to exit.");

		while(true){
			System.out.print("Input: ");
			str = sc.nextLine();
			if(str.equals("")){
				System.out.println("Accepted (empty string, first DFA)");
				continue;	
			}else if(str.equals("E") ||str.equals("e")){
				break;
			}
			else {
				apt = 0;
				
				int i = 0;

				if(str.charAt(0)=='a')dfa_flag = 1;
				else if (str.charAt(0)=='#' || str.charAt(0)=='$')dfa_flag = 2;
				else if (str.charAt(0)=='x' || str.charAt(0)=='s')dfa_flag = 3;
				else {
					System.out.println("Rejected (Wrong Input)");
                                	continue;
				}
				
				switch (dfa_flag){
					case 1: apt = classify_dfa(str, dfa_trs_tbl_1);
						break;
					case 2: apt = classify_dfa(str, dfa_trs_tbl_2);
						break;
					case 3: apt = classify_dfa(str, dfa_trs_tbl_3);
						break;
				}
				if(apt == 1){
					System.out.println("Accepted");
                                	continue;	
				}
				else if(apt == 0){
					System.out.println("Rejected");
                                        continue;				
				}
				else {
                                        System.out.println("Rejected (Wrong Input)");
                                        continue;
                                }
			}

		}
	}
	public static int classify_dfa(String str, char[][] dfa_trs_tbl){
		int apt = 0;
		int state = 0;
		for(int i = 0; i<str.length(); i++){
			if(apt == 2)return apt;
			for(int j = 0; j<dfa_trs_tbl[0].length;j++){
				if(str.charAt(i)==dfa_trs_tbl[0][j]&&str.charAt(i)!='d'){
					state = dfa_trs_tbl[state+1][j];
					if(dfa_trs_tbl[state+1][dfa_trs_tbl[0].length-1]=='y'){
						apt = 1;
					}
					else {
						apt = 0;
					}
					break;
				}else{
					apt = 2;
				}
			}
		}
		return apt;
	}
}

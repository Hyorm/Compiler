/**
* 
*@author KimHyoRim
*
*@since 2020.04.10
*
*
**/
import java.util.*;
import java.awt.*;
import java.io.*;

public class scnr{

	public static int keyflag = 0;
	public static void main(String[] args)throws IOException{

		char[][] trs_tbl = {
			{'!', 'l', 'd', '>','<','+','-','/','*','=', '(', ')', ' ', ';', '{', '}', '"', ',', 'e'},
			{0,1,2,3,3,3,3,3,3,3,5,5,5,5,5,5,4,6,6},
			{1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,5,6,5,6},
			{2,6,2,2,2,2,2,2,2,2,5,5,5,5,5,5,5,5,6},
			{3,1,2,0,0,0,0,6,6,0,0,0,0,0,0,0,6,6,6},
			{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,4,6},
			{5,1,2,6,6,0,0,6,6,6,5,5,5,5,5,5,4,5,6},
			{6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6}//error state
		};		

		char[] special_symbol = {'+', '-', '=', '<', '>', '/', '*'};
		char[] terminator = {'(', ')', ' ', ';', '{', '}', '"', ','};
	
		String cur_str = "";

		char cur = ' ';
		char row = ' ';
		char col = ' ';
		
		int state = 0;

		char before = ' ';
		char after = ' ';

		if(args.length < 1){
		
			System.out.println("Usage: java smalljs [file name]");
			System.exit(0);
		}

		String cnt_str = readFile(args[0]);
		cnt_str = cnt_str.trim();//.replaceAll("##", " ");

		cnt_str += ' ';

		for(int i = 0; i< cnt_str.length(); i++){

			int hyorm = 0;

			cur = cnt_str.charAt(i);
			if(cur == '\n')continue;

			if((Character.isLetter(cur)) || (cur == '$') || (cur == '_') || (cur == '.')){
				cur_str += cur;
				row = 'l';
				hyorm = 1;
			}
			else if((hyorm == 0) && Character.isDigit(cur)){
				cur_str += cur;
				row = 'd';
				hyorm = 1;
			}
			else if(hyorm == 0){
				for(int j = 0; j < terminator.length; j++){
					if(cur == terminator[j]){
						keyflag = 0;
						if(!(Character.isWhitespace(cur))){
							print_token_type(cur_str, state);
							cur_str = "";
							cur_str += cur;
							print_token_type(cur_str, 5);
						}
						else{
							print_token_type(cur_str, state);
						}
						cur_str = "";
						row = cur;
						hyorm = 1;
						break;
					}
				}
				
				for(int j = 0; j < special_symbol.length; j++){
					
					if(cur == special_symbol[j]){
						if(keyflag == 1){
							cur_str+=cur;
							continue;
						}
						int operator = 0;
						for(int k = 0; k < special_symbol.length; k++){
							if(cnt_str.charAt(i-1) == special_symbol[k]){
								operator++;
								break;
							}
						}
						if(operator==0 && cur_str.length() > 1){
							print_token_type(cur_str, state);
							cur_str = "";
						}
						operator = 0;
						for(int k = 0; k < special_symbol.length; k++){
							if(cnt_str.charAt(i+1) == special_symbol[k]){
								operator++;
								break;
							}
						}
						cur_str += cur;
						if(operator == 0){
							print_token_type(cur_str, 3);
							cur_str = "";
						}
						row = cur;
						hyorm = 1;
						break;
					}
				}
			}
			else{
				row = 'e';
			}
			if(keyflag == 1){
				if((row != 'l')&&(row !='d') &&(row != ' ')){
						System.out.println(state +">>"+cur_str+" : illegal id");
						System.out.println("stop scanning");
						System.exit(0);
				
				}
			}
			for(int j = 0; j < trs_tbl[0].length ; j++){
				if(trs_tbl[0][j] == row){
					state = trs_tbl[state+1][j];
				}
			}
		}
	}
	public static String readFile(final String fileName) throws IOException{	

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		String line;

		while((line = br.readLine()) != null){
			
			int cmnt = line.indexOf("//");
			if(cmnt > -1)
				line = line.substring(0,cmnt);

			sb.append(line + " ");
		}

		return sb.toString();
	}
	
	public static void print_token_type(String str, int state){
		String type = "";
		String[] keywd = {"while", "if", "for", "do", "switch", "case", "then", "else", "break", "out.println", "main", "class", "int"};
		if( !str.equals(" ") && (str.length() != 0)){
			if(state == 1){
				int hyorm = 0;
				for(int i = 0; i< keywd.length ; i++){
					if(str.equals(keywd[i])){
						System.out.println(str+" : keyword");
						if(str.equals("int"))keyflag = 1;
						hyorm = 1;
						break;
					}
				}

				if(hyorm == 0){
					if(Character.isLetter(str.charAt(0)) || (str.charAt(0)=='$') || (str.charAt(0) == '_')){
						if(str.equals("_")){
							System.out.println(str+" : illegal id");
							System.out.println("stop scanning");
							System.exit(0);
						}
						for(int j = 0; j < str.length(); j++){
							if((Character.isLetter(str.charAt(j))) || (str.charAt(j) == '$') || (str.charAt(j) == '_') || (str.charAt(j) == '.' || (Character.isDigit(str.charAt(j))))){
								continue;
							}
							else{	
								System.out.println(str+" : illegal id");
								System.out.println("stop scanning");
								System.exit(0);
							}
						}
						System.out.println(str+" : id");
					}
					else{
						System.out.println(str+" : illegal id");
						System.out.println("stop scanning");
						System.exit(0);
					}
				}
			}
			else if(state == 2){
				System.out.println(str+" : number literal");
			}
			else if(state == 3){
				if(str.equals("+")){
					System.out.println(str+" : plus symbol");
				}
				else if(str.equals("-")){
					System.out.println(str+" : minus symbol");
				}
				else if(str.equals("/")){
					System.out.println(str+" : divide symbol");
				}
				else if(str.equals("*")){
					System.out.println(str+" : apply symbol");
				}
				else if(str.equals("++")){
					System.out.println(str+" : increment symbol");
				}
				else if(str.equals("--")){
					System.out.println(str+" : decrement symbol");
				}
				else if(str.equals("<")){
					System.out.println(str+" : greater than symbol");
				}
				else if(str.equals(">")){
					System.out.println(str+" : less than symbol");
				}
				else if(str.equals("=")){
					System.out.println(str+" : assignment symbol");
				}
				else if(str.equals("==")){
					System.out.println(str+" : equal symbol");
				}
				else if(str.equals("<=")){
					System.out.println(str+" : less than equal symbol");
				}
				else if(str.equals(">=")){
					System.out.println(str+" : greater than equal symbol");
				}
				else 
					System.out.println(str + " : operator error");
			}	
			else if(state == 4){
				System.out.println(str + " : literal");
			}
			else if(state == 5){
				if(str.equals("(")){
					System.out.println(str + " : left parenthesis");
				}
				else if(str.equals(")")){
					System.out.println(str + " : right parenthesis");
				}
				else if(str.equals("{")){
					System.out.println(str + " : left curly brace");
				}
				else if(str.equals("}")){
					System.out.println(str + " : right curly brace");
				}
				else if(str.equals("\"")){
					System.out.println(str + " : double quote symbol");
				}
				else if(str.equals(";")){
					System.out.println(str + " : semicolon");
				}
				else if(str.equals(",")){
					System.out.println(str + " : comma");
				}
				else
					System.out.println("terminator error");

			}
			else if(state == 6){
				System.out.println(str+" : illegal scanning error");
				System.out.println("stop scanning");
                                System.exit(0);

			}
			else
				System.out.println("state error");
		}
	}
}

/**
* 
*@author KimHyoRim
*
*@since 2020.05.15
*
*
**/
import java.util.*;
import java.awt.*;
import java.io.*;

public class scnr_prsr{

	public static int keyflag = 0;
	public static int str_length = 0;
	public static int idx_str = 0;
	public static String cnt_str = "";
	public static String cur_str = "";
	public static char cur = ' ';
	public static char row = ' ';
	public static char col = ' ';
	public static int state = 0;
	public static char before = ' ';
	public static char after = ' ';
	public static int tok1 = 0;
	public static int tok2 = 0;

	public static int expr_flag = 0;
	public static int
		err = 0, 
		while_ = 1,
		if_ = 2,
		for_ = 3,
		else_ = 4,
	       	func_ = 5,
		main_ = 6,
		class_ = 7,
		int_ = 8,
		id_ = 9, 
		nl_ = 10,
		addop_ = 11,
		cmpop_ = 12,
		mulop_ = 13,
		assign_ = 14,
		lcb_ = 15,
		rcb_ = 16,
		lb_ = 17,
		rb_ = 18,
		s_ = 19,
		quo_ = 20,
		ltrl_ = 21,
		cm_ = 22,
		assignop_ = 23
	;
	

	public static int get_token_one(){
		int toktok[] = {0, 0};
		if(tok2 == 0){
			if(idx_str < str_length){
				toktok = get_token();	
				tok1 = toktok[0];
				tok2 = toktok[1];
			}
			else {
				System.out.println("Parsing Ok");
				System.exit(0);
			}
		}
		else {
			int tmp = tok2;
			tok2 = 0;
			return tmp;
		}
		return tok1;
	}

	public static void main(String[] args)throws IOException{

		if(args.length < 1){

                        System.out.println("Usage: java smalljs [file name]");
                        System.exit(0);
                }

                cnt_str = readFile(args[0]);
		cnt_str = cnt_str.trim();//.replaceAll("##", " ");
		cnt_str += ' ';
		str_length = cnt_str.length();

		int re = stmt_sequence();

	}

	public static int stmt_sequence(){
		int token_now, re=0;

		re = statement();

		if(re != 50){
			token_now=0;while(token_now==0)token_now = get_token_one();
			if(token_now == s_){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}

		if(idx_str < str_length){
			re = stmt_sequence();
		}
		else{
			System.out.println("Parsing error");
			System.exit(0);
		}

		return re;
	}

	public static int statement(){
		int token_now, re=0;
		//System.out.println("adfafsaf");
		token_now=0;while(token_now==0)token_now = get_token_one();
		if(token_now == rcb_)re = stmt_sequence();
		if(token_now == for_){
			re = for_stmt();
		}
		else if(token_now == while_){
			re = while_stmt();
		}
		else if(token_now == if_){
			re = if_stmt();
		}
		else if(token_now == class_){
			re = class_stmt();
		}
		else if(token_now == func_){
			re = func_stmt();
		}
		else if(token_now == id_ || token_now == int_){
			re = assign_stmt();
		}
		else if(token_now == main_){
			re = main_stmt();
		}
		else if(token_now == else_){	
			re = else_stmt();
		}else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		return re;
	}
	
	public static int main_stmt(){
		int token_now, re=0;

		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == lb_ ){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == rb_ ){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
	
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == lcb_ ){
			re = stmt_sequence();
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
	
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == rcb_ ){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		return re;

	}

	public static int if_stmt(){

		int token_now, re=0;
		
		token_now=0;while(token_now==0)token_now = get_token_one();
		
		if(token_now == lb_ ){
			re = expr();
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		if(re != 60){
			token_now=0;while(token_now==0)token_now = get_token_one();

			if(token_now == rb_ ){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		if(re != 70){
			token_now=0;while(token_now==0)token_now = get_token_one();

			if(token_now == lcb_ ){
				//System.out.println("Pa");
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		re = stmt_sequence();
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == rcb_ ){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		return re;
	}

	public static int else_stmt(){
		int token_now, re = 0;	
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == if_ ){
			re = if_stmt();
		}
		else if(token_now == lcb_){
			re = stmt_sequence();	
			token_now=0;while(token_now==0)token_now = get_token_one();
			if(token_now == rcb_){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		return re;
	}
	public static int class_stmt(){
		int token_now, re=0;

		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == id_){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == lcb_){
			re = stmt_sequence();
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == rcb_){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		return 1;
	}

	public static int func_stmt(){
		int token_now, re=0; 

		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == lb_){
			re = func_parameter();
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		return 1;
	}

	public static int assign_stmt(){
	
		int token_now, re=0;
		
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == int_){

			token_now=0;while(token_now==0)token_now = get_token_one();
		}

		if(token_now == assign_){
			re = expr();
		}
		else if(token_now == assignop_){
			re = 1;
		}
		else if(token_now == id_){

			token_now=0;while(token_now==0)token_now = get_token_one();
			
			if(token_now == assign_){
				re = expr();
			}
			else if(token_now == assignop_){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}	
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		return re;

	}

	public static int for_stmt(){
		int token_now, re=0; 
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == lb_ ){
			re = assign_stmt();
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		//System.out.println("ahoho");
		if(re!=50){
			token_now=0;while(token_now==0)token_now = get_token_one();
		
			if(token_now == s_ ){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		re = expr();
		if(re!=50){
			token_now=0;while(token_now==0)token_now = get_token_one();
			if(token_now == s_ ){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		re = assign_stmt();
		if(re != 60){
			token_now=0;while(token_now==0)token_now = get_token_one();

			if(token_now == rb_ ){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == lcb_ ){
			re = stmt_sequence();
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == rcb_ ){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		return re;
	}

	public static int while_stmt(){
		int token_now, re=0; 
		token_now=0;while(token_now==0)token_now = get_token_one();
		if(token_now == lb_ ){
			re = expr();
			if(expr_flag  == 0){
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		if(re != 60 ){
			token_now=0;while(token_now==0)token_now = get_token_one();

			if(token_now == rb_ ){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		token_now=0;while(token_now==0)token_now = get_token_one();
		
		if(token_now == lcb_ ){
			re = stmt_sequence();
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == rcb_ ){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		return re;
	}

	public static int expr(){
		int token_now, re=0; 

		re = term();
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == cmpop_ || token_now == addop_){
			if(token_now == cmpop_) expr_flag = 1;
			else expr_flag = 0;
			re = expr();
		}
		else if(token_now == s_){
			re = 50;
		}
		else if (token_now == rb_){
			re = 60;
		}
		else if(token_now == lcb_){
			re = 70;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}
		
		return re;
	}

	public static int func_parameter(){
		int token_now, re=0; 
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == id_){
			token_now=0;while(token_now==0)token_now = get_token_one();
			if(token_now == cm_){
				re = func_parameter();
			}
			else if(token_now == rb_){
				re = 1;
			}else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		else if(token_now == quo_){
			token_now=0;while(token_now==0)token_now = get_token_one();
			while(token_now != quo_){
				token_now=0;while(token_now==0)token_now = get_token_one();
			}
			token_now=0;while(token_now==0)token_now = get_token_one();
			if(token_now == rb_){
				re = 1;
			}
			else{
				re = -1;
				System.out.println("Parsing error");
				System.exit(0);
			}
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		return re;
	}

	public static int term(){
		int token_now, re=0; 
		
		token_now=0;while(token_now==0)token_now = get_token_one();

		if(token_now == nl_){
			re = 1;
		}
		else if(token_now == id_){
			re = 1;
		}
		else{
			re = -1;
			System.out.println("Parsing error");
			System.exit(0);
		}

		return re;
	}

	public static int[] get_token(){

		int result[] = {0, 0};

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

			int hyorm = 0;

			cur = cnt_str.charAt(idx_str++);
			if(cur == '\n') return result;

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
							result[0] = get_token_type(cur_str, state);
							cur_str = "";
							cur_str += cur;
							result[1] = get_token_type(cur_str, 5);
						}
						else{
							result[0] = get_token_type(cur_str, state);
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
							if(cnt_str.charAt(idx_str-2) == special_symbol[k]){
								operator++;
								break;
							}
						}
						if(operator==0 && cur_str.length() > 1){
							result[0] = get_token_type(cur_str, state);
							cur_str = "";
						}
						operator = 0;
						for(int k = 0; k < special_symbol.length; k++){
							if(cnt_str.charAt(idx_str) == special_symbol[k]){
								operator++;
								break;
							}
						}
						cur_str += cur;
						if(operator == 0){
							result[0] = get_token_type(cur_str, 3);
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
			return result;
		
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
	
	public static int get_token_type(String str, int state){
		String type = "";
		int result_type = 0;
		String[] keywd = {"while", "if", "for", "else", "out.println", "main", "class", "int"};
		if( !str.equals(" ") && (str.length() != 0)){
			if(state == 1){
				int hyorm = 0;
				for(int i = 0; i< keywd.length ; i++){
					if(str.equals(keywd[i])){
						System.out.println(str+" : keyword");
						if(str.equals("int"))keyflag = 1;
						hyorm = 1;
						result_type = i+1;
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
						result_type = 9;
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
				result_type = 10;
			}
			else if(state == 3){
				if(str.equals("+")){
					System.out.println(str+" : plus symbol");
					result_type = 11;
				}
				else if(str.equals("-")){
					System.out.println(str+" : minus symbol");
					result_type = 11;
				}
				else if(str.equals("/")){
					System.out.println(str+" : divide symbol");
					result_type = 13;
				}
				else if(str.equals("*")){
					System.out.println(str+" : apply symbol");
					result_type = 13;
				}
				else if(str.equals("++")){
					System.out.println(str+" : increment symbol");
					result_type = 23;
				}
				else if(str.equals("--")){
					System.out.println(str+" : decrement symbol");
					result_type = 23;
				}
				else if(str.equals("<")){
					System.out.println(str+" : greater than symbol");
					result_type = 12;
				}
				else if(str.equals(">")){
					System.out.println(str+" : less than symbol");
					result_type = 12;
				}
				else if(str.equals("=")){
					System.out.println(str+" : assignment symbol");
					result_type = 14;
				}
				else if(str.equals("==")){
					System.out.println(str+" : equal symbol");
					result_type = 12;
				}
				else if(str.equals("<=")){
					System.out.println(str+" : less than equal symbol");
					result_type = 12;
				}
				else if(str.equals(">=")){
					System.out.println(str+" : greater than equal symbol");
					result_type = 12;
				}
				else{ 
					System.out.println(str + " : operator error");
				}
			}	
			else if(state == 4){
				System.out.println(str + " : literal");
				result_type = 21;
			}
			else if(state == 5){
				if(str.equals("(")){
					System.out.println(str + " : left parenthesis");
					result_type = 17;
				}
				else if(str.equals(")")){
					System.out.println(str + " : right parenthesis");
					result_type = 18;
				}
				else if(str.equals("{")){
					System.out.println(str + " : left curly brace");
					result_type = 15;
				}
				else if(str.equals("}")){
					System.out.println(str + " : right curly brace");
					result_type = 16;
				}
				else if(str.equals("\"")){
					System.out.println(str + " : double quote symbol");
					result_type = 20;
				}
				else if(str.equals(";")){
					System.out.println(str + " : semicolon");
					result_type = 19;
				}
				else if(str.equals(",")){
					System.out.println(str + " : comma");
					result_type = 22;
				}
				else{
					System.out.println("terminator error");
				}
			}
			else if(state == 6){
				System.out.println(str+" : illegal scanning error");
				System.out.println("stop scanning");
                                System.exit(0);

			}
			else {
				System.out.println("state error");
			}
		}
		return result_type;
	}
}

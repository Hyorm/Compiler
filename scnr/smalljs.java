/**
* 
*@author KimHyoRim
*
*@since 2019.04.10
*
*
**/
import java.util.*;
import java.awt.*;
import java.io.*;

public class smalljs{

	public static void main(String[] args) throws IOException{
		
		String[] rsvd_wd = {"false","default","for","while","if","then","<script_start>","<script_end>","else","case","switch","break","do"};
		String[] rsvd_id = {"var"};
		String[] result = {"operator","number","literal","assignment operator", "comparison operator", "equality comparison operator", "punctuation character", "keyword id","keyword function name","function parameter and literal","function parameter", "increment operator", "decrement operator"};

		String cur = "";
		int rslt_idx = 2;

		if(args.length < 1){
		
			System.out.println("Usage: java smalljs [file name]");

			System.exit(1);
		}

		String cnt_str = readFile(args[0]);
		cnt_str = cnt_str.trim().replaceAll(" +", " ");

		int state = 0;
		int func_flag = 0;
		int litr_flag = 0;
		int equal_flag = 0;
		int start_flag = 0;

		cnt_str += ' ';

		for(int i = 0; i < cnt_str.length(); i++){
		        //System.out.println(">>"+cnt_str.charAt(i)); 
			switch(state){
			
				case 0: switch(Character.toLowerCase(cnt_str.charAt(i))){
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
						case 'g':
						case 'h':
						case 'i':
						case 'j':
						case 'k':
						case 'l':
						case 'm':
						case 'n':
						case 'o':
						case 'p':
						case 'q':
						case 'r':
						case 's':
						case 't':
						case 'u':
						case 'v':
						case 'w':
						case 'x':
						case 'y':
						case 'z':
						case '.':
						case '_':
								cur += cnt_str.charAt(i);
								state = 1;
								break;
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
								cur += cnt_str.charAt(i);
								state = 2;
								break;
						default:	
								i--;
								state = 3;
								break;
					}
					break;

				case 1: switch(Character.toLowerCase(cnt_str.charAt(i))){
                                                case 'a':
                                                case 'b':
                                                case 'c':
                                                case 'd':
                                                case 'e':
                                                case 'f':
                                                case 'g':
                                                case 'h':
                                                case 'i':
                                                case 'j':
                                                case 'k':
                                                case 'l':
                                                case 'm':
                                                case 'n':
                                                case 'o':
                                                case 'p':
                                                case 'q':
                                                case 'r':
                                                case 's':
                                                case 't':
                                                case 'u':
                                                case 'v':
                                                case 'w':
                                                case 'x':
                                                case 'y':
                                                case 'z':
						case '.':
						case '_':
								start_flag = 1;
								rslt_idx = 7;
								cur += cnt_str.charAt(i);
                                                                state = 1;
                                                                break;
                                                case '0':
                                                case '1':
                                                case '2':
                                                case '3':
                                                case '4':
                                                case '5':
                                                case '6':
                                                case '7':
                                                case '8':
                                                case '9':
								cur += cnt_str.charAt(i);
                                                                state = 1;
                                                                break;
                                                default:
								for(int j = 0; j< rsvd_wd.length ;j++){
									if(cur.equals(rsvd_wd[j])){
										if(!cur.equals(" ") && !cur.equals(""))
											System.out.println(cur+" : keyword(reserved word)");
										cur = "";
										break;
									}
								}
								if(!cur.equals("")&& litr_flag != 1){
									if(cur.equals("var"))System.out.println(cur+" : keyword id");
									else if(cnt_str.charAt(i) == '('){
										if(!cur.equals(""))System.out.println(cur+" : function name");
										cur = "";
										func_flag = 1;
									}
									else if(!cur.equals(""))System.out.println(cur+" : keyword (user-defind id)");
								}
								if(litr_flag == 1 && cnt_str.charAt(i)=='"'){
									cur += cnt_str.charAt(i);
									if(!cur.equals(""))System.out.println(cur+" : literal");
									litr_flag = 0;
									cur = "";
									state = 0;
									break;
								}
								cur = "";
								i--;
                                                                state = 3;
                                                                break;
                                        }
					break;

				case 2:
					switch(Character.toUpperCase(cnt_str.charAt(i))){
                                                case 'a':
                                                case 'b':
                                                case 'c':
                                                case 'd':
                                                case 'e':
                                                case 'f':
                                                case 'g':
                                                case 'h':
                                                case 'i':
                                                case 'j':
                                                case 'k':
                                                case 'l':
                                                case 'm':
                                                case 'n':
                                                case 'o':
                                                case 'p':
                                                case 'q':
                                                case 'r':
                                                case 's':
                                                case 't':
                                                case 'u':
                                                case 'v':
                                                case 'w':
                                                case 'x':
                                                case 'y':
                                                case 'z':
						case '.':
						case '_':
                                                                cur += cnt_str.charAt(i);
								if(!cur.equals(" ") && !cur.equals(""))
									System.out.println(cur+" : keyword");
								cur = "";
                                                                state = 0;
                                                                break;
                                                case '0':
                                                case '1':
                                                case '2':
                                                case '3':
                                                case '4':
                                                case '5':
                                                case '6':
                                                case '7':
                                                case '8':
                                                case '9':
                                                                cur += cnt_str.charAt(i);
                                                                state = 2;
                                                                break;
                                                default:
								if(!cur.equals(""))System.out.println(cur + " : number");
								cur = "";
                                                                cur += cnt_str.charAt(i);
								i--;
                                                                state = 3;
                                                                break;
                                        }
                                        break;
					
				case 3: switch(Character.toLowerCase(cnt_str.charAt(i))){
                                                case 'a':
                                                case 'b':
                                                case 'c':
                                                case 'd':
                                                case 'e':
                                                case 'f':
                                                case 'g':
                                                case 'h':
                                                case 'i':
                                                case 'j':
                                                case 'k':
                                                case 'l':
                                                case 'm':
                                                case 'n':
                                                case 'o':
                                                case 'p':
                                                case 'q':
                                                case 'r':
                                                case 's':
                                                case 't':
                                                case 'u':
                                                case 'v':
                                                case 'w':
                                                case 'x':
                                                case 'y':
                                                case 'z':
						case '.':
						case '_':
								if(func_flag == 1){
									cur+=cnt_str.charAt(i);
									state = 3;
									break;
								}
								if(litr_flag == 1){
                                                                        cur+=cnt_str.charAt(i);
                                                                        state = 3;
                                                                        break;
                                                                }
								if(rslt_idx == 4){
									cur+=cnt_str.charAt(i);
									state = 3;
									break;
								}
								if(equal_flag == 1)equal_flag = 0;
								if(!cur.equals("") && !cur.equals(" "))System.out.println(cur + " : "+result[rslt_idx]);
								rslt_idx = 2;
								cur = "";
								cur += cnt_str.charAt(i);
								state = 1;
                                               			break;
						case '0':
                                                case '1':
                                                case '2':
                                                case '3':
                                                case '4':
                                                case '5':
                                                case '6':
                                                case '7':
                                                case '8':
                                                case '9':	
								if(func_flag == 1){
                                                                        cur+=cnt_str.charAt(i);
                                                                        state = 3;
                                                                        break;
                                                                }
								if(litr_flag == 1){
                                                                        cur+=cnt_str.charAt(i);
                                                                        state = 3;
                                                                        break;
                                                                }
								if(equal_flag == 1)equal_flag = 0;
								if(!cur.equals("")&& !cur.equals(" "))System.out.println(cur + " : "+result[rslt_idx]);
								rslt_idx = 2;
								cur = "";
								cur += cnt_str.charAt(i);
								state = 2;
                                                                break;
						case ' ':	
								if(func_flag == 1){
                                                                        cur+=cnt_str.charAt(i);
                                                                        state = 3;
                                                                        break;
                                                                }
								if(litr_flag == 1){
                                                                        cur+=cnt_str.charAt(i);
                                                                        state = 3;
                                                                        break;
                                                                }
								if(cur.equals("")){
									state = 0;
									break;
								}
								
								if(equal_flag == 1){
									cur += cnt_str.charAt(i);
									state = 3;
									break;
								}
								if(!cur.equals("")&& !cur.equals(" "))System.out.println(cur + " : "+result[rslt_idx]);
								cur = "";
								state = 0;
								rslt_idx = 2;
								break;

                                                default:	switch( Character.toLowerCase(cnt_str.charAt(i))){
									case '+':
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                 			       cur+=cnt_str.charAt(i);
                                                                 			       state = 3;
                                                                 		       		break;
                                                                			}
											if(rslt_idx != 0)
												rslt_idx = 0;
											else
												rslt_idx = 11;
											
											cur += cnt_str.charAt(i);
											state = 3;
											break;
									case '-':
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(rslt_idx != 0)
												rslt_idx = 0;
											else
												rslt_idx = 12;
											cur += cnt_str.charAt(i);
											state = 3;
											break;
									case '=':	
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(equal_flag == 1){
												rslt_idx = 5;
												equal_flag = 0;
											}
											else{
												if(rslt_idx != 3)
													rslt_idx = 3; 
												equal_flag = 1;
											}
											cur += cnt_str.charAt(i);
											state = 3;
											break;
									case '<':
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											rslt_idx = 4;
											state = 3;
											cur += cnt_str.charAt(i);
											break;
									case '>':
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(rslt_idx == 4){
												rslt_idx = 7;
												state = 3;
												cur += cnt_str.charAt(i);
											}
											else {
												rslt_idx = 4;
												state = 3;
												cur += cnt_str.charAt(i);
											}
											break;
									case ',':	
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											rslt_idx = 6;
											if(!cur.equals("")&& !cur.equals(" "))System.out.println(cur + " : "+result[rslt_idx]);
											cur = "";
											rslt_idx = 2;
											state = 0;
											break;

									case '/':	
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(cnt_str.charAt(i-1)=='<'){
												state = 3;
												cur += cnt_str.charAt(i);
												break;
											}
									case '%':
									case '*':
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											rslt_idx = 0;
											cur += cnt_str.charAt(i);
											state = 3;
											break;
									case '"':
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											if(litr_flag == 1){
												cur+=cnt_str.charAt(i);
												if(!cur.equals("")&& !cur.equals(" "))System.out.println(cur + " : literal");
												cur = "";
												state = 0;
												rslt_idx = 2;
												litr_flag = 0;
                                   								break;
				   							}
											else litr_flag = 1;
											if(!cur.equals("") && !cur.equals(" ")){
												System.out.println(cur + " : "+result[rslt_idx]);
												cur = "";
											}
											state = 3;
											cur += cnt_str.charAt(i);
											rslt_idx = 2;
											break;
									case '(':
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(func_flag == 1)
												cur += cnt_str.charAt(i);
											else if(!cur.equals("")&& !cur.equals(" "))System.out.println(cnt_str.charAt(i) + " : symbol");
											state = 3;
											break;
									case ')':	
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(func_flag == 1){
												cur += cnt_str.charAt(i);
												if(!cur.equals("")&& !cur.equals(" "))System.out.println(cur + " : function parameter");
												func_flag = 0;
												state = 0;
												cur = "";
												break;
											}

									case ';': 	
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(rslt_idx != 2){
												if(!cur.equals("")&& !cur.equals(" "))System.out.println(cur + " : "+result[rslt_idx]);
												rslt_idx = 2;
											}
									case ':':
                                                                        case '{':
                                                                        case '}':	
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											if(func_flag == 1){
                                                                                                cur+=cnt_str.charAt(i);
                                                                                                state = 3;
                                                                                                break;
                                                                                        }
											System.out.println(cnt_str.charAt(i) + " : symbol");
											state = 0;
											cur = "";
											break;
											
									default:	
											if(func_flag == 1){
                                                                        			cur+=cnt_str.charAt(i);
                                                                        			state = 3;
                                                                        			break;
                                                                			}
											if(litr_flag == 1){
                                                                                               cur+=cnt_str.charAt(i);
                                                                                               state = 3;
                                                                                                break;
                                                                                        }
											break;
								}
								break;
						}	
				default:
					break;

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

}

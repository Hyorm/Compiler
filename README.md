# compiler
## the Development of a Scanner (A Scanner for a Small JavaScript)
### 1. regular expression
1) Digit<br>
    (1) [0-9]<br>
2) Letter<br>
    (2) ‘_’ ([a-zA-Z]| [0-9] |‘_’ | ‘$’)+ | ([a-zA-Z]| ‘$’)* ([a-zA-Z]| [0-9] | ‘_’ | ‘$’)+ <br>
3) Identifier<br>
    (3) identifier = letter(letter | digit | ‘.’ )*<br>
4) Reserved Word and Identifier<br>
    (1) reserved = while | if | for | do | switch | case | then | else | break | out.println | main | class<br>
    (2) identifier = int <br>
5) Comment<br>
    (1) [^//][a-zA-Z0-1]*[(~newline)$]+<br>
    - begin with a ‘//’ characters and continue to the end of the line <br>
6) White Space<br>
    (1) whitespace = (newline | blank | tab | comment)<br>
### 2. NFA
<p align="center"><img width="588" alt="image" src="https://user-images.githubusercontent.com/28642467/117953685-39e58c80-b351-11eb-87cd-7a1ede78d32f.png"></p>
<p align="center">Figure 1: NFA notation for scanner</p>
<p align="center"><img width="588" alt="image" src="https://user-images.githubusercontent.com/28642467/117953924-7dd89180-b351-11eb-94ca-66e39b8f4005.png"></p>
<p align="center">Figure 2: DFA notation for scanner</p>

### 4. JAVA Source Code
[the Development of a Scanner](https://github.com/Hyorm/Compiler/tree/master/theDevelopmentofaScanner)
### 5. Design Document 
1) UML
<p align="center"><img width="385" alt="image" src="https://user-images.githubusercontent.com/28642467/117954281-d576fd00-b351-11eb-812b-fa1dfd832ae7.png"></p>
<p align="center">Figure 3: UML of scnr java code</p>

- The scnr.java code has a figure 2 UML.<br>
- public static int keyflag: Stores the keyword properties of a token<br>
- public static void main function: Acts as the main driver. Distinguish type of each tokens. <br>
- public static String readFile function: Reads the file and converts it to a static String.<br>
- public static void print token_type: Print each token with the type.<br>
2) Flow
<p align="center"><img width="594" alt="image" src="https://user-images.githubusercontent.com/28642467/117954468-022b1480-b352-11eb-8c3b-24e1c2d64d5f.png"></p>
<p align="center">Figure 4: Flow of scnr java program</p>

- When scnr is executed, it goes to the main function first.<br>
- The main function takes the name of the file to be scanned as an argument.<br>
- Put the file name in the readFile function, convert the contents of the file to String, and return to main to scan the String.The pseudo-code of the algorithm used for scanning is the same as in 3 with Figure 5).<br>
- When the token is separated with the type, it outputs with print_token_type function and returns to main. Repeat until there are no remaining characters in buffer.<br>
<p align="center"><img width="586" alt="image" src="https://user-images.githubusercontent.com/28642467/117954659-330b4980-b352-11eb-92ba-873eb2639ae8.png"></p>
<p align="center">Figure 5: Transition Table</p>

- A table representing the transition state corresponding to the DFA in 3.<br>
- This table does not show an acceptance state.<br>
3) Pseudocode
<pre>
state:= 0
terminator = '(', ')', ' ', ';', '{', '}', '"', ',' 
special_symbol = '+', '-', '=', '<', '>', '/', '*' 
ch := next input character;
while ch is not empty do
  if ch is digit
    state = trs_tbl[state+1][digit];
  else if ch is letter
    state = trs_tbl[state+1][letter];
  else if ch is terminator print_token_type();
    state = trs_tbl[state+1][terminator];
  else if ch is special_symbol print_token_type();
    state = trs_tbl[state+1][special_symbol];
  else
    error occur;
end while;
</pre>
### 6. Snapshot of Running Result
1) Using ant
<p align="center"><img width="673" alt="image" src="https://user-images.githubusercontent.com/28642467/117955065-9d23ee80-b352-11eb-9d28-13569dda5c9a.png"></p>

2) Using javac
<p align="center"><img width="755" alt="image" src="https://user-images.githubusercontent.com/28642467/117955183-bd53ad80-b352-11eb-8e27-4eeb395068f7.png"></p>

### 7. User Manual
1) Use ant (directory name)<br>
    (1) ant version<br>
    - Apache Ant(TM) version 1.10.5 compiled on March 28 2019<br>
    (2) build.xml
```
<project name="scnr" default="build" basedir=".">
  <property name="src" value="src"/>
  <property name="build" value="build"/>
  <property name="doc" value="doc"/>
  <path id="lib.path">
    <pathelement location="${build}" />
  </path>
  <target name="init">
    <mkdir dir="${build}"/>
  </target>
  <target name="build" depends="init">
    <javac srcdir="${src}" destdir="${build}" debug="true" includeantruntime="false">
    </javac>
  </target>

  <target name="run" depends="build">
    <java classname="scnr" fork="true" dir="." maxmemory="4096m">
      <classpath location="."/>
      <classpath refid="lib.path"/>
      <arg file="data/test.txt"/>
    </java>
  </target>
  <target name="clean">
    <delete dir="${build}"/>
  </target>
</project>
```
(3) command<br>
- ant build<br>
- ant run<br>
* this build.xml already set the file name(test.txt)<br>

2) Use Javac (directory name)<br>
   (1) java version<br>
   - openjdk version "11.0.6" 2020-01-14
   - OpenJDK Runtime Environment (build 11.0.6+10-post-Ubuntu-1ubuntu118.04.1)
   - OpenJDK 64-Bit Server VM (build 11.0.6+10-post-Ubuntu-1ubuntu118.04.1, mixed mode)<br>
   (2) command
   - javac scnr.java
   - java scnr [file name]
## RecursiveDescentParser
[pdf](https://github.com/Hyorm/Compiler/blob/master/RecursiveDescentParser/hw2_21600193.pdf)
## LL_1_Parser
[pdf](https://github.com/Hyorm/Compiler/blob/master/LL_1_Parser/hw3_21600193.pdf)

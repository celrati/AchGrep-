Clone of egrep using java by Celr@ti
this clone support the operators  .,|,*,(,)


the process of this algorithm is :
1. take the regular expression and list of files.
2. if the regular expression is a set of concatenations we run just the KMP algorithms.
3. if its contain one of the operators above we construct the automata NFA 
4. we convert the NFA to DFA get the matrix of it
5. after we run the matching of this DFA matrix of every string in the files yes it takes o(n^2) lol

#examples:
javac *.java
java Run "a|b" input 1




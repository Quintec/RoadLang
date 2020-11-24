# RoadLang

Compiling: 
```
cd src
javac *.java
```

Running: `java Interpreter [filename]`

[Sample program: factoring](src/factor.road)

# Commands

All programs must start with `wagwan my slime` and end with `chat wit u later fam`.
Double quotes (for strings) and parentheses (for expressions) work as normal.

## Values

| Name    | Actual value |
| ------- | ------------ |
| peng    | true         |
| bawbag  | false        |
| nuttin  | 0            |
| bar     | 1            |
| bice    | 2            |
| beehive | 5            |
| bender  | 6            |

## Operators

| Name        | Type    | Equivalent in other languages | Description                                                  |
| ----------- | ------- | ----------------------------- | ------------------------------------------------------------ |
| man say     | Monadic | print                         | Outputs the text with a newline after.                       |
| 3up my man  | Mondaic | del                           | Deletes the value of a variable. (Currently functionally useless) |
| is          | Dyadic  | Variable assignment           | Assigns the name on the left side to be equal to the value on the right. |
| be          | Dyadic  | ==                            | Checks for equality.                                         |
| n           | Dyadic  | +                             | Addition.                                                    |
| o           | Dyadic  | or                            | Or for booleans.                                             |
| x           | Dyadic  | *                             | Multiplication.                                              |
| ova         | Dyadic  | /                             | Division.                                                    |
| leftova     | Dyadic  | %                             | Modulo.                                                      |
| witout      | Dyadic  | -                             | Subtraction.                                                 |
| lickler den | Dyadic  | <                             | Less than.                                                   |
| bigger den  | Dyadic  | >                             | Greater than.                                                |
| not         | Monadic | not                           | Not.                                                         |

## Control flow

| Template                                                     | Type         | Usage                                                        |
| ------------------------------------------------------------ | ------------ | ------------------------------------------------------------ |
| <pre>rip dat bong till [expression] n dat<br />&nbsp;&nbsp;&nbsp;&nbsp;[statements]<br />mmm </pre>| while loop   | Executes [statements] as long as [expression] is true.       |
| <pre>ayy bossman [expression] init bruv<br />&nbsp;&nbsp;&nbsp;&nbsp;[statements]<br />yeah init bruv<br />&nbsp;&nbsp;&nbsp;&nbsp;[optional statements]<br />yeah | if statement </pre>| Executes [statements] if [expression] is true. Otherwise, executes [optional statements] if they exist. (Note that you still need the second `yeah` even if the else block doesn't exist.) |
| <pre>pass me [variable name] from asda lad            </pre>            | input        | Sets the value of [variable name] to the next line of input from stdin. |



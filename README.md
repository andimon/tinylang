## tinylang
An interpreter constructed for the study unit [CPS2000](https://www.um.edu.mt/courses/studyunit/CPS2000) at the University of Malta as partial fulfilment of a degree in Maths and Computer Science.
## Implementation
For information regarding implementation details see [report](/documentation/main.pdf).
## Usage

Interpreting the following tinylang program [program1.tl](/sample_programs_and_binary/program1.tl):
```
/*
 Testing
 the
 lexer
*/
let numru : float = (-2)+3.2;
//print numru
print numru;
```

From project's directory:

1. Compile and package: `cd ./tinylang && mvn clean package`
2. Execute the encapsulated interpreter and load a program::  `java -jar ./target/tinylang-compiler-0.0.1-SNAPSHOT.jar ../sample_programs_and_binary/program1`





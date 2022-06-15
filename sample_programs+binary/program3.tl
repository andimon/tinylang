//a function must always return
let x: bool=false;
fn forLoop()->bool{
    for(let i:int=1;i<=10;i=i+1){
        print i;
    }
    return true;
}
/*
a statement cannot be a function call (see EBNF)
 we assign an identifier bool x
*/
x=forLoop();
if(x==(true)) {print 'T';} else {print 'F';}

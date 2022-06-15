//a function must always return,this function is not guaranteed to return
fn notGuranteedToReturn(x:char)->bool{
     if(x=='a'){ return true; }
     else{ 
         //conditional branching 
         if(x=='b'){ return true; }
     else{
             if(x=='c'){ return true; }
             else{
        		//no return statement in this block
        		print 'c';
             }  
        }     
     }
}


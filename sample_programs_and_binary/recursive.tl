fn add(a:int,b:int)->int{
    if(b==0){return a;}
    else{
    	return add(a+1,b-1);
    }
}
//reuse add function
fn multiply(a:int,b:int)->int{
    if(b==0){return 0;}
    else{
    	
    	return a+multiply(a,(b-1));
    	
    }
}
//a^b, reuse multiply function
fn power(a:int,b:int)->int{
    if(b==0){return 1;}
    else{
    	if(b==1){return a;} else{
    	return a*power(a,b-1);
    	}
    }
}
print add(5,3);
print multiply(5,3);
print power(5,3);



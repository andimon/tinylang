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

let sum:int=0;
for(let i:int=0;i<=5;i=i+1){
	let a:int = power(i,2);
	let b:int = multiply(i,2);
	let c:int = add(b,2);
	let d:int = add(a,c);
	sum=sum+d;

}
print sum;

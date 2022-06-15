fn isDigit(x:int)->bool{
	if((x==0) or (x==1) or (x==2) or (x==3) or (x==4) or (x==5) or (x==6) or (x==7) or (x==8) or (x==9)){
		return true;
	}
	else{
		return false;
	}
}
fn get0or1char(x:int)->char{
	if((x==0) or (x==1)){
		if(x==0){
			return '0';
		}
		else{
			return '1';
		}
	}
	else{
		return 'n';
	}
}
fn fibonacci(x:int)->int{
	if((x==0) or (x==1)){
		if(x==0){
			return 0;
		}
		else{
			return 1;
		}
	}
	else{
		return fibonacci(x-1)+fibonacci(x-2);
	}
}

print isDigit(10);
print get0or1char(1);
print fibonacci(5);

fn nice(x:int)->float{
	fn return2()->int{
		return 2;
	}
	return x+return2();
}
fn nice(x:float)->float{
	fn return3()->int{
		return 3;
	}
	return x+return3();
}

print nice(1);
print nice(1.0);

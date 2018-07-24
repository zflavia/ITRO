// ar fi bine 

function requestError(errorObj, message, errorCode){
	console.log("fct requestError");
	if (errorCode == 400){
		console.log('message: ' ,message);
		errorObj.message = message;
	}else {
		//redirect to a page ... in care se pune ceva de genul internal error, if persist report
		console.log("ITRO HTTP Error: ", message, 'ErrorCode:', errorCode);
	}
}

function requestErrorDelete(message, errorCode){
	console.log("fct requestError");
	if (errorCode == 400){
		console.log('message: ' ,message);
		alert(message);
	}else {
		//redirect to a page ... in care se pune ceva de genul internal error, if persist report
		console.log("ITRO HTTP Error: ", message, 'ErrorCode:', errorCode);
	}
}
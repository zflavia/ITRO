angular.module('userView.filters',[])
	.filter('filterL', function() {
	    return function(input, text){
	       return input.filter(function(item) {
	           if(text !== '' && text.length === 1 && item.nume) {
	           		console.log("item="+item);
	               return (item.nume[0]).toUpperCase() === text.toUpperCase();
	           } else {
	               return true;
	           }
	       });
	   };
	});
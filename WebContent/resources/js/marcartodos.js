function marcar_todos(frm,verif){
  for(var contador=0;contador<frm.elements.length;contador++)
    if (frm.elements[contador].type=='checkbox')
	   frm.elements[contador].checked=verif.checked; 	                
}
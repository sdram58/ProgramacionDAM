/* OPTIMIZAR CÓDIGO - MEJORAS */

switch(mes){
	case 1: System.out.println(“Enero”);
			break;
	case 2: System.out.println(“Febrero”);
			break;
	case 3: System.out.println(“Marzo”);
			break;
	case 4: System.out.println(“Abril”);
			break;
	case 5: System.out.println(“Mayo”);
			break;
	case 6: System.out.println(“Junio”);
			break;;
	case 7: System.out.println(“Julio”);
			break;
	case 8: System.out.println(“Agosto”);
			break;
	case 9: System.out.println(“Septiembre”);
			break;
	case 10: System.out.println(“Octubre”);
			 break;
	case 11: System.out.println(“Noviembre”);
			 break;
	case 12: System.out.println(“Diciembre”);
			 break;
	default: System.out.println(“Mes erróneo.”);
		     break;
}

/* VEAMOS UNA MEJORA DEL CÓDIGO */
String nombre;
switch(mes){
	case 1: nombre = “Enero”;
			break;
	case 2: nombre = “Febrero”;
			break;
	case 3: nombre = “Marzo”;
			break;
	case 4: nombre = “Abril”;
			break;
	case 5: nombre = “Mayo”;
			break;
	case 6: nombre = “Junio”;
			break;;
	case 7: nombre = “Julio”;
			break;
	case 8: nombre = “Agosto";
			break;
	case 9: nombre = “Septiembre”;
			break;
	case 10: nombre = “Octubre”;
			 break;
	case 11: nombre = “Noviembre”;
			 break;
	case 12: nombre = “Diciembre”;
			 break;
	default: nombre = “Mes erróneo.”;
		     break;
}

System.out.println(nombre);
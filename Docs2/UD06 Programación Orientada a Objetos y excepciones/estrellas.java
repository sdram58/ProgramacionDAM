class Estrella {
    float x;
    float y;
}

Estrella[] listaEstrellas = new Estrella[10];  

for (int i=0; i<listaEstrellas.length; i++) {
    listaEstrellas[i] = new Estrella();  
    listaEstrellas[i].x = random(0,width); 
    listaEstrellas[i].y = random(0,height);  
}

for (int i=0; i<listaEstrellas.length; i++) {
    System.out.println(listaEstrellas[i].x, listaEstrellas[i].y);
}
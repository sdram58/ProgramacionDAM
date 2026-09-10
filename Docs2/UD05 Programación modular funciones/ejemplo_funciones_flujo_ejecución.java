public class Ejercicios
{
    public static String saludar(String nombre)
    {
        //Se crea el mensaje de saludo
        String saludo = "Bienvenido/a " + nombre;

        return saludo;//Se retorna el saludo
    }

    public static String error(String nombre)
    {
        //Se crea el mensaje de error
        String error = "Error validando datos... " + nombre + " es tu usuario?";

        return error; //Se retorna el error
    }

    public static void verificar(String usuario, String contra)
    {
        String usuarioValido = "Nombre1";

        String passwordValido = "Password1";

        //Se validan los datos
        if(usuarioValido.equals(usuario) && passwordValido.equals(contra))
        {
            //Si son validos se llama ala función saludar y se muestra el mensaje retornado por pantalla
            System.out.println(saludar(usuario));
            return; //Terminamos la ejecución
        }
        //Si no son válidos entonces mostramos el mensaje de error de la funcion error.
        System.out.println(error(usuario));
    }

    public static void main(String[] args)
    {
        String usuario = "Juan";
        String contra = "pass";

        //Se hace la verificación
        verificar(usuario, contra);

        //Mostrará el mensaje error.
    }
}
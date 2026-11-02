import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Examen Práctico Unidad 3 - Programación DAM
 * Sistema de Gestión y Auditoría de Envíos "LogistiTrack"
 * 
 * Cumple con los criterios de evaluación de la Unidad 3 (y consolida UD 2):
 * RA 1: 1.a, 1.b, 1.c (modularidad mediante métodos estáticos)
 * RA 2: 2.a, 2.b, 2.c (instanciación de objetos predefinidos, métodos)
 * RA 3: 3.a (if-else, switch), 3.b/3.c (bucles for, while, do-while), 3.d (break, return),
 *       3.e (try-catch contra fallos de entrada), 3.f (programa ejecutable), 3.g (documentación)
 * RA 6: 6.a (arrays unidimensionales), 6.g/6.f (expresiones regulares con Pattern/matches)
 */
public class LogistiTrack {

    // Constantes de tarificación y límites (CE: 1.f)
    public static final double TARIFA_BASE = 6.50;               // Tarifa base estándar
    public static final double RECARGO_URGENTE = 8.00;           // Suplemento servicio urgente
    public static final double RECARGO_FRAGIL = 5.50;            // Suplemento manipulación frágil
    public static final double RECARGO_PELIGROSO = 12.00;        // Suplemento mercancía peligrosa
    public static final double PRECIO_POR_KG = 1.25;             // Tarifa por kilogramo
    public static final double UMBRAL_PAQUETE_PESADO = 10.0;     // Paquetes > 10 kg se consideran pesados

    // Expresión regular para códigos de seguimiento: PKG-4dígitos-2letras (ej: PKG-4821-ES) (CE: 6.g)
    public static final String REGEX_CODIGO = "^PKG-\\d{4}-[A-Z]{2}$";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        teclado.useLocale(Locale.US);

        System.out.println("==================================================");
        System.out.println("   LOGISTITRACK - SISTEMA DE AUDITORIA DE ENVIOS  ");
        System.out.println("==================================================");

        // 1. Validación de código de seguimiento mediante Expresiones Regulares (CE: 6.g)
        String codigoLote = pedirCodigoValido(teclado);

        // 2. Selección de categoría con estructura SWITCH (CE: 3.a)
        char tipoEnvio = pedirTipoEnvioValido(teclado);
        double recargoCategoria = calcularRecargoCategoria(tipoEnvio);

        // 3. Manejo de excepciones y lectura del tamaño del array (CE: 3.e, 6.a)
        int numPaquetes = pedirNumeroPaquetes(teclado);

        // 4. Creación y llenado del array con control de excepciones y bucle FOR (CE: 3.b, 3.e, 6.a)
        double[] pesos = new double[numPaquetes];
        llenarPesos(teclado, pesos);

        // 5. Procesamiento estadístico modular sobre el array (CE: 1.a, 6.a)
        double pesoTotal = calcularPesoTotal(pesos);
        double pesoMedio = calcularPesoMedio(pesos);
        double pesoMaximo = obtenerPesoMaximo(pesos);
        int paquetesPesados = contarPaquetesPesados(pesos, UMBRAL_PAQUETE_PESADO);

        // 6. Cálculo de coste total con operadores aritméticos y condicionales (CE: 1.g, 3.a)
        double costeTotal = (numPaquetes * TARIFA_BASE) + recargoCategoria + (pesoTotal * PRECIO_POR_KG);
        
        // Descuento por gran volumen (más de 5 paquetes) (CE: 3.a)
        double descuentoVolumen = 0.0;
        if (numPaquetes >= 5) {
            descuentoVolumen = costeTotal * 0.10; // 10% de descuento
            costeTotal -= descuentoVolumen;
        }

        // 7. Salida formateada con printf (CE: 1.j)
        mostrarResumenAuditoria(codigoLote, tipoEnvio, recargoCategoria, pesos,
                               pesoTotal, pesoMedio, pesoMaximo, paquetesPesados,
                               descuentoVolumen, costeTotal);

        teclado.close();
    }

    /**
     * Solicita y valida el código del lote usando expresiones regulares (CE: 6.g, 3.c)
     */
    public static String pedirCodigoValido(Scanner teclado) {
        String codigo;
        boolean valido = false;
        do {
            System.out.print("Introduce codigo de lote (Formato PKG-0000-XX): ");
            codigo = teclado.nextLine().trim().toUpperCase();
            if (codigo.matches(REGEX_CODIGO)) {
                valido = true;
            } else {
                System.out.println("  [ERROR] Formato invalido. Debe ser PKG seguido de 4 digitos y 2 letras mayusculas.");
            }
        } while (!valido);
        return codigo;
    }

    /**
     * Solicita la categoría del lote y valida que sea una opción válida (CE: 3.a, 3.c)
     */
    public static char pedirTipoEnvioValido(Scanner teclado) {
        char tipo = ' ';
        boolean valido = false;
        while (!valido) {
            System.out.print("Tipo de envio [E=Estandar, U=Urgente, F=Fragil, P=Peligroso]: ");
            String entrada = teclado.nextLine().trim().toUpperCase();
            if (entrada.length() > 0) {
                tipo = entrada.charAt(0);
                if (tipo == 'E' || tipo == 'U' || tipo == 'F' || tipo == 'P') {
                    valido = true;
                } else {
                    System.out.println("  [ERROR] Opcion no reconocida. Selecciona E, U, F o P.");
                }
            }
        }
        return tipo;
    }

    /**
     * Devuelve el recargo según la categoría utilizando una sentencia switch (CE: 3.a)
     */
    public static double calcularRecargoCategoria(char tipo) {
        switch (tipo) {
            case 'U':
                return RECARGO_URGENTE;
            case 'F':
                return RECARGO_FRAGIL;
            case 'P':
                return RECARGO_PELIGROSO;
            case 'E':
            default:
                return 0.0;
        }
    }

    /**
     * Pide la cantidad de paquetes protegiendo la lectura contra excepciones (CE: 3.e)
     */
    public static int pedirNumeroPaquetes(Scanner teclado) {
        int n = 0;
        boolean correcto = false;
        while (!correcto) {
            try {
                System.out.print("Cantidad de paquetes a auditar (1-10): ");
                n = teclado.nextInt();
                if (n >= 1 && n <= 10) {
                    correcto = true;
                } else {
                    System.out.println("  [ERROR] Debe ser un numero entre 1 y 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("  [ERROR] Entrada no numerica. Debe introducir un numero entero.");
                teclado.nextLine(); // Limpiar el buffer corrupto
            }
        }
        teclado.nextLine(); // Limpiar el salto de línea pendiente
        return n;
    }

    /**
     * Llena el array de pesos validando cada entrada con try-catch (CE: 3.b, 3.e, 6.a)
     */
    public static void llenarPesos(Scanner teclado, double[] pesos) {
        System.out.println("\n--- REGISTRO DE PESOS POR PAQUETE (en kg) ---");
        for (int i = 0; i < pesos.length; i++) {
            boolean pesoValido = false;
            while (!pesoValido) {
                try {
                    System.out.printf("Peso del paquete #%d (kg): ", (i + 1));
                    double peso = teclado.nextDouble();
                    if (peso > 0.0 && peso <= 50.0) {
                        pesos[i] = peso;
                        pesoValido = true;
                    } else {
                        System.out.println("  [ERROR] El peso debe ser mayor a 0 y maximo 50 kg.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("  [ERROR] Valor decimal no valido. Introduzca un numero (ej. 3.45).");
                    teclado.nextLine(); // Limpiar buffer
                }
            }
        }
        teclado.nextLine(); // Limpiar buffer
    }

    /**
     * Calcula la suma total de los pesos del array (CE: 6.a)
     */
    public static double calcularPesoTotal(double[] pesos) {
        double total = 0.0;
        for (int i = 0; i < pesos.length; i++) {
            total += pesos[i];
        }
        return total;
    }

    /**
     * Calcula la media aritmética del peso (CE: 6.a)
     */
    public static double calcularPesoMedio(double[] pesos) {
        if (pesos.length == 0) return 0.0;
        return calcularPesoTotal(pesos) / pesos.length;
    }

    /**
     * Encuentra el peso máximo en el array (CE: 6.a)
     */
    public static double obtenerPesoMaximo(double[] pesos) {
        if (pesos.length == 0) return 0.0;
        double max = pesos[0];
        for (int i = 1; i < pesos.length; i++) {
            if (pesos[i] > max) {
                max = pesos[i];
            }
        }
        return max;
    }

    /**
     * Cuenta cuántos paquetes superan un determinado umbral de peso (CE: 3.a, 6.a)
     */
    public static int contarPaquetesPesados(double[] pesos, double umbral) {
        int contador = 0;
        for (int i = 0; i < pesos.length; i++) {
            if (pesos[i] > umbral) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Muestra el resumen formateado por consola usando printf (CE: 1.j)
     */
    public static void mostrarResumenAuditoria(String codigo, char tipo, double recargo,
                                             double[] pesos, double totalKg, double mediaKg,
                                             double maxKg, int pesados, double descuento,
                                             double costeFinal) {
        System.out.println("\n==================================================");
        System.out.println("            INFORME FINAL DE AUDITORIA            ");
        System.out.println("==================================================");
        System.out.printf("Codigo de lote           : %s%n", codigo);
        System.out.printf("Modalidad de envio       : %c (Suplemento: %.2f EUR)%n", tipo, recargo);
        System.out.printf("Total de paquetes        : %d unidades%n", pesos.length);
        System.out.println("--------------------------------------------------");
        System.out.printf("Peso total del lote      : %8.2f kg%n", totalKg);
        System.out.printf("Peso medio por paquete   : %8.2f kg%n", mediaKg);
        System.out.printf("Paquete mas pesado       : %8.2f kg%n", maxKg);
        System.out.printf("Paquetes pesados (>10kg) : %d unidades%n", pesados);
        System.out.println("--------------------------------------------------");
        if (descuento > 0.0) {
            System.out.printf("Descuento volumen (10%%)  : -%7.2f EUR%n", descuento);
        }
        System.out.printf("COSTE TOTAL FACTURADO    : %8.2f EUR%n", costeFinal);
        System.out.println("==================================================");
    }
}

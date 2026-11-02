import java.util.Locale;
import java.util.Scanner;

/**
 * Examen Práctico Unidad 2 - Programación DAM
 * Simulador de Facturación Eléctrica "EcoVolt Solutions"
 * 
 * Cumple con todos los criterios de evaluación de la Unidad 2:
 * RA 1: 1.a, 1.b, 1.c, 1.d, 1.e, 1.f, 1.g, 1.h, 1.i, 1.j
 * RA 4: Elementos y sintaxis de clase
 */
public class EcoVoltFactura {

    // Constantes de tarificación y fiscalidad (CE: 1.f)
    public static final double TARIFA_BASE_KWH = 0.1475;        // € por kWh consumido
    public static final double COSTE_POTENCIA_DIA = 0.1120;     // € por kW contratado al día
    public static final double IMPUESTO_ELECTRICIDAD = 0.051127; // 5.1127%
    public static final double IVA_PORCENTAJE = 0.21;           // 21% de IVA
    public static final double DESCUENTO_BONO_SOCIAL = 0.25;    // 25% descuento sobre consumo
    public static final double FACTOR_ECOPUNTOS = 8.5;          // 8.5 ecopuntos por cada kWh

    public static void main(String[] args) {
        // Configurar Scanner con Locale.US para asegurar el punto decimal (CE: 1.j, 2.c)
        Scanner teclado = new Scanner(System.in);
        teclado.useLocale(Locale.US);

        // Cabecera inicial del programa
        System.out.println("==================================================");
        System.out.println("   ECOVOLT SOLUTIONS S.L. - SIMULADOR DE FACTURA  ");
        System.out.println("==================================================");

        // 1. Entrada de datos con distintos tipos primitivos y String (CE: 1.d, 1.e, 1.j)
        System.out.print("Introduzca el nombre del titular: ");
        String nombreTitular = teclado.nextLine();

        System.out.print("Dias del periodo de facturacion (ej. 30): ");
        int diasFacturados = teclado.nextInt();

        System.out.print("Potencia contratada en kW (ej. 4.60): ");
        double potenciaContratadaKW = teclado.nextDouble();

        System.out.print("Energia total consumida en kWh (ej. 235.80): ");
        double consumoKWh = teclado.nextDouble();

        System.out.print("Tipo de tarifa horaria [V = Valle, P = Punta, E = Estandar]: ");
        char tipoTarifa = teclado.next().toUpperCase().charAt(0);

        System.out.print("Dispone de Bono Social activo [true/false]: ");
        boolean tieneBonoSocial = teclado.nextBoolean();

        // 2. Operaciones y expresiones aritméticas/lógicas (CE: 1.g)
        // Cálculo del término de potencia fijo
        double costePotencia = diasFacturados * potenciaContratadaKW * COSTE_POTENCIA_DIA;

        // Determinación del multiplicador según discriminación horaria
        // Valle: 15% rebaja (0.85), Punta: 20% recargo (1.20), Estándar: 1.00
        double factorTarifa = (tipoTarifa == 'V') ? 0.85 : ((tipoTarifa == 'P') ? 1.20 : 1.00);

        // Cálculo del término de energía variable
        double costeEnergiaBruto = consumoKWh * TARIFA_BASE_KWH * factorTarifa;

        // Descuento por bono social si aplica
        double descuentoBono = tieneBonoSocial ? (costeEnergiaBruto * DESCUENTO_BONO_SOCIAL) : 0.0;
        double costeEnergiaNeto = costeEnergiaBruto - descuentoBono;

        // Base imponible antes de impuestos
        double baseImponible = costePotencia + costeEnergiaNeto;

        // Aplicación del Impuesto Eléctrico Especial
        double importeImpuestoElectrico = baseImponible * IMPUESTO_ELECTRICIDAD;

        // Base para el IVA y cálculo del IVA (21%)
        double subtotalConImpuestoElectrico = baseImponible + importeImpuestoElectrico;
        double importeIVA = subtotalConImpuestoElectrico * IVA_PORCENTAJE;

        // Importe final total de la factura
        double totalFactura = subtotalConImpuestoElectrico + importeIVA;

        // 3. Conversión de tipo explícita (Casting) (CE: 1.h)
        // Los ecopuntos se otorgan como enteros truncados
        int ecopuntosGanados = (int) (consumoKWh * FACTOR_ECOPUNTOS);

        // 4. Salida tabular formateada con System.out.printf (CE: 1.j)
        System.out.println("\n--------------------------------------------------");
        System.out.println("               DESGLOSE DE FACTURA                ");
        System.out.println("--------------------------------------------------");
        System.out.printf("Titular del contrato     : %s%n", nombreTitular);
        System.out.printf("Modalidad de tarifa      : Tarifa '%c' (Factor: %.2f)%n", tipoTarifa, factorTarifa);
        System.out.printf("Periodo facturado        : %d dias%n", diasFacturados);
        System.out.printf("Potencia (%5.2f kW)      : %8.2f EUR%n", potenciaContratadaKW, costePotencia);
        System.out.printf("Consumo  (%6.2f kWh)    : %8.2f EUR%n", consumoKWh, costeEnergiaBruto);

        if (tieneBonoSocial) {
            System.out.printf("Descuento Bono Social    : -%7.2f EUR%n", descuentoBono);
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Base imponible           : %8.2f EUR%n", baseImponible);
        System.out.printf("Impuesto Electricidad    : %8.2f EUR%n", importeImpuestoElectrico);
        System.out.printf("IVA (21.00%%)             : %8.2f EUR%n", importeIVA);
        System.out.println("==================================================");
        System.out.printf("TOTAL A PAGAR            : %8.2f EUR%n", totalFactura);
        System.out.println("==================================================");
        System.out.printf("Fidelizacion EcoPuntos   : %d pts (Canjeables)%n", ecopuntosGanados);
        System.out.println("==================================================");

        teclado.close();
    }
}

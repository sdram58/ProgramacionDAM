#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script Autocorrector de Exámenes Prácticos - Programación Java (DAM 0485)
Unidad 2 (Primeros pasos con Java) y Unidad 3 (Estructuras de control, bucles, arrays y métodos)

Evalúa automáticamente el código Java de un alumno contra la rúbrica oficial (0 - 10 puntos),
ejecuta análisis estático, detecta "horrores de programación" y ejecuta pruebas dinámicas con casos test.
"""

import argparse
import os
import re
import shutil
import subprocess
import sys
import tempfile

# Asegurar salida utf-8 en terminales Windows
if sys.stdout.encoding and sys.stdout.encoding.lower() != 'utf-8':
    try:
        sys.stdout.reconfigure(encoding='utf-8')
    except Exception:
        pass

def normalizar_salida(texto):
    """Normaliza texto para comparaciones flexibles (reemplaza coma por punto, espacios, mayúsculas)"""
    t = texto.replace(',', '.')
    t = re.sub(r'\s+', ' ', t).strip().lower()
    return t

def extraer_numeros(texto):
    """Extrae todos los números enteros o flotantes de una cadena"""
    # Reemplazar comas decimales por puntos primero
    t = re.sub(r'(\d+),(\d+)', r'\1.\2', texto)
    return [float(x) for x in re.findall(r'[-+]?\d*\.?\d+', t)]

def analizar_horrores(codigo):
    """Analiza malas prácticas graves en el código y devuelve lista de horrores detectados"""
    horrores = []
    
    # 1. Bloque catch vacío o que solo imprime printStackTrace
    if re.search(r'catch\s*\([^\)]+\)\s*\{\s*\}', codigo):
        horrores.append({
            "tipo": "Bloque CATCH vacío",
            "descripcion": "Se ha detectado un bloque 'catch (...) {}' vacío. Tragar excepciones sin informar al usuario ni registrar el error es un antipatrón crítico.",
            "penalizacion": 1.5
        })

    # 2. Comparación de Strings con == o != (evitar falsos positivos con separadores tipo "======")
    # Detecta variable == "texto" o "texto" == variable
    if re.search(r'([a-zA-Z0-9_]+\s*(?:==|!=)\s*"[a-zA-Z0-9_ ]+")|("[a-zA-Z0-9_ ]+"\s*(?:==|!=)\s*[a-zA-Z0-9_]+)', codigo):
        horrores.append({
            "tipo": "Comparación de cadenas con ==",
            "descripcion": "Se comparan objetos String mediante '==' o '!=' en lugar de usar '.equals()' o '.equalsIgnoreCase()'.",
            "penalizacion": 1.0
        })

    # 3. Nombres de variables crípticos (a, b, c, x, y, z) fuera de bucles
    lineas = codigo.splitlines()
    variables_cripticas = []
    for l in lineas:
        l_trim = l.strip()
        # Ignorar líneas de bucles for y comentarios
        if l_trim.startswith("for") or l_trim.startswith("//") or l_trim.startswith("*"):
            continue
        m = re.search(r'\b(int|double|float|String|boolean|char)\s+([a-zA-Z])\s*(=|;|,)', l_trim)
        if m:
            var_name = m.group(2)
            if var_name.lower() not in ['i', 'j', 'k', 'n', 'e']:
                variables_cripticas.append(var_name)
    if variables_cripticas:
        horrores.append({
            "tipo": "Nombres de variables no descriptivos",
            "descripcion": f"Se han declarado variables con nombres crípticos de una sola letra: {', '.join(set(variables_cripticas))}. El código debe ser autodocumentado.",
            "penalizacion": 0.75
        })

    # 4. Números mágicos (hardcoded) en cálculos
    # Si hay 0.21 directamente en fórmulas sin haberse definido una constante final de IVA
    tiene_constante_iva = bool(re.search(r'final\s+[a-zA-Z0-9_<>\s]+\b.*IVA.*\b\s*=', codigo))
    if "0.21" in codigo and not tiene_constante_iva:
        horrores.append({
            "tipo": "Uso de números mágicos",
            "descripcion": "Se utilizan literales numéricos como porcentajes o tasas de cálculo directamente en las fórmulas en lugar de constantes simbólicas 'final'.",
            "penalizacion": 0.5
        })

    # 5. Ausencia total de comentarios
    num_comentarios = len(re.findall(r'(//|/\*|\*)', codigo))
    if num_comentarios < 3:
        horrores.append({
            "tipo": "Código indocumentado",
            "descripcion": "Apenas se han incluido comentarios explicativos en el código fuente (criterio CE 1.i / 3.g).",
            "penalizacion": 0.5
        })

    return horrores

def evaluar_unidad_2(codigo, ruta_archivo):
    """Evalúa un examen de la Unidad 2 según su rúbrica (0 a 10 puntos)"""
    rubrica = {
        "1. Estructura y Sintaxis (RA 1, CE 1.a, 1.b, 1.c)": {"max": 1.0, "obtenido": 0.0, "detalles": []},
        "2. Variables, Tipos y Constantes (RA 1, CE 1.d, 1.f)": {"max": 2.5, "obtenido": 0.0, "detalles": []},
        "3. Entrada y Conversión Explícita (RA 1, CE 1.e, 1.h, 1.j)": {"max": 2.0, "obtenido": 0.0, "detalles": []},
        "4. Expresiones y Cálculos (RA 1, CE 1.g)": {"max": 2.5, "obtenido": 0.0, "detalles": []},
        "5. Salida Formateada y Tabulación (RA 1, CE 1.j)": {"max": 2.0, "obtenido": 0.0, "detalles": []}
    }

    # 1. Análisis de estructura
    if "public class" in codigo and "public static void main" in codigo:
        rubrica["1. Estructura y Sintaxis (RA 1, CE 1.a, 1.b, 1.c)"]["obtenido"] += 1.0
        rubrica["1. Estructura y Sintaxis (RA 1, CE 1.a, 1.b, 1.c)"]["detalles"].append("Estructura canónica de clase y método main correcta.")
    else:
        rubrica["1. Estructura y Sintaxis (RA 1, CE 1.a, 1.b, 1.c)"]["detalles"].append("Falta declaración correcta de clase pública o método main.")

    # 2. Variables, tipos y constantes
    puntos_var = 0.0
    # Constantes final
    final_matches = len(re.findall(r'\bfinal\s+(double|int|float|String)\b', codigo))
    if final_matches >= 3:
        puntos_var += 1.0
        rubrica["2. Variables, Tipos y Constantes (RA 1, CE 1.d, 1.f)"]["detalles"].append(f"Se definen {final_matches} constantes simbólicas con 'final'.")
    elif final_matches > 0:
        puntos_var += 0.5
        rubrica["2. Variables, Tipos y Constantes (RA 1, CE 1.d, 1.f)"]["detalles"].append("Se definen algunas constantes 'final', pero faltan parámetros clave.")
    else:
        rubrica["2. Variables, Tipos y Constantes (RA 1, CE 1.d, 1.f)"]["detalles"].append("No se utilizan constantes simbólicas 'final'.")

    # Tipos variados: int, double, boolean, char, String
    tipos_usados = []
    for t in ['int', 'double', 'boolean', 'char', 'String']:
        if re.search(r'\b' + t + r'\b', codigo):
            tipos_usados.append(t)
    if len(tipos_usados) >= 4:
        puntos_var += 1.5
        rubrica["2. Variables, Tipos y Constantes (RA 1, CE 1.d, 1.f)"]["detalles"].append(f"Uso variado y adecuado de tipos de datos: {', '.join(tipos_usados)}.")
    elif len(tipos_usados) >= 2:
        puntos_var += 0.75
        rubrica["2. Variables, Tipos y Constantes (RA 1, CE 1.d, 1.f)"]["detalles"].append(f"Uso básico de tipos: {', '.join(tipos_usados)}.")
    rubrica["2. Variables, Tipos y Constantes (RA 1, CE 1.d, 1.f)"]["obtenido"] = puntos_var

    # 3. Entrada y casting
    puntos_in = 0.0
    if "Scanner" in codigo and ("System.in" in codigo or "useLocale" in codigo):
        puntos_in += 1.0
        rubrica["3. Entrada y Conversión Explícita (RA 1, CE 1.e, 1.h, 1.j)"]["detalles"].append("Lectura de datos por consola implementada con clase Scanner.")
    # Casting explícito: (int) o (double)
    if re.search(r'\(\s*(int|double|long)\s*\)', codigo):
        puntos_in += 1.0
        rubrica["3. Entrada y Conversión Explícita (RA 1, CE 1.e, 1.h, 1.j)"]["detalles"].append("Conversión de tipo explícita (casting) aplicada correctamente.")
    else:
        rubrica["3. Entrada y Conversión Explícita (RA 1, CE 1.e, 1.h, 1.j)"]["detalles"].append("No se detecta casting explícito para el cálculo de enteros truncados/puntos.")
    rubrica["3. Entrada y Conversión Explícita (RA 1, CE 1.e, 1.h, 1.j)"]["obtenido"] = puntos_in

    # 4. Expresiones y Cálculos (evaluación dinámica)
    # Ejecutamos con datos de prueba
    test_input = "Carlos Garcia\n30\n4.60\n235.80\nV\ntrue\n"
    # Salidas esperadas:
    # Potencia: 30 * 4.60 * 0.1120 = 15.456 -> ~15.46
    # Consumo Valle: 235.80 * 0.1475 * 0.85 = 29.563 -> ~29.56
    # Descuento: 29.563 * 0.25 = 7.39
    # Base: 15.456 + (29.563 - 7.390) = 37.629 -> ~37.63
    # Total: ~47.86
    # Ecopuntos: (int)(235.8 * 8.5) = 2004
    salida_ejecucion, err_ejecucion = compilar_y_ejecutar(ruta_archivo, test_input)
    
    puntos_calc = 0.0
    if salida_ejecucion:
        nums = extraer_numeros(salida_ejecucion)
        # Comprobar si aparecen valores cercanos a los cálculos clave
        # Verificamos si total está en el rango [45.0, 52.0]
        tiene_total = any(46.0 <= n <= 49.5 for n in nums)
        tiene_base = any(36.0 <= n <= 39.0 for n in nums)
        tiene_puntos = any(1900 <= n <= 2100 for n in nums)
        
        if tiene_total and tiene_base:
            puntos_calc += 2.0
            rubrica["4. Expresiones y Cálculos (RA 1, CE 1.g)"]["detalles"].append("Cálculos aritméticos y lógicos correctos (término potencia, energía, IVA y total verificados).")
        elif tiene_total or tiene_base:
            puntos_calc += 1.25
            rubrica["4. Expresiones y Cálculos (RA 1, CE 1.g)"]["detalles"].append("Cálculos parcialmente correctos con pequeñas discrepancias.")
        else:
            puntos_calc += 0.5
            rubrica["4. Expresiones y Cálculos (RA 1, CE 1.g)"]["detalles"].append("Fórmulas implementadas pero los valores calculados no coinciden con la especificación.")
            
        if tiene_puntos:
            puntos_calc += 0.5
            rubrica["4. Expresiones y Cálculos (RA 1, CE 1.g)"]["detalles"].append("Cálculo de fidelización (ecopuntos truncados) verificado dinámicamente.")
    else:
        rubrica["4. Expresiones y Cálculos (RA 1, CE 1.g)"]["detalles"].append(f"Fallo de ejecución en prueba dinámica: {err_ejecucion}")
    rubrica["4. Expresiones y Cálculos (RA 1, CE 1.g)"]["obtenido"] = puntos_calc

    # 5. Salida Formateada
    puntos_out = 0.0
    if "printf" in codigo:
        puntos_out += 1.0
        rubrica["5. Salida Formateada y Tabulación (RA 1, CE 1.j)"]["detalles"].append("Uso de System.out.printf con especificadores de formato.")
    if re.search(r'%\.?\d*[dfsn]', codigo):
        puntos_out += 1.0
        rubrica["5. Salida Formateada y Tabulación (RA 1, CE 1.j)"]["detalles"].append("Formateo de decimales (%.2f) y alineación tabular implementados.")
    rubrica["5. Salida Formateada y Tabulación (RA 1, CE 1.j)"]["obtenido"] = puntos_out

    return rubrica

def evaluar_unidad_3(codigo, ruta_archivo):
    """Evalúa un examen de la Unidad 3 según su rúbrica (0 a 10 puntos)"""
    rubrica = {
        "1. Modularidad y Expresiones Regulares (RA 1, RA 6, CE 1.a, 6.g)": {"max": 2.0, "obtenido": 0.0, "detalles": []},
        "2. Estructuras de Selección (RA 3, CE 3.a)": {"max": 1.5, "obtenido": 0.0, "detalles": []},
        "3. Bucles y Control de Excepciones (RA 3, CE 3.b, 3.c, 3.e)": {"max": 2.5, "obtenido": 0.0, "detalles": []},
        "4. Procesamiento de Arrays (RA 6, CE 6.a)": {"max": 2.5, "obtenido": 0.0, "detalles": []},
        "5. Salida Formateada y Robustez (RA 1, RA 3, CE 1.j, 3.f)": {"max": 1.5, "obtenido": 0.0, "detalles": []}
    }

    # 1. Modularidad y Regex
    puntos_mod = 0.0
    metodos_estaticos = len(re.findall(r'public\s+static\s+(?!void\s+main)[a-zA-Z0-9_<>\[\]]+\s+[a-zA-Z0-9_]+\s*\(', codigo))
    if metodos_estaticos >= 3:
        puntos_mod += 1.0
        rubrica["1. Modularidad y Expresiones Regulares (RA 1, RA 6, CE 1.a, 6.g)"]["detalles"].append(f"Excelente modularización: se definen {metodos_estaticos} métodos estáticos auxiliares.")
    elif metodos_estaticos >= 1:
        puntos_mod += 0.5
        rubrica["1. Modularidad y Expresiones Regulares (RA 1, RA 6, CE 1.a, 6.g)"]["detalles"].append("Modularización básica (1-2 métodos auxiliares).")
    else:
        rubrica["1. Modularidad y Expresiones Regulares (RA 1, RA 6, CE 1.a, 6.g)"]["detalles"].append("Todo el código se concentra en el main sin modularizar.")

    if ".matches(" in codigo or "Pattern.compile(" in codigo:
        puntos_mod += 1.0
        rubrica["1. Modularidad y Expresiones Regulares (RA 1, RA 6, CE 1.a, 6.g)"]["detalles"].append("Expresiones regulares implementadas correctamente para validar el código de seguimiento.")
    else:
        rubrica["1. Modularidad y Expresiones Regulares (RA 1, RA 6, CE 1.a, 6.g)"]["detalles"].append("No se utilizan expresiones regulares (.matches o Pattern).")
    rubrica["1. Modularidad y Expresiones Regulares (RA 1, RA 6, CE 1.a, 6.g)"]["obtenido"] = puntos_mod

    # 2. Estructuras de selección (if-else y switch)
    puntos_sel = 0.0
    tiene_switch = "switch" in codigo
    tiene_if = "if" in codigo
    if tiene_switch and tiene_if:
        puntos_sel = 1.5
        rubrica["2. Estructuras de Selección (RA 3, CE 3.a)"]["detalles"].append("Uso combinado de estructuras 'switch' (categoría) e 'if-else' (descuentos/validaciones).")
    elif tiene_switch or tiene_if:
        puntos_sel = 0.75
        rubrica["2. Estructuras de Selección (RA 3, CE 3.a)"]["detalles"].append("Uso parcial de estructuras de selección (falta switch o if-else).")
    rubrica["2. Estructuras de Selección (RA 3, CE 3.a)"]["obtenido"] = puntos_sel

    # 3. Bucles y control de excepciones
    puntos_bucle = 0.0
    tiene_for = bool(re.search(r'\bfor\s*\(', codigo))
    tiene_while = bool(re.search(r'\bwhile\s*\(', codigo) or re.search(r'\bdo\s*\{', codigo))
    if tiene_for and tiene_while:
        puntos_bucle += 1.25
        rubrica["3. Bucles y Control de Excepciones (RA 3, CE 3.b, 3.c, 3.e)"]["detalles"].append("Uso adecuado de bucle FOR (recorrido array) y WHILE/DO-WHILE (validación interactiva).")
    elif tiene_for or tiene_while:
        puntos_bucle += 0.75
        rubrica["3. Bucles y Control de Excepciones (RA 3, CE 3.b, 3.c, 3.e)"]["detalles"].append("Se utiliza solo un tipo de estructura repetitiva.")

    tiene_try_catch = "try" in codigo and "catch" in codigo
    if tiene_try_catch:
        puntos_bucle += 1.25
        rubrica["3. Bucles y Control de Excepciones (RA 3, CE 3.b, 3.c, 3.e)"]["detalles"].append("Control de excepciones try-catch presente contra entradas no numéricas.")
    else:
        rubrica["3. Bucles y Control de Excepciones (RA 3, CE 3.b, 3.c, 3.e)"]["detalles"].append("No se implementa control de excepciones con try-catch.")
    rubrica["3. Bucles y Control de Excepciones (RA 3, CE 3.b, 3.c, 3.e)"]["obtenido"] = puntos_bucle

    # 4. Procesamiento de Arrays (pruebas dinámicas)
    # Test case:
    # 1. Invalid code 'malo'
    # 2. Valid code 'PKG-8492-ES'
    # 3. Valid type 'U' (Urgente: +8.00)
    # 4. Error number 'error' -> then 3
    # 5. Error weight 'bad' -> then 4.5, 12.8, 6.2
    # Total kg = 23.50, Media = 7.83, Max = 12.80, Pesados (>10kg) = 1
    # Coste = (3 * 6.50) + 8.00 + (23.50 * 1.25) = 19.50 + 8.00 + 29.375 = 56.875 -> ~56.88
    test_input = "malo\nPKG-8492-ES\nU\nerror\n3\nbad\n4.5\n12.8\n6.2\n"
    salida, err = compilar_y_ejecutar(ruta_archivo, test_input)

    puntos_arr = 0.0
    if "[]" in codigo and ("new double[" in codigo or "new float[" in codigo):
        puntos_arr += 0.5
        rubrica["4. Procesamiento de Arrays (RA 6, CE 6.a)"]["detalles"].append("Declaración e instanciación correcta del array de pesos.")

    if salida:
        nums = extraer_numeros(salida)
        tiene_peso_total = any(23.4 <= n <= 23.6 for n in nums)
        tiene_peso_medio = any(7.7 <= n <= 8.0 for n in nums)
        tiene_maximo = any(12.7 <= n <= 12.9 for n in nums)
        tiene_conteo_pesados = (1.0 in nums)
        tiene_coste = any(56.5 <= n <= 57.2 for n in nums)

        if tiene_peso_total and tiene_peso_medio:
            puntos_arr += 1.0
            rubrica["4. Procesamiento de Arrays (RA 6, CE 6.a)"]["detalles"].append("Cálculo de sumatorio y promedio de elementos del array verificado.")
        elif tiene_peso_total or tiene_peso_medio:
            puntos_arr += 0.5
            rubrica["4. Procesamiento de Arrays (RA 6, CE 6.a)"]["detalles"].append("Cálculo parcial de métricas sobre el array.")

        if tiene_maximo:
            puntos_arr += 0.5
            rubrica["4. Procesamiento de Arrays (RA 6, CE 6.a)"]["detalles"].append("Algoritmo de búsqueda del valor máximo en array verificado.")
        if tiene_conteo_pesados:
            puntos_arr += 0.5
            rubrica["4. Procesamiento de Arrays (RA 6, CE 6.a)"]["detalles"].append("Conteo de elementos bajo condición (> 10 kg) verificado.")
    else:
        rubrica["4. Procesamiento de Arrays (RA 6, CE 6.a)"]["detalles"].append(f"Error de ejecución al evaluar el array: {err}")
    rubrica["4. Procesamiento de Arrays (RA 6, CE 6.a)"]["obtenido"] = puntos_arr

    # 5. Salida Formateada y Robustez
    puntos_rob = 0.0
    if "printf" in codigo:
        puntos_rob += 0.75
        rubrica["5. Salida Formateada y Robustez (RA 1, RA 3, CE 1.j, 3.f)"]["detalles"].append("Informe final estructurado mediante printf con formato tabular.")
    if salida and ("error" in salida.lower() or "invalido" in salida.lower()):
        puntos_rob += 0.75
        rubrica["5. Salida Formateada y Robustez (RA 1, RA 3, CE 1.j, 3.f)"]["detalles"].append("Robustez demostrada: el programa interceptó entradas inválidas y se recuperó sin abortar.")
    rubrica["5. Salida Formateada y Robustez (RA 1, RA 3, CE 1.j, 3.f)"]["obtenido"] = puntos_rob

    return rubrica

def compilar_y_ejecutar(ruta_archivo, stdin_data, timeout_segundos=4):
    """Compila el archivo Java en directorio temporal y lo ejecuta pasando stdin"""
    temp_dir = tempfile.mkdtemp()
    try:
        # Copiar archivo a temp_dir asegurando que la clase coincida con el nombre
        nombre_base = os.path.basename(ruta_archivo)
        destino = os.path.join(temp_dir, nombre_base)
        shutil.copyfile(ruta_archivo, destino)
        
        # Obtener nombre de la clase principal
        nombre_clase = os.path.splitext(nombre_base)[0]
        
        # Compilación
        comp = subprocess.run(["javac", destino], capture_output=True, text=True)
        if comp.returncode != 0:
            return None, f"Error de compilación:\n{comp.stderr}"
            
        # Ejecución
        proc = subprocess.run(
            ["java", "-cp", temp_dir, nombre_clase],
            input=stdin_data,
            capture_output=True,
            text=True,
            timeout=timeout_segundos
        )
        return proc.stdout, proc.stderr
    except subprocess.TimeoutExpired:
        return None, "Error: Tiempo de ejecución excedido (posible bucle infinito o Scanner esperando entrada no enviada)."
    except Exception as e:
        return None, f"Error en ejecución: {str(e)}"
    finally:
        shutil.rmtree(temp_dir, ignore_errors=True)

def generar_informe(unidad, ruta_archivo, rubrica, horrores):
    """Genera el informe final de corrección formateado en texto / consola"""
    suma_obtenida = sum(item["obtenido"] for item in rubrica.values())
    total_penalizaciones = sum(h["penalizacion"] for h in horrores)
    nota_final = max(0.0, round(suma_obtenida - total_penalizaciones, 2))

    print("\n" + "=" * 70)
    print(f"      INFORME OFICIAL DE EVALUACIÓN - EXAMEN UNIDAD {unidad} (DAM 0485)      ")
    print("=" * 70)
    print(f"Archivo evaluado : {os.path.abspath(ruta_archivo)}")
    print(f"Puntuación base  : {suma_obtenida:.2f} / 10.00")
    print(f"Penalizaciones   : -{total_penalizaciones:.2f}")
    print(f"CALIFICACIÓN     : {nota_final:.2f} / 10.00")
    print("-" * 70)
    print(f"{'CRITERIO EVALUADO':<50} | {'NOTA':>15}")
    print("-" * 70)

    for seccion, datos in rubrica.items():
        print(f"{seccion:<50} | {datos['obtenido']:>6.2f} / {datos['max']:.2f}")
        for det in datos["detalles"]:
            print(f"  • {det}")

    print("-" * 70)
    if horrores:
        print("⚠️  HORRORES Y MALAS PRÁCTICAS DETECTADAS:")
        for h in horrores:
            print(f"  [PENALIZACIÓN -{h['penalizacion']:.2f} pts] {h['tipo']}")
            print(f"    -> {h['descripcion']}")
    else:
        print("✨ CÓDIGO LIMPIO: No se detectaron horrores graves de programación.")
    
    print("=" * 70)
    print(f"NOTA FINAL DEFINITIVA: {nota_final:.2f} SOBRE 10.00")
    print("=" * 70 + "\n")
    return nota_final

def main():
    parser = argparse.ArgumentParser(description="Autocorrector de Exámenes de Programación DAM")
    parser.add_argument("--unidad", type=int, required=True, choices=[2, 3], help="Unidad a evaluar (2 o 3)")
    parser.add_argument("--archivo", type=str, required=True, help="Ruta al archivo .java del alumno")
    args = parser.parse_args()

    if not os.path.exists(args.archivo):
        print(f"Error: El archivo '{args.archivo}' no existe.")
        sys.exit(1)

    with open(args.archivo, 'r', encoding='utf-8', errors='ignore') as f:
        codigo = f.read()

    horrores = analizar_horrores(codigo)

    if args.unidad == 2:
        rubrica = evaluar_unidad_2(codigo, args.archivo)
    else:
        rubrica = evaluar_unidad_3(codigo, args.archivo)

    generar_informe(args.unidad, args.archivo, rubrica, horrores)

if __name__ == "__main__":
    main()

# -*- coding: utf-8 -*-
"""
Generador del documento oficial de Criterios de Evaluación
del módulo 0485 - Programación (DAM) para el curso 2026-2027.
"""

import os
import re
import sys
import docx
from docx import Document
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_ALIGN_VERTICAL
from docx.oxml import OxmlElement, parse_xml
from docx.oxml.ns import nsdecls, qn

def parse_original_document(docx_path):
    doc = docx.Document(docx_path)
    
    # 1. Parse RA and Criteria from paragraphs
    ra_list = []
    current_ra = None
    current_ra_num = 0

    for i in range(78, 175):
        text = doc.paragraphs[i].text.strip()
        if not text:
            continue
        if text.startswith("RA "):
            current_ra_num += 1
            parts = text.split(". Criterios de evaluación:")
            ra_full = parts[0]
            if ". " in ra_full:
                _, ra_desc = ra_full.split(". ", 1)
            else:
                ra_desc = ra_full
            current_ra = {
                "num": current_ra_num,
                "code": f"RA {current_ra_num}",
                "description": ra_desc,
                "criteria": []
            }
            ra_list.append(current_ra)
        elif current_ra and not text.startswith("De todos los criterios"):
            idx = len(current_ra["criteria"])
            letter = chr(ord('a') + idx)
            current_ra["criteria"].append({
                "letter": letter,
                "code": f"{current_ra_num}.{letter})",
                "text": text
            })

    # 2. Parse Empresa Table (Table 2)
    empresa_table = doc.tables[2]
    empresa_criteria = []
    for row in empresa_table.rows[1:]:
        cells = [c.text.strip().replace('\n', ' ') for c in row.cells]
        if len(cells) >= 2:
            empresa_criteria.append({
                "ra": cells[0],
                "criterio": cells[1]
            })

    # 3. Parse Unit Tables (Tables 3 to 13)
    units = []
    for idx in range(3, 14):
        u_num = idx - 2
        t = doc.tables[idx]
        title = t.rows[0].cells[0].text.strip().replace('\n', ' ')
        hours = t.rows[1].cells[1].text.strip().replace('\n', ' ')
        ras = t.rows[8].cells[1].text.strip().replace('\n', ' ')
        ces = t.rows[9].cells[1].text.strip().replace('\n', ' ')

        # Integrar y subsanar criterios omitidos o con errata en las tablas originales
        if u_num == 3:
            ces = "3.b) 6.g) " + ces
        elif u_num == 4:
            ces = "4.e) " + ces
        elif u_num == 5:
            ces = "4.e) 7.i) 7.j) " + ces + " Se han identificado y evaluado los escenarios de uso de interfaces. Se han identificado y evaluado los escenarios de utilización de la herencia y la composición."
        elif u_num == 7:
            ces = "6.j) " + ces + " Se han utilizado operaciones agregadas para el manejo de información almacenada en colecciones."
        elif u_num == 11:
            ces = "8.h) " + ces

        units.append({
            "unit_number": u_num,
            "title": title,
            "hours": hours,
            "ras": ras,
            "ces": ces
        })

    # 4. Parse Evaluation and Grading details (P310 - P375)
    calificacion_texts = []
    for i in range(310, 375):
        p = doc.paragraphs[i]
        txt = p.text.strip()
        if txt:
            calificacion_texts.append(txt)

    return {
        "ra_list": ra_list,
        "empresa_criteria": empresa_criteria,
        "units": units,
        "calificacion_texts": calificacion_texts
    }

# Paleta institucional del proyecto
COLOR_PRIMARY_HEX = "2D8A4E"       # Verde calma institucional (#2d8a4e)
COLOR_PRIMARY_DARK = "1B5E33"      # Verde bosque oscuro
COLOR_SECONDARY_HEX = "1E293B"     # Slate 800
COLOR_BG_LIGHT = "F0FDF4"          # Verde menta muy suave (#f0fdf4)
COLOR_BG_ALT = "F8FAFC"            # Fondo alterno neutro
COLOR_BORDER = "CBD5E1"            # Borde gris suave
COLOR_EMPRESA = "0D9488"           # Verde azulado / Teal para criterios de empresa

def set_cell_background(cell, color_hex):
    tcPr = cell._tc.get_or_add_tcPr()
    shd = parse_xml(f'<w:shd {nsdecls("w")} w:fill="{color_hex}"/>')
    tcPr.append(shd)

def set_cell_margins(cell, top=100, bottom=100, left=140, right=140):
    tcPr = cell._tc.get_or_add_tcPr()
    tcMar = parse_xml(
        f'<w:tcMar {nsdecls("w")}>'
        f'  <w:top w:w="{top}" w:type="dxa"/>'
        f'  <w:bottom w:w="{bottom}" w:type="dxa"/>'
        f'  <w:left w:w="{left}" w:type="dxa"/>'
        f'  <w:right w:w="{right}" w:type="dxa"/>'
        f'</w:tcMar>'
    )
    tcPr.append(tcMar)

def set_table_borders(table, color="D1D5DB", sz="4"):
    tblPr = table._tbl.tblPr
    borders = parse_xml(
        f'<w:tblBorders {nsdecls("w")}>'
        f'  <w:top w:val="single" w:sz="{sz}" w:space="0" w:color="{color}"/>'
        f'  <w:bottom w:val="single" w:sz="{sz}" w:space="0" w:color="{color}"/>'
        f'  <w:left w:val="none"/>'
        f'  <w:right w:val="none"/>'
        f'  <w:insideH w:val="single" w:sz="{sz}" w:space="0" w:color="{color}"/>'
        f'  <w:insideV w:val="none"/>'
        f'</w:tblBorders>'
    )
    tblPr.append(borders)

def add_page_number_field(paragraph):
    pPr = paragraph._p.get_or_add_pPr()
    # "Página "
    run1 = paragraph.add_run("Página ")
    run1.font.name = "Calibri"
    run1.font.size = Pt(8.5)
    run1.font.color.rgb = RGBColor(0x94, 0xA3, 0xB8)
    
    # Simple field PAGE
    fld1 = parse_xml(f'<w:fldSimple {nsdecls("w")} w:instr="PAGE"/>')
    paragraph._p.append(fld1)
    
    # " de "
    run2 = paragraph.add_run(" de ")
    run2.font.name = "Calibri"
    run2.font.size = Pt(8.5)
    run2.font.color.rgb = RGBColor(0x94, 0xA3, 0xB8)
    
    # Simple field NUMPAGES
    fld2 = parse_xml(f'<w:fldSimple {nsdecls("w")} w:instr="NUMPAGES"/>')
    paragraph._p.append(fld2)

def make_callout(doc, title, text_paragraphs):
    table = doc.add_table(rows=1, cols=1)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    table.autofit = False
    
    cell = table.cell(0, 0)
    cell.width = Inches(6.5)
    set_cell_background(cell, COLOR_BG_LIGHT)
    set_cell_margins(cell, top=140, bottom=140, left=180, right=160)
    
    tcPr = cell._tc.get_or_add_tcPr()
    borders = parse_xml(
        f'<w:tcBorders {nsdecls("w")}>'
        f'  <w:top w:val="none"/>'
        f'  <w:bottom w:val="none"/>'
        f'  <w:left w:val="single" w:sz="24" w:space="0" w:color="{COLOR_PRIMARY_HEX}"/>'
        f'  <w:right w:val="none"/>'
        f'</w:tcBorders>'
    )
    tcPr.append(borders)
    
    p = cell.paragraphs[0]
    p.paragraph_format.space_before = Pt(2)
    p.paragraph_format.space_after = Pt(4)
    run_title = p.add_run(f"📌 {title}\n")
    run_title.font.name = "Calibri"
    run_title.font.size = Pt(11)
    run_title.font.bold = True
    run_title.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)
    
    for t in text_paragraphs:
        p2 = cell.add_paragraph()
        p2.paragraph_format.space_before = Pt(2)
        p2.paragraph_format.space_after = Pt(3)
        run_txt = p2.add_run(t)
        run_txt.font.name = "Calibri"
        run_txt.font.size = Pt(9.5)
        run_txt.font.color.rgb = RGBColor(0x1E, 0x29, 0x3B)
    
    doc.add_paragraph().paragraph_format.space_after = Pt(6)

def format_unit_ces(ces_raw):
    codes = re.findall(r'(\d+\.[a-z]\)?)', ces_raw)
    
    # Buscar el inicio de las frases descriptivas
    m = re.search(r'(?:[0-9]\.[a-z]\)?\s*,?\s*)+(?=[A-ZÁÉÍÓÚ])', ces_raw)
    if m:
        codes_part = ces_raw[:m.end()].strip()
        text_part = ces_raw[m.end():].strip()
    else:
        codes_part = ", ".join(codes)
        text_part = ces_raw
        for c in codes:
            text_part = text_part.replace(c, "")
        text_part = text_part.strip()
        
    sentences = [s.strip() for s in re.split(r'\.\s+(?=[A-ZÁÉÍÓÚ])', text_part) if s.strip()]
    cleaned_sentences = []
    for s in sentences:
        s_clean = s.rstrip(".") + "."
        cleaned_sentences.append(s_clean)
        
    return codes, codes_part, cleaned_sentences

def create_criterios_document(source_path, output_path):
    data = parse_original_document(source_path)
    
    doc = Document()
    
    # Configuración de página A4 con márgenes de 2.5 cm
    for section in doc.sections:
        section.top_margin = Inches(0.98)
        section.bottom_margin = Inches(0.98)
        section.left_margin = Inches(0.98)
        section.right_margin = Inches(0.98)
        section.page_width = Inches(8.27)
        section.page_height = Inches(11.69)
        
        # Header institucional
        header = section.header
        hp = header.paragraphs[0]
        hp.alignment = WD_ALIGN_PARAGRAPH.RIGHT
        hrun = hp.add_run("CFGS DAM — Módulo 0485: Programación | Criterios de Evaluación Oficiales")
        hrun.font.name = "Calibri"
        hrun.font.size = Pt(8.5)
        hrun.font.color.rgb = RGBColor(0x94, 0xA3, 0xB8)
        
        # Footer institucional con número de página automático
        footer = section.footer
        fp = footer.paragraphs[0]
        fp.alignment = WD_ALIGN_PARAGRAPH.RIGHT
        add_page_number_field(fp)

    # PORTADA / ENCABEZADO PRINCIPAL
    title_p = doc.add_paragraph()
    title_p.paragraph_format.space_before = Pt(10)
    title_p.paragraph_format.space_after = Pt(2)
    run_badge = title_p.add_run("DOCUMENTO TÉCNICO CURRICULAR — CURSO 2026/2027\n")
    run_badge.font.name = "Calibri"
    run_badge.font.size = Pt(10)
    run_badge.font.bold = True
    run_badge.font.color.rgb = RGBColor(0x2D, 0x8A, 0x4E)

    run_title = title_p.add_run("Criterios de Evaluación Oficiales")
    run_title.font.name = "Calibri"
    run_title.font.size = Pt(26)
    run_title.font.bold = True
    run_title.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    sub_p = doc.add_paragraph()
    sub_p.paragraph_format.space_before = Pt(2)
    sub_p.paragraph_format.space_after = Pt(14)
    run_sub = sub_p.add_run("Módulo Profesional 0485: Programación\n")
    run_sub.font.name = "Calibri"
    run_sub.font.size = Pt(13)
    run_sub.font.bold = True
    run_sub.font.color.rgb = RGBColor(0x33, 0x41, 0x55)

    run_sub2 = sub_p.add_run("CFGS en Desarrollo de Aplicaciones Multiplataforma (DAM) — Formación Profesional")
    run_sub2.font.name = "Calibri"
    run_sub2.font.size = Pt(10.5)
    run_sub2.font.italic = True
    run_sub2.font.color.rgb = RGBColor(0x64, 0x74, 0x8B)

    # Tabla resumen de metadatos del módulo
    meta_table = doc.add_table(rows=2, cols=4)
    meta_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    meta_table.autofit = False
    set_table_borders(meta_table, color="2D8A4E", sz="6")
    
    meta_headers = ["Módulo / Código", "Carga Horaria", "Régimen Lectivo", "Total Criterios"]
    meta_values = ["Programación (0485)", "240 Horas Anuales", "8 Horas / Semana", "78 Criterios (9 RAs)"]
    
    col_widths = [Inches(1.8), Inches(1.4), Inches(1.8), Inches(1.5)]
    for c_i, header_txt in enumerate(meta_headers):
        cell = meta_table.cell(0, c_i)
        cell.width = col_widths[c_i]
        set_cell_background(cell, COLOR_PRIMARY_HEX)
        set_cell_margins(cell, top=80, bottom=80, left=100, right=100)
        p = cell.paragraphs[0]
        p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        r = p.add_run(header_txt)
        r.font.name = "Calibri"
        r.font.size = Pt(9.5)
        r.font.bold = True
        r.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)
        
        cell_v = meta_table.cell(1, c_i)
        cell_v.width = col_widths[c_i]
        set_cell_background(cell_v, COLOR_BG_LIGHT)
        set_cell_margins(cell_v, top=100, bottom=100, left=100, right=100)
        pv = cell_v.paragraphs[0]
        pv.alignment = WD_ALIGN_PARAGRAPH.CENTER
        rv = pv.add_run(meta_values[c_i])
        rv.font.name = "Calibri"
        rv.font.size = Pt(10)
        rv.font.bold = True
        rv.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    p_space = doc.add_paragraph()
    p_space.paragraph_format.space_before = Pt(12)
    p_space.paragraph_format.space_after = Pt(2)

    # TEXTO INTRODUCTORIO
    intro_p = doc.add_paragraph()
    intro_p.paragraph_format.space_after = Pt(8)
    r_intro = intro_p.add_run(
        "El presente documento técnico extrae y organiza todos los criterios de evaluación del módulo profesional "
        "0485 — Programación a partir de la Programación Didáctica oficial para el curso 2026/2027. "
        "Ofrece una referencia clara y exhaustiva para el profesorado, el alumnado y los tutores de empresa, estructurada en "
        "cuatro ejes fundamentales: el marco normativo de los 9 Resultados de Aprendizaje, el desglose de los 78 criterios "
        "específicos, su asignación a las 11 Unidades Didácticas del curso, los criterios evaluados en el centro de trabajo (empresa), "
        "y los criterios y procedimientos de calificación correspondientes."
    )
    r_intro.font.name = "Calibri"
    r_intro.font.size = Pt(10)
    r_intro.font.color.rgb = RGBColor(0x33, 0x41, 0x55)

    # =========================================================================
    # SECCIÓN 1: CUADRO GENERAL DE RESULTADOS DE APRENDIZAJE
    # =========================================================================
    h1 = doc.add_paragraph()
    h1.paragraph_format.space_before = Pt(14)
    h1.paragraph_format.space_after = Pt(6)
    r_h1 = h1.add_run("1. Cuadro General de Resultados de Aprendizaje (RA)")
    r_h1.font.name = "Calibri"
    r_h1.font.size = Pt(15)
    r_h1.font.bold = True
    r_h1.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    summary_table = doc.add_table(rows=10, cols=5)
    summary_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    summary_table.autofit = False
    set_table_borders(summary_table, color="D1D5DB", sz="4")

    s_headers = ["RA", "Resultado de Aprendizaje (Enunciado Oficial)", "Criterios", "Ponderación", "Criterio Empresa"]
    s_widths = [Inches(0.6), Inches(3.5), Inches(0.8), Inches(0.8), Inches(0.8)]

    for c_i, h_txt in enumerate(s_headers):
        cell = summary_table.cell(0, c_i)
        cell.width = s_widths[c_i]
        set_cell_background(cell, COLOR_PRIMARY_HEX)
        set_cell_margins(cell, top=90, bottom=90, left=80, right=80)
        p = cell.paragraphs[0]
        if c_i in [0, 2, 3, 4]:
            p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        r = p.add_run(h_txt)
        r.font.name = "Calibri"
        r.font.size = Pt(9)
        r.font.bold = True
        r.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)

    # Diccionario de criterios evaluados en empresa
    empresa_dict = {}
    for item in data["empresa_criteria"]:
        ra_num_str = item["ra"].split(".")[0].replace("RA", "").strip()
        crit_letter = item["criterio"].split(")")[0].strip()
        empresa_dict[int(ra_num_str)] = f"{ra_num_str}.{crit_letter})"

    for r_i, ra_obj in enumerate(data["ra_list"]):
        row_idx = r_i + 1
        num = ra_obj["num"]
        num_crit = len(ra_obj["criteria"])
        emp_crit = empresa_dict.get(num, "—")
        
        cells = [
            f"RA {num}",
            ra_obj["description"],
            f"{num_crit} CEs\n(a - {chr(ord('a') + num_crit - 1)})",
            "Equitativa\n(1/9 cada RA)",
            emp_crit
        ]
        
        bg_col = COLOR_BG_ALT if (r_i % 2 == 1) else "FFFFFF"
        for c_i, val in enumerate(cells):
            cell = summary_table.cell(row_idx, c_i)
            cell.width = s_widths[c_i]
            set_cell_background(cell, bg_col)
            set_cell_margins(cell, top=70, bottom=70, left=80, right=80)
            p = cell.paragraphs[0]
            if c_i in [0, 2, 3, 4]:
                p.alignment = WD_ALIGN_PARAGRAPH.CENTER
            r = p.add_run(val)
            r.font.name = "Calibri"
            r.font.size = Pt(8.5)
            if c_i == 0:
                r.font.bold = True
                r.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)
            elif c_i == 4 and emp_crit != "—":
                r.font.bold = True
                r.font.color.rgb = RGBColor(0x0D, 0x94, 0x88)

    doc.add_paragraph().paragraph_format.space_after = Pt(2)
    
    make_callout(
        doc,
        "Principio de Evaluación y Ponderación de los RAs",
        [
            "• Peso equitativo de los RAs: Los RA 1 al RA 9 tienen el mismo peso porcentual en la calificación global.",
            "• Resultados de Aprendizaje Nucleares: RA 2 (Fundamentos de POO), RA 3 (Estructuras de control y depuración) y RA 4 (Clases y programación orientada a objetos). Debido a su carácter fundacional y acumulativo, no superarlos condicionará el aprobado final de la materia.",
            "• Evaluación en Entorno Empresarial: Entre el 10% y el 15% de los criterios se evalúan en colaboración con la empresa formadora."
        ]
    )

    # =========================================================================
    # SECCIÓN 2: DESGLOSE DETALLADO DE LOS 78 CRITERIOS DE EVALUACIÓN
    # =========================================================================
    doc.add_page_break()
    
    h2_sec = doc.add_paragraph()
    h2_sec.paragraph_format.space_before = Pt(12)
    h2_sec.paragraph_format.space_after = Pt(6)
    r_h2_sec = h2_sec.add_run("2. Desglose Normativo Detallado de los Criterios de Evaluación")
    r_h2_sec.font.name = "Calibri"
    r_h2_sec.font.size = Pt(15)
    r_h2_sec.font.bold = True
    r_h2_sec.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    p_desc_sec2 = doc.add_paragraph()
    p_desc_sec2.paragraph_format.space_after = Pt(10)
    p_desc_sec2.add_run(
        "A continuación se presenta el catálogo oficial íntegro de los 78 criterios de evaluación, organizados "
        "por Resultado de Aprendizaje. Para cada criterio se detalla su identificador normativo, su enunciado literal "
        "y su ámbito de evaluación preferente (Centro Educativo o Centro / Empresa colaboradora)."
    ).font.color.rgb = RGBColor(0x47, 0x55, 0x69)

    for ra_obj in data["ra_list"]:
        num = ra_obj["num"]
        
        # Tarjeta encabezada del RA
        ra_box = doc.add_table(rows=1, cols=1)
        ra_box.alignment = WD_TABLE_ALIGNMENT.CENTER
        ra_box.autofit = False
        cell_box = ra_box.cell(0, 0)
        cell_box.width = Inches(6.5)
        set_cell_background(cell_box, COLOR_PRIMARY_HEX)
        set_cell_margins(cell_box, top=90, bottom=90, left=130, right=130)
        
        p_ra = cell_box.paragraphs[0]
        p_ra.paragraph_format.space_before = Pt(2)
        p_ra.paragraph_format.space_after = Pt(2)
        r_ra_title = p_ra.add_run(f"RESULTADO DE APRENDIZAJE {num} (RA {num})\n")
        r_ra_title.font.name = "Calibri"
        r_ra_title.font.size = Pt(11)
        r_ra_title.font.bold = True
        r_ra_title.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)
        
        r_ra_desc = p_ra.add_run(ra_obj["description"])
        r_ra_desc.font.name = "Calibri"
        r_ra_desc.font.size = Pt(9.5)
        r_ra_desc.font.color.rgb = RGBColor(0xF0, 0xFD, 0xF4)

        doc.add_paragraph().paragraph_format.space_before = Pt(3)

        # Tabla de criterios de este RA
        n_crit = len(ra_obj["criteria"])
        ra_table = doc.add_table(rows=n_crit + 1, cols=3)
        ra_table.alignment = WD_TABLE_ALIGNMENT.CENTER
        ra_table.autofit = False
        set_table_borders(ra_table, color="D1D5DB", sz="4")
        
        t_widths = [Inches(1.0), Inches(4.3), Inches(1.2)]
        t_headers = ["Identificador", "Enunciado del Criterio de Evaluación", "Ámbito"]
        
        for c_i, h_txt in enumerate(t_headers):
            c_cell = ra_table.cell(0, c_i)
            c_cell.width = t_widths[c_i]
            set_cell_background(c_cell, "E2E8F0")
            set_cell_margins(c_cell, top=70, bottom=70, left=80, right=80)
            p = c_cell.paragraphs[0]
            if c_i in [0, 2]:
                p.alignment = WD_ALIGN_PARAGRAPH.CENTER
            r = p.add_run(h_txt)
            r.font.name = "Calibri"
            r.font.size = Pt(9)
            r.font.bold = True
            r.font.color.rgb = RGBColor(0x1E, 0x29, 0x3B)
            
        emp_letter = None
        if num in empresa_dict:
            emp_letter = empresa_dict[num].split(".")[1].replace(")", "").strip()

        for c_idx, crit in enumerate(ra_obj["criteria"]):
            row_idx = c_idx + 1
            is_empresa = (crit["letter"] == emp_letter)
            
            c0 = ra_table.cell(row_idx, 0)
            c0.width = t_widths[0]
            set_cell_margins(c0, top=65, bottom=65, left=80, right=80)
            c0.paragraphs[0].alignment = WD_ALIGN_PARAGRAPH.CENTER
            r0 = c0.paragraphs[0].add_run(f"RA {num}.{crit['letter']})")
            r0.font.name = "Calibri"
            r0.font.size = Pt(9)
            r0.font.bold = True
            r0.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33) if not is_empresa else RGBColor(0x0D, 0x94, 0x88)

            c1 = ra_table.cell(row_idx, 1)
            c1.width = t_widths[1]
            set_cell_margins(c1, top=65, bottom=65, left=80, right=80)
            r1 = c1.paragraphs[0].add_run(crit["text"])
            r1.font.name = "Calibri"
            r1.font.size = Pt(9)
            r1.font.color.rgb = RGBColor(0x1E, 0x29, 0x3B)

            c2 = ra_table.cell(row_idx, 2)
            c2.width = t_widths[2]
            set_cell_margins(c2, top=65, bottom=65, left=80, right=80)
            c2.paragraphs[0].alignment = WD_ALIGN_PARAGRAPH.CENTER
            
            if is_empresa:
                r2 = c2.paragraphs[0].add_run("Centro / Empresa ⭐")
                r2.font.bold = True
                r2.font.color.rgb = RGBColor(0x0D, 0x94, 0x88)
                set_cell_background(c0, "F0FDFA")
                set_cell_background(c1, "F0FDFA")
                set_cell_background(c2, "F0FDFA")
            else:
                r2 = c2.paragraphs[0].add_run("Centro Educativo")
                r2.font.color.rgb = RGBColor(0x64, 0x74, 0x8B)
                bg_col = COLOR_BG_ALT if (c_idx % 2 == 1) else "FFFFFF"
                set_cell_background(c0, bg_col)
                set_cell_background(c1, bg_col)
                set_cell_background(c2, bg_col)
            r2.font.name = "Calibri"
            r2.font.size = Pt(8.5)

        p_end = doc.add_paragraph()
        p_end.paragraph_format.space_before = Pt(4)
        p_end.paragraph_format.space_after = Pt(8)

    # =========================================================================
    # SECCIÓN 3: CONCRECIÓN POR UNIDADES DIDÁCTICAS (11 UNIDADES)
    # =========================================================================
    doc.add_page_break()
    
    h3_sec = doc.add_paragraph()
    h3_sec.paragraph_format.space_before = Pt(12)
    h3_sec.paragraph_format.space_after = Pt(6)
    r_h3_sec = h3_sec.add_run("3. Distribución y Concreción de Criterios por Unidades Didácticas")
    r_h3_sec.font.name = "Calibri"
    r_h3_sec.font.size = Pt(15)
    r_h3_sec.font.bold = True
    r_h3_sec.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    p_desc_sec3 = doc.add_paragraph()
    p_desc_sec3.paragraph_format.space_after = Pt(10)
    p_desc_sec3.add_run(
        "La Programación Didáctica organiza el aprendizaje en 11 unidades de trabajo que integran y combinan "
        "los diferentes criterios de evaluación. Dado que el desarrollo del software requiere reutilizar destrezas "
        "previas, los criterios se van consolidando de forma progresiva a lo largo de las distintas unidades."
    ).font.color.rgb = RGBColor(0x47, 0x55, 0x69)

    # Tabla resumen de las 11 unidades
    ud_summary_table = doc.add_table(rows=12, cols=5)
    ud_summary_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    ud_summary_table.autofit = False
    set_table_borders(ud_summary_table, color="D1D5DB", sz="4")
    
    ud_widths = [Inches(0.6), Inches(2.1), Inches(0.5), Inches(0.9), Inches(2.4)]
    ud_headers = ["Unidad", "Título de la Unidad Didáctica", "Horas", "RAs", "Criterios Asignados"]
    
    for c_i, h_txt in enumerate(ud_headers):
        cell = ud_summary_table.cell(0, c_i)
        cell.width = ud_widths[c_i]
        set_cell_background(cell, COLOR_PRIMARY_HEX)
        set_cell_margins(cell, top=80, bottom=80, left=70, right=70)
        p = cell.paragraphs[0]
        if c_i in [0, 2, 3]:
            p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        r = p.add_run(h_txt)
        r.font.name = "Calibri"
        r.font.size = Pt(8.5)
        r.font.bold = True
        r.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)

    for u_idx, u in enumerate(data["units"]):
        row_idx = u_idx + 1
        bg = COLOR_BG_ALT if (u_idx % 2 == 1) else "FFFFFF"
        
        clean_title = u["title"]
        if "." in clean_title:
            parts = clean_title.split(".", 1)
            clean_title = parts[1].strip()
            
        codes_list, _, _ = format_unit_ces(u["ces"])
        # Format codes neatly sorted
        unique_codes = sorted(list(set(c.rstrip(')') for c in codes_list)), key=lambda x: (int(x.split('.')[0]), x.split('.')[1]))
        codes_formatted = ", ".join(unique_codes)

        cells_val = [
            f"UD {u['unit_number']}",
            clean_title,
            f"{u['hours']} h",
            f"RA {u['ras']}",
            codes_formatted
        ]
        
        for c_i, val in enumerate(cells_val):
            cell = ud_summary_table.cell(row_idx, c_i)
            cell.width = ud_widths[c_i]
            set_cell_background(cell, bg)
            set_cell_margins(cell, top=60, bottom=60, left=70, right=70)
            p = cell.paragraphs[0]
            if c_i in [0, 2, 3]:
                p.alignment = WD_ALIGN_PARAGRAPH.CENTER
            r = p.add_run(val)
            r.font.name = "Calibri"
            r.font.size = Pt(8)
            if c_i in [0, 2]:
                r.font.bold = True
                r.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)
            elif c_i == 4:
                r.font.bold = False
                r.font.color.rgb = RGBColor(0x33, 0x41, 0x55)

    doc.add_paragraph().paragraph_format.space_after = Pt(10)

    # Detalle por Unidad Didáctica
    h3_sub = doc.add_paragraph()
    h3_sub.paragraph_format.space_before = Pt(8)
    h3_sub.paragraph_format.space_after = Pt(6)
    r_h3_sub = h3_sub.add_run("Desglose Específico de Criterios por Cada Unidad Didáctica")
    r_h3_sub.font.name = "Calibri"
    r_h3_sub.font.size = Pt(13)
    r_h3_sub.font.bold = True
    r_h3_sub.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    for u in data["units"]:
        codes, codes_part, sentences = format_unit_ces(u["ces"])
        
        u_card = doc.add_table(rows=2, cols=1)
        u_card.alignment = WD_TABLE_ALIGNMENT.CENTER
        u_card.autofit = False
        
        header_cell = u_card.cell(0, 0)
        header_cell.width = Inches(6.5)
        set_cell_background(header_cell, COLOR_SECONDARY_HEX)
        set_cell_margins(header_cell, top=70, bottom=70, left=110, right=110)
        
        p_hdr = header_cell.paragraphs[0]
        r_hdr_num = p_hdr.add_run(f"UD {u['unit_number']}: {u['title']}  |  {u['hours']} Horas  |  RAs implicados: {u['ras']}")
        r_hdr_num.font.name = "Calibri"
        r_hdr_num.font.size = Pt(9.5)
        r_hdr_num.font.bold = True
        r_hdr_num.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)
        
        body_cell = u_card.cell(1, 0)
        body_cell.width = Inches(6.5)
        set_cell_background(body_cell, COLOR_BG_LIGHT)
        set_cell_margins(body_cell, top=80, bottom=80, left=120, right=120)
        
        tcPr = body_cell._tc.get_or_add_tcPr()
        borders = parse_xml(
            f'<w:tcBorders {nsdecls("w")}>'
            f'  <w:top w:val="none"/>'
            f'  <w:bottom w:val="single" w:sz="4" w:space="0" w:color="CBD5E1"/>'
            f'  <w:left w:val="single" w:sz="20" w:space="0" w:color="{COLOR_PRIMARY_HEX}"/>'
            f'  <w:right w:val="single" w:sz="4" w:space="0" w:color="CBD5E1"/>'
            f'</w:tcBorders>'
        )
        tcPr.append(borders)
        
        p_b = body_cell.paragraphs[0]
        p_b.paragraph_format.space_before = Pt(2)
        p_b.paragraph_format.space_after = Pt(3)
        
        r_lbl = p_b.add_run(f"Códigos evaluados: {', '.join(codes) if codes else u['ras']}\n")
        r_lbl.font.name = "Calibri"
        r_lbl.font.size = Pt(9)
        r_lbl.font.bold = True
        r_lbl.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)
        
        for s in sentences:
            p_s = body_cell.add_paragraph()
            p_s.paragraph_format.space_before = Pt(1)
            p_s.paragraph_format.space_after = Pt(2)
            p_s.paragraph_format.left_indent = Inches(0.15)
            r_bullet = p_s.add_run("• ")
            r_bullet.font.bold = True
            r_bullet.font.color.rgb = RGBColor(0x2D, 0x8A, 0x4E)
            
            r_txt = p_s.add_run(s)
            r_txt.font.name = "Calibri"
            r_txt.font.size = Pt(8.5)
            r_txt.font.color.rgb = RGBColor(0x1E, 0x29, 0x3B)
        
        p_sep = doc.add_paragraph()
        p_sep.paragraph_format.space_after = Pt(6)

    # =========================================================================
    # SECCIÓN 4: CRITERIOS EVALUADOS EN LA EMPRESA
    # =========================================================================
    doc.add_page_break()
    
    h4_sec = doc.add_paragraph()
    h4_sec.paragraph_format.space_before = Pt(12)
    h4_sec.paragraph_format.space_after = Pt(6)
    r_h4_sec = h4_sec.add_run("4. Criterios de Evaluación Aplicados en la Empresa (10% - 15%)")
    r_h4_sec.font.name = "Calibri"
    r_h4_sec.font.size = Pt(15)
    r_h4_sec.font.bold = True
    r_h4_sec.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    p_desc_sec4 = doc.add_paragraph()
    p_desc_sec4.paragraph_format.space_after = Pt(10)
    p_desc_sec4.add_run(
        "De todos los criterios de evaluación asociados a los resultados de aprendizaje, aproximadamente "
        "entre el 10% y el 15% se aplican y valoran durante la realización de las prácticas en la empresa "
        "(Formación en Centros de Trabajo / Formación Profesional Dual). La siguiente tabla recoge los criterios "
        "específicos asignados a cada resultado de aprendizaje que son evaluados por la empresa formadora:"
    ).font.color.rgb = RGBColor(0x47, 0x55, 0x69)

    empresa_full_table = doc.add_table(rows=10, cols=3)
    empresa_full_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    empresa_full_table.autofit = False
    set_table_borders(empresa_full_table, color="D1D5DB", sz="4")
    
    emp_widths = [Inches(1.2), Inches(2.9), Inches(2.4)]
    emp_headers = ["Resultado de Aprendizaje", "Descripción del Resultado", "Criterio de Evaluación Evaluado por la Empresa"]
    
    for c_i, h_txt in enumerate(emp_headers):
        cell = empresa_full_table.cell(0, c_i)
        cell.width = emp_widths[c_i]
        set_cell_background(cell, COLOR_PRIMARY_HEX)
        set_cell_margins(cell, top=80, bottom=80, left=80, right=80)
        p = cell.paragraphs[0]
        if c_i == 0:
            p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        r = p.add_run(h_txt)
        r.font.name = "Calibri"
        r.font.size = Pt(9)
        r.font.bold = True
        r.font.color.rgb = RGBColor(0xFF, 0xFF, 0xFF)

    for e_idx, item in enumerate(data["empresa_criteria"]):
        row_idx = e_idx + 1
        bg = COLOR_BG_ALT if (e_idx % 2 == 1) else "FFFFFF"
        
        ra_full = item["ra"]
        parts = ra_full.split(". ")
        ra_code = parts[0]
        ra_desc = parts[1] if len(parts) > 1 else ""
        
        c0 = empresa_full_table.cell(row_idx, 0)
        c0.width = emp_widths[0]
        set_cell_background(c0, bg)
        set_cell_margins(c0, top=65, bottom=65, left=80, right=80)
        c0.paragraphs[0].alignment = WD_ALIGN_PARAGRAPH.CENTER
        r0 = c0.paragraphs[0].add_run(ra_code)
        r0.font.name = "Calibri"
        r0.font.size = Pt(9)
        r0.font.bold = True
        r0.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

        c1 = empresa_full_table.cell(row_idx, 1)
        c1.width = emp_widths[1]
        set_cell_background(c1, bg)
        set_cell_margins(c1, top=65, bottom=65, left=80, right=80)
        r1 = c1.paragraphs[0].add_run(ra_desc)
        r1.font.name = "Calibri"
        r1.font.size = Pt(8.5)
        r1.font.color.rgb = RGBColor(0x33, 0x41, 0x55)

        c2 = empresa_full_table.cell(row_idx, 2)
        c2.width = emp_widths[2]
        set_cell_background(c2, "F0FDFA")
        set_cell_margins(c2, top=65, bottom=65, left=80, right=80)
        r2 = c2.paragraphs[0].add_run(item["criterio"])
        r2.font.name = "Calibri"
        r2.font.size = Pt(8.5)
        r2.font.bold = True
        r2.font.color.rgb = RGBColor(0x0D, 0x94, 0x88)

    doc.add_paragraph().paragraph_format.space_after = Pt(10)

    # =========================================================================
    # SECCIÓN 5: CRITERIOS DE CALIFICACIÓN Y EVALUACIÓN
    # =========================================================================
    h5_sec = doc.add_paragraph()
    h5_sec.paragraph_format.space_before = Pt(12)
    h5_sec.paragraph_format.space_after = Pt(6)
    r_h5_sec = h5_sec.add_run("5. Criterios de Calificación y Procedimientos de Evaluación")
    r_h5_sec.font.name = "Calibri"
    r_h5_sec.font.size = Pt(15)
    r_h5_sec.font.bold = True
    r_h5_sec.font.color.rgb = RGBColor(0x1B, 0x5E, 0x33)

    make_callout(
        doc,
        "Instrumentos y Ponderación en la Evaluación Continua",
        [
            "• Estructura Temporal: El curso se divide en 3 evaluaciones trimestrales.",
            "• Ponderación 1ª Evaluación: 100% Examen teórico/práctico de la evaluación.",
            "• Ponderación 2ª Evaluación: 100% Examen teórico/práctico de la evaluación.",
            "• Ponderación 3ª Evaluación: 50% Examen de la evaluación + 50% Trabajo / Proyecto Final.",
            "• Condición de Promedio: Para promediar las evaluaciones trimestrales se exige una nota mínima de 4.5 en cada una de ellas.",
            "• Instrumentos de Apoyo: Proyectos prácticos con rúbricas detalladas, cuestionarios tipo test teórico-prácticos y problemas de autoevaluación continua."
        ]
    )

    make_callout(
        doc,
        "Criterios de Calificación en Convocatorias Ordinaria, Extraordinaria y Recuperación",
        [
            "• Superación del Módulo: Nota final ponderada igual o superior a 5.0 sobre 10.",
            "• Convocatoria Ordinaria: Los alumnos que hayan seguido la evaluación continua y mantengan RAs no alcanzados, se examinarán exclusivamente de los Resultados de Aprendizaje pendientes. Alumnos sin evaluación continua se examinarán de la totalidad de RAs.",
            "• Convocatoria Extraordinaria: Se preserva la liberación de RAs superados. El alumnado recuperará únicamente los RAs no alcanzados en la continua ni en la ordinaria.",
            "• RAs Esenciales e Ineludibles: Sentencias de control y estructuras (RA 3), fundamentos de objetos (RA 2) y clases/POO (RA 4). Al condensar las competencias indispensables sobre las que descansan las demás unidades, su no consecución impedirá el aprobado del módulo.",
            "• Proyecto Intermodular: Tal como señala la programación, el RA 2 se refuerza y evalúa transversalmente en el Proyecto Intermodular del ciclo."
        ]
    )

    doc.save(output_path)
    print(f"Documento generado exitosamente en: {output_path}")

if __name__ == "__main__":
    src = "Docs/2026-27_DAMS_0485_PRO.docx"
    out = "Docs/Criterios_de_Evaluacion_0485_PRO.docx"
    create_criterios_document(src, out)
    alt_out = "Docs/Criterios_Evaluacion_0485_Programacion.docx"
    try:
        create_criterios_document(src, alt_out)
    except Exception as e:
        print(f"Nota: {alt_out} está actualmente abierto en Word.")


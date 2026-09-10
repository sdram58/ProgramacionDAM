import os
import sys
import subprocess
import fitz  # PyMuPDF
from bs4 import BeautifulSoup

EDGE_PATH = r'C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe'
if not os.path.exists(EDGE_PATH):
    EDGE_PATH = r'C:\Program Files\Microsoft\Edge\Application\msedge.exe'

PORTADA_PATH = os.path.abspath('public/portada.png')

PRINT_CSS = """
@page {
  size: A4 portrait;
  margin: 16mm 14mm 16mm 14mm;
}

*, *::before, *::after {
  box-sizing: border-box;
  -webkit-print-color-adjust: exact !important;
  print-color-adjust: exact !important;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  font-size: 10pt;
  line-height: 1.55;
  color: #1e293b;
  background: #ffffff;
  margin: 0;
  padding: 0;
}

/* Headings */
h1, h2, h3, h4, h5, h6 {
  color: #0f172a;
  font-weight: 700;
  page-break-after: avoid;
  break-after: avoid;
}

h1 {
  font-size: 20pt;
  margin-top: 0;
  margin-bottom: 12pt;
  color: #166534;
  border-bottom: 2px solid #22c55e;
  padding-bottom: 6pt;
}

h2 {
  font-size: 13.5pt;
  margin-top: 18pt;
  margin-bottom: 8pt;
  color: #1e3a2b;
  border-bottom: 1px solid #e2e8f0;
  padding-bottom: 4pt;
}

h3 {
  font-size: 11pt;
  margin-top: 14pt;
  margin-bottom: 6pt;
  color: #15803d;
}

h4 {
  font-size: 10pt;
  margin-top: 10pt;
  margin-bottom: 4pt;
  color: #334155;
}

p, ul, ol {
  margin-top: 0;
  margin-bottom: 8pt;
}

li {
  margin-bottom: 3pt;
}

/* Cover page for full unit */
.unit-cover {
  page-break-after: always;
  break-after: page;
  height: 250mm;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10mm 0;
}

.cover-header {
  border-bottom: 3px solid #16a34a;
  padding-bottom: 15pt;
}

.cover-badge {
  display: inline-block;
  background: #dcfce7;
  color: #15803d;
  font-weight: 700;
  font-size: 9pt;
  padding: 4pt 10pt;
  border-radius: 9999px;
  border: 1px solid #86efac;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  margin-bottom: 12pt;
}

.cover-title {
  font-size: 24pt;
  line-height: 1.15;
  color: #0f172a;
  margin: 0 0 10pt 0;
  font-weight: 800;
}

.cover-subtitle {
  font-size: 12pt;
  color: #475569;
  line-height: 1.4;
  margin: 0;
}

.cover-image-box {
  text-align: center;
  margin: 20pt 0;
}

.cover-image-box img {
  max-width: 90%;
  max-height: 105mm;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
  border: 1px solid #cbd5e1;
}

.cover-meta-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12pt;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  padding: 14pt;
}

.cover-meta-item strong {
  color: #166534;
  display: block;
  font-size: 8.5pt;
  text-transform: uppercase;
  margin-bottom: 2pt;
}

.cover-meta-item span {
  font-size: 9.5pt;
  color: #1e293b;
  font-weight: 500;
}

/* Bulletin Header Banner */
.bulletin-header {
  background: linear-gradient(135deg, #15803d 0%, #166534 100%);
  color: #ffffff;
  padding: 14pt 18pt;
  border-radius: 8px;
  margin-bottom: 16pt;
  page-break-after: avoid;
  break-after: avoid;
}

.bulletin-badge {
  display: inline-block;
  background: rgba(255,255,255,0.2);
  color: #ffffff;
  font-size: 8pt;
  font-weight: 600;
  padding: 3pt 8pt;
  border-radius: 4px;
  margin-bottom: 6pt;
}

.bulletin-header h1 {
  color: #ffffff !important;
  border: none !important;
  font-size: 17pt !important;
  margin: 0 0 4pt 0 !important;
  padding: 0 !important;
}

.bulletin-header p {
  color: #e2e8f0;
  font-size: 9.5pt;
  margin: 0;
  line-height: 1.4;
}

/* Code blocks */
pre {
  background-color: #0f172a !important;
  color: #e2e8f0 !important;
  font-family: "Cascadia Code", "Fira Code", Consolas, monospace !important;
  font-size: 8.5pt !important;
  line-height: 1.42 !important;
  padding: 8pt 10pt !important;
  border-radius: 6px !important;
  border: 1px solid #334155 !important;
  page-break-inside: avoid !important;
  break-inside: avoid !important;
  margin: 6pt 0 10pt 0 !important;
  overflow-x: hidden !important;
  white-space: pre-wrap !important;
  word-break: break-word !important;
}

code {
  font-family: "Cascadia Code", "Fira Code", Consolas, monospace !important;
  font-size: 8.5pt;
  background-color: #f1f5f9;
  color: #0f766e;
  padding: 1pt 3pt;
  border-radius: 3px;
  border: 1px solid #e2e8f0;
}

pre code {
  background-color: transparent !important;
  color: inherit !important;
  padding: 0 !important;
  border: none !important;
  font-size: 8.5pt !important;
}

.expressive-code {
  margin: 6pt 0 10pt 0 !important;
  page-break-inside: avoid !important;
  break-inside: avoid !important;
}

.expressive-code pre {
  margin: 0 !important;
}

/* Tables */
table {
  width: 100%;
  border-collapse: collapse;
  margin: 8pt 0 12pt 0;
  font-size: 8.5pt;
  page-break-inside: avoid;
  break-inside: avoid;
}

th, td {
  border: 1px solid #cbd5e1;
  padding: 5pt 7pt;
  text-align: left;
  vertical-align: top;
}

th {
  background-color: #f1f5f9;
  color: #0f172a;
  font-weight: 700;
  border-bottom: 2px solid #94a3b8;
}

tr:nth-child(even) {
  background-color: #f8fafc;
}

/* Callouts */
.starlight-aside {
  border-left: 4px solid #3b82f6;
  background-color: #f8fafc;
  padding: 8pt 10pt;
  margin: 8pt 0 12pt 0;
  border-radius: 0 6px 6px 0;
  page-break-inside: avoid;
  break-inside: avoid;
}

.starlight-aside--note {
  border-left-color: #0284c7;
  background-color: #f0f9ff;
}

.starlight-aside--tip {
  border-left-color: #16a34a;
  background-color: #f0fdf4;
}

.starlight-aside--caution, .starlight-aside--warning {
  border-left-color: #d97706;
  background-color: #fffbeb;
}

.starlight-aside__title {
  font-weight: 700;
  font-size: 9pt;
  color: #0f172a;
  margin-bottom: 3pt;
}

.starlight-aside__title svg {
  display: none;
}

.starlight-aside p {
  margin-bottom: 3pt;
}

.starlight-aside p:last-child {
  margin-bottom: 0;
}

.copy, button, .pagination-links, .sl-anchor-link, svg.caret {
  display: none !important;
}

img {
  max-width: 100%;
  height: auto;
  page-break-inside: avoid;
  break-inside: avoid;
}

blockquote {
  border-left: 3px solid #94a3b8;
  margin: 6pt 0 8pt 0;
  padding-left: 8pt;
  color: #475569;
  font-style: italic;
}
"""

def extract_content(html_path):
    with open(html_path, 'r', encoding='utf-8') as f:
        soup = BeautifulSoup(f.read(), 'html.parser')
    
    h1 = soup.find('h1')
    title_text = h1.get_text().strip() if h1 else 'Documento'
    
    desc_tag = soup.find('meta', attrs={'name': 'description'})
    desc_text = desc_tag['content'] if desc_tag and desc_tag.get('content') else ''
    
    content_div = soup.find('div', class_='sl-markdown-content')
    if not content_div:
        main = soup.find('main')
        content_div = main if main else soup.body
        
    for bad in content_div.find_all(['button', 'nav', 'sl-sidebar-restore']):
        bad.decompose()
    for bad in content_div.find_all(class_=['pagination-links', 'copy', 'sl-anchor-link']):
        bad.decompose()
        
    return title_text, desc_text, str(content_div)

def build_html_document(title, desc, content_html, is_unit=False):
    portada_url = 'file:///' + PORTADA_PATH.replace('\\', '/')
    
    if is_unit:
        header_html = f"""
        <div class="unit-cover">
          <div class="cover-header">
            <span class="cover-badge">CFGS Desarrollo de Aplicaciones Multiplataforma</span>
            <h1 class="cover-title">{title}</h1>
            <p class="cover-subtitle">{desc}</p>
          </div>
          <div class="cover-image-box">
            <img src="{portada_url}" alt="Portada" />
          </div>
          <div class="cover-meta-grid">
            <div class="cover-meta-item">
              <strong>Módulo Profesional</strong>
              <span>Programación (0485) — 1º DAM</span>
            </div>
            <div class="cover-meta-item">
              <strong>Resultados de Aprendizaje</strong>
              <span>RA1 (Fundamentos del Software y Algorítmica)</span>
            </div>
            <div class="cover-meta-item">
              <strong>Duración Estimada</strong>
              <span>8 horas lectivas</span>
            </div>
            <div class="cover-meta-item">
              <strong>Entorno Oficial</strong>
              <span>IntelliJ IDEA / PSeInt / Java LTS</span>
            </div>
          </div>
        </div>
        """
    else:
        header_html = f"""
        <div class="bulletin-header">
          <span class="bulletin-badge">DAM 0485 · Programación · Unidad Didáctica 1</span>
          <h1>{title}</h1>
          <p>{desc}</p>
        </div>
        """

    full_html = f"""<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>{title}</title>
  <style>
    {PRINT_CSS}
  </style>
</head>
<body>
  {header_html}
  <div class="document-body">
    {content_html}
  </div>
</body>
</html>
"""
    return full_html

def convert_html_to_pdf(html_str, out_pdf_path):
    temp_html = out_pdf_path.replace('.pdf', '_temp.html')
    with open(temp_html, 'w', encoding='utf-8') as f:
        f.write(html_str)
        
    temp_url = 'file:///' + os.path.abspath(temp_html).replace('\\', '/')
    cmd = [
        EDGE_PATH,
        '--headless=new',
        '--disable-gpu',
        '--no-pdf-header-footer',
        f'--print-to-pdf={os.path.abspath(out_pdf_path)}',
        temp_url
    ]
    res = subprocess.run(cmd, capture_output=True, text=True)
    if os.path.exists(temp_html):
        os.remove(temp_html)
        
    if res.returncode != 0 or not os.path.exists(out_pdf_path):
        raise RuntimeError(f"Failed to generate PDF for {out_pdf_path}: {res.stderr}")

def add_headers_and_footers(pdf_path, doc_title, is_unit=False):
    doc = fitz.open(pdf_path)
    total_pages = len(doc)
    
    start_page = 1 if is_unit else 0
    
    for i in range(start_page, total_pages):
        page = doc[i]
        rect = page.rect
        page_num = i + 1
        
        if i > 0:
            header_text = 'Programación (DAM 0485) · Unidad 1'
            page.insert_text(
                (rect.width - 240, 28),
                header_text,
                fontsize=8,
                color=(0.3, 0.4, 0.35)
            )
            page.draw_line(
                (36, 34),
                (rect.width - 36, 34),
                color=(0.8, 0.85, 0.8),
                width=0.6
            )
            
        footer_left = doc_title[:45] + ('...' if len(doc_title) > 45 else '')
        page.insert_text(
            (36, rect.height - 22),
            footer_left,
            fontsize=8,
            color=(0.4, 0.45, 0.45)
        )
        
        page_str = f"Página {page_num} de {total_pages}"
        page.insert_text(
            (rect.width - 110, rect.height - 22),
            page_str,
            fontsize=8,
            color=(0.4, 0.45, 0.45)
        )
        
        page.draw_line(
            (36, rect.height - 30),
            (rect.width - 36, rect.height - 30),
            color=(0.85, 0.88, 0.85),
            width=0.6
        )
        
    temp_out = pdf_path + ".tmp.pdf"
    doc.save(temp_out)
    doc.close()
    if os.path.exists(pdf_path):
        os.remove(pdf_path)
    os.rename(temp_out, pdf_path)

def main():
    items = [
        {
            'html': 'dist/ud01/index.html',
            'pdf': 'public/pdf/ud01/UD01-Teoria-Introduccion-Programacion.pdf',
            'is_unit': True,
            'title_fallback': 'Unidad 1. Introducción a la Programación',
            'desc_fallback': 'Fundamentos del pensamiento computacional, arquitectura y ciclo de vida del software, diagramas de flujo ANSI, trazas de algoritmos y pseudocódigo estructurado con PSeInt.'
        },
        {
            'html': 'dist/ud01/boletin-01-inicial/index.html',
            'pdf': 'public/pdf/ud01/Boletin-01-Inicial.pdf',
            'is_unit': False,
            'title_fallback': 'Boletín 01 Inicial — Algorítmica y Pseudocódigo',
            'desc_fallback': '10 ejercicios prácticos guiados para afianzar variables, expresiones, condicionales simples y bucles.'
        },
        {
            'html': 'dist/ud01/boletin-01-inicial-resuelto/index.html',
            'pdf': 'public/pdf/ud01/Boletin-01-Inicial-Resuelto.pdf',
            'is_unit': False,
            'title_fallback': 'Boletín 01 Inicial (Resuelto) — Algorítmica y Pseudocódigo',
            'desc_fallback': 'Soluciones completas, explicadas paso a paso con pseudocódigo y tablas de traza de memoria.'
        },
        {
            'html': 'dist/ud01/boletin-01-intermedio/index.html',
            'pdf': 'public/pdf/ud01/Boletin-01-Intermedio.pdf',
            'is_unit': False,
            'title_fallback': 'Boletín 01 Intermedio — Algorítmica y Pseudocódigo',
            'desc_fallback': '10 ejercicios de nivel intermedio: anidamiento condicional, acumuladores, bucles complejos y diseño modular.'
        },
        {
            'html': 'dist/ud01/boletin-01-intermedio-resuelto/index.html',
            'pdf': 'public/pdf/ud01/Boletin-01-Intermedio-Resuelto.pdf',
            'is_unit': False,
            'title_fallback': 'Boletín 01 Intermedio (Resuelto) — Algorítmica y Pseudocódigo',
            'desc_fallback': 'Soluciones técnicas detalladas con modularización, pseudocódigo canónico y buenas prácticas.'
        },
        {
            'html': 'dist/ud01/boletin-01-extras/index.html',
            'pdf': 'public/pdf/ud01/Boletin-01-Extras-Retos.pdf',
            'is_unit': False,
            'title_fallback': 'Boletín 01 Extras — Retos de Pensamiento Computacional',
            'desc_fallback': 'Retos algorítmicos avanzados, optimización y preparación para programación competitiva.'
        }
    ]
    
    os.makedirs('public/pdf/ud01', exist_ok=True)
    
    for it in items:
        html_src = it['html']
        pdf_dst = it['pdf']
        print(f"Processing: {html_src} -> {pdf_dst}")
        
        if not os.path.exists(html_src):
            print(f"Warning: {html_src} not found! Skipping.")
            continue
            
        title, desc, content = extract_content(html_src)
        if not desc:
            desc = it['desc_fallback']
            
        full_doc = build_html_document(title, desc, content, is_unit=it['is_unit'])
        convert_html_to_pdf(full_doc, pdf_dst)
        add_headers_and_footers(pdf_dst, title, is_unit=it['is_unit'])
        
        sz = os.path.getsize(pdf_dst)
        doc = fitz.open(pdf_dst)
        print(f" -> OK! Generated {pdf_dst} ({len(doc)} pages, {sz/1024:.1f} KB)")
        doc.close()

    # Also build combined PDF ProgramacionDAM-es.pdf
    combined_pdf = 'public/pdf/ProgramacionDAM-es.pdf'
    merged_doc = fitz.open()
    for it in items:
        pdf_dst = it['pdf']
        if os.path.exists(pdf_dst):
            sub_doc = fitz.open(pdf_dst)
            merged_doc.insert_pdf(sub_doc)
            sub_doc.close()
    merged_doc.save(combined_pdf)
    print(f"Combined PDF generated: {combined_pdf} ({len(merged_doc)} pages, {os.path.getsize(combined_pdf)/1024:.1f} KB)")
    merged_doc.close()
    print("All PDFs generated successfully!")

if __name__ == '__main__':
    main()

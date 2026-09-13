import os
import sys
import subprocess
import pymupdf as fitz

# Paths
BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
PDF_SRC = os.path.join(BASE_DIR, 'Docs2', 'UD01 Fundamentos Programación', 'Ejercicios_PSeInt.pdf')
PDF_DEST = os.path.join(BASE_DIR, 'public', 'pdf', 'ud01', 'Ejercicios_PSeInt.pdf')
PORTADA_IMG = os.path.join(BASE_DIR, 'portada.png')

EDGE_PATH = r'C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe'
if not os.path.exists(EDGE_PATH):
    EDGE_PATH = r'C:\Program Files\Microsoft\Edge\Application\msedge.exe'

def generate_cover_html():
    img_uri = 'file:///' + PORTADA_IMG.replace('\\', '/')
    
    html = f"""<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Pseudocódigo y PSEINT</title>
  <style>
    @page {{
      size: 612pt 792pt;
      margin: 0;
    }}
    *, *::before, *::after {{
      box-sizing: border-box;
      -webkit-print-color-adjust: exact !important;
      print-color-adjust: exact !important;
    }}
    html, body {{
      margin: 0;
      padding: 0;
      width: 612pt;
      height: 792pt;
      overflow: hidden;
      font-family: 'Geist Sans', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
      background-color: #ffffff;
      color: #0f172a;
    }}
    .page-container {{
      width: 612pt;
      height: 792pt;
      padding: 36pt 42pt 30pt 42pt;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
    }}
    .top-bar {{
      height: 5pt;
      width: 100%;
      background: linear-gradient(90deg, #15803d 0%, #2d8a4e 50%, #4ade80 100%);
      border-radius: 3pt;
      margin-bottom: 22pt;
    }}
    .header {{
      margin-bottom: 12pt;
    }}
    .badge {{
      display: inline-block;
      background: #dcfce7;
      color: #166534;
      font-size: 8.5pt;
      font-weight: 700;
      padding: 4pt 12pt;
      border-radius: 9999px;
      border: 1px solid #86efac;
      letter-spacing: 0.6px;
      text-transform: uppercase;
      margin-bottom: 12pt;
    }}
    .title {{
      font-size: 27pt;
      font-weight: 800;
      color: #14532d;
      margin: 0 0 8pt 0;
      line-height: 1.15;
      letter-spacing: -0.5px;
    }}
    .subtitle {{
      font-size: 11pt;
      color: #475569;
      margin: 0;
      line-height: 1.45;
      max-width: 500pt;
    }}
    .hero-container {{
      text-align: center;
      margin: 14pt 0;
    }}
    .hero-container img {{
      width: 100%;
      max-height: 235pt;
      object-fit: cover;
      border-radius: 10pt;
      box-shadow: 0 10pt 25pt -5pt rgba(0, 0, 0, 0.14), 0 8pt 10pt -6pt rgba(0, 0, 0, 0.08);
      border: 1px solid #cbd5e1;
    }}
    .meta-grid {{
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 10pt;
      margin-top: 8pt;
    }}
    .meta-card {{
      background: #f8fafc;
      border: 1px solid #e2e8f0;
      border-left: 3.5pt solid #2d8a4e;
      border-radius: 6pt;
      padding: 9pt 12pt;
    }}
    .meta-card strong {{
      display: block;
      font-size: 7.5pt;
      color: #166534;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      margin-bottom: 2pt;
      font-weight: 700;
    }}
    .meta-card span {{
      font-size: 9.5pt;
      font-weight: 600;
      color: #1e293b;
    }}
    .footer {{
      border-top: 1px solid #e2e8f0;
      padding-top: 10pt;
      display: flex;
      justify-content: space-between;
      font-size: 8pt;
      color: #64748b;
      font-weight: 500;
    }}
  </style>
</head>
<body>
  <div class="page-container">
    <div>
      <div class="top-bar"></div>
      <div class="header">
        <span class="badge">CFGS DESARROLLO DE APLICACIONES MULTIPLATAFORMA · DAM</span>
        <h1 class="title">Pseudocódigo y PSEINT</h1>
        <p class="subtitle">Manual Práctico de Fundamentos de Algorítmica, Estructuras de Control y Resolución de Problemas con PSeInt</p>
      </div>
      <div class="hero-container">
        <img src="{img_uri}" alt="Portada Pseudocódigo y PSEINT" />
      </div>
      <div class="meta-grid">
        <div class="meta-card">
          <strong>Módulo Profesional</strong>
          <span>Programación (0485) — 1º DAM</span>
        </div>
        <div class="meta-card">
          <strong>Unidad Temática</strong>
          <span>UD01 · Algorítmica y Diagramas</span>
        </div>
        <div class="meta-card">
          <strong>Resultados de Aprendizaje</strong>
          <span>RA1 (Diseño Lógico y Pseudocódigo)</span>
        </div>
        <div class="meta-card">
          <strong>Entorno de Prácticas</strong>
          <span>PSeInt / Diagramas ANSI / Java LTS</span>
        </div>
      </div>
    </div>
    <div class="footer">
      <span>Ciclo Formativo DAM · Curso de Programación</span>
      <span>Material Didáctico y de Prácticas</span>
    </div>
  </div>
</body>
</html>
"""
    return html

def main():
    print(f"Source PDF: {PDF_SRC}")
    print(f"Destination PDF: {PDF_DEST}")
    if not os.path.exists(PDF_SRC):
        print(f"Error: {PDF_SRC} does not exist!")
        sys.exit(1)

    os.makedirs(os.path.dirname(PDF_DEST), exist_ok=True)

    # 1. Open original PDF
    orig_doc = fitz.open(PDF_SRC)
    orig_len = len(orig_doc)
    print(f"Original PDF opened: {orig_len} pages. First page rect: {orig_doc[0].rect}")

    # 2. Generate cover HTML
    cover_html_path = os.path.join(BASE_DIR, 'scripts', 'temp_cover.html')
    cover_pdf_path = os.path.join(BASE_DIR, 'scripts', 'temp_cover.pdf')

    html_content = generate_cover_html()
    with open(cover_html_path, 'w', encoding='utf-8') as f:
        f.write(html_content)

    # 3. Print HTML to PDF using Edge
    cover_uri = 'file:///' + cover_html_path.replace('\\', '/')
    cmd = [
        EDGE_PATH,
        '--headless=new',
        '--disable-gpu',
        '--no-pdf-header-footer',
        f'--print-to-pdf={cover_pdf_path}',
        cover_uri
    ]
    print(f"Rendering cover using Edge...")
    res = subprocess.run(cmd, capture_output=True, text=True)
    if res.returncode != 0 or not os.path.exists(cover_pdf_path):
        print(f"Error executing Edge: {res.stderr}")
        sys.exit(1)

    # 4. Verify cover PDF
    cover_doc = fitz.open(cover_pdf_path)
    print(f"Cover PDF generated: {len(cover_doc)} page(s), rect: {cover_doc[0].rect}")
    if len(cover_doc) != 1:
        print(f"Warning: Cover has {len(cover_doc)} pages! Trimming to 1.")

    # 5. Assemble final PDF: Cover (page 0) + original pages (1 to end)
    new_doc = fitz.open()
    # Insert new cover
    new_doc.insert_pdf(cover_doc, from_page=0, to_page=0)
    # Insert original pages from index 1 to end (dropping page 0)
    new_doc.insert_pdf(orig_doc, from_page=1, to_page=orig_len - 1)

    print(f"New PDF page count: {len(new_doc)} (expected {orig_len})")

    # 6. Save new PDF to destination
    temp_target = PDF_DEST + ".new.pdf"
    new_doc.save(temp_target, garbage=4, deflate=True)

    # Close documents
    orig_doc.close()
    cover_doc.close()
    new_doc.close()

    # Replace destination file
    if os.path.exists(PDF_DEST):
        os.remove(PDF_DEST)
    os.rename(temp_target, PDF_DEST)
    print(f"Successfully saved {PDF_DEST}!")

    # Clean up temp files
    if os.path.exists(cover_html_path):
        os.remove(cover_html_path)
    if os.path.exists(cover_pdf_path):
        os.remove(cover_pdf_path)

    print("Process completed successfully!")

if __name__ == '__main__':
    main()

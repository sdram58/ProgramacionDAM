import os
import subprocess
import fitz  # PyMuPDF
from PIL import Image
import numpy as np

html_file = os.path.abspath('public/presentacion.html')
pdf_file = os.path.abspath('public/presentacion.pdf')
edge_path = r'C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe'

# 1. Read existing HTML
with open(html_file, 'r', encoding='utf-8') as f:
    content = f.read()

# 2. Refined print CSS for clear light theme and landscape slides
light_print_css = """    @page {
      size: 297mm 210mm;
      margin: 0;
    }

    @media print {
      :root {
        --bg-dark: #ffffff !important;
        --bg-card: #f8fafc !important;
        --bg-card-hover: #f1f5f9 !important;
        --border-color: #cbd5e1 !important;
        --border-accent: #15803d !important;
        --text-main: #0f172a !important;
        --text-muted: #334155 !important;
        --text-dim: #64748b !important;
        --primary: #15803d !important;
        --primary-light: #166534 !important;
        --primary-dark: #14532d !important;
      }

      * {
        -webkit-print-color-adjust: exact !important;
        print-color-adjust: exact !important;
      }

      html, body {
        background-color: #ffffff !important;
        color: #0f172a !important;
        overflow: visible !important;
        height: auto !important;
        margin: 0 !important;
        padding: 0 !important;
      }

      #nav-bar, #progress-container, #menu-modal {
        display: none !important;
      }

      #slider {
        position: static !important;
        width: 100% !important;
        height: auto !important;
      }

      .slide {
        position: relative !important;
        opacity: 1 !important;
        visibility: visible !important;
        transform: none !important;
        page-break-after: always !important;
        break-after: page !important;
        height: 210mm !important;
        max-height: 210mm !important;
        width: 297mm !important;
        max-width: 297mm !important;
        box-sizing: border-box !important;
        padding: 14mm 20mm !important;
        display: flex !important;
        flex-direction: column !important;
        justify-content: center !important;
        align-items: center !important;
        overflow: hidden !important;
        background: #ffffff !important;
      }

      .slide-content {
        max-width: 100% !important;
        width: 100% !important;
      }

      .slide-tag {
        background: #ecfdf5 !important;
        color: #047857 !important;
        border: 1px solid #a7f3d0 !important;
      }

      .slide-title {
        background: none !important;
        -webkit-text-fill-color: initial !important;
        color: #0f172a !important;
        font-size: 2.1rem !important;
      }

      .slide-subtitle {
        color: #334155 !important;
        font-size: 1rem !important;
        margin-bottom: 1.2rem !important;
      }

      .hero-badge {
        background: #ecfdf5 !important;
        border: 1px solid #10b981 !important;
        color: #047857 !important;
        box-shadow: none !important;
      }

      .hero-title {
        background: none !important;
        -webkit-text-fill-color: initial !important;
        color: #14532d !important;
        font-size: 3.2rem !important;
      }

      .hero-sub {
        color: #334155 !important;
        font-size: 1.15rem !important;
        margin-bottom: 1.6rem !important;
      }

      .meta-card {
        background: #f8fafc !important;
        border: 1px solid #cbd5e1 !important;
      }

      .meta-label {
        color: #64748b !important;
      }

      .meta-val {
        color: #0f172a !important;
      }

      .card {
        background: #f8fafc !important;
        border: 1px solid #cbd5e1 !important;
        box-shadow: 0 1px 2px rgba(0,0,0,0.04) !important;
      }

      .card::before {
        height: 2px !important;
      }

      .card-highlight {
        background: #f0fdf4 !important;
        border: 1px solid #86efac !important;
      }

      .card-col {
        background: #fff1f2 !important;
        border: 1px solid #fecdd3 !important;
      }

      .card-col .card-title {
        color: #991b1b !important;
      }

      .card-col .card-list li::before {
        color: #dc2626 !important;
      }

      .card-ind {
        background: #f0f9ff !important;
        border: 1px solid #bae6fd !important;
      }

      .card-ind .card-title {
        color: #0369a1 !important;
      }

      .card-ind .card-list li::before {
        color: #0284c7 !important;
      }

      .card-title {
        color: #0f172a !important;
      }

      .card-text {
        color: #334155 !important;
      }

      .card-list li {
        color: #334155 !important;
      }

      .card-list li::before {
        color: #15803d !important;
      }

      .table-container {
        background: #ffffff !important;
        border: 1px solid #cbd5e1 !important;
      }

      table th {
        background: #f1f5f9 !important;
        color: #1e293b !important;
        border-bottom: 2px solid #cbd5e1 !important;
        font-weight: 700 !important;
      }

      table td {
        color: #0f172a !important;
        border-bottom: 1px solid #e2e8f0 !important;
      }

      .calc-box {
        background: #f8fafc !important;
        border: 1px dashed #059669 !important;
        color: #065f46 !important;
      }

      .calc-box strong {
        color: #064e3b !important;
      }

      .alert-box {
        border-width: 1px !important;
        border-style: solid !important;
      }

      .alert-warning {
        background: #fefce8 !important;
        border-color: #fde047 !important;
        color: #854d0e !important;
      }

      .alert-info {
        background: #f0f9ff !important;
        border-color: #7dd3fc !important;
        color: #075985 !important;
      }

      .alert-success {
        background: #f0fdf4 !important;
        border-color: #86efac !important;
        color: #166534 !important;
      }

      .badge-ra {
        background: #dcfce7 !important;
        color: #15803d !important;
        border: 1px solid #86efac !important;
      }

      .badge-hour {
        background: #fef3c7 !important;
        color: #92400e !important;
        border: 1px solid #fde68a !important;
      }

      .badge-col {
        background: #fee2e2 !important;
        color: #991b1b !important;
        border: 1px solid #fca5a5 !important;
      }

      .badge-ind {
        background: #e0f2fe !important;
        color: #0369a1 !important;
        border: 1px solid #7dd3fc !important;
      }
    }"""

# Replace @media print block
start_idx = content.find('@media print {')
if start_idx != -1:
    end_idx = content.find('</style>', start_idx)
    # find closing brace of @media print
    content = content[:start_idx] + light_print_css + "\n  " + content[end_idx:]
    with open(html_file, 'w', encoding='utf-8') as f:
        f.write(content)
    print("Updated public/presentacion.html with light print CSS")
else:
    print("Could not find @media print block")

# 3. Generate PDF using Edge headless
html_url = 'file:///' + html_file.replace('\\', '/')
cmd = [
    edge_path,
    '--headless=new',
    '--disable-gpu',
    '--no-pdf-header-footer',
    f'--print-to-pdf={pdf_file}',
    html_url
]

print("Generating PDF...")
res = subprocess.run(cmd, capture_output=True, text=True)
print("Return code:", res.returncode)

if os.path.exists(pdf_file):
    sz = os.path.getsize(pdf_file)
    print(f"Generated PDF: {pdf_file} ({sz} bytes)")
    
    # 4. Verify PDF pages and brightness
    doc = fitz.open(pdf_file)
    print(f"Total pages: {len(doc)}")
    for pno in [0, 1, 4, 7, 10]:
        if pno < len(doc):
            page = doc[pno]
            pix = page.get_pixmap(dpi=150)
            img = Image.frombytes("RGB", [pix.width, pix.height], pix.samples)
            brightness = np.array(img).mean()
            print(f"Page {pno+1} ({page.rect.width:.1f} x {page.rect.height:.1f} pt) brightness: {brightness:.1f} / 255")
            pix.save(f"test_page_{pno+1}.png")
else:
    print("Error: PDF file was not created.")

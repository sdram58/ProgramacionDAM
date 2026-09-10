import os
import subprocess
import fitz
from PIL import Image
import numpy as np

html_path = os.path.abspath('public/presentacion.html')

with open(html_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Replace the print stylesheet with a bulletproof direct-styling light theme
print_css = """
    @page {
      size: 297mm 210mm;
      margin: 0;
    }

    @media print {
      /* 1. Disable ALL animations and transitions and force 100% opacity */
      *, *::before, *::after {
        animation: none !important;
        -webkit-animation: none !important;
        transition: none !important;
        -webkit-transition: none !important;
        -webkit-print-color-adjust: exact !important;
        print-color-adjust: exact !important;
      }

      .slide, .slide-content, .card, .meta-card, .table-container, table, tr, td, th, div, section, article {
        opacity: 1 !important;
        visibility: visible !important;
        transform: none !important;
        filter: none !important;
      }

      /* 2. Page & Body */
      html, body {
        background: #ffffff !important;
        background-color: #ffffff !important;
        color: #0f172a !important;
        overflow: visible !important;
        height: auto !important;
        margin: 0 !important;
        padding: 0 !important;
      }

      /* 3. Hide web UI */
      #nav-bar, #progress-container, #menu-modal {
        display: none !important;
      }

      #slider {
        position: static !important;
        width: 100% !important;
        height: auto !important;
      }

      /* 4. Slides layout */
      .slide {
        position: relative !important;
        page-break-after: always !important;
        break-after: page !important;
        height: 210mm !important;
        max-height: 210mm !important;
        width: 297mm !important;
        max-width: 297mm !important;
        box-sizing: border-box !important;
        padding: 12mm 18mm !important;
        display: flex !important;
        flex-direction: column !important;
        justify-content: center !important;
        align-items: center !important;
        overflow: hidden !important;
        background: #ffffff !important;
        background-color: #ffffff !important;
      }

      .slide-content {
        max-width: 100% !important;
        width: 100% !important;
        opacity: 1 !important;
      }

      /* 5. Typography - FORCED DARK SOLID COLORS */
      h1, h2, h3, h4, h5, h6, .slide-title, .hero-title, .card-title {
        background: none !important;
        -webkit-background-clip: border-box !important;
        -webkit-text-fill-color: #0f172a !important;
        color: #0f172a !important;
        opacity: 1 !important;
        font-weight: 800 !important;
      }

      .hero-title {
        color: #14532d !important;
        -webkit-text-fill-color: #14532d !important;
        font-size: 3.2rem !important;
      }

      .slide-title {
        font-size: 2.1rem !important;
        margin-bottom: 0.4rem !important;
      }

      p, span, div, li, td, .slide-subtitle, .hero-sub, .card-text {
        color: #1e293b !important;
        -webkit-text-fill-color: #1e293b !important;
        opacity: 1 !important;
      }

      .slide-subtitle {
        font-size: 1.05rem !important;
        color: #334155 !important;
        -webkit-text-fill-color: #334155 !important;
        margin-bottom: 1.1rem !important;
      }

      .hero-sub {
        font-size: 1.2rem !important;
        color: #334155 !important;
        -webkit-text-fill-color: #334155 !important;
        margin-bottom: 1.5rem !important;
      }

      /* 6. Tags & Badges */
      .slide-tag {
        background: #ecfdf5 !important;
        color: #047857 !important;
        -webkit-text-fill-color: #047857 !important;
        border: 1.5px solid #059669 !important;
        font-weight: 700 !important;
      }

      .hero-badge {
        background: #ecfdf5 !important;
        color: #047857 !important;
        -webkit-text-fill-color: #047857 !important;
        border: 1.5px solid #059669 !important;
        box-shadow: none !important;
        font-weight: 700 !important;
      }

      .badge-ra {
        background: #dcfce7 !important;
        color: #14532d !important;
        -webkit-text-fill-color: #14532d !important;
        border: 1.5px solid #16a34a !important;
        font-weight: 800 !important;
      }

      .badge-hour {
        background: #fef3c7 !important;
        color: #78350f !important;
        -webkit-text-fill-color: #78350f !important;
        border: 1.5px solid #d97706 !important;
        font-weight: 700 !important;
      }

      .badge-col {
        background: #fee2e2 !important;
        color: #991b1b !important;
        -webkit-text-fill-color: #991b1b !important;
        border: 1.5px solid #dc2626 !important;
        font-weight: 700 !important;
      }

      .badge-ind {
        background: #e0f2fe !important;
        color: #0369a1 !important;
        -webkit-text-fill-color: #0369a1 !important;
        border: 1.5px solid #0284c7 !important;
        font-weight: 700 !important;
      }

      /* 7. Cards */
      .card {
        background: #f8fafc !important;
        background-color: #f8fafc !important;
        border: 1.5px solid #94a3b8 !important;
        box-shadow: 0 1px 3px rgba(0,0,0,0.08) !important;
      }

      .card::before {
        display: none !important;
      }

      .card-highlight {
        background: #f0fdf4 !important;
        background-color: #f0fdf4 !important;
        border: 2px solid #22c55e !important;
      }

      .card-col {
        background: #fff1f2 !important;
        background-color: #fff1f2 !important;
        border: 2px solid #f87171 !important;
      }

      .card-col .card-title {
        color: #b91c1c !important;
        -webkit-text-fill-color: #b91c1c !important;
      }

      .card-ind {
        background: #f0f9ff !important;
        background-color: #f0f9ff !important;
        border: 2px solid #38bdf8 !important;
      }

      .card-ind .card-title {
        color: #0369a1 !important;
        -webkit-text-fill-color: #0369a1 !important;
      }

      .card-list li {
        color: #1e293b !important;
        -webkit-text-fill-color: #1e293b !important;
      }

      .card-list li::before {
        color: #15803d !important;
        font-weight: bold !important;
      }

      .card-col .card-list li::before {
        color: #dc2626 !important;
      }

      .card-ind .card-list li::before {
        color: #0284c7 !important;
      }

      .meta-card {
        background: #f8fafc !important;
        background-color: #f8fafc !important;
        border: 1.5px solid #94a3b8 !important;
      }

      .meta-label {
        color: #475569 !important;
        -webkit-text-fill-color: #475569 !important;
        font-weight: 700 !important;
      }

      .meta-val {
        color: #0f172a !important;
        -webkit-text-fill-color: #0f172a !important;
        font-weight: 800 !important;
      }

      /* 8. Tables */
      .table-container {
        background: #ffffff !important;
        background-color: #ffffff !important;
        border: 1.5px solid #94a3b8 !important;
      }

      table {
        background: #ffffff !important;
      }

      table th {
        background: #e2e8f0 !important;
        background-color: #e2e8f0 !important;
        color: #0f172a !important;
        -webkit-text-fill-color: #0f172a !important;
        border-bottom: 2px solid #64748b !important;
        font-weight: 800 !important;
      }

      table td {
        color: #0f172a !important;
        -webkit-text-fill-color: #0f172a !important;
        border-bottom: 1px solid #cbd5e1 !important;
        font-weight: 500 !important;
      }

      table tr:nth-child(even) td {
        background: #f8fafc !important;
      }

      /* 9. Calculation & Alert boxes */
      .calc-box {
        background: #f8fafc !important;
        background-color: #f8fafc !important;
        border: 1.5px dashed #059669 !important;
        color: #064e3b !important;
        -webkit-text-fill-color: #064e3b !important;
        font-weight: 600 !important;
      }

      .calc-box strong {
        color: #022c22 !important;
        -webkit-text-fill-color: #022c22 !important;
        font-weight: 800 !important;
      }

      .alert-box {
        border-width: 1.5px !important;
        border-style: solid !important;
        font-weight: 500 !important;
      }

      .alert-warning {
        background: #fefce8 !important;
        border-color: #eab308 !important;
        color: #713f12 !important;
        -webkit-text-fill-color: #713f12 !important;
      }

      .alert-warning strong {
        color: #422006 !important;
        -webkit-text-fill-color: #422006 !important;
      }

      .alert-info {
        background: #f0f9ff !important;
        border-color: #0284c7 !important;
        color: #0c4a6e !important;
        -webkit-text-fill-color: #0c4a6e !important;
      }

      .alert-info strong {
        color: #082f49 !important;
        -webkit-text-fill-color: #082f49 !important;
      }

      .alert-success {
        background: #f0fdf4 !important;
        border-color: #16a34a !important;
        color: #14532d !important;
        -webkit-text-fill-color: #14532d !important;
      }

      .alert-success strong {
        color: #052e16 !important;
        -webkit-text-fill-color: #052e16 !important;
      }
    }
"""

# Replace in content
first_page_idx = content.find('@page {')
last_style_idx = content.find('</style>')

if first_page_idx != -1 and last_style_idx != -1:
    new_content = content[:first_page_idx] + print_css.strip() + "\n  " + content[last_style_idx:]
    with open(html_path, 'w', encoding='utf-8') as f:
        f.write(new_content)
    print("Replaced print CSS successfully in public/presentacion.html")
else:
    print("Could not locate @page / </style> tags")

# Run Edge headless to render PDF
edge_path = r'C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe'
pdf_out = os.path.abspath('public/presentacion.pdf')
html_url = 'file:///' + html_path.replace('\\', '/')

cmd = [
    edge_path,
    '--headless=new',
    '--disable-gpu',
    '--no-pdf-header-footer',
    '--virtual-time-budget=5000',
    f'--print-to-pdf={pdf_out}',
    html_url
]

res = subprocess.run(cmd, capture_output=True, text=True)
print("Edge return code:", res.returncode)

if os.path.exists(pdf_out):
    print("PDF generated. Size:", os.path.getsize(pdf_out))
    doc = fitz.open(pdf_out)
    print("Total pages:", len(doc))
    
    # Check Page 3 (Resultados de Aprendizaje)
    page3 = doc[2]
    pix = page3.get_pixmap(dpi=150)
    img = Image.frombytes('RGB', [pix.width, pix.height], pix.samples)
    arr = np.array(img)
    darkest = arr.min(axis=(0,1))
    print("NEW PDF Page 3 darkest pixel:", darkest)
    pix.save('page3_new.png')

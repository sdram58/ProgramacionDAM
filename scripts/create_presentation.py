import sys

html_parts = []

def add(p):
    html_parts.append(p)

add('''<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Presentación del Módulo: Programación — 1.º DAM Semipresencial (Curso 26-27)</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Geist:wght@300;400;500;600;700;800&family=Geist+Mono:wght@400;500;600&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary: #2d8a4e;
      --primary-light: #3ba762;
      --primary-dark: #1f5e35;
      --accent-glow: rgba(59, 167, 98, 0.25);
      --bg-dark: #090e0b;
      --bg-card: #101913;
      --bg-card-hover: #152219;
      --border-color: #1e3324;
      --border-accent: #2c5437;
      --text-main: #f0fdf4;
      --text-muted: #94a3b8;
      --text-dim: #64748b;
      --warning: #eab308;
      --danger: #ef4444;
      --info: #0ea5e9;
      --success: #22c55e;
      --tut-col: #f87171;
      --tut-ind: #38bdf8;
      --font-sans: 'Geist', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
      --font-mono: 'Geist Mono', monospace;
    }

    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
    }

    body {
      background-color: var(--bg-dark);
      color: var(--text-main);
      font-family: var(--font-sans);
      overflow: hidden;
      height: 100vh;
      width: 100vw;
      user-select: none;
      -webkit-font-smoothing: antialiased;
    }

    #progress-container {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 4px;
      background: rgba(255, 255, 255, 0.05);
      z-index: 100;
    }

    #progress-bar {
      height: 100%;
      width: 0%;
      background: linear-gradient(90deg, var(--primary), var(--primary-light));
      box-shadow: 0 0 10px var(--primary-light);
      transition: width 0.3s ease;
    }

    #slider {
      position: relative;
      width: 100vw;
      height: 100vh;
    }

    .slide {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      opacity: 0;
      visibility: hidden;
      transform: scale(0.98);
      transition: opacity 0.35s ease, transform 0.35s ease, visibility 0.35s;
      padding: 2.8rem 4.5rem 5.5rem;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      overflow-y: auto;
    }

    .slide.active {
      opacity: 1;
      visibility: visible;
      transform: scale(1);
      z-index: 10;
    }

    .slide-content {
      width: 100%;
      max-width: 1180px;
      animation: fadeIn 0.4s ease forwards;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(12px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .slide-tag {
      display: inline-flex;
      align-items: center;
      gap: 0.5rem;
      background: rgba(45, 138, 78, 0.15);
      color: var(--primary-light);
      border: 1px solid var(--border-accent);
      padding: 0.3rem 0.85rem;
      border-radius: 9999px;
      font-size: 0.78rem;
      font-weight: 600;
      letter-spacing: 0.05em;
      text-transform: uppercase;
      margin-bottom: 0.75rem;
    }

    .slide-title {
      font-size: 2.2rem;
      font-weight: 800;
      line-height: 1.2;
      margin-bottom: 0.5rem;
      letter-spacing: -0.025em;
      background: linear-gradient(135deg, #ffffff 40%, #cbd5e1 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }

    .slide-subtitle {
      font-size: 1.05rem;
      color: var(--text-muted);
      font-weight: 400;
      margin-bottom: 1.4rem;
      max-width: 900px;
      line-height: 1.5;
    }

    .hero-slide {
      text-align: center;
      justify-content: center;
      align-items: center;
    }

    .hero-badge {
      display: inline-flex;
      align-items: center;
      gap: 0.5rem;
      background: rgba(45, 138, 78, 0.2);
      border: 1px solid var(--primary-light);
      color: var(--primary-light);
      padding: 0.4rem 1.2rem;
      border-radius: 9999px;
      font-size: 0.95rem;
      font-weight: 600;
      margin-bottom: 1.25rem;
      box-shadow: 0 0 20px var(--accent-glow);
    }

    .hero-title {
      font-size: 3.5rem;
      font-weight: 800;
      letter-spacing: -0.03em;
      line-height: 1.1;
      margin-bottom: 0.85rem;
      background: linear-gradient(135deg, #ffffff 20%, #4ade80 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }

    .hero-sub {
      font-size: 1.2rem;
      color: #94a3b8;
      margin-bottom: 1.85rem;
      max-width: 750px;
      margin-left: auto;
      margin-right: auto;
      line-height: 1.6;
    }

    .hero-meta-grid {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 1.2rem;
      max-width: 1050px;
      margin: 0 auto;
    }

    .meta-card {
      background: var(--bg-card);
      border: 1px solid var(--border-color);
      border-radius: 12px;
      padding: 1.15rem;
      text-align: left;
      transition: border-color 0.2s;
    }

    .meta-card:hover {
      border-color: var(--border-accent);
    }

    .meta-label {
      font-size: 0.72rem;
      text-transform: uppercase;
      letter-spacing: 0.05em;
      color: var(--text-dim);
      margin-bottom: 0.35rem;
    }

    .meta-val {
      font-size: 1rem;
      font-weight: 600;
      color: var(--text-main);
      word-break: break-word;
    }

    .grid-2 {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 1.35rem;
    }

    .grid-3 {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 1.35rem;
    }

    .grid-4 {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 1.15rem;
    }

    .card {
      background: var(--bg-card);
      border: 1px solid var(--border-color);
      border-radius: 14px;
      padding: 1.3rem;
      position: relative;
      overflow: hidden;
    }

    .card::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 3px;
      background: linear-gradient(90deg, transparent, var(--border-accent), transparent);
    }

    .card-highlight {
      border-color: var(--border-accent);
      background: linear-gradient(180deg, rgba(45, 138, 78, 0.08) 0%, var(--bg-card) 100%);
    }

    .card-col {
      border-color: rgba(248, 113, 113, 0.35);
      background: linear-gradient(180deg, rgba(239, 68, 68, 0.06) 0%, var(--bg-card) 100%);
    }
    .card-col::before {
      background: linear-gradient(90deg, transparent, #ef4444, transparent);
    }

    .card-ind {
      border-color: rgba(56, 189, 248, 0.35);
      background: linear-gradient(180deg, rgba(14, 165, 233, 0.06) 0%, var(--bg-card) 100%);
    }
    .card-ind::before {
      background: linear-gradient(90deg, transparent, #0ea5e9, transparent);
    }

    .card-icon {
      font-size: 1.75rem;
      margin-bottom: 0.6rem;
      display: inline-block;
    }

    .card-title {
      font-size: 1.1rem;
      font-weight: 700;
      margin-bottom: 0.45rem;
      color: var(--text-main);
    }

    .card-text {
      color: var(--text-muted);
      font-size: 0.885rem;
      line-height: 1.5;
    }

    .card-list {
      list-style: none;
      margin-top: 0.6rem;
    }

    .card-list li {
      position: relative;
      padding-left: 1.25rem;
      margin-bottom: 0.45rem;
      font-size: 0.85rem;
      color: var(--text-muted);
      line-height: 1.4;
    }

    .card-list li::before {
      content: '✔';
      position: absolute;
      left: 0;
      color: var(--primary-light);
      font-size: 0.75rem;
    }

    .card-col .card-list li::before {
      color: var(--tut-col);
    }

    .card-ind .card-list li::before {
      color: var(--tut-ind);
    }

    .badge {
      display: inline-block;
      padding: 0.2rem 0.55rem;
      border-radius: 6px;
      font-size: 0.72rem;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.03em;
    }

    .badge-ra {
      background: rgba(59, 167, 98, 0.18);
      color: #4ade80;
      border: 1px solid rgba(59, 167, 98, 0.35);
      font-family: var(--font-mono);
    }

    .badge-hour {
      background: rgba(234, 179, 8, 0.15);
      color: #facc15;
      border: 1px solid rgba(234, 179, 8, 0.3);
    }

    .badge-col {
      background: rgba(239, 68, 68, 0.18);
      color: #fca5a5;
      border: 1px solid rgba(239, 68, 68, 0.35);
    }

    .badge-ind {
      background: rgba(14, 165, 233, 0.18);
      color: #7dd3fc;
      border: 1px solid rgba(14, 165, 233, 0.35);
    }

    .table-container {
      background: var(--bg-card);
      border: 1px solid var(--border-color);
      border-radius: 12px;
      overflow: hidden;
      width: 100%;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      text-align: left;
      font-size: 0.88rem;
    }

    th {
      background: rgba(255, 255, 255, 0.03);
      padding: 0.75rem 1.15rem;
      font-weight: 600;
      color: var(--text-muted);
      text-transform: uppercase;
      font-size: 0.72rem;
      letter-spacing: 0.05em;
      border-bottom: 1px solid var(--border-color);
    }

    td {
      padding: 0.72rem 1.15rem;
      border-bottom: 1px solid rgba(255, 255, 255, 0.04);
      color: var(--text-main);
      vertical-align: middle;
    }

    tr:last-child td {
      border-bottom: none;
    }

    tr:hover td {
      background: rgba(45, 138, 78, 0.05);
    }

    .calc-box {
      background: rgba(0, 0, 0, 0.4);
      border: 1px dashed var(--border-accent);
      border-radius: 10px;
      padding: 1.1rem;
      font-family: var(--font-mono);
      font-size: 0.88rem;
      color: #a7f3d0;
      margin: 0.85rem 0;
      line-height: 1.6;
    }

    .calc-box strong {
      color: #fff;
    }

    .alert-box {
      border-radius: 10px;
      padding: 0.85rem 1.15rem;
      margin-top: 1.15rem;
      display: flex;
      align-items: flex-start;
      gap: 0.75rem;
      font-size: 0.865rem;
      line-height: 1.45;
    }

    .alert-warning {
      background: rgba(234, 179, 8, 0.1);
      border: 1px solid rgba(234, 179, 8, 0.3);
      color: #fef08a;
    }

    .alert-info {
      background: rgba(14, 165, 233, 0.1);
      border: 1px solid rgba(14, 165, 233, 0.3);
      color: #bae6fd;
    }

    .alert-success {
      background: rgba(34, 197, 94, 0.1);
      border: 1px solid rgba(34, 197, 94, 0.3);
      color: #bbf7d0;
    }

    #nav-bar {
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 56px;
      background: rgba(9, 14, 11, 0.9);
      backdrop-filter: blur(12px);
      border-top: 1px solid var(--border-color);
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 2rem;
      z-index: 90;
    }

    .nav-left {
      display: flex;
      align-items: center;
      gap: 0.85rem;
      font-size: 0.85rem;
      color: var(--text-dim);
    }

    .nav-title-short {
      font-weight: 600;
      color: var(--text-muted);
    }

    .nav-controls {
      display: flex;
      align-items: center;
      gap: 0.65rem;
    }

    .btn-nav {
      background: var(--bg-card);
      border: 1px solid var(--border-color);
      color: var(--text-main);
      width: 36px;
      height: 36px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 1rem;
      transition: all 0.2s;
    }

    .btn-nav:hover {
      background: var(--primary-dark);
      border-color: var(--primary-light);
      color: #fff;
    }

    .btn-text {
      width: auto;
      padding: 0 0.85rem;
      font-size: 0.8rem;
      font-weight: 500;
      gap: 0.4rem;
    }

    .slide-counter {
      font-family: var(--font-mono);
      font-size: 0.85rem;
      color: var(--text-muted);
      min-width: 65px;
      text-align: center;
    }

    #menu-modal {
      position: fixed;
      top: 0;
      left: 0;
      width: 100vw;
      height: 100vh;
      background: rgba(0, 0, 0, 0.8);
      backdrop-filter: blur(8px);
      z-index: 200;
      display: none;
      justify-content: center;
      align-items: center;
      padding: 2rem;
    }

    #menu-modal.active {
      display: flex;
    }

    .menu-box {
      background: var(--bg-card);
      border: 1px solid var(--border-accent);
      border-radius: 16px;
      max-width: 950px;
      width: 100%;
      max-height: 80vh;
      overflow-y: auto;
      padding: 1.75rem;
      box-shadow: 0 20px 50px rgba(0, 0, 0, 0.6);
    }

    .menu-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.25rem;
      padding-bottom: 0.75rem;
      border-bottom: 1px solid var(--border-color);
    }

    .menu-grid {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 0.65rem;
    }

    .menu-item {
      padding: 0.65rem 0.85rem;
      background: rgba(255, 255, 255, 0.02);
      border: 1px solid var(--border-color);
      border-radius: 8px;
      color: var(--text-muted);
      cursor: pointer;
      font-size: 0.85rem;
      display: flex;
      align-items: center;
      gap: 0.65rem;
      transition: all 0.2s;
    }

    .menu-item:hover, .menu-item.active {
      background: rgba(45, 138, 78, 0.15);
      border-color: var(--primary-light);
      color: #fff;
    }

    .menu-item-num {
      font-family: var(--font-mono);
      font-size: 0.75rem;
      color: var(--primary-light);
      background: rgba(45, 138, 78, 0.2);
      padding: 0.15rem 0.4rem;
      border-radius: 4px;
    }

    @media print {
      body { overflow: visible; height: auto; }
      #nav-bar, #progress-container, #menu-modal { display: none !important; }
      .slide {
        position: static !important;
        opacity: 1 !important;
        visibility: visible !important;
        transform: none !important;
        page-break-after: always;
        height: 100vh;
        padding: 3rem;
      }
    }
  </style>
</head>
<body>

  <div id="progress-container">
    <div id="progress-bar"></div>
  </div>

  <div id="slider">

    <!-- ==================== SLIDE 1: PORTADA ==================== -->
    <section class="slide hero-slide active" data-title="Portada">
      <div class="slide-content">
        <div class="hero-badge">
          <span>🚀</span> CFGS Desarrollo de Aplicaciones Multiplataforma (DAM) · 1.º Curso
        </div>
        <h1 class="hero-title">Programación</h1>
        <p class="hero-sub">
          Módulo Profesional 0485 · Modalidad Semipresencial<br>
          Presentación oficial del módulo, organización docente y calendario académico.
        </p>

        <div class="hero-meta-grid">
          <div class="meta-card">
            <div class="meta-label">Profesor</div>
            <div class="meta-val">Carlos Tarazona</div>
          </div>
          <div class="meta-card">
            <div class="meta-label">Correo Electrónico</div>
            <div class="meta-val" style="font-size: 0.88rem; color: #4ade80;">c.tarazonatarrega@edu.gva.es</div>
          </div>
          <div class="meta-card">
            <div class="meta-label">Curso Académico</div>
            <div class="meta-val">2026 – 2027 (26-27)</div>
          </div>
          <div class="meta-card">
            <div class="meta-label">Centro Educativo</div>
            <div class="meta-val">IES Serra Perenxisa</div>
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 2: EL MÓDULO ==================== -->
    <section class="slide" data-title="El Módulo de Programación">
      <div class="slide-content">
        <div class="slide-tag"><span>📌</span> Contextualización</div>
        <h2 class="slide-title">El pilar formativo del ciclo DAM</h2>
        <p class="slide-subtitle">
          Programación es el módulo vertebrador con mayor asignación lectiva (256 horas totales, 14 créditos ECTS). Proporciona los cimientos lógicos y técnicos para el resto del ciclo.
        </p>

        <div class="grid-3">
          <div class="card card-highlight">
            <div class="card-icon">🧠</div>
            <h3 class="card-title">Pensamiento Computacional</h3>
            <p class="card-text">Aprenderás a descomponer problemas complejos en instrucciones lógicas, algoritmos reproducibles y código limpio y mantenible.</p>
          </div>
          <div class="card card-highlight">
            <div class="card-icon">☕</div>
            <h3 class="card-title">Java Estándar y Moderno</h3>
            <p class="card-text">Utilizamos Java en su versión LTS actual: un lenguaje robusto, fuertemente tipado, orientado a objetos y demandado globalmente.</p>
          </div>
          <div class="card card-highlight">
            <div class="card-icon">🛠️</div>
            <h3 class="card-title">Ecosistema Profesional</h3>
            <p class="card-text">Dominio de IntelliJ IDEA, depuración interactiva (breakpoints), control de versiones con Git/GitHub y persistencia en bases de datos.</p>
          </div>
        </div>

        <div class="alert-box alert-info">
          <span>💡</span>
          <div>
            <strong>Perfil del curso semipresencial:</strong> Diseñado para adultos que compatibilizan estudios con su actividad laboral o personal. El éxito radica en la constancia semanal y en el aprovechamiento activo de las tutorías.
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 3: RESULTADOS DE APRENDIZAJE ==================== -->
    <section class="slide" data-title="Resultados de Aprendizaje">
      <div class="slide-content">
        <div class="slide-tag"><span>📜</span> Currículo Oficial</div>
        <h2 class="slide-title">Resultados de Aprendizaje (RA 1 al RA 9)</h2>
        <p class="slide-subtitle">
          Competencias oficiales del título fijadas por la normativa y la programación didáctica del módulo.
        </p>

        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th style="width: 90px;">Código</th>
                <th>Denominación Oficial del Resultado de Aprendizaje</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><span class="badge badge-ra">RA 1</span></td>
                <td>Reconoce la estructura de un programa informático, identificando y relacionando los elementos propios del lenguaje de programación utilizado.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 2</span></td>
                <td>Escribe y prueba programas sencillos, reconociendo y aplicando los fundamentos de la programación orientada a objetos.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 3</span></td>
                <td>Escribe y depura código, analizando y utilizando las estructuras de control del lenguaje.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 4</span></td>
                <td>Desarrolla programas organizados en clases analizando y aplicando los principios de la programación orientada a objetos.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 5</span></td>
                <td>Realiza operaciones de entrada y salida de información, utilizando procedimientos específicos del lenguaje y librerías de clases.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 6</span></td>
                <td>Escribe programas que manipulen información seleccionando y utilizando tipos avanzados de datos.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 7</span></td>
                <td>Desarrolla programas aplicando características avanzadas de los lenguajes orientados a objetos y del entorno de programación.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 8</span></td>
                <td>Utiliza bases de datos orientadas a objetos, analizando sus características y aplicando técnicas para mantener la persistencia de la información.</td>
              </tr>
              <tr>
                <td><span class="badge badge-ra">RA 9</span></td>
                <td>Gestiona información almacenada en bases de datos relacionales manteniendo la integridad y consistencia de los datos.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 4: UNIDADES DIDÁCTICAS OFICIALES ==================== -->
    <section class="slide" data-title="Unidades Didácticas Oficiales">
      <div class="slide-content">
        <div class="slide-tag"><span>📚</span> Contenidos del Curso</div>
        <h2 class="slide-title">11 Unidades Didácticas Oficiales (GEMINI.md)</h2>
        <p class="slide-subtitle">
          El temario completo según la programación didáctica oficial y la estructura de los apuntes web del módulo.
        </p>

        <div class="grid-3">
          <div class="card">
            <span class="badge badge-hour" style="margin-bottom: 0.5rem;">1.ª Evaluación</span>
            <ul class="card-list" style="margin-top: 0.25rem;">
              <li><strong>🚀 UD 01.</strong> Introducción a la programación <span class="badge badge-ra">RA1 · 8h</span></li>
              <li><strong>🔤 UD 02.</strong> Primeros pasos con Java <span class="badge badge-ra">RA1, RA4 · 24h</span></li>
              <li><strong>🔀 UD 03.</strong> Excepciones, bucles, arrays y métodos <span class="badge badge-ra">RA1,2,3,6 · 28h</span></li>
            </ul>
          </div>

          <div class="card">
            <span class="badge badge-hour" style="margin-bottom: 0.5rem;">2.ª Evaluación</span>
            <ul class="card-list" style="margin-top: 0.25rem;">
              <li><strong>🏗️ UD 04.</strong> Clases y objetos <span class="badge badge-ra">RA1,2,3,4 · 28h</span></li>
              <li><strong>🧬 UD 05.</strong> POO avanzada <span class="badge badge-ra">RA4, RA7 · 30h</span></li>
              <li><strong>📚 UD 06.</strong> Genéricos y colecciones <span class="badge badge-ra">RA6, RA7 · 30h</span></li>
              <li><strong>⚡ UD 07.</strong> La API Stream <span class="badge badge-ra">RA6,7,8 · 19h</span></li>
            </ul>
          </div>

          <div class="card">
            <span class="badge badge-hour" style="margin-bottom: 0.5rem;">3.ª Evaluación</span>
            <ul class="card-list" style="margin-top: 0.25rem;">
              <li><strong>📁 UD 08.</strong> Ficheros y streams <span class="badge badge-ra">RA2,3,4,5 · 22h</span></li>
              <li><strong>🖼️ UD 09.</strong> Aplicaciones por eventos <span class="badge badge-ra">RA5 · 22h</span></li>
              <li><strong>🗄️ UD 10.</strong> Bases de datos relacionales <span class="badge badge-ra">RA9 · 20h</span></li>
              <li><strong>🌐 UD 11.</strong> BD orientadas a objetos <span class="badge badge-ra">RA8 · 17h</span></li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 5: REPARTO Y FECHAS ==================== -->
    <section class="slide" data-title="Reparto y Calendario por Evaluación">
      <div class="slide-content">
        <div class="slide-tag"><span>📅</span> Calendario Oficial 2026-2027</div>
        <h2 class="slide-title">Reparto de Unidades y Fechas Clave</h2>
        <p class="slide-subtitle">
          Planificación temporal de contenidos, sesiones de evaluación y semanas previstas para los exámenes.
        </p>

        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th style="width: 140px;">Evaluación</th>
                <th style="width: 250px;">Unidades Didácticas</th>
                <th>Semana de Exámenes (aprox.)</th>
                <th style="width: 190px; text-align: center;">Sesión Evaluación</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><strong>1.ª Evaluación</strong></td>
                <td>UD 01, UD 02, UD 03 (60 h)</td>
                <td>Una semana antes (~16 al 20 de noviembre)</td>
                <td style="text-align: center;"><span class="badge badge-hour">23 – 27 Noviembre</span></td>
              </tr>
              <tr>
                <td><strong>2.ª Evaluación</strong></td>
                <td>UD 04, UD 05, UD 06, UD 07 (107 h)</td>
                <td>Una semana antes (~22 al 26 de febrero)</td>
                <td style="text-align: center;"><span class="badge badge-hour">1 – 5 Marzo</span></td>
              </tr>
              <tr>
                <td><strong>3.ª Eval. / Final</strong></td>
                <td>UD 08, UD 09, UD 10, UD 11 (81 h) + Proyecto</td>
                <td>Una semana antes (~31 mayo al 4 de junio)</td>
                <td style="text-align: center;"><span class="badge badge-hour">7 – 11 Junio</span></td>
              </tr>
              <tr>
                <td><strong>Extraordinaria</strong></td>
                <td>Recuperación de evaluaciones pendientes</td>
                <td>Una semana antes (~14 al 18 de junio)</td>
                <td style="text-align: center;"><span class="badge badge-hour" style="background: rgba(239, 68, 68, 0.2); color: #fca5a5;">21 – 25 Junio</span></td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="alert-box alert-warning">
          <span>⚠️</span>
          <div>
            <strong>Fin de clases lectivas:</strong> Las clases regulares terminarán sobre el <strong>10 de mayo</strong>. Los exámenes de cada evaluación se celebran aproximadamente <strong>una semana antes</strong> de las fechas de evaluación indicadas.
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 6: CÓMO SE TRABAJARÁ ==================== -->
    <section class="slide" data-title="Cómo se trabajará">
      <div class="slide-content">
        <div class="slide-tag"><span>💼</span> Metodología Semipresencial</div>
        <h2 class="slide-title">¿Cómo vamos a trabajar en el día a día?</h2>
        <p class="slide-subtitle">
          Un marco flexible y organizado pensado para que gestiones tu propio ritmo con acompañamiento docente continuo.
        </p>

        <div class="grid-3">
          <div class="card card-highlight">
            <div class="card-icon">🏃</div>
            <h3 class="card-title">Ritmo Propio Guiado</h3>
            <p class="card-text">Cada estudiante tiene su propio ritmo de trabajo. El profesor indicará semanalmente en Aules qué contenido se debe ver en la semana o semanas siguientes.</p>
          </div>

          <div class="card card-highlight">
            <div class="card-icon">📝</div>
            <h3 class="card-title">Prácticas con Plazo</h3>
            <p class="card-text">El profesor indicará qué práctica debe realizarse y su fecha límite de entrega a través de los buzones oficiales de Aules.</p>
          </div>

          <div class="card card-highlight">
            <div class="card-icon">🤝</div>
            <h3 class="card-title">Resolución en Foros</h3>
            <p class="card-text">Si necesitas resolver dudas: la primera opción siempre son los foros de cada unidad en Aules. Tus compañeros pueden ayudarte, y el profesor también.</p>
          </div>
        </div>

        <div class="alert-box alert-info">
          <span>📢</span>
          <div>
            <strong>Pautas clave de comunicación:</strong> No publiques nunca la solución completa en los foros (ayuda a razonar). <strong>No uses el correo electrónico para tus dudas</strong>; utiliza los foros de Aules o las tutorías colectivas e individuales.
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 7: DOS TIPOS DE TUTORÍAS ==================== -->
    <section class="slide" data-title="Dos tipos de tutorías">
      <div class="slide-content">
        <div class="slide-tag"><span>👥</span> Asistencia Docente</div>
        <h2 class="slide-title">Dos tipos de tutorías: Individuales y Colectivas</h2>
        <p class="slide-subtitle">
          Espacios complementarios diseñados tanto para explicaciones grupales como para resolver dudas concretas de código.
        </p>

        <div class="grid-2">
          <!-- COLECTIVAS -->
          <div class="card card-col">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
              <h3 class="card-title" style="color: #fca5a5; margin: 0;">👥 Tutorías Colectivas</h3>
              <span class="badge badge-col">Clase 2.21</span>
            </div>
            <p class="card-text" style="margin-bottom: 0.75rem;"><strong>Ubicación:</strong> Aula 2.21 (Segunda planta).</p>
            <ul class="card-list">
              <li>Serán <strong>siempre presenciales</strong> (salvo acontecimiento especial).</li>
              <li>Se resolverán dudas comunes, se explicarán partes vistas hasta el momento o se realizarán actividades prácticas.</li>
              <li>El profesor indicará qué se verá en las tutorías colectivas y, si te interesa, puedes asistir.</li>
              <li><strong>La asistencia NO es obligatoria.</strong></li>
            </ul>
          </div>

          <!-- INDIVIDUALES -->
          <div class="card card-ind">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
              <h3 class="card-title" style="color: #7dd3fc; margin: 0;">👤 Tutorías Individuales</h3>
              <span class="badge badge-ind">Cita Previa</span>
            </div>
            <p class="card-text" style="margin-bottom: 0.75rem;"><strong>Modalidad:</strong> Preferentemente telemáticas o presenciales.</p>
            <ul class="card-list">
              <li><strong>Online:</strong> El profesor os dará una dirección de Microsoft Teams para la reunión.</li>
              <li><strong>Presencial:</strong> Normalmente en el departamento de informática (segunda planta).</li>
              <li><strong>Solicitud con al menos 24 h de antelación</strong> indicando la pregunta o cuestión a tratar.</li>
              <li>El profesor debe aceptarla (otro compañero puede haberla solicitado antes).</li>
              <li><strong>La asistencia NO es obligatoria.</strong></li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 8: HORARIO DE TUTORÍAS ==================== -->
    <section class="slide" data-title="Horario de Tutorías">
      <div class="slide-content">
        <div class="slide-tag"><span>⏰</span> Cuadro Horario Oficial</div>
        <h2 class="slide-title">Horario Oficial de Tutorías (Curso 26-27)</h2>
        <p class="slide-subtitle">
          Distribución de franjas horarias semanales para la atención colectiva en aula y la atención telemática individual.
        </p>

        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th style="width: 170px;">Día</th>
                <th style="width: 48%;">Tutorías Colectivas (Aula 2.21)</th>
                <th>Tutorías Individuales (Preferentemente telemática / Cita previa)</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><strong>Lunes</strong></td>
                <td><span class="badge badge-col">16:50 – 17:45</span> Presencial (Aula 2.21)</td>
                <td style="color: var(--text-dim);">—</td>
              </tr>
              <tr>
                <td><strong>Martes</strong></td>
                <td><span class="badge badge-col">13:05 – 14:00</span> Presencial (Aula 2.21)</td>
                <td><span class="badge badge-ind">12:10 – 13:05</span> Preferentemente telemática (Teams)</td>
              </tr>
              <tr>
                <td><strong>Miércoles</strong></td>
                <td style="color: var(--text-dim);">—</td>
                <td style="color: var(--text-dim);">—</td>
              </tr>
              <tr>
                <td><strong>Jueves</strong></td>
                <td><span class="badge badge-col">13:05 – 14:00</span> Presencial (Aula 2.21)</td>
                <td><span class="badge badge-ind">12:10 – 13:05</span> Preferentemente telemática (Teams)</td>
              </tr>
              <tr>
                <td><strong>Viernes</strong></td>
                <td><span class="badge badge-col">19:55 – 20:50</span> Presencial (Aula 2.21)</td>
                <td>
                  <span class="badge badge-ind">18:05 – 19:00</span> y <span class="badge badge-ind">19:00 – 19:55</span> (2 sesiones)
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="alert-box alert-info">
          <span>📌</span>
          <div>
            <strong>Procedimiento de cita:</strong> Solicita tu tutoría individual a través de Aules con un mínimo de <strong>24 horas de antelación</strong>, especificando con claridad la duda a tratar.
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 9: AULA VIRTUAL AULES ==================== -->
    <section class="slide" data-title="Aula Virtual Aules">
      <div class="slide-content">
        <div class="slide-tag"><span>🌐</span> Plataforma Oficial</div>
        <h2 class="slide-title">Aules: Tu aula real durante el curso</h2>
        <p class="slide-subtitle">
          El portal web (basado en Moodle) donde se centraliza todo el material, las entregas y la comunicación oficial.
        </p>

        <div class="grid-2">
          <div class="card card-highlight">
            <div class="card-icon">💻</div>
            <h3 class="card-title">Acceso Oficial al Curso</h3>
            <p class="card-text" style="margin-bottom: 1rem;">Enlace directo al espacio del módulo de Programación en la plataforma semipresencial:</p>
            <a href="https://aules.edu.gva.es/semipresencial/course/view.php?id=55721" target="_blank" style="display: inline-flex; align-items: center; gap: 0.5rem; background: var(--primary); color: #fff; padding: 0.65rem 1.15rem; border-radius: 8px; text-decoration: none; font-size: 0.85rem; font-weight: 600; font-family: var(--font-mono); word-break: break-all;">
              🔗 https://aules.edu.gva.es/semipresencial/course/view.php?id=55721
            </a>
            <ul class="card-list" style="margin-top: 1.25rem;">
              <li><strong>Unidades para descargar y estudiar:</strong> Documentación teórica, ejemplos y guías.</li>
              <li><strong>Entrega de ejercicios y prácticas:</strong> Buzones con control de fechas límite.</li>
              <li><strong>Exámenes y convocatorias:</strong> Instrucciones oficiales de cada evaluación.</li>
            </ul>
          </div>

          <div class="card">
            <div class="card-icon">💬</div>
            <h3 class="card-title">Buenas Prácticas en los Foros</h3>
            <p class="card-text">
              Los foros por unidad son la herramienta colaborativa más potente del curso:
            </p>
            <ul class="card-list">
              <li>Plantea tu duda aportando el fragmento de código relevante y el mensaje de error de IntelliJ.</li>
              <li>Ayuda a tus compañeros a razonar sin dar la solución hecha.</li>
              <li>Revisa si tu duda ya fue resuelta previamente en otro hilo del foro.</li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 10: CALIFICACIÓN ==================== -->
    <section class="slide" data-title="Sistema de Calificación">
      <div class="slide-content">
        <div class="slide-tag"><span>⚖️</span> Criterios de Evaluación</div>
        <h2 class="slide-title">Sistema de Calificación Oficial</h2>
        <p class="slide-subtitle">
          Ponderaciones fijadas para cada evaluación y cálculo de la calificación final ordinaria del módulo.
        </p>

        <div class="grid-3">
          <div class="card">
            <div style="font-size: 0.8rem; color: var(--primary-light); font-weight: 700; text-transform: uppercase;">1.ª Evaluación</div>
            <div style="font-size: 2.7rem; font-weight: 800; color: #fff; margin: 0.5rem 0;">100 %</div>
            <div style="font-weight: 600; color: var(--text-main); margin-bottom: 0.4rem;">Examen Teórico / Práctico</div>
            <p class="card-text">Prueba presencial individual en ordenador sobre los contenidos de las UDs 1 a 3.</p>
          </div>

          <div class="card">
            <div style="font-size: 0.8rem; color: var(--primary-light); font-weight: 700; text-transform: uppercase;">2.ª Evaluación</div>
            <div style="font-size: 2.7rem; font-weight: 800; color: #fff; margin: 0.5rem 0;">100 %</div>
            <div style="font-weight: 600; color: var(--text-main); margin-bottom: 0.4rem;">Examen Teórico / Práctico</div>
            <p class="card-text">Prueba presencial individual en ordenador sobre POO, colecciones y Streams (UDs 4 a 7).</p>
          </div>

          <div class="card card-highlight">
            <div style="font-size: 0.8rem; color: var(--primary-light); font-weight: 700; text-transform: uppercase;">3.ª Evaluación</div>
            <div style="font-size: 2.7rem; font-weight: 800; color: #4ade80; margin: 0.5rem 0;">50% + 50%</div>
            <div style="font-weight: 600; color: var(--text-main); margin-bottom: 0.4rem;">Proyecto Presentado + Examen</div>
            <p class="card-text">50 % Trabajo/proyecto que presentarán + 50 % Examen presencial (UDs 8 a 11).</p>
          </div>
        </div>

        <div class="calc-box" style="text-align: center; font-size: 1.05rem;">
          Nota Final Ordinaria = Media Aritmética: <strong>(1.ª Eval + 2.ª Eval + 3.ª Eval) / 3</strong>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 11: CONDICIONES DE APROBADO ==================== -->
    <section class="slide" data-title="Condiciones de Aprobado">
      <div class="slide-content">
        <div class="slide-tag"><span>🎯</span> Requisitos Académicos</div>
        <h2 class="slide-title">¿Qué necesito para aprobar el módulo?</h2>
        <p class="slide-subtitle">
          Condiciones mínimas para realizar la media y superar con éxito la asignatura.
        </p>

        <div class="grid-3">
          <div class="card">
            <div class="card-icon">🟢</div>
            <h3 class="card-title">Superar una parte</h3>
            <div style="font-size: 2rem; font-weight: 800; color: #4ade80; margin-bottom: 0.5rem;">≥ 5,0</div>
            <p class="card-text">Cada prueba evaluable independiente (exámenes y proyecto presentado de la 3ª) se supera con un mínimo de 5 sobre 10.</p>
          </div>

          <div class="card">
            <div class="card-icon">🟡</div>
            <h3 class="card-title">Mínimo para mediar</h3>
            <div style="font-size: 2rem; font-weight: 800; color: #facc15; margin-bottom: 0.5rem;">≥ 4,5</div>
            <p class="card-text">Para realizar la media aritmética de las 3 evaluaciones, es <strong>imprescindible obtener como mínimo un 4,5</strong> en cada una de ellas.</p>
          </div>

          <div class="card card-highlight">
            <div class="card-icon">🏆</div>
            <h3 class="card-title">Superación del Módulo</h3>
            <div style="font-size: 2rem; font-weight: 800; color: #fff; margin-bottom: 0.5rem;">≥ 5,0</div>
            <p class="card-text">Para considerar superado el módulo es necesario obtener una <strong>nota final superior o igual a 5</strong> cumpliendo los mínimos.</p>
          </div>
        </div>

        <div class="alert-box alert-warning">
          <span>⚠️</span>
          <div>
            <strong>Regla del umbral 4,5:</strong> Si en alguna evaluación obtienes menos de 4,5, dicha evaluación no promedia con las demás y deberá ser recuperada para poder superar la asignatura.
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 12: EJEMPLOS NUMÉRICOS ==================== -->
    <section class="slide" data-title="Ejemplos Numéricos de Calificación">
      <div class="slide-content">
        <div class="slide-tag"><span>🔢</span> Casos Prácticos</div>
        <h2 class="slide-title">Ejemplos reales de cálculo de nota</h2>
        <p class="slide-subtitle">
          Casos prácticos paso a paso para verificar cómo se aplican las fórmulas de calificación.
        </p>

        <div class="grid-3">
          <div class="card">
            <h3 class="card-title">Caso A · 3.ª Evaluación</h3>
            <p class="card-text" style="margin-bottom: 0.5rem;">Cálculo 50% proyecto + 50% examen:</p>
            <div class="calc-box" style="font-size: 0.85rem;">
              Proyecto presentado: 7,0 × 50% = 3,5<br>
              Examen trimestral:   8,0 × 50% = 4,0<br>
              -----------------------------------<br>
              <strong>Nota 3.ª Evaluación = 7,5</strong>
            </div>
            <span class="badge" style="background: rgba(34, 197, 94, 0.2); color: #4ade80;">Partes superadas</span>
          </div>

          <div class="card">
            <h3 class="card-title">Caso B · Media Aprobada</h3>
            <p class="card-text" style="margin-bottom: 0.5rem;">Media aritmética ordinaria:</p>
            <div class="calc-box" style="font-size: 0.85rem;">
              1.ª Eval: 6,5 (≥ 4,5)<br>
              2.ª Eval: 5,0 (≥ 4,5)<br>
              3.ª Eval: 7,5 (≥ 4,5)<br>
              -----------------------------------<br>
              Nota = (6,5 + 5,0 + 7,5) / 3<br>
              <strong>Nota Final = 6,33 → Aprobado</strong>
            </div>
          </div>

          <div class="card">
            <h3 class="card-title">Caso C · Menos de 4,5</h3>
            <p class="card-text" style="margin-bottom: 0.5rem;">Cuando no se alcanza el mínimo para mediar:</p>
            <div class="calc-box" style="font-size: 0.85rem; color: #fca5a5;">
              1.ª Eval: 7,0 (Superada)<br>
              2.ª Eval: 3,8 (&lt; 4,5 ¡No media!)<br>
              3.ª Eval: 6,5 (Superada)<br>
              -----------------------------------<br>
              <strong>Debe recuperar la 2.ª Evaluación</strong>
            </div>
            <span class="badge" style="background: rgba(239, 68, 68, 0.2); color: #fca5a5;">No se hace media</span>
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 13: RECUPERACIONES Y EXTRAORDINARIA ==================== -->
    <section class="slide" data-title="Recuperaciones y Extraordinaria">
      <div class="slide-content">
        <div class="slide-tag"><span>🔄</span> Oportunidades de Superación</div>
        <h2 class="slide-title">Recuperaciones en Ordinaria y Extraordinaria</h2>
        <p class="slide-subtitle">
          El sistema premia el esfuerzo continuo: se conservan las evaluaciones que ya hayas aprobado.
        </p>

        <div class="grid-2">
          <div class="card card-highlight">
            <div class="card-icon">📋</div>
            <h3 class="card-title">Conservación de Evaluaciones Superadas</h3>
            <p class="card-text">
              Tanto en la recuperación de la convocatoria ordinaria como en la <strong>convocatoria extraordinaria (21-25 de junio)</strong>, <strong>únicamente se recuperan aquellas evaluaciones no superadas</strong>.
            </p>
            <div class="calc-box" style="margin-top: 1rem;">
              1.ª Evaluación → Superada (Se conserva)<br>
              2.ª Evaluación → Superada (Se conserva)<br>
              3.ª Evaluación → No superada (&lt; 5)<br>
              ----------------------------------------<br>
              <strong>En Extraordinaria: Solo te examinas de la 3.ª Eval</strong>
            </div>
          </div>

          <div class="card">
            <div class="card-icon">🗓️</div>
            <h3 class="card-title">Calendario de la Extraordinaria</h3>
            <ul class="card-list">
              <li><strong>Convocatoria Extraordinaria:</strong> Del 21 al 25 de junio de 2027.</li>
              <li><strong>Exámenes extraordinarios:</strong> Se celebrarán aproximadamente <strong>una semana antes</strong> (~14 al 18 de junio).</li>
              <li>Si lo que queda pendiente es el proyecto de la 3.ª evaluación, podrás completarlo, corregirlo y volver a presentarlo.</li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 14: HERRAMIENTAS Y ENTORNO ==================== -->
    <section class="slide" data-title="Herramientas Oficiales">
      <div class="slide-content">
        <div class="slide-tag"><span>🛠️</span> Entorno de Desarrollo</div>
        <h2 class="slide-title">Herramientas y Tecnologías Oficiales</h2>
        <p class="slide-subtitle">
          Trabajamos con el entorno estándar de la industria profesional del desarrollo de software.
        </p>

        <div class="grid-4">
          <div class="card card-highlight">
            <div class="card-icon">⚡</div>
            <h3 class="card-title">IntelliJ IDEA</h3>
            <div style="font-size: 0.72rem; color: var(--primary-light); font-weight: 700; margin-bottom: 0.5rem;">IDE OFICIAL DEL CURSO</div>
            <p class="card-text">Todas las explicaciones, capturas, atajos y sesiones de depuración se realizan exclusivamente en IntelliJ IDEA.</p>
          </div>

          <div class="card">
            <div class="card-icon">☕</div>
            <h3 class="card-title">Java LTS</h3>
            <div style="font-size: 0.72rem; color: var(--text-dim); font-weight: 700; margin-bottom: 0.5rem;">LENGUAJE ESTÁNDAR</div>
            <p class="card-text">Última versión con soporte extendido (OpenJDK / Eclipse Temurin). Sintaxis moderna y APIs oficiales del ecosistema Java.</p>
          </div>

          <div class="card">
            <div class="card-icon">🐙</div>
            <h3 class="card-title">Git & GitHub</h3>
            <div style="font-size: 0.72rem; color: var(--text-dim); font-weight: 700; margin-bottom: 0.5rem;">CONTROL DE VERSIONES</div>
            <p class="card-text">Gestión de repositorios, commits limpios y canal de entrega y revisión del proyecto final integrador.</p>
          </div>

          <div class="card">
            <div class="card-icon">🐬</div>
            <h3 class="card-title">MySQL / MariaDB</h3>
            <div style="font-size: 0.72rem; color: var(--text-dim); font-weight: 700; margin-bottom: 0.5rem;">BASES DE DATOS</div>
            <p class="card-text">Motor relacional para la persistencia mediante JDBC en las unidades de acceso a datos y el proyecto integrador.</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ==================== SLIDE 15: CIERRE Y CONTACTO ==================== -->
    <section class="slide hero-slide" data-title="¡Comenzamos!">
      <div class="slide-content">
        <div class="hero-badge">
          <span>🏁</span> Curso 2026 – 2027 · IES Serra Perenxisa
        </div>
        <h1 class="hero-title" style="font-size: 3.4rem;">¡Bienvenidos a Programación!</h1>
        <p class="hero-sub">
          Estáis a punto de adquirir la habilidad más creativa y demandada del desarrollo de software.<br>
          Profesor: <strong>Carlos Tarazona</strong> (<a href="mailto:c.tarazonatarrega@edu.gva.es" style="color: #4ade80; text-decoration: none;">c.tarazonatarrega@edu.gva.es</a>)
        </p>

        <div style="display: flex; justify-content: center; gap: 1.25rem; margin-bottom: 2.25rem; flex-wrap: wrap;">
          <a href="https://aules.edu.gva.es/semipresencial/course/view.php?id=55721" target="_blank" style="text-decoration: none; background: linear-gradient(135deg, #2d8a4e 0%, #1f5e35 100%); color: #fff; padding: 0.85rem 1.75rem; border-radius: 10px; font-weight: 600; font-size: 1rem; box-shadow: 0 4px 15px rgba(45, 138, 78, 0.4); display: inline-flex; align-items: center; gap: 0.5rem;">
            <span>🌐</span> Acceder a Aules (Curso 55721)
          </a>
          <a href="./index.html" style="text-decoration: none; background: var(--bg-card); border: 1px solid var(--border-color); color: var(--text-main); padding: 0.85rem 1.75rem; border-radius: 10px; font-weight: 600; font-size: 1rem; display: inline-flex; align-items: center; gap: 0.5rem;">
            <span>📚</span> Ver Apuntes Web
          </a>
        </div>

        <div style="font-size: 0.95rem; color: var(--text-dim);">
          ¿Dudas o preguntas iniciales? ¡Es el momento de resolverlas en el foro de Aules o en la primera tutoría colectiva!
        </div>
      </div>
    </section>

  </div>

  <!-- Barra de navegación inferior -->
  <footer id="nav-bar">
    <div class="nav-left">
      <span class="nav-title-short">Programación · 1.º DAM Semipresencial (26-27)</span>
      <span style="color: var(--border-accent);">|</span>
      <span id="current-slide-title" style="color: var(--primary-light); font-weight: 500;">Portada</span>
    </div>

    <div class="nav-controls">
      <button class="btn-nav btn-text" id="btn-menu" title="Índice de diapositivas (M)">
        <span>☰</span> Índice
      </button>
      <button class="btn-nav" id="btn-prev" title="Anterior (←)">‹</button>
      <span class="slide-counter" id="slide-counter">1 / 15</span>
      <button class="btn-nav" id="btn-next" title="Siguiente (→)">›</button>
      <button class="btn-nav" id="btn-fullscreen" title="Pantalla completa (F)">⛶</button>
    </div>
  </footer>

  <!-- Modal / Drawer de índice de diapositivas -->
  <div id="menu-modal">
    <div class="menu-box">
      <div class="menu-header">
        <h3 style="font-size: 1.25rem; font-weight: 700; color: #fff;">Índice de la Presentación</h3>
        <button id="btn-close-menu" style="background: none; border: none; color: var(--text-muted); font-size: 1.5rem; cursor: pointer;">✕</button>
      </div>
      <div class="menu-grid" id="menu-items-container"></div>
    </div>
  </div>

  <script>
    const slides = Array.from(document.querySelectorAll('.slide'));
    const totalSlides = slides.length;
    let currentIndex = 0;

    const progressBar = document.getElementById('progress-bar');
    const slideCounter = document.getElementById('slide-counter');
    const currentSlideTitle = document.getElementById('current-slide-title');
    const btnPrev = document.getElementById('btn-prev');
    const btnNext = document.getElementById('btn-next');
    const btnFullscreen = document.getElementById('btn-fullscreen');
    const btnMenu = document.getElementById('btn-menu');
    const btnCloseMenu = document.getElementById('btn-close-menu');
    const menuModal = document.getElementById('menu-modal');
    const menuContainer = document.getElementById('menu-items-container');

    slides.forEach((slide, idx) => {
      const title = slide.getAttribute('data-title') || `Diapositiva ${idx + 1}`;
      const item = document.createElement('div');
      item.className = `menu-item ${idx === 0 ? 'active' : ''}`;
      item.innerHTML = `<span class="menu-item-num">${String(idx + 1).padStart(2, '0')}</span> <span>${title}</span>`;
      item.addEventListener('click', () => {
        goToSlide(idx);
        closeMenu();
      });
      menuContainer.appendChild(item);
    });

    function updateMenuHighlight() {
      const menuItems = menuContainer.querySelectorAll('.menu-item');
      menuItems.forEach((item, idx) => {
        if (idx === currentIndex) {
          item.classList.add('active');
        } else {
          item.classList.remove('active');
        }
      });
    }

    function showSlide(index) {
      if (index < 0) index = 0;
      if (index >= totalSlides) index = totalSlides - 1;

      slides.forEach((slide, idx) => {
        if (idx === index) {
          slide.classList.add('active');
          slide.scrollTop = 0;
        } else {
          slide.classList.remove('active');
        }
      });

      currentIndex = index;

      const percent = ((index + 1) / totalSlides) * 100;
      progressBar.style.width = `${percent}%`;
      slideCounter.textContent = `${index + 1} / ${totalSlides}`;
      currentSlideTitle.textContent = slides[index].getAttribute('data-title') || '';
      updateMenuHighlight();

      history.replaceState(null, null, `#${index + 1}`);
    }

    function nextSlide() {
      if (currentIndex < totalSlides - 1) {
        showSlide(currentIndex + 1);
      }
    }

    function prevSlide() {
      if (currentIndex > 0) {
        showSlide(currentIndex - 1);
      }
    }

    function goToSlide(idx) {
      showSlide(idx);
    }

    function openMenu() {
      menuModal.classList.add('active');
    }

    function closeMenu() {
      menuModal.classList.remove('active');
    }

    function toggleFullscreen() {
      if (!document.fullscreenElement) {
        document.documentElement.requestFullscreen().catch(() => {});
      } else {
        if (document.exitFullscreen) {
          document.exitFullscreen().catch(() => {});
        }
      }
    }

    btnPrev.addEventListener('click', prevSlide);
    btnNext.addEventListener('click', nextSlide);
    btnFullscreen.addEventListener('click', toggleFullscreen);
    btnMenu.addEventListener('click', openMenu);
    btnCloseMenu.addEventListener('click', closeMenu);
    menuModal.addEventListener('click', (e) => {
      if (e.target === menuModal) closeMenu();
    });

    document.addEventListener('keydown', (e) => {
      if (menuModal.classList.contains('active')) {
        if (e.key === 'Escape') closeMenu();
        return;
      }

      switch (e.key) {
        case 'ArrowRight':
        case 'ArrowDown':
        case ' ':
        case 'PageDown':
          e.preventDefault();
          nextSlide();
          break;
        case 'ArrowLeft':
        case 'ArrowUp':
        case 'PageUp':
          e.preventDefault();
          prevSlide();
          break;
        case 'Home':
          e.preventDefault();
          showSlide(0);
          break;
        case 'End':
          e.preventDefault();
          showSlide(totalSlides - 1);
          break;
        case 'f':
        case 'F':
          toggleFullscreen();
          break;
        case 'm':
        case 'M':
          openMenu();
          break;
      }
    });

    if (window.location.hash) {
      const hashNum = parseInt(window.location.hash.replace('#', ''), 10);
      if (!isNaN(hashNum) && hashNum >= 1 && hashNum <= totalSlides) {
        showSlide(hashNum - 1);
      } else {
        showSlide(0);
      }
    } else {
      showSlide(0);
    }
  </script>
</body>
</html>
''')

with open('public/presentacion.html', 'w', encoding='utf-8') as f:
    f.write(''.join(html_parts))

print('SUCCESS: public/presentacion.html regenerated with 15 optimized slides.')

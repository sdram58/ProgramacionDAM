import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

// https://astro.build/config
export default defineConfig({
  site: 'https://sdram58.github.io',
  base: '/ProgramacionDAM',
  integrations: [
    starlight({
      title: 'Programacion - 1º DAM semipresencial',
      defaultLocale: 'root',
      locales: {
        root: {
          label: 'Castellano',
          lang: 'es',
        },
      },
      customCss: [
        './src/styles/custom.css',
      ],
      sidebar: [
        {
          label: '🚀 01. Introducción a la programación',
          collapsed: true,
          items: [
            { label: '📖 Teoría y Contenidos', link: '/ud01/' },
            {
              label: '📝 Boletines de Ejercicios',
              collapsed: true,
              items: [
                { label: 'Boletín Inicial', link: '/ud01/boletin-01-inicial/' },
                { label: 'Boletín Inicial (Resuelto)', link: '/ud01/boletin-01-inicial-resuelto/' },
                { label: 'Boletín Intermedio', link: '/ud01/boletin-01-intermedio/' },
                { label: 'Boletín Intermedio (Resuelto)', link: '/ud01/boletin-01-intermedio-resuelto/' },
                { label: 'Retos y Extras', link: '/ud01/boletin-01-extras/' },
              ],
            },
          ],
        },
        { label: '🔤 02. Primeros pasos con Java', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '🔀 03. Excepciones, bucles, arrays y métodos', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '🏗️ 04. Clases y objetos', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '🧬 05. Programación orientada a objetos avanzada', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '📚 06. Genéricos y colecciones de datos', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '⚡ 07. La API Stream', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '📁 08. Ficheros y streams', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '🖼️ 09. Aplicaciones controladas por eventos', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '🗄️ 10. Trabajar con bases de datos relacionales', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
        { label: '🌐 11. Trabajar con bases de datos orientadas a objetos', link: '#', badge: { text: 'Próximamente', variant: 'note' }, attrs: { class: 'sidebar-link-disabled' } },
      ],
    }),
  ],
});

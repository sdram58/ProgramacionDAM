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
          label: 'Temario Oficial',
          items: [
            { label: '🚀 01. Introducción a la programación', link: '/ud01/' },
            { label: '🔤 02. Primeros pasos con Java', link: '/ud02/' },
            { label: '🔀 03. Excepciones, bucles, arrays y métodos', link: '/ud03/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🏗️ 04. Clases y objetos', link: '/ud04/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🧬 05. Programación orientada a objetos avanzada', link: '/ud05/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '📚 06. Genéricos y colecciones de datos', link: '/ud06/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '⚡ 07. La API Stream', link: '/ud07/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '📁 08. Ficheros y streams', link: '/ud08/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🖼️ 09. Aplicaciones controladas por eventos', link: '/ud09/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🗄️ 10. Trabajar con bases de datos relacionales', link: '/ud10/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🌐 11. Trabajar con bases de datos orientadas a objetos', link: '/ud11/', badge: { text: 'Próximamente', variant: 'note' } },
          ],
        },
        {
          label: '📝 Boletines de Ejercicios',
          items: [
            {
              label: 'Unidad 01',
              items: [
                { label: 'Boletín Inicial', link: '/ud01/boletin-01-inicial/' },
                { label: 'Boletín Inicial (Resuelto)', link: '/ud01/boletin-01-inicial-resuelto/' },
                { label: 'Boletín Intermedio', link: '/ud01/boletin-01-intermedio/' },
                { label: 'Boletín Intermedio (Resuelto)', link: '/ud01/boletin-01-intermedio-resuelto/' },
                { label: 'Retos y Extras', link: '/ud01/boletin-01-extras/' },
              ],
            },
            {
              label: 'Unidad 02',
              items: [
                { label: 'Boletín Inicial', link: '/ud02/boletin-02-inicial/' },
                { label: 'Boletín Inicial (Resuelto)', link: '/ud02/boletin-02-inicial-resuelto/' },
                { label: 'Boletín Intermedio', link: '/ud02/boletin-02-intermedio/' },
                { label: 'Boletín Intermedio (Resuelto)', link: '/ud02/boletin-02-intermedio-resuelto/' },
                { label: 'Retos y Extras', link: '/ud02/boletin-02-extras/' },
              ],
            },
          ],
        },
      ],
    }),
  ],
});

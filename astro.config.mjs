import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

// https://astro.build/config
export default defineConfig({
  site: 'https://sdram58.github.io',
  base: '/ProgramacionDAM',
  integrations: [
    starlight({
      title: 'Programación Java — DAM',
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
      social: {
        github: 'https://github.com/sdram58/ProgramacionDAM.git',
      },
      sidebar: [
        {
          label: 'Temario Oficial',
          items: [
            { label: '🚀 01. Algorítmica y Pseudocódigo', link: '/ud01/' },
            { label: '🔤 02. Primeros pasos en Java', link: '/ud02/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🔀 03. Control de flujo y excepciones', link: '/ud03/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🧩 04. Métodos y recursividad', link: '/ud04/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '⚡ 05. Clases y Objetos', link: '/ud05/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🏗️ 06. Encapsulación y static', link: '/ud06/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🔒 07. Herencia y Polimorfismo', link: '/ud07/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🧬 08. Arrays y Colecciones lineales', link: '/ud08/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '📚 09. Genéricos y Mapas', link: '/ud09/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🗺️ 10. Programación Funcional y Streams', link: '/ud10/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '📁 11. Ficheros y Expresiones Regulares', link: '/ud11/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🗄️ 12. Programación por Eventos y GUI', link: '/ud12/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🌐 13. Acceso a Datos y JDBC', link: '/ud13/', badge: { text: 'Próximamente', variant: 'note' } },
            { label: '🎯 14. Proyecto Final Integrador', link: '/ud14/', badge: { text: 'Próximamente', variant: 'note' } },
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
          ],
        },
      ],
    }),
  ],
});

/** @type {import('tailwindcss').Config} */
export default {
  content: ["./frontend/index.html", "./frontend/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {},
  },
  plugins: [
    require('@tailwindcss/aspect-ratio')
  ],
  corePlugins: {
    overflowWrap: true
  }
}


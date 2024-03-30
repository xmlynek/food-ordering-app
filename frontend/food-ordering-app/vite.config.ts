import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react({
    include: '**/*.{jsx,tsx}',
  })],
  publicDir: './public',
  envDir: './',
  server: {
    port: 5173,
  }
})

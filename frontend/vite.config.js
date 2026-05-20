import { defineConfig } from 'vite'

export default defineConfig({
  server: {
    host: true,
    port: 5173,
    proxy: {
      '/api/baby': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})

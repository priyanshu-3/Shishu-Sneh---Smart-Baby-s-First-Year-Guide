import { defineConfig } from 'vite'

export default defineConfig({
  server: {
    port: 5173,
    proxy: {
      '/api/baby': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})

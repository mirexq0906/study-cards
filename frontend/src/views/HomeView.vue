<script>
export default {
  name: 'HomeView',
  data() {
    return {
      health: null,
      error: null,
      loading: true,
    }
  },
  async mounted() {
    try {
      const response = await fetch('/api/health')
      if (!response.ok) {
        throw new Error(`HTTP ${response.status}`)
      }
      this.health = await response.json()
    } catch (e) {
      this.error = e instanceof Error ? e.message : 'Unknown error'
    } finally {
      this.loading = false
    }
  },
}
</script>

<template>
  <main>
    <h2>Frontend + Backend</h2>
    <p v-if="loading">Checking backend connection...</p>
    <p v-else-if="error" class="error">Backend unavailable: {{ error }}</p>
    <p v-else class="success">
      Connected to <strong>{{ health.application }}</strong> ({{ health.status }})
    </p>
  </main>
</template>

<style scoped>
.error {
  color: #c0392b;
}

.success {
  color: #2e7d32;
}
</style>

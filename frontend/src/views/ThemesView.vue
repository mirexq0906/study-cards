<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import ProgressSpinner from 'primevue/progressspinner'
import Message from 'primevue/message'
import { getThemes } from '../api/catalog'

const router = useRouter()
const themes = ref([])
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    themes.value = await getThemes()
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Не удалось загрузить темы'
  } finally {
    loading.value = false
  }
})

function openTheme(theme) {
  router.push({ name: 'folders', params: { themeId: theme.id } })
}
</script>

<template>
  <main class="page">
    <header class="page-header">
      <h1 class="page-title">Выберите тему</h1>
      <p class="page-subtitle">
        Начните с темы — дальше откроются связанные папки и карточки для повторения.
      </p>
    </header>

    <div v-if="loading" class="state-block">
      <ProgressSpinner strokeWidth="4" style="width: 48px; height: 48px" />
      <span>Загрузка тем...</span>
    </div>

    <Message v-else-if="error" severity="error" :closable="false">{{ error }}</Message>

    <Message v-else-if="themes.length === 0" severity="info" :closable="false">
      Тем пока нет. Добавьте данные на сервере.
    </Message>

    <div v-else class="selection-grid">
      <button
        v-for="theme in themes"
        :key="theme.id"
        class="selection-item"
        type="button"
        @click="openTheme(theme)"
      >
        <h2>{{ theme.title }}</h2>
        <span class="hint">
          Открыть папки
          <i class="pi pi-arrow-right" />
        </span>
      </button>
    </div>
  </main>
</template>

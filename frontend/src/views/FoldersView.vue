<script setup>
import { onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import Button from 'primevue/button'
import ProgressSpinner from 'primevue/progressspinner'
import Message from 'primevue/message'
import { getFoldersByThemeId } from '../api/catalog'

const props = defineProps({
  themeId: {
    type: [String, Number],
    required: true,
  },
})

const router = useRouter()
const folders = ref([])
const loading = ref(true)
const error = ref(null)

async function load() {
  loading.value = true
  error.value = null

  try {
    folders.value = await getFoldersByThemeId(props.themeId)
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Не удалось загрузить папки'
  } finally {
    loading.value = false
  }
}

function openFolder(folder) {
  router.push({
    name: 'cards',
    params: {
      themeId: props.themeId,
      folderId: folder.id,
    },
  })
}

onMounted(load)
watch(() => props.themeId, load)
</script>

<template>
  <main class="page">
    <header class="page-header">
      <div>
        <Button
          label="Назад к темам"
          icon="pi pi-arrow-left"
          severity="secondary"
          text
          class="back-btn"
          @click="router.push({ name: 'themes' })"
        />
      </div>
      <h1 class="page-title">Выберите папку</h1>
      <p class="page-subtitle">
        В каждой папке — набор карточек для изучения выбранной темы.
      </p>
    </header>

    <div v-if="loading" class="state-block">
      <ProgressSpinner strokeWidth="4" style="width: 48px; height: 48px" />
      <span>Загрузка папок...</span>
    </div>

    <Message v-else-if="error" severity="error" :closable="false">{{ error }}</Message>

    <Message v-else-if="folders.length === 0" severity="info" :closable="false">
      Для этой темы пока нет папок.
    </Message>

    <div v-else class="selection-grid">
      <button
        v-for="folder in folders"
        :key="folder.id"
        class="selection-item"
        type="button"
        @click="openFolder(folder)"
      >
        <h2>{{ folder.title }}</h2>
        <span class="hint">
          Смотреть карточки
          <i class="pi pi-arrow-right" />
        </span>
      </button>
    </div>
  </main>
</template>

<style scoped>
.back-btn {
  margin-left: -0.75rem;
}
</style>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import Button from 'primevue/button'
import Carousel from 'primevue/carousel'
import ProgressSpinner from 'primevue/progressspinner'
import Message from 'primevue/message'
import Tag from 'primevue/tag'
import StudyCard from '../components/StudyCard.vue'
import { getCardsByFolderId, getFoldersByThemeId } from '../api/catalog'

const props = defineProps({
  themeId: {
    type: [String, Number],
    required: true,
  },
  folderId: {
    type: [String, Number],
    required: true,
  },
})

const router = useRouter()
const cards = ref([])
const folders = ref([])
const loading = ref(true)
const error = ref(null)
const page = ref(0)

const folderTitle = computed(() => {
  const folder = folders.value.find((item) => String(item.id) === String(props.folderId))
  return folder?.title || `Папка #${props.folderId}`
})

async function load() {
  loading.value = true
  error.value = null
  page.value = 0

  try {
    const [cardList, folderList] = await Promise.all([
      getCardsByFolderId(props.folderId),
      getFoldersByThemeId(props.themeId),
    ])
    cards.value = cardList
    folders.value = folderList
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Не удалось загрузить карточки'
  } finally {
    loading.value = false
  }
}

onMounted(load)
watch(() => [props.themeId, props.folderId], load)
</script>

<template>
  <main class="page cards-page">
    <header class="page-header">
      <div>
        <Button
          label="Назад к папкам"
          icon="pi pi-arrow-left"
          severity="secondary"
          text
          class="back-btn"
          @click="router.push({ name: 'folders', params: { themeId } })"
        />
      </div>
      <div class="title-row">
        <h1 class="page-title">{{ folderTitle }}</h1>
        <Tag
          v-if="!loading && cards.length"
          :value="`${page + 1} / ${cards.length}`"
          severity="secondary"
          rounded
        />
      </div>
      <p class="page-subtitle">
        Листайте карточки и нажимайте на них, чтобы увидеть ответ и пример кода.
      </p>
    </header>

    <div v-if="loading" class="state-block">
      <ProgressSpinner strokeWidth="4" style="width: 48px; height: 48px" />
      <span>Загрузка карточек...</span>
    </div>

    <Message v-else-if="error" severity="error" :closable="false">{{ error }}</Message>

    <Message v-else-if="cards.length === 0" severity="info" :closable="false">
      В этой папке пока нет карточек.
    </Message>

    <section v-else class="carousel-wrap">
      <Carousel
        v-model:page="page"
        :value="cards"
        :num-visible="1"
        :num-scroll="1"
        :circular="false"
        :show-indicators="cards.length > 1"
        :show-navigators="cards.length > 1"
      >
        <template #item="slotProps">
          <div class="slide">
            <StudyCard :card="slotProps.data" />
          </div>
        </template>
      </Carousel>
    </section>
  </main>
</template>

<style scoped>
.back-btn {
  margin-left: -0.75rem;
}

.title-row {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
}

.carousel-wrap {
  animation: rise 0.45s ease-out both;
}

.slide {
  padding: 0.5rem 0.75rem 1rem;
}

.cards-page :deep(.p-carousel-content) {
  gap: 0.5rem;
}

.cards-page :deep(.p-carousel-prev-button),
.cards-page :deep(.p-carousel-next-button) {
  width: 2.75rem;
  height: 2.75rem;
  background: rgba(255, 255, 255, 0.85);
  border: 1px solid rgba(15, 118, 110, 0.15);
  box-shadow: 0 10px 24px rgba(15, 61, 52, 0.1);
}

.cards-page :deep(.p-carousel-indicator-button) {
  width: 0.65rem;
  height: 0.65rem;
  background: rgba(15, 118, 110, 0.25);
}

.cards-page :deep(.p-carousel-indicator-active .p-carousel-indicator-button) {
  background: #0f766e;
}

@keyframes rise {
  from {
    opacity: 0;
    transform: translateY(12px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 640px) {
  .slide {
    padding-inline: 0.15rem;
  }

  .cards-page :deep(.p-carousel-prev-button),
  .cards-page :deep(.p-carousel-next-button) {
    width: 2.35rem;
    height: 2.35rem;
  }
}
</style>

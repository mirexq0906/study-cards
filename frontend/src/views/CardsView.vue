<script setup>
import { computed, nextTick, onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import Button from 'primevue/button'
import Carousel from 'primevue/carousel'
import ProgressSpinner from 'primevue/progressspinner'
import Message from 'primevue/message'
import Tag from 'primevue/tag'
import ToggleSwitch from 'primevue/toggleswitch'
import StudyCard from '../components/StudyCard.vue'
import { getCardsByFolderId, getFoldersByThemeId } from '../api/catalog'
import { useFavorites } from '../composables/useFavorites'
import { useProgress } from '../composables/useProgress'

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
const { isFavorite, toggleFavorite } = useFavorites()
const { getProgress, saveProgress, findPage } = useProgress()
const cards = ref([])
const folders = ref([])
const loading = ref(true)
const error = ref(null)
const page = ref(0)
const studyFavoritesOnly = ref(false)
const ready = ref(false)
const lastCardId = ref(null)

const visibleCards = computed(() => {
  if (!studyFavoritesOnly.value) {
    return cards.value
  }

  return cards.value.filter((card) => isFavorite(card.id))
})

const currentCardId = computed(() => visibleCards.value[page.value]?.id ?? null)

const folderTitle = computed(() => {
  const folder = folders.value.find((item) => String(item.id) === String(props.folderId))
  return folder?.title || `Папка #${props.folderId}`
})

function persistProgress() {
  if (!ready.value) {
    return
  }

  saveProgress(props.folderId, {
    cardId: currentCardId.value ?? lastCardId.value,
    favoritesOnly: studyFavoritesOnly.value,
  })
}

function restoreProgress(cardList) {
  const saved = getProgress(props.folderId)
  studyFavoritesOnly.value = saved.favoritesOnly
  const list = saved.favoritesOnly
    ? cardList.filter((card) => isFavorite(card.id))
    : cardList
  const restoredPage = findPage(list, saved.cardId)
  page.value = restoredPage
  lastCardId.value = list[restoredPage]?.id ?? saved.cardId
  return restoredPage
}

async function load() {
  ready.value = false
  loading.value = true
  error.value = null

  try {
    const [cardList, folderList] = await Promise.all([
      getCardsByFolderId(props.folderId),
      getFoldersByThemeId(props.themeId),
    ])
    cards.value = cardList
    folders.value = folderList
    const restoredPage = restoreProgress(cardList)
    loading.value = false
    await nextTick()
    if (page.value !== restoredPage) {
      page.value = restoredPage
    }
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Не удалось загрузить карточки'
    loading.value = false
  } finally {
    ready.value = true
  }
}

onMounted(load)
watch(() => [props.themeId, props.folderId], load)
watch(studyFavoritesOnly, () => {
  if (!ready.value) {
    return
  }

  page.value = findPage(visibleCards.value, lastCardId.value)
  persistProgress()
})
watch(currentCardId, (cardId) => {
  if (cardId != null) {
    lastCardId.value = cardId
  }

  persistProgress()
})
watch(
  () => visibleCards.value.length,
  (length) => {
    if (page.value >= length) {
      page.value = Math.max(0, length - 1)
    }
  },
)
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
      <h1 class="page-title">{{ folderTitle }}</h1>
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

    <template v-else>
      <div class="favorites-filter">
        <ToggleSwitch inputId="favorites-only" v-model="studyFavoritesOnly" />
        <label for="favorites-only">Изучать только карточки с ★</label>
      </div>

      <Message v-if="visibleCards.length === 0" severity="info" :closable="false">
        В этой папке нет избранных карточек. Отметьте карточку звёздочкой, чтобы она появилась здесь.
      </Message>

      <section v-else class="carousel-wrap">
        <Carousel
          v-model:page="page"
          :value="visibleCards"
          :num-visible="1"
          :num-scroll="1"
          :circular="false"
          :show-indicators="false"
          :show-navigators="visibleCards.length > 1"
        >
          <template #item="slotProps">
            <div class="slide">
              <StudyCard
                :card="slotProps.data"
                :favorite="isFavorite(slotProps.data.id)"
                @toggle-favorite="toggleFavorite(slotProps.data.id)"
              />
            </div>
          </template>
        </Carousel>
        <div class="carousel-counter">
          <Tag :value="`${page + 1} / ${visibleCards.length}`" severity="secondary" rounded />
        </div>
      </section>
    </template>
  </main>
</template>

<style scoped>
.back-btn {
  margin-left: -0.75rem;
}

.favorites-filter {
  display: inline-flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 1.25rem;
  color: var(--sc-ink);
  font-size: 1rem;
  font-weight: 600;
  user-select: none;
}

.favorites-filter label {
  cursor: pointer;
}

.carousel-wrap {
  animation: rise 0.45s ease-out both;
}

.carousel-counter {
  display: flex;
  justify-content: center;
  margin-top: 0.5rem;
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

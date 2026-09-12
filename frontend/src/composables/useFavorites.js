import { computed, ref } from 'vue'

const STORAGE_KEY = 'studycards.favorites'

function readStoredIds() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    const parsed = raw ? JSON.parse(raw) : []
    return Array.isArray(parsed) ? parsed.map(String) : []
  } catch {
    return []
  }
}

const favoriteIds = ref(new Set(readStoredIds()))

function persist(ids) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify([...ids]))
}

export function useFavorites() {
  const hasFavorites = computed(() => favoriteIds.value.size > 0)

  function isFavorite(cardId) {
    return favoriteIds.value.has(String(cardId))
  }

  function toggleFavorite(cardId) {
    const key = String(cardId)
    const next = new Set(favoriteIds.value)

    if (next.has(key)) {
      next.delete(key)
    } else {
      next.add(key)
    }

    favoriteIds.value = next
    persist(next)
  }

  return {
    favoriteIds,
    hasFavorites,
    isFavorite,
    toggleFavorite,
  }
}

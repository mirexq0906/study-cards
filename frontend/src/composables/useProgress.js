import { ref } from 'vue'

const STORAGE_KEY = 'studycards.progress'

function readProgress() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    const parsed = raw ? JSON.parse(raw) : {}
    return parsed && typeof parsed === 'object' && !Array.isArray(parsed) ? parsed : {}
  } catch {
    return {}
  }
}

const progressByFolder = ref(readProgress())

function persist(next) {
  progressByFolder.value = next
  localStorage.setItem(STORAGE_KEY, JSON.stringify(next))
}

export function useProgress() {
  function getProgress(folderId) {
    const saved = progressByFolder.value[String(folderId)]

    if (!saved || typeof saved !== 'object') {
      return { cardId: null, favoritesOnly: false }
    }

    return {
      cardId: saved.cardId != null ? String(saved.cardId) : null,
      favoritesOnly: Boolean(saved.favoritesOnly),
    }
  }

  function saveProgress(folderId, { cardId, favoritesOnly }) {
    persist({
      ...progressByFolder.value,
      [String(folderId)]: {
        cardId: cardId != null ? String(cardId) : null,
        favoritesOnly: Boolean(favoritesOnly),
      },
    })
  }

  function findPage(cards, cardId) {
    if (cardId == null || !cards.length) {
      return 0
    }

    const index = cards.findIndex((card) => String(card.id) === String(cardId))
    return index >= 0 ? index : 0
  }

  return {
    getProgress,
    saveProgress,
    findPage,
  }
}

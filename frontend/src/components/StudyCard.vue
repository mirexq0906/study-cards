<script setup>
import {computed, ref, watch} from 'vue'
import Prism from 'prismjs'
import 'prismjs/components/prism-java'
import 'prismjs/components/prism-sql'
import 'prismjs/components/prism-javascript'

const props = defineProps({
  card: {
    type: Object,
    required: true,
  },
  favorite: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['toggle-favorite'])

const flipped = ref(false)

const language = computed(() => detectLanguage(props.card.code))

const highlightedCode = computed(() => {
  if (!props.card.code) {
    return ''
  }

  const lang = language.value
  const grammar = Prism.languages[lang] || Prism.languages.javascript
  return Prism.highlight(props.card.code, grammar, lang)
})

function detectLanguage(code) {
  if (!code) {
    return 'javascript'
  }

  if (/\b(SELECT|INSERT|UPDATE|DELETE|FROM|WHERE)\b/i.test(code)) {
    return 'sql'
  }

  if (/\b(public\s+class|System\.out|@\w+|List<)/.test(code)) {
    return 'java'
  }

  return 'javascript'
}

function toggleFlip() {
  flipped.value = !flipped.value
}

function onFavoriteClick() {
  emit('toggle-favorite')
}

function onCardKeydown(event) {
  if (event.key === 'Enter' || event.key === ' ') {
    event.preventDefault()
    toggleFlip()
  }
}

watch(
  () => props.card.id,
  () => {
    flipped.value = false
  },
)
</script>

<template>
  <div
    class="study-card"
    :class="{ flipped }"
    role="button"
    tabindex="0"
    :aria-pressed="flipped"
    @click="toggleFlip"
    @keydown="onCardKeydown"
  >
    <div class="study-card-inner">
      <div class="face face-front">
        <div class="face-top">
          <p class="face-label">Вопрос</p>
          <button
            class="favorite-btn"
            type="button"
            :class="{ 'is-on': favorite }"
            :aria-pressed="favorite"
            :aria-hidden="flipped"
            :tabindex="flipped ? -1 : 0"
            :aria-label="favorite ? 'Убрать из избранного' : 'Добавить в избранное'"
            @click.stop="onFavoriteClick"
          >
            <i class="pi" :class="favorite ? 'pi-star-fill' : 'pi-star'" />
          </button>
        </div>
        <h3>{{ card.title }}</h3>
        <p class="face-hint">Нажмите, чтобы перевернуть</p>
      </div>

      <div class="face face-back">
        <div class="face-top">
          <p class="face-label">Ответ</p>
          <button
            class="favorite-btn"
            type="button"
            :class="{ 'is-on': favorite }"
            :aria-pressed="favorite"
            :aria-hidden="!flipped"
            :tabindex="flipped ? 0 : -1"
            :aria-label="favorite ? 'Убрать из избранного' : 'Добавить в избранное'"
            @click.stop="onFavoriteClick"
          >
            <i class="pi" :class="favorite ? 'pi-star-fill' : 'pi-star'" />
          </button>
        </div>
        <div class="face-content">
          <div v-if="card.description" class="description">{{ card.description }}</div>
          <pre v-if="card.code" class="code-block"><code :class="`language-${language}`"
                                                         v-html="highlightedCode"/></pre>
        </div>
        <p class="face-hint">Нажмите, чтобы вернуть вопрос</p>
      </div>
    </div>
  </div>
</template>


<style scoped>
.face-top {
  display: grid;
  grid-template-columns: 2.5rem 1fr 2.5rem;
  align-items: center;
}

.face-top .face-label {
  grid-column: 2;
  text-align: center;
}

.favorite-btn {
  grid-column: 3;
  display: grid;
  place-items: center;
  width: 2.5rem;
  height: 2.5rem;
  padding: 0;
  border: 0;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.88);
  color: #b7a056;
  box-shadow: 0 8px 18px rgba(15, 61, 52, 0.1);
  cursor: pointer;
  transition:
    transform 0.18s ease,
    color 0.18s ease,
    background 0.18s ease;
}

.favorite-btn i {
  font-size: 1.15rem;
}

.favorite-btn.is-on {
  color: #eab308;
}

.favorite-btn:hover,
.favorite-btn:focus-visible {
  transform: scale(1.08);
  background: #fff;
  outline: none;
}

.study-card {
  display: block;
  width: min(100%, 700px);
  margin: 0 auto;
  padding: 0;
  border: 0;
  background: transparent;
  perspective: 1400px;
  cursor: pointer;
  text-align: left;
}

.study-card-inner {
  position: relative;
  min-height: 380px;
  transform-style: preserve-3d;
  transition: transform 0.55s cubic-bezier(0.22, 1, 0.36, 1);
}

.study-card.flipped .study-card-inner {
  transform: rotateY(180deg);
}

.face {
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1.75rem;
  border-radius: 1.5rem;
  border: 1px solid rgba(15, 118, 110, 0.14);
  background: linear-gradient(165deg, rgba(255, 255, 255, 0.95), rgba(236, 253, 245, 0.92));
  box-shadow: 0 24px 50px rgba(15, 61, 52, 0.14);
  backface-visibility: hidden;
  overflow: hidden;
}

.face-front {
  display: grid;
  grid-template-rows: auto 1fr auto;
  align-items: center;
  text-align: center;
}

.face-back {
  transform: rotateY(180deg);
}

.face-back > .face-top,
.face-back > .face-hint {
  flex-shrink: 0;
}

.face-back > .face-hint {
  text-align: center;
}

.face-content {
  display: flex;
  min-height: 0;
  flex: 1;
  flex-direction: column;
  gap: 1rem;
  overflow-y: auto;
}

.face-label {
  margin: 0;
  color: #0f766e;
  font-size: 0.8rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.face-front h3 {
  margin: 0;
  font-size: clamp(1.35rem, 3.6vw, 1.9rem);
  line-height: 1.25;
  letter-spacing: -0.03em;
}

.description {
  margin: 0;
  color: #274841;
  font-size: 1.05rem;
  line-height: 1.6;
  white-space: pre-wrap;
}

.code-block {
  flex-shrink: 0;
  margin: 0;
  padding: 1rem 1.1rem;
  border-radius: 0.9rem;
  background: #1e293b;
  overflow-x: auto;
  overflow-y: hidden;
}

.code-block code {
  font-family: var(--sc-mono);
  font-size: 0.88rem;
  line-height: 1.55;
  white-space: pre;
}

.face-hint {
  margin-top: 0;
  margin-bottom: 0;
  color: #6b857e;
  font-size: 0.9rem;
}

@media (max-width: 640px) {
  .study-card-inner {
    min-height: 340px;
  }

  .face {
    padding: 1.25rem;
  }
}
</style>

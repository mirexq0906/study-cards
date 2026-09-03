<script setup>
import { computed, ref, watch } from 'vue'
import Prism from 'prismjs'
import 'prismjs/components/prism-java'
import 'prismjs/components/prism-sql'
import 'prismjs/components/prism-javascript'

const props = defineProps({
  card: {
    type: Object,
    required: true,
  },
})

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

watch(
  () => props.card.id,
  () => {
    flipped.value = false
  },
)
</script>

<template>
  <button
    class="study-card"
    type="button"
    :class="{ flipped }"
    :aria-pressed="flipped"
    @click="toggleFlip"
  >
    <div class="study-card-inner">
      <div class="face face-front">
        <p class="face-label">Вопрос</p>
        <h3>{{ card.title }}</h3>
        <p class="face-hint">Нажмите, чтобы перевернуть</p>
      </div>

      <div class="face face-back">
        <p class="face-label">Ответ</p>
        <p v-if="card.description" class="description">{{ card.description }}</p>
        <pre v-if="card.code" class="code-block"><code :class="`language-${language}`" v-html="highlightedCode" /></pre>
        <p class="face-hint">Нажмите, чтобы вернуть вопрос</p>
      </div>
    </div>
  </button>
</template>


<style scoped>
.study-card {
  display: block;
  width: min(100%, 640px);
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
  background:
    linear-gradient(165deg, rgba(255, 255, 255, 0.95), rgba(236, 253, 245, 0.92));
  box-shadow: 0 24px 50px rgba(15, 61, 52, 0.14);
  backface-visibility: hidden;
  overflow: auto;
}

.face-front {
  justify-content: center;
  align-items: center;
  text-align: center;
}

.face-back {
  transform: rotateY(180deg);
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
  max-width: 18ch;
  font-size: clamp(1.5rem, 4vw, 2.1rem);
  line-height: 1.2;
  letter-spacing: -0.03em;
}

.description {
  margin: 0;
  color: #274841;
  font-size: 1.05rem;
  line-height: 1.55;
}

.code-block {
  margin: 0;
  padding: 1rem 1.1rem;
  border-radius: 0.9rem;
  background: #1e293b;
  overflow-x: auto;
}

.code-block code {
  font-family: var(--sc-mono);
  font-size: 0.88rem;
  line-height: 1.55;
  white-space: pre;
}

.face-hint {
  margin-top: auto;
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

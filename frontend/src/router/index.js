import { createRouter, createWebHistory } from 'vue-router'
import ThemesView from '../views/ThemesView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'themes',
      component: ThemesView,
    },
    {
      path: '/themes/:themeId/folders',
      name: 'folders',
      component: () => import('../views/FoldersView.vue'),
      props: true,
    },
    {
      path: '/themes/:themeId/folders/:folderId/cards',
      name: 'cards',
      component: () => import('../views/CardsView.vue'),
      props: true,
    },
  ],
  scrollBehavior() {
    return { top: 0 }
  },
})

export default router

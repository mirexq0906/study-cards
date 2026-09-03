export async function getThemes() {
  return request('/api/themes')
}

export async function getFoldersByThemeId(themeId) {
  return request(`/api/folders?themeId=${themeId}`)
}

export async function getCardsByFolderId(folderId) {
  return request(`/api/cards?folderId=${folderId}`)
}

async function request(url) {
  const response = await fetch(url)

  if (!response.ok) {
    const errorBody = await response.json().catch(() => null)
    const message = errorBody?.message || `Ошибка запроса (${response.status})`
    throw new Error(message)
  }

  return response.json()
}

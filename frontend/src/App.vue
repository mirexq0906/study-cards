<script>
export default {
  name: 'App',
}
</script>

<template>
  <div class="app">
    <header>
      <h1>StudyCards</h1>
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </header>

    <RouterView />
  </div>
</template>

<style scoped>
.app {
  max-width: 720px;
  margin: 0 auto;
  padding: 2rem 1rem;
  font-family: system-ui, sans-serif;
}

header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 2rem;
}

h1 {
  margin: 0;
  font-size: 1.5rem;
}

nav {
  display: flex;
  gap: 1rem;
}

nav a {
  color: #42b883;
  text-decoration: none;
}

nav a.router-link-exact-active {
  font-weight: 600;
  text-decoration: underline;
}
</style>

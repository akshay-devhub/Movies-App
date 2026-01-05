<h1 align="center">🎬 Movies Hub</h1>

<p>
  <strong>Movies Hub</strong> is a modern Android application built using
  <strong>Jetpack Compose</strong> and the <strong>TMDB API</strong>,
  following <strong>Clean Architecture</strong> and <strong>MVI</strong> principles.
  The app delivers a smooth, scalable, and offline-capable movie browsing experience.
</p>

<hr/>

<h2>✨ Overview</h2>
<p>
  Movies Hub allows users to explore multiple movie categories, view detailed
  movie information, search for movies, and manage a personalized favorites list.
  The application is designed using modern Android best practices with a strong
  focus on performance, maintainability, and scalability.
</p>

<hr/>

<h2>🚀 Key Features</h2>
<ul>
  <li>Splash Screen</li>
  <li>Intro / Onboarding Screen</li>
  <li>Home Screen with categorized movies:
    <ul>
      <li>Trending</li>
      <li>Now Playing</li>
    </ul>
  </li>
  <li>Movie Details Screen</li>
  <li>Search Screen for movies</li>
  <li>Favorites Screen (stored locally)</li>
  <li>Offline movie caching using Room Database</li>
  <li>Movie sharing using Deep linking</li>
  <li>Pagination using Paging 3 (RemoteMediator + PagingSource)</li>
</ul>

<hr/>

<h2>🆕 No Internet Handling & Data Sync</h2>
<ul>
  <li>Real-time network connectivity detection</li>
  <li>Displays cached movie data when offline</li>
  <li>Retry mechanism when network is unavailable</li>
  <li>Automatic data synchronization when internet is restored</li>
  <li>Prevents UI flicker and unnecessary reloads</li>
</ul>

<hr/>

<h2>🧠 Architecture</h2>
<ul>
  <li><strong>Presentation Layer</strong> – Jetpack Compose UI, ViewModels, MVI</li>
  <li><strong>Domain Layer</strong> – Use cases and business logic</li>
  <li><strong>Data Layer</strong> – Remote API, Room database, repositories</li>
</ul>

<hr/>

<h2>🛠️ Technologies Used</h2>
<ul>
  <li><strong>Language:</strong> Kotlin</li>
  <li><strong>UI:</strong> Jetpack Compose</li>
  <li><strong>Architecture:</strong> Clean Architecture + MVI</li>
  <li><strong>Navigation:</strong> Navigation Compose</li>
  <li><strong>State Management:</strong> StateFlow / Flow</li>
  <li><strong>Networking:</strong> Retrofit + Gson</li>
  <li><strong>Pagination:</strong> Paging 3</li>
  <li><strong>Local Storage:</strong> Room Database</li>
  <li><strong>Dependency Injection:</strong> Dagger Hilt</li>
  <li><strong>Concurrency:</strong> Kotlin Coroutines</li>
  <li><strong>Image Loading:</strong> Coil (Compose)</li>
  <li><strong>Testing:</strong> Unit Testing, MockK</li>
</ul>

<hr/>

<h2>📦 Data Handling</h2>
<ul>
  <li>Movies fetched from TMDB API</li>
  <li>Local caching using Room</li>
  <li>Paging ensures smooth scrolling and optimized memory usage</li>
  <li>Offline data reuse</li>
  <li>Automatic sync on network recovery</li>
</ul>

<hr/>

<h2>🧪 Testing</h2>
<ul>
  <li>Unit tests for ViewModels and UseCases</li>
  <li>Mocking using MockK</li>
  <li>Predictable state handling with MVI</li>
</ul>

<hr/>

<h2>📌 Why Movies Hub?</h2>
<ul>
  <li>Fully declarative UI with Jetpack Compose</li>
  <li>Offline-first architecture</li>
  <li>Scalable and maintainable codebase</li>
  <li>Modern Android development best practices</li>
  <li>Designed for real-world large datasets</li>
</ul>

<hr/>

<p align="center">
  <strong>Built with ❤️ using Jetpack Compose</strong>
</p>

<p align="center">
  ⭐ If you find <strong>Movies Hub</strong> useful, consider starring the repository.
</p>

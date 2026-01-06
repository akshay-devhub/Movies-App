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
 <li>Shimmer for Appealing User Experience</li>
  <li>Offline movie caching using Room Database</li>
  <li>Movie sharing using Deep linking</li>
  <li>Pagination using Paging 3 (RemoteMediator + PagingSource)</li>

</ul>

<hr/>
<p align="center">
  <a href="https://github.com/user-attachments/assets/abc819b8-3db0-474b-8c6c-8f7bda3f4dd0">
    <img src="https://github.com/user-attachments/assets/abc819b8-3db0-474b-8c6c-8f7bda3f4dd0" width="180"/>
  </a>
  <a href="https://github.com/user-attachments/assets/c3958dbf-a163-49b0-8401-5081f6c69a0d">
    <img src="https://github.com/user-attachments/assets/c3958dbf-a163-49b0-8401-5081f6c69a0d" width="180"/>
  </a>
  <a href="https://github.com/user-attachments/assets/831ae2c8-ea8b-4735-9ce9-07b57aceb35f">
    <img src="https://github.com/user-attachments/assets/831ae2c8-ea8b-4735-9ce9-07b57aceb35f" width="180"/>
  </a>
</p>

<p align="center">
  <a href="https://github.com/user-attachments/assets/6ca022e7-8231-4b09-b116-3a138e291026">
    <img src="https://github.com/user-attachments/assets/6ca022e7-8231-4b09-b116-3a138e291026" width="180"/>
  </a>
  <a href="https://github.com/user-attachments/assets/bcd2c89d-4b8e-4ed2-aa6e-30f472eea1cd">
    <img src="https://github.com/user-attachments/assets/bcd2c89d-4b8e-4ed2-aa6e-30f472eea1cd" width="180"/>
  </a>
  <a href="https://github.com/user-attachments/assets/53e0e6f2-60fb-40b9-9c18-9d12bfd17edb">
    <img src="https://github.com/user-attachments/assets/53e0e6f2-60fb-40b9-9c18-9d12bfd17edb" width="180"/>
  </a>
</p>

<p align="center">
  <a href="https://github.com/user-attachments/assets/f22395f0-02c7-4430-8490-69fe6ae095e7">
    <img src="https://github.com/user-attachments/assets/f22395f0-02c7-4430-8490-69fe6ae095e7" width="180"/>
  </a>
  <a href="https://github.com/user-attachments/assets/e55af1e5-d982-4576-80ba-06f4a7477931">
    <img src="https://github.com/user-attachments/assets/e55af1e5-d982-4576-80ba-06f4a7477931" width="180"/>
  </a>
  <a href="https://github.com/user-attachments/assets/cf3c41a8-cea0-40f2-8e7c-132933721c65">
    <img src="https://github.com/user-attachments/assets/cf3c41a8-cea0-40f2-8e7c-132933721c65" width="180"/>
  </a>
</p>

<p align="center">
  <a href="https://github.com/user-attachments/assets/a269dcd3-b2ac-4307-89cd-764e9456f3e9">
    <img src="https://github.com/user-attachments/assets/a269dcd3-b2ac-4307-89cd-764e9456f3e9" width="180"/>
  </a>
  <a href="https://github.com/user-attachments/assets/1e65ac8d-6b90-47bc-95e5-2c4a16d2e7f5">
    <img src="https://github.com/user-attachments/assets/1e65ac8d-6b90-47bc-95e5-2c4a16d2e7f5" width="180"/>
  </a>
</p>


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

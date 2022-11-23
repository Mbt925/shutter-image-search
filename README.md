# Shutter Image Search
A sample Android app for searching images via ShutterStock API. The app is built using compose with clean architecture in mind.

- The app has a minimal material UI. The searching functionality works without any confirmation - type something and wait for two seconds.
- You should also expect a infinite scrolling.
- In case of network connection failure or other known failures, the UI would propose a try again call to action, together with the respective error.


## Run the App
First, you need to add your ShutterStock API key in `API_TOKEN` in `local.properties` for basic authentication.


## Architecture
- The app follows UDF (unidirectional data flow), using kotlin flow.
- It also respects separation of concerns, having data, feature, and ui layers.
- The architecture is based on MVVM + clean architecture. 


### Layers
- `Networking`: Is in charge of network communication, including headers interception, and generic response handling. All responses are translated to an ApiResponse object, with well-defined success and error cases.
- `Service`: Provides feature-specific networking service - namely, fetching images - using the networking module. Ideally, every feature should have a respective service module. However, connected features can share a single service module.
- `Design`: Implements the design language, including theming and dimensions.
- `Feature`: Ideally, a feature module includes only a single feature which is a set of connected functionalities by definition. In case of the current app, the app module contains the feature, for the sake of keeping the app as simple as possible.
- `Plugins`: Provides version catalogs. It could also contain other plugins, e.g., lint, build, etc.


## Libraries
- [Retrofit](https://square.github.io/retrofit): A REST API communication framework
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html): A asynchronous or non-blocking programming framework
- [OkHttp](https://square.github.io/okhttp): A HTTP client
- [Koin](https://github.com/InsertKoinIO/koin) A lightweight dependency injection (service locator) framework
- [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html): A JSON serialization library
- [Paging](https://developer.android.com/jetpack/androidx/releases/paging): A data pagination loading library which loads the data gradually and gracefully
- [Mockk](https://mockk.io): A mocking framework
- [Coil](https://coil-kt.github.io/coil): An image loading library backed by kotlin coroutines
- [JetPack Compose](https://developer.android.com/jetpack/compose): Google's recommended declarative UI framework for building native UI


## Future Improvements
- Extract the feature - searching images - from the app module. With that, there won't be any business logic inside the app module.
- For simplicity, the app module has been kept in charge of the feature. But ideally, a feature module should be an isolated set of functionalities that can run independently, e.g. as a demo. A feature module wraps all the required layers to make a feature function as expected, e.g., UI, domain, data.
- All layers must be fully covered with unit/ui tests. Only, a few tests are added in each layers for demonstration purposes.
- A plugin can be useful for aggregating the `build.gradle` boilerplate scripts.
- Github CI/CD integration must be set up.

# KoinGitList

Koin important keywords/functions
- startKoin : Create and register KoinApplication instance
- modules : Declare used modules
- androidContext : Use given Android context
- by inject() : allows to retrieve instances lazily
- get() : function to retrieve directly an instance (non lazy)
- koinComponent : For using koin features, tag the class with the same to get access to koin functions

Koin Scopes
- single : creates an object that persistent with the entire container lifetime
- factory : creates new object each time. No persistence in container
- scoped: creates object that persist to associated scope lifetime

 link to tutorial
https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150

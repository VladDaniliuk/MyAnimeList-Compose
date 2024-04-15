# TrackAnimeList

Android application for tracking animes and mangas.

## Modules

```mermaid
flowchart TD
    app:trackanimelist --> feature:trackanimelist:ui & core:network
    app:trackanimelist ----> core:ui
    feature:trackanimelist:ui --> feature:trackanimelist:domain
    feature:trackanimelist:ui ---> core:network
    feature:trackanimelist:ui ----> feature:trackanimelist:core & core:ui
    feature:trackanimelist:domain ---> feature:trackanimelist:core
    feature:trackanimelist:domain --> feature:trackanimelist:data & core:network
    feature:trackanimelist:data ---> feature:trackanimelist:core & core:network
    core:ui --> core:network & feature:settings:domain & feature:settings:core
    feature:settings:domain --> feature:settings:data & feature:settings:core
    feature:settings:data --> feature:settings:core
```

## Links

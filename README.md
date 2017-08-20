## Scope driven layered architecture

- This repository is rough prototype for my idea
- `EventRegister` screen is a kind of input form feature, build with multiple screen
- By defining `scope`, assign appropriate lifecycle to each data and make secure for Activity re-creation

[preview](./gif/gifan.gif)

## Points of this approach

Two points are important

(1) How to define scope & dependency graph

(2) How to manage them to realize appropriate lifecycle

### Define Scopes based on data lifecycle

1. `@AppScope` : from app launch to its end
    - auth info...etc (but in this example, no data has `@AppScope` for simpleness)
2. `@EventRegisterScope` : from starting EventRegister to apply event or closed
    - model data for event register
    - view state (which step is active) in EventRegister
3. `@EventRegisterStepScope` : from opening EventRegister's one step to its closing
    - ViewModel of EventRegister top / Description form / Prefecture select views


(To be updated)
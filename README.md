# multiplayer-client-prediction

A Client Prediction multiplayer game model.

Let `GameLogic` be an implementation of the game's working logic, and let `lc` and `ls` be instances of the game logic from the client and server respectively. Then, upon any input, the client will execute this input with `lc` and send that input to the server, which will simulate the same input with `ls`.
The server will then periodically (every ~33 ms in this code) send every client the game state (including player positions, velocities, states, etc.) as determined by `ls`. Then the client will 'replace' its own game state with the server's. This is called reconciliation. In between the reconciliations, `lc` is still running which allows the client to render its current game state (essentially predicting the next game state based on the previous reconciliation).

### TODO: Create a diagram for this
### TODO: Implement a Frame-Buffer Reconciliation

# Running-Tracker
App that helps user to track his runs and shows running stats.

## Screnshots
![running-mockup](https://user-images.githubusercontent.com/84252625/192082558-fcb470b3-4e73-40e7-b34a-0a9446c5e4c7.jpg)

## Built with
- Kotlin as programming language.
- Room to save runs in local database.
- Coroutienes to avoid long running tasks being excuted in the main thread.
- GoogleMaps to track the run and draw running path on the screen.
- ForegroundService to keep the application running while it's in the background.
- DaggerHilt for dependecy injection.
- LiveData which notifies views of any database changes in an observer way.
- MVVM as an architecture pattern.

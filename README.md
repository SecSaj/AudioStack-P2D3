# AudioStack 

**Team:** Sajjad, Ian, Emma, Luci

AudioStack is a Java playlist manager for songs, podcasts, and audiobooks. All three media types use the same generic `LinkedChain<MediaItem>` for playlist storage.

## Run in Eclipse

1. Open the project in Eclipse using **Java 17**.
2. Make sure **JUnit 5** is on the build path.
3. Right-click `src/audiostack/AudioStackApp.java` → **Run As → Java Application**.
4. To run all tests, right-click the `audiostack` package under `src` → **Run As → JUnit Test**.
5. If the project does not import directly into Eclipse, create a new Java 17 project, copy the src/audiostack files into it, and add JUnit 5 to the build path before running the tests.

## System Diagram

<img width="1225" height="1284" alt="system-diagram" src="https://github.com/user-attachments/assets/0c094b76-53d4-4f0b-9644-27718df5f5f8" />

## Attribution

| Class or test file | Team member(s) | Contribution |
|---|---|---|
| `LinkedChain.java` | Ian, Luci | Instructor-provided starter; chain design and index-removal review |
| `MediaItem.java` | Luci, Ian | Shared media model and equality review |
| `Song.java` | Luci, Emma | Song model design and review |
| `Podcast.java` | Luci, Emma | Podcast model design and validation review |
| `Audiobook.java` | Sajjad | Added audiobook functionality for D4; GenAI-assisted |
| `Playlist.java` | Ian, Emma; Sajjad (D4) | Playlist design and review; D4 updates integrated with GenAI assistance |
| `PlaylistLibrary.java` | Ian, Emma; Sajjad (D4) | Multiple-playlist design and integration; GenAI-assisted D4 updates |
| `InputHelper.java` | Ian, Sajjad | Input validation and retry behavior |
| `PlaylistMenu.java` | Luci, Sajjad | Menu design and input handling; Sajjad added D4 audiobook options with GenAI assistance |
| `AudioStackApp.java` | Luci | Application entry point and review |
| `LinkedChainTest.java` | Ian, Sajjad | Generic reuse and chain edge-case tests |
| `MediaItemTest.java` | Emma, Sajjad | Media equality and validation tests |
| `AudiobookTest.java` | Sajjad | New audiobook and three-media-type tests; GenAI-assisted |
| `PlaylistTest.java` | Ian, Sajjad | Playlist tests and D4 integration review; GenAI-assisted |
| `PlaylistLibraryTest.java` | Ian, Sajjad | Multiple-playlist tests and review |
| `InputHelperTest.java` | Sajjad | Input validation and EOF tests |
| `PlaylistMenuTest.java` | Luci, Sajjad | Menu tests; Sajjad integrated D4 audiobook tests with GenAI assistance |
| `AudioStackAppTest.java` | Luci, Sajjad | Startup, exit, and invalid-input tests |

## GenAI Use

We used ChatGPT to help turn our Deliverable 2 specification into a small baseline Java prototype for us to build off of, adapt it to the staff-provided `LinkedChain`, draft Javadocs, generate JUnit test cases, and prepare documentation and use better phrasing. During development, JUnit identified failures involving playlist display/search comparisons, and we used those results to guide revisions. The project is based on the team's written specification and the instructor's supplied reusable chain, rather than a new data structure generated for the assignment. We used ChatGPT in the end to format everything and make our code readable, fix problems we couldn't resolve ourselves, and generate a more concise README. For Deliverable 4, ChatGPT also assisted with the audiobook addition and related tests.

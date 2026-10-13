# AudioStack — Project 2, Deliverable 3: Prototype and Tests

**Course:** CS 2114 — Software Design & Data Structures, Fall 2026  
**Team:** Luci, Emma, Ian, Sajjad

## Overview

AudioStack is a Java console application for an ordered playlist of songs and podcast episodes. This deliverable is a **working prototype slice**, not the final project. It demonstrates adding both media types, displaying the playlist in order, removing an item at a user-selected position, and searching titles without regard to capitalization. Duplicate entries are permitted.

`Playlist` stores its entries in one `LinkedChain<MediaItem>`; it does not manage linked nodes directly. The same generic `LinkedChain<T>` is also exercised with `String` and `Integer` in the tests. No type-specific copies of the chain are used.

## Compiling, Running, and Testing in Eclipse

1. Open the **AudioStackD3** Java project in Eclipse (Java 17).
2. Find `src > audiostack > AudioStackApp.java`.
3. Right-click `AudioStackApp.java` and choose **Run As > Java Application**.
4. Use the Console menu: **1** to add a song, **2** to add a podcast, **3** to display all items, **4** to remove by position, **5** to search by title, and **0** to quit.
5. To execute the tests, right-click the `audiostack` package under `src` and choose **Run As > JUnit Test**. The tests use **JUnit 5** and are in the same `src/audiostack` package as the application classes.
6. Before being able to JUnit test, you must first Add JUnit 5 library by right clicking the AudioStack project --> Build Path --> Add Libraries --> JUnit --> JUnit 5.

Eclipse compiles the `.java` files automatically when **Build Automatically** is enabled. No Maven or external build tool is required. The automated test suite includes ordinary cases and invalid/edge cases. JUnit reports a failure whenever an actual result differs from the expected result; a successful program launch by itself does not establish that every test passes.

## Implemented Prototype

- **Media types:** `Song` and `Podcast` extend `MediaItem`, which holds a shared title and creator.
- **Adding:** `Playlist.addItem` uses the staff chain's `addToEnd` to preserve insertion order.
- **Display:** `Playlist.toDisplayString` creates a numbered listing and reports `(empty)` for an empty playlist.
- **Position removal:** `Playlist.removeItemAt` accepts a **1-based** position; the generic chain's added `removeAt(int)` works with **0-based** indexes.
- **Title search:** `Playlist.searchTitle` matches case-insensitively and preserves original position numbers in its results.
- **Input safety:** `InputHelper` retries malformed integers and blank required text. Model constructors reject invalid required fields and episode numbers. `Playlist` checks null items and positions. `PlaylistMenu` prints error messages and continues; end of input exits cleanly.
- **Generic reuse:** `LinkedChain<MediaItem>`, `LinkedChain<String>`, and `LinkedChain<Integer>` use the same class. The application interacts with the chain only through public methods.

## Reflection

### Changes from our specification

The Deliverable 2 specification described methods that differed from the actual instructor-provided chain. It assumed `add(T)` would append and referenced `getCurrentSize()`, `getFrequencyOf(T)`, `removeEntry(T)`, and `toArray()` with no argument. The staff implementation instead provides `addToEnd(T)`, `size()`, `count(T)`, `remove(T)`, and `toArray(T[])`. We adapted the application to the **provided public API**, rather than replacing the staff class with our originally proposed design.

We also narrowed the scope for this prototype. Our specification included `insertItem`, `getItem`, `removeItem`, and `countItem`; these are **not implemented yet** in the prototype application. Instead, we completed the add/display/remove-at-position path and added `searchTitle` as an extra feature. This keeps a working, testable slice without pretending the final MVP is finished. The streamlined implementation uses basic Java classes, loops, arrays, `Scanner`, and JUnit 5 appropriate for CS 2114.

Our original separation of validation remains: `InputHelper` validates console formats, `MediaItem` and `Podcast` validate their own fields, `Playlist` validates playlist operations, and `PlaylistMenu` handles messages shown to the user. The change in method names and the reduced prototype scope came from comparing the GenAI-assisted implementation against our specification and the staff code.

### Changes to LinkedChain

The instructor's generic, singly linked `LinkedChain<T>` is the basis of storage. Its existing public methods, including `addToEnd`, `get`, `size`, `count`, `contains`, `remove`, and `toArray(T[])`, are retained. We added one general-purpose method, **`removeAt(int index)`**, because the prototype needs to remove the item at a specific position—even when two equal media objects appear in the list. The method works for any `T` and leaves playlist-specific 1-based rules in `Playlist`. We did **not** add song, podcast, menu, or display rules to LinkedChain.

### Unknowns resolved and still open

We resolved how Song and Podcast share title/creator information, how case-insensitive equality works, how podcast episode numbers distinguish episodes, how to avoid `Scanner` newline problems by reading full lines before parsing integers, and how 1-based menu positions map to 0-based chain indexes. We also learned the actual chain API and the consequences of its head-only implementation.

Still open for the final project are the most appropriate generic insertion operation and whether to optimize traversal-heavy playlist features with typed `toArray` calls.

### What does not work yet

The prototype does not yet offer insert-at-position, view-one-item by position, remove-a-matching-item, or count-matching-items through the Playlist/menu. Corresponding application methods, menu cases, and tests belong to the final implementation. The current prototype is intentionally not a finished playlist manager.

### Time complexity

| Application operation | Complexity | Reason |
|---|---|---|
| `size`, `isEmpty` | O(1) | Staff chain tracks its number of entries |
| `addItem` | O(n) | `addToEnd` traverses from the head |
| `removeItemAt` | O(n) | `removeAt` walks to the requested node |
| `toDisplayString` | O(n²) | Current simple implementation repeatedly uses indexed `get` |
| `searchTitle` | O(n²) | Current simple implementation repeatedly uses indexed `get` |

These estimates correct our specification's O(1) append assumption: the provided chain does not have a tail pointer. Traversal-heavy methods can be improved to O(n) by copying entries once with `toArray(new MediaItem[size])`.

## Next Steps

The remaining MVP work is ordered by dependencies and ownership.

| Order | Remaining work | Owner | Tests to add |
|---|---|---|---|
| 1 | Add generic insertion support and `Playlist.insertItem(int, MediaItem)` | Ian | Insert into empty list; front, middle, end; invalid position; null |
| 2 | Add `Playlist.getItem(int)` | Emma | First/last valid positions; empty list; zero and out-of-range positions |
| 3 | Add `Playlist.removeItem(MediaItem)` | Sajjad | Existing match, duplicate first match, absent match, empty list, null |
| 4 | Add `Playlist.countItem(MediaItem)` | Sajjad | Zero, one, multiple matches; empty list; null |
| 5 | Extend menu choices to expose the completed MVP operations | Luci | Normal flow and invalid input for each new choice |
| 6 | Refactor display/search to one traversal, rerun regression tests, and finalize documentation | All four | Ordering, duplicates, empty collection, bad input, and full menu regression |

**Stretch goals:** Keep **case-insensitive title search** in the prototype and final version. Other optional enhancements beyond the documented MVP are **dropped** so the team can concentrate on implementing and testing the required playlist features first. Title-search tests cover different capitalization, leading/trailing query spaces, duplicate matches, an unmatched query, and a blank query.

## Attribution

The Deliverable 2 specification was written collaboratively. The file-level entries below distinguish specification responsibilities from the **GenAI-assisted code drafts** used for the Deliverable 3 prototype; they do not represent unverified claims that one person independently wrote all of a generated file.

| Class or test file | Team member tied to design/test area | Contribution and provenance |
|---|---|---|
| `LinkedChain.java` | Ian, Luci | Staff-provided base; GenAI-assisted `removeAt` addition based on Ian's chain contract and Luci's edge-case design |
| `MediaItem.java` | Luci, Ian, Sajjad |  application draft based on shared media design and equality specification |
| `Song.java` | Luci, Emma | draft based on Song fields and generic-contract review along with GenAI help |
| `Podcast.java` | Luci, Emma | GenAI-assisted draft based on podcast fields, episode validation, and generic-contract review |
| `Playlist.java` | Ian, Emma | draft based on method/complexity specification and signature review |
| `InputHelper.java` | Ian, Sajjad | GenAI-assisted draft based on validation plan |
| `PlaylistMenu.java` | Luci, Sajjad | draft based on menu design and validation cases |
| `AudioStackApp.java` | Luci | entry-point draft following class design |
| `LinkedChainTest.java` | Sajjad | GenAI-assisted tests based on chain test plan and edge cases |
| `MediaItemTest.java` | Emma, Sajjad | GenAI-assisted tests based on model contracts and test plan |
| `PlaylistTest.java` | Ian, Sajjad | GenAI-assisted tests based on playlist methods and invalid-position cases |
| `InputHelperTest.java` | Sajjad | GenAI-assisted tests based on retry and end-of-input cases |
| `PlaylistMenuTest.java` | Luci, Sajjad | GenAI-assisted tests based on menu interaction and invalid input |
| `AudioStackAppTest.java` | Luci, Sajjad | GenAI-assisted tests based on application startup and input-end handling |

### GenAI Usage

We used ChatGPT to help turn our Deliverable 2 specification into a small baseline Java prototype for us to build off of, adapt it to the staff-provided `LinkedChain`, draft Javadocs, generate JUnit test cases, and prepare documentation and use better phrasing. During development, JUnit identified failures involving playlist display/search comparisons, and we used those results to guide revisions. The project is based on the team's written specification and the instructor's supplied reusable chain, rather than a new data structure generated for the assignment. We used chatGPT in the end to format everything and make our code readable, fix any problems we couldn't resolve ourselves, and generate a more concise READ.ME.

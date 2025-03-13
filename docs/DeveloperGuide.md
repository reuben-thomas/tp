---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of organisation members
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage organisation members faster than a typical mouse/GUI driven app


### User stories

Priorities: MVP (need to have) - `* * * *` High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| <div style="width:60px">Priority</div> | As a …​          | I want to …​                                                                                                                         | So that I can…​                                                                                                                                                       |
|----------------------------------------|------------------|--------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `* * *`                                | Administrator    | update contact information                                                                                                           | if a contact's data changes, I can update their corresponding data to reflect it                                                                                      |
| `* * *`                                | Administrator    | ensure that only staff with the appropriate authority can make key changes                                                           | I can guarantee the safety that nobody unauthorized can use the application.                                                                                          |
| `*`                                    | Administrator    | see a history of activity and changes made by each user of the platform                                                              | I can easily identify erroneous actions from users, and rectify them if necessary                                                                                     |
| `*`                                    | Administrator    | set the application to save data in an encrypted, password protected state                                                           | i can store sensitive or private information while protecting clients' data.                                                                                          |
| `*`                                    | Administrator    | scan or upload an image of a contact document, and have it automatically add as a contact                                            | I can avoid wasting time and minimize errors associated with manually entering data                                                                                   |
| `* *`                                  | Administrator    | archive contacts                                                                                                                     | I can store data about previous organization members, while keeping it separate in search                                                                             |
| `* *`                                  | Administrator    | add new organization members with similar data (names, etc.) while being able to manage duplicates by differentiating through orgIDs | I can ensure that we can cater for users with the same name which is a likely occurrence                                                                              |
| `* *`                                  | Administrator    | add validation for organization specific fields such as orgID and email                                                              | I can reduce the possibility of adding erroneous or invalid contact information                                                                                       |
| `* *`                                  | Administrator    | give other members administrator rights                                                                                              |                                                                                                                                                                       |
| `* *`                                  | Administrator    | import contact information to the addressbook in batches such as in CSV                                                              | I can easily import data from other organisations services, making migration to the addressbook software easier for a large enterprise.                               |
| `* *`                                  | Administrator    | store, or export contact information to the addressbook in batches such as in CSV                                                    | I can easily use the addressbook data with other services, teams, or analysis I might find necessary.                                                                 |
| `* * * *`                              | Administrator    | add contacts                                                                                                                         | I can create records for new organization members                                                                                                                     |
| `* * * *`                              | Administrator    | delete contacts                                                                                                                      | I can remove unnecessary contacts                                                                                                                                     |
| `* * *`                                | All Users        | type my password without worrying about others seeing it                                                                             | my password is secure                                                                                                                                                 |
| `*`                                    | All Users        | undo important commands like additions, deletions, updates, and status changes                                                       | I can have room for error if I were to make a mistake                                                                                                                 |
| `*`                                    | All Users        | get live syntax highlighting, to visually know while I type that my command format is valid                                          | can reduce errors when typing lengthy commands.                                                                                                                       |
| `* * * *`                              | All Users        | login and logout of my user profile                                                                                                  | I can ensure the device and data is safe when I am away from it.                                                                                                      |
| `* * * *`                              | Any User         | list out all user information                                                                                                        | I can see all information at a glance, and search for it                                                                                                              |
| `*`                                    | Experienced User | use tab completions when typing commands or their corresponding arguments                                                            | I can speed up my workflow and avoid typing out lengthy names fully. This will also reduce the chances of giving the program invalid input.                           |
| `*`                                    | Experienced User | generate automated documents for invoices, service events, etc.                                                                      | minimize time spent and minimize errors made when performing administrative tasks such as this.                                                                       |
| `*`                                    | IT Staff         | I can toggle column visibility                                                                                                       | I can look at only the fields that are relevant to me. For instance, when repairing a laptop, their device information is relevant, while their house address is not. |
| `*`                                    | IT Staff         | search for organization members in natural language, such as through a partial match of their name, device, or current status        | I can save time by avoiding typing needlessly long commands, and don't need to remember every single detail when searching for an organization member                 |
| `*`                                    | IT Staff         | easily send emails with any email application to organization members from a button within the app                                   | I can easily contact a person without the risk of mistyping an email.                                                                                                 |
| `* *`                                  | IT Staff         | have an overview dashboard of organization members with each status                                                                  | I can know our current workload at a glance.                                                                                                                          |
| `* *`                                  | IT Staff         | sort or filter contacts by their device type (e.g. MacBook or ThinkPad)                                                              | If I specialize in a particular type of device, I can look only at contacts that use it.                                                                              |
| `* *`                                  | IT Staff         | sort or filter contacts based on their service status tag                                                                            | I can see wo needs help most urgently                                                                                                                                 |
| `* * * *`                              | IT Staff         | store status tags to clients representing our current progress in serving them, such as (Request Recieved, In Progress, Completed}   | I can easily keep track of who is in need of help.                                                                                                                    |
| `* * * *`                              | IT Staff         | store device information associated with a contact                                                                                   | I can be prepared in terms of spare parts, expertise, or consider possible issues prior to meeting up with a contact.                                                 |
| `* * * *`                              | IT Staff         | store an orgID and organization email associated with a contact                                                            | I can easily access the organization relevant information for our internal company processes                                                                          |
| `* * * *`                              | IT Staff         | I can view contacts in a neatly organized, tabular form                                                                              | I can easily compare data fields between organization members, and view more contacts in a page at a given time                                                       |
| `* * * *`                              | IT Staff         | search for organization members using an organization email or orgID                                                                    | if referring to other organization documents, I can easily reference the orgID or email to find a organization member in the address book                                |
| `* * *`                                | New User         | easily access a help message or see a list of all available commands, and their descriptions                                         | I can learn to use the app quickly and independently, without requiring additional training.                                                                          |
| `*`                                    | New User         | go through a quick tutorial to familiarize myself with the interface                                                                 | I can independently learn without needing training from another user                                                                                                  |

### Use cases

(For all use cases below, the **System** is `DeskFlow` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Delete a member**

**MSS**

1.  User requests to list organisation members
2.  DeskFlow shows a list of organisation members
3.  User requests to delete a specific member in the list
4.  DeskFlow deletes the member

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. DeskFlow shows an error message.

      Use case resumes at step 2.

**Use case: UC2 - Add a member**

**MSS**


1. User requests to add a member in the list with corresponding details
2. DeskFlow adds the person 
3. User requests to list organisation members
4. DeskFlow shows a list of organisation members including the newly added member

    Use case ends.

**Extensions**

* 1a. DeskFlow detects error in given format.

    * 1a1. DeskFlow shows an error message with the valid format.

      Use case resumes at step 1.

* 4a. The listed member added is incorrect.
    
    * 4a1 User performs a deletion (UC1)
    * 4a2 DeskFlow deletes incorrect member
  
        Use case resumes at step 1.

**Use case: UC3 - Search for a member**

**MSS**


1. User requests to search for an existing member
2. DeskFlow shows a list of related members

   Use case ends.

**Extensions**

* 1a. DeskFlow detects error in given format.

    * 1a1. DeskFlow shows an error message with the valid format.

      Use case resumes at step 1.

* 2a. The list is empty.
    
    * 2a1. User retries with new search term

      Use case resumes at step 2.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 10 000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should not take more than 10 seconds to load at any stage of using DeskFlow.
5.  Should have contrasting colors that improve readability.
6.  Should display data in clear tabular format.
7.  Should require minimal dependencies to ensure easy installation in restricted environments.
8.  Should work on both local machines and remote SSH sessions.
9.  Should be able to recover from crashes without data loss.
10. Should be in compliance with relevant data standards and protection policies.
11. Should work without connection to the internet.
12. Should be able to minimize user mistakes and allow user rectification.

*{More to be added}*

### Glossary

* **MSS**: Main Scenario Success
* **MVP**: Features that are must-haves for DeskFlow
* **Users**: Users of the addressbook application
* **Administrator**: Person with authority with the ability to add or remove users, change permissions, change data 
directly
* **IT Staff**: Person that works with DeskFLow more, has the ability to toggle column visibility, search for 
organization members, edit service tag field, and manages data in addressbook
* **Organization Member**: Members of the organization are the contacts that are managed within the addressbook
* **orgID**: Unique staff identifier given to all employees
* **Natural Language**: Natural language in searching refers to using human-like, conversational queries instead of 
keyword-based searches to find information. (i.e. "best smartphones 2024" and "what are the best smartphones in 2024" )
* **Devices**: The laptop / device that each organization member posesses. E.g. Some users may have systems such as a 
MacBook, ThinkPad, etc.
* **CSV**:  CSV (comma-separated values) is a plain text file format that stores data in a table. CSVs are commonly 
used to store spreadsheets and databases.
* **Tabular Format**: Tabular format refers to data or information that is organized in a table format, 
typically using rows and columns. This structure makes it easier to read, analyze, and process data systematically.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **SSH**: SSH (Secure Shell) is a cryptographic network protocol that allows secure remote access to another computer
over an unsecured network. It is commonly used for remote login, file transfers, and command execution on servers.
* **Java**: Java is a high-level, object-oriented programming language developed by Sun Microsystems 
(now owned by Oracle) in 1995. It is designed to be platform-independent, meaning that Java programs can run on any system that has a Java Virtual Machine (JVM).


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

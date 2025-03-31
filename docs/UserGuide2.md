# DeskFlow User Guide

DeskFlow is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, DeskFlow can get your contact management tasks done faster than traditional GUI apps.

## Table of Contents
- [Quick start](#quick-start)
    - [Installation](#installation)
    - [Usage](#usage)
- [Features](#features)
    - [Viewing help](#viewing-help--help)
    - [Adding a person](#adding-a-person-add)
    - [Logging in](#logging-in-login)
    - [Role-based access control](#role-based-access-control)
    - [Listing all persons](#listing-all-persons--list)
    - [Editing a person](#editing-a-person--edit)
    - [Set a persons status](#set-a-persons-status-set_status)
    - [Filter existing status](#filter-existing-status-filter_status)
    - [Locating persons by name](#locating-persons-by-name-find)
    - [Locating persons by any attribute](#locating-persons-by-any-attribute-findby)
    - [Deleting a person](#deleting-a-person--delete)
    - [Clearing all entries](#clearing-all-entries--clear)
    - [Exiting the program](#exiting-the-program--exit)
    - [Importing data](#importing-data-import)
    - [Saving the data](#saving-the-data)
    - [Editing the data file](#editing-the-data-file)
    - [Archiving data files](#archiving-data-files-coming-in-v20)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

## Quick start

### Installation

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

   **Windows Users:** Check your Java version by running the following command in Command Prompt:
2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).
3. Copy the file to the folder you want to use as the *home folder* for your DeskFlow.

**home folder** is where all DeskFlow files will be stored. Pick a location on your computer where you want to keep DeskFlow.

### Usage

*Ensure that you have followed steps 1-3 from the installation.*

1. Open a command terminal, and run `cd /home/folder` to navigate to the home folder, and run `java -jar addressbook.jar` command in the terminal to run the application.<br>
   A GUI similar to the below should appear.

![UI Image](image.png)

Here are some key elements of the UI you should be familiar with:
- **Main Menu:** Here, you can conveniently login, access help if you're stuck, or change the file to which the address book is saved.
- **Command Input:** This is where you type commands to interact with the address book.
- **Command Result:** When you enter a command, its result will be displayed in this box.
- **Result Table:** When listing or viewing contacts, they will be presented in a table here, whereby each column is associated with a field in the header.
- **Person Card:** A single contact is represented in a person card, which displays all of their relevant information. These cards are collapsed by default.
- **Saved File:** The file that this address book is saved to.

2. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:
- `login`: A pop-up appears for you to login.
- `list`: Lists all contacts.
- `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/Urgent i/123 d/DeviceInfoXYZ s/pending_approval`: Adds a contact named `John Doe` to DeskFlow.
- `delete 3`: Deletes the 3rd contact shown in the current list.
- `set_status 3 s/none`: Sets the status of the 3rd contact to `none`.
- `filter_status s/none`: Filter all contacts that have `none` as a status.
- `clear`: Deletes all contacts.
- `exit`: Exits the app.

3. Refer to the [Features](#features) below for details of each command.

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.
- Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
- Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as `` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.
- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `login`, `logout`and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… i/ORGID d/DEVICEINFO s/STATUS`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
- `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney i/123 d/DeviceInfoXYZ s/pending_approval`
- `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal d/DeviceInfoXYZ s/pending_approval`

### Logging in: `login`

Opens a Login Dialog that the user can enter their username and password to login to have access to DeskFlow Features.

Format: `login`

### Role-based access control:

DeskFlow grants different access rights to certain features based on account roles.

**Administrator**
- can do batch import/export
- can add / remove IT staff
- can add / delete
- all other features

**IT Staff**
- search
- filter
- edit status tags

### Listing all persons : `list`

Shows a list of all employees in the organization recorded in DeskFlow.

Format: `list`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… i/ORGID d/DEVICEINFO s/STATUS`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
- `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney i/123 d/DeviceInfoXYZ s/pending_approval`
- `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal d/DeviceInfoXYZ s/pending_approval`

### Set a persons status: `set_status`

Sets an existing persons status.

Format: `set_status INDEX s/status`

Examples:
- `set_status 1 s/pending_approval` sets the status for the 1st person currently listed.

### Filter existing status: `filter_status`

Format: `filter_status s/status`

Examples:
- `filter_status s/pending_approval` filters all contacts with status of `pending approval`.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

- `find` is case-insensitive. (i.e. `hans` will match `Hans`)
- The order of the keywords does not matter. (i.e. `Hans Bo` will match `Bo Hans`)
- Only full words will be matched. (i.e. `Han` will not match `Hans`)
- Persons matching at least one keyword will be returned.
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
- `find John` returns `john` and `John Doe`
- `find alex david` returns `Alex Yeoh`, `David Li`

### Locating persons by any attribute: `findby`

Finds persons whose attributes match a set of keywords.

Format: `findby [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG_KEYWORDS]…`

- The search is case-insensitive for all attributes, so the addresses `BLK123` will match with `blk123`
- The order of specifying attributes and corresponding keywords does not matter, so the query `findby n/jon s/none` will be equivalent to `findby s/none n/jon`.
- For each attribute, the order of keywords does not matter, meaning that the query `findby n/Jonathen Cheng` will be equivalent to `findby n/Cheng Jonathen`.

Examples:
- Finding persons containing the name "alex" or the status is "pending_external" `find n/alex s/pending_external`.
- Finding persons with an address containing "jurong" or the status is "in_progress", `find a/jurong s/in_progress`.

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …

Examples:
- `list` followed by `delete 2` deletes the 2nd person in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Importing data: `import`

AddressBook data can be imported from an existing AddressBook JSON file.

Format: `import PATH`

Example:
- `import data/imported_AB.json` **replaces** existing data with the imported JSON

### Saving the data

DeskFlow data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

DeskFlow data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

*Details coming soon ...*

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

## Command summary

| Action | Format, Examples |
| --- | --- |
| **Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Delete** | `delete INDEX`<br> e.g., `delete 3` |
| **Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com` |
| **Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake` |
| **FindBy** | `findby [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG_KEYWORDS]…` <br> e.g., `findby n/James Jake s/none` |
| **Status** | `set_status s/[STATUS]` |
| **Filter** | `filter_status s/[STATUS]` |
| **List** | `list` |
| **Help** | `help` |
| **Login** | `login` |
| **Logout** | `logout` |
| **Register** | `register` |
| **Clear** | `clear` |
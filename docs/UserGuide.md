---
layout: page
title: User Guide
---

# DeskFlow User Guide

DeskFlow is a powerful desktop application built for IT helpdesk staff to streamline the management of contact
information of employees, track device information, and keep track of service events. For the tech-savvy, DeskFlow is
optimized for use via a Command Line Interface (CLI), while also featuring a
well-designed, easy to use Graphical User Interface (GUI), making it easy to use for anyone.

## Table of Contents

1. [Quick start](#quick-start)
   a. [Installation](#installation)
   b. [Usage](#usage)
2. [Information for testers](#information-for-testers)
3. [Features](#features)
   a. [Role-based access control](#role-based-access-control-)
   b. [Viewing help](#viewing-help--help)
   c. [Logging in](#logging-in--login)
   d. [Registering a new IT staff](#registering-a-new-it-staff--register)
   e. [Adding an employee](#adding-an-employee--add)
   f. [Editing an employee's details](#editing-an-employees-details--edit)
   g. [Listing all employees](#listing-all-employees--list)
   h. [Set an employee's status](#set-an-employees-status--set-status)
   i. [Filter by status](#filter-by-status--filter-status)
   j. [Locating employees by name](#locating-employee-by-name--find)
   k. [Locating employees by any attribute](#locating-employees-by-any-attribute--findby)
   l. [Deleting an employee](#deleting-an-employee--delete)
   m. [Clearing all entries](#clearing-all-entries--clear)
   n. [Exiting the program](#exiting-the-program--exit)
   o. [Importing data](#importing-data--import)
   p. [Saving data](#saving-data)
   q. [Editing data file](#editing-data-file)
4. [FAQ](#faq)
5. [Known issues](#known-issues)
6. [Command summary](#command-summary)

## Quick start

### Installation

> **Tip**
>
> For experienced users, the latest release of DeskFlow is available as a `.jar`
> file  [here](https://github.cob/AY2425S2-CS2103T-T10-2/tp/releases),
> or you may wish to build the project from [source](https://github.com/AY2425S2-CS2103T-T10-2/tp). Ensure you have
> Java `17` installed.

#### Step 1. Install Java `17` or Above

##### Windows / Linux

Most Windows and Linux distributions come with Java pre-installed. To verify if you have a compatible version of Java,
open a Command Prompt on Windows, or a terminal
on Linux, and run the command `java -version`. You should see an output similar to either of the following:

```bash
java version "17.0.1" 2021-10-19 LTS
openjdk version "17.0.14" 2025-01-21
```

If you do not have Java installed, or if you have a version lower than `17` you may follow the installation
instructions for Windows [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html), and
Linux [here](https://se-education.org/guides/tutorials/javaInstallationLinux.html).

##### Mac OS

You must follow the instructions  [here](https://se-education.org/guides/tutorials/javaInstallationMac.html) to
install a specific version of the Java Development Kit (JDK).

To verify a successful installation, open a terminal and run the command `java -version`. You should see an output
similar to the following:

```bash
java version "17.0.14.fx-zulu" 2021-10-19 LTS
```

#### 2. Download DeskFlow

DeskFlow is available as a Java ARchive (JAR) file, terminating in `.jar`. Install the JAR file from our latest
release [here](https://github.com/AY2425S2-CS2103T-T10-2/tp/releases).

#### 3. Creating a DeskFlow Home Folder

A home folder is the directory where the DeskFlow application's `.jar`, as well as your data files, will be stored.
We recommend creating a new folder in your home directory called `DeskFlow` for this purpose. Copy the previously
downloaded `DeskFlow.jar` file into this folder.

### Usage

*Ensure that you have followed steps 1-3 from the installation.*

1. Open a command terminal, and run `cd /home/folder` to navigate to the home folder, and run `java -jar DeskFlow.jar`
   command in the terminal to run the application.<br>
   A GUI similar to the below should appear.
    - Employee records will only show after you login.

![UI Image](images/StartUi.png)

Here are some key elements of the UI you should be familiar with after you login:<br>
![UI Image with labels](images/LabelledUi.png)

- **Main Menu:** Here, you can conveniently login, access help if you're stuck, or change the file to which the address
  book is saved.
- **Command Input:** This is where you type commands to interact with the address book.
- **Command Result:** When you enter a command, its result will be displayed in this box.
- **Result Table:** When listing or viewing employees, they will be presented in a table here, whereby each column is
  associated with a field in the header.
- **Person Card:** A single employee is represented in a person card, which displays all of their relevant information.
  These cards are collapsed by default.
- **Saved File:** The file that this address book is saved to.

2. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

- `login`: A pop-up appears for you to log in to your account.
- `list`: Lists all employees.
- `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/Urgent i/123 d/DeviceInfoXYZ s/pending_approval`:
  Adds a employee named `John Doe` to DeskFlow.
- `delete 3`: Deletes the 3rd employee shown in the current list.
- `set-status 3 s/none`: Sets the status of the 3rd employee to `none`.
- `filter-status s/none`: Filter all employees that have `none` as a status.
- `clear`: Deletes all employees.
- `logout`: Logs out of account.
- `exit`: Exits the app.

3. Refer to the [Features](#features) below for details of each command.

## Information for testers

If you are testing DeskFlow the following admin credentials will be useful for you.

username: Admin  
password: Admin@123

To test with IT staff privileges, you can log in with admin and register a new user.

1. Run `login` and enter with admin credential.
2. Next run `register` and enter username and password for the new IT staff.
3. Run `logout`.
4. Then run `login` again but with the new credentials created.
5. You now have restricted access to functions as an IT staff.

## Features

<div markdown="block" class="alert alert-info">

**Notes about reading the command format:**<br>

- Commands are case-sensitive and should all be in small letters.
- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.
- Words in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
- Items with `…` after them can be added multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.
- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `login`, `logout`
  and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Role-based access control :

DeskFlow grants different access rights to certain features based on your account's role.

**Administrator**

- can do batch import/export (`import`)
- can add / remove IT staff
- can add / delete (`add`/`delete`)
- all other features

**IT Staff**

- search (`find`/`findby`)
- filter by status (`filter-status`)
- edit status tags (`set-status`)

### Viewing help : `help`

Shows you a message explaining how to access the help page.

Format: `help`

### Logging in : `login`

Opens a Login Dialog where you are prompted to enter your username and password to log in to gain access to DeskFlow
Features.

Format: `login`

![Login Dialog Img](images/LoginDialog.png)

Additional Information:

- Deskflow will not grant access to other features until you are logged in.
- Deskflow is a CLI first application where keyboard inputs are optimised, as such you may hit the `Enter` to log in.

### Registering a new IT staff : `register`

Adds a new IT staff user that has limited privileges.

Format: `register`

![Register_Dialog.png](images/RegisterDialog.png)

Additional Information:

- You must have a unique username for each IT staff

### Adding an employee : `add`

Adds an employee to DeskFlow.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… i/ORGID d/DEVICEINFO s/STATUS`

Examples:

- `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/Urgent t/SoftwareIssue i/000123 d/DeviceInfoXYZ s/pending_approval`
- `add n/Betsy Crowe t/NetworkIssue e/betsycrowe@example.com a/Newgate Prison p/98752135 d/DeviceInfoABC s/none`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Additional Information:

- Each employee must have a unique organisation ID
- Every field must be filled up

### Editing an employee's details : `edit`

Edits an existing employee's details in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]… [i/ORGID] [d/DEVICEINFO] [s/STATUS]`

Examples:<br>

- `edit 1 p/91234567 e/johndoe@example.com` edits the phone number and email address of the 1st person to be `91234567`
  and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

Additional Information:

- Edits the person at the specified INDEX. The index refers to the index number shown in the displayed person list. The
  index must be a positive integer 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
- You can remove all the person’s tags by typing t/ without specifying any tags after it.

### Listing all employees : `list`

Shows a list of all employees in the organization recorded in DeskFlow.

Format: `list`

### Set an employee's status : `set-status`

Sets an existing employee's current status to the provided status.

Format: `set-status INDEX s/STATUS`

Examples:

- `set-status 1 s/pending_approval` sets the status for the 1st person in the list shown currently listed
  to `pending_approval`.

Additional Information:

- An employee's status may only be set to one of five options.

    - `none`
    - `pending_approval`
    - `servicing`
    - `pending_external`
    - `on_hold`

### Filter by status : `filter-status`

Format: `filter-status s/STATUS`

Examples:

- `filter-status s/pending_approval` gets all employees with status of `pending approval`.

Additional Information:

- An employee's status only includes one of five options. Searching with an invalid status will return an error.
- Valid status includes:
    - `none`
    - `pending_approval`
    - `servicing`
    - `pending_external`
    - `on_hold`

### Locating employee by name : `find`

Finds employees whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

Examples:

- `find John` returns employees with name containing `john` like `John Doe`
- `find alex david` returns employees with name containing `alex` and `david` like `Alex Yeoh`, `David Li`

Additional Information:

- `find` is case-insensitive. (i.e. `hans` will match `Hans`)
- The order of the keywords does not matter. (i.e. `Hans Bo` will match `Bo Hans`)
- Only full words will be matched. (i.e. `Han` will not match `Hans`)
- employees whose names matching at least one keyword will be returned.
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

### Locating employees by any attribute : `findby`

Finds employees whose attributes match a set of keywords.

Format: `findby [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [s/STATUS_KEYWORDS] [t/TAG_KEYWORDS]…`

Examples:

- `findby n/alex s/pending_external` returns employees containing the name `alex` or the status is `pending_external` .
- `findby a/jurong s/pending_approval` finds employees with an address containing `jurong` or the status
  is `pending_approval`.

Additional Information:

- The search is case-insensitive for all attributes, so the addresses `BLK123` will match with `blk123`.
- The order of specifying attributes and corresponding keywords does not matter, so the query `findby n/jon s/none` will
  be equivalent to `findby s/none n/jon`.
- For each attribute, the order of keywords does not matter, meaning that the query `findby n/Jonathen Cheng` will be
  equivalent to `findby n/Cheng Jonathen`.
- If multiple attributes are given, employees that contain the keyword in any of the corresponding attribute will be
  returned.
- Partial words will be matched. (i.e. `Han` will match `Hans`)
- In a single attribute all keywords will be considered as one. (i.e. `H n` will not match `Hans` or `Han`)
- Special symbols will not be filtered before and after keywords.

### Deleting an employee : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

Examples:

- `list` followed by `delete 2` deletes the 2nd person in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

Additional Information:

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Logging out : `logout`

Logs the user out.

Format: `logout`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Importing data : `import`

AddressBook data can be imported from an existing AddressBook JSON file.

Format: `import PATH`

Example:

- `import data\import.json` **replaces** existing data with the imported JSON.

![import screenshot](images/import_screenshot.png "import example")

- You should see a success message after successfully importing.
  ![successful import](images/successful_import.png "successful import example")

Additional Information:

- You can use either relative (`data/imported_AB.json`) or absolute
  path (`C:\Users\keega\Documents\tp\data\import.json`).
- You should use the pathing convention that matches your Operating System for better results.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Importing a new data file will completely replace the existing address book. Hence, you are recommended to make a backup of the file before importing.
</div>

### Saving data

Your DeskFlow data is saved in the hard disk automatically after any command that changes the data. There is no need for
you to save
manually. You can see where the data is stored at the bottom of the DeskFlow window.

### Editing data file

Your DeskFlow data is saved automatically as a JSON file `[JAR file location]/data/deskFlow.json`. Advanced users are
welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, DeskFlow will discard all data and start with an empty data file at the next run. Hence, it is recommended to make a backup of the file before editing it.<br>
Furthermore, certain edits can cause DeskFlow to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**:
You should install DeskFlow on the other computer and overwrite the empty JSON data file with the JSON file of your
previous DeskFlow application.
You can find your previous JSON file in the home folder.

**Q**: Is there a way to undo an accidental employee deletion?<br>
**A**:
Unfortunately you cannot undo an accidental employee deletion, but it is possible to add the employee back again.

**Q**: Who has access to employee data?<br>
**A**:
Only Admin users or IT staff users that are logged in can access and read the employee data.

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard
   shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy
   is to manually restore the minimized Help Window.
3. You might not see icons rendering correctly depending on your Operating System. We are working on allowing icons to
   be universally visible.

## Command summary

| Action            | Format, Examples                                                                                                                                                                                                                                   |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**           | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS i/ORGID d/DEVICEINFO s/STATUS [t/TAG]… `<br/>e.g., `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 i/000123 d/DeviceInfoXYZ s/pending_approval t/Urgent t/SoftwareIssue` |
| **Delete**        | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                                                |
| **Edit**          | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]… [i/ORGID] [d/DEVICEINFO] [s/STATUS]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                     |
| **Find**          | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                                                         |
| **Find By**       | `findby [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [s/STATUS_KEYWORDS] [t/TAG_KEYWORDS]…` <br> e.g., `findby n/James Jake s/none`                                                                                |
| **Set Status**    | `set-status INDEX s/STATUS` <br> e.g., `set-status 1 s/none`                                                                                                                                                                                       |
| **Filter Status** | `filter-status s/STATUS`   <br> e.g., `filter-status s/none`                                                                                                                                                                                       |
| **Import**        | `import PATH`         <br> e.g., `import data/imported_AB.json`                                                                                                                                                                                    |
| **List**          | `list`                                                                                                                                                                                                                                             |
| **Help**          | `help`                                                                                                                                                                                                                                             |
| **Login**         | `login`                                                                                                                                                                                                                                            |
| **Logout**        | `logout`                                                                                                                                                                                                                                           |
| **Register**      | `register`                                                                                                                                                                                                                                         |
| **Clear**         | `clear`                                                                                                                                                                                                                                            |


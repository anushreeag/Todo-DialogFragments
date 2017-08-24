# Todo-DialogFragments
Todo Application with List view, sqlite support , custom adapter, dialog fragments & UI/UX improvements
# Pre-work - *Todo*

**Todo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Anushree Ganjam**

Time spent: **26** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] List anything else that you can get done to improve the app functionality!
* [x] Added delete button to delete the Todo
* [x] Added edit button to edit the Todo
* [x] Due dates are shown in red colors
* [x] Date picker in the edit screen will show calendar only from current date. Cannot navigate to old date
* [x] Title of the To-do is limited to 40 characters
* [x] Appropriate Toasts if Title or Date is empty while creating or editing Todo's
* [x] Add button is included to add the New todo and opens a dialog to fill the details
* [x] User can cancel the creation of new Todo Item
* [x] Added HELP to the app
* [x] Displays the added To-do list based on priority (high-low)
* [x] Highlight the priorities with colors

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/BOtgfXx.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** [
Android is an open source platform which enables the developers to use it more vastly when compared to iOS.
In case of iOS some libraries are available only for Apple employees where as android is available for everyone.
It is based on Linux kernel, which makes it easy , robust and more secure.
Just knowing Java is enough to code android since the syntax is same.

Android's layout xml file is very easy & simple when compared to html/CSS files which was used before to develop Java applications, especially games. 
UI wise we can have same application for multiple devices with different resolutions using hdpi, mdpi, xdhpi etc, drawable resources. Mainly in android reuse of the code is done which 
enables developers to write logic once and use it on multiple phones. ].

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** [In our pre-work we MVC design pattern being used. Adapters are based on Model-View-Control design pattern. The list contains the data,
List view presents the data in a view , Adapters control the data being shown on the ListView.
Here we are using a simple array list as the data, hence array adapter is enough to control this arraylist to be displayed in ListView.

Convert View in getView

When we scroll the listview, complete list will not be shown to the user. We can display only part of the list, since mobile screen is small.
The Adapter uses the convertView as a way of recycling old View objects that are no longer being used in ListView (part of the list that is scrolled up/down). 
In this way, the ListView can send the Adapter old, "recycled" view objects that are no longer being displayed instead of instantiating an entirely new object each time the Adapter wants to display a new list item.
This is the purpose of the convertView parameter.

 ].

## Notes

Describe any challenges encountered while building the app.
Dialog Fragments usage for UI editing was little complex than an activity. Learning and implementing this was interesting


## License

    Copyright [2017] [Anushree Ganjam]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

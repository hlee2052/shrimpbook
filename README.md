## Shrimp Book

### Google Play Store - https://play.google.com/store/apps/details?id=com.github.shrimpbook

### Internet access required to run the application - If not, app will provide warning

### To run this app locally, user must provide: 
1. Parse Server URL, 
2. application ID, 
3. client Key
### which can be inserted in Parse.initialize() method inside MainActivity.
### To run it with your own database, I recommend back4app.com to sign up a free Parse hosting.


## About

An Android project that allows users to share shrimp photo and tank parameters with others
- User account/login system
- User can save favourite items
- User can view other people's system
- Recommends shrimp based on user's input. 
- Used Parse SDK to manage database connection

### Notes:
- The shrimp picture used in the App are from my tank (eg, login page)
- The app saves the following information to the database
  1. User ID
  2. User Password (hidden by Parse server)
  3. Shrimp related posts associated with each user (image, water parameters)
  
### Improvements
- The main landing page loads all list of entries through querying. In the case where there are large number of entries, it may be taking up too much data on request. One solution is to put a 'WHERE' clause, so that only a certain amout of entries are quried, and if user chooses to scroll down, then do additional queries.

- Create more responsive UI - eg, fade in/out, slide, fancy animations, etc.

- Currently allows upload from image storage but taking image directly from camera would be useuful

- Additional features that could be useful:
  1. Add a loading spinner/page when app starts up
  2. Provide search/filter, and make recommendation based on certain critera (eg, show new photo first or most liked items on top)
  3. Implement details page for users to browse through each entry, perhaps add comment section
  4. Also support 3rd party log in, such as Google
  5. create a help page, so that user can have better grasp of the app.

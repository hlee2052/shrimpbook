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
  

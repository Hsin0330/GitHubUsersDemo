# How to build
After download the project, please put the Github bearer token in HttpHeader in the NetworkModule

or 

We can use the app-debug.apk for demo


# How to use

## Search page
1. Open the app
2. Input the user name we want to search and then the app will show 10 suggestion user Ids
3. Click one of the suggestions
4. Show the search user list
5. Click the item and then we can open the user repository list page

## User repository page
* We will show the user information on the top. It contains the avatar, user login id, user name, follower count and following count
* We also show the repository list (excluded the forked repositories) of the user
* Click the repository, we can open the web page of the repository

# About this project
* Use MVVM
* Use Kotlin Flow
* Android Compose for UI
* Apollo GraphQL for API call

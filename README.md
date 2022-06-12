# Alcoholic README


README document for the Alcoholic app

This app is a cocktail communication application. You can search for the cocktails you want to cast, save them as favorites, and share the information with the community.

The platforms used to build the application include:
1. [Android Studio](https://developer.android.com/?hl=ko) is the main platform for application development.
2. [Firebase](https://firebase.google.com) is Google's web server framework that makes DB easy to use in JSON format from Android applications.
3. [TheCocktailDB.com](https://thecocktaildb.com) is a paid API that provides 653 cocktail databases and search queries. Various data are obtained from the API, parsed, and used as desired data.
4. [Papago API](https://developers.naver.com/docs/papago/README.md) is translates the data of 'The Cocktail DB' in English into another language. Used for user convenience.


## Table of Contents

- [Background](#background)
- [Install](#install)
- [Functional Flow](#functional-flow)
- [Technologies implementation](#technologies-implementation)
- [Improvements](#improvements)
- [Contributing](#contributing)
- [License](#license)

## Background

This application was created to convey the beauty of cocktails to many people because our alcholic team members like cocktails. 

In fact, there are many cocktail manufacturing applications. You can download many applications just by searching the App Store with cocktails. These applications are more powerful than you think. There's a lot of data, and there's all the features that we normally support. However, community functionality does not exist. So we wanted to differentiate ourselves by adding community functions.

The goals for this application are:

1. Provide users with a variety of **cocktail recipes**. (It's more than just searching on Google)
2. Make it possible for users to save and view only what they want from numerous cocktail recipes as a **favorite feature**.
3. **Create an account** through login to the user and make the **community available**.
4. If you have your own cocktail recipe, use it as a **custom recipe** and upload it to the community, and let others download it and save it in their favorites.

## Install

This App install can [APK file](https://github.com/WiFros/Alcoholic/tree/main/release). If you want to install it, click the link to download the APK.

## Functional Flow

1. **Log-in,Sign up**
<img width="819" alt="image" src="https://user-images.githubusercontent.com/39113029/173241391-d8ac1772-f732-4734-b04a-bd156e13dc15.png">

The first time you run the application, the **login** screen appears. If you don't have user information in the database, you need to add users through membership.
The **email**, **password**, and **nickname** entered through this membership will be stored in the database.
You can sign up by entering your nickname and email password, and if the email format does not fit, an extension will occur.

<img width="819" alt="image" src="https://user-images.githubusercontent.com/39113029/173241477-e6c6e1ea-b988-4587-bcef-ceaabbc177a4.png">

If the login function has an incorrect email or password, or if the email is not formatted, an extension will occur.
If you have successfully logged in, you can go to the main screen and see your information displayed briefly on the menu and menu items.

2. **Menu fragments**
<img width="819" alt="image" src="https://user-images.githubusercontent.com/39113029/173241622-a37d0ed2-5895-4a1a-9a87-64e3142d3a55.png">

There are two searches, and a cocktail keyword-based search searches search all cocktails with the string entered in the search bar as a substring.

And this is your favorite menu. If you find a recipe that you want to make later in your cocktail search or encyclopedia, you can use it as a key value 
Save to Favorite data child.
And the logout button functions to go to the login screen.

3. **Cocktail Search**
<img width="823" alt="image" src="https://user-images.githubusercontent.com/39113029/173241658-e2f11a88-afcf-460b-88ab-5a7ba9d7b43b.png">
This is a recycling view that allows us to check all the cocktail data we have. Except for the search function, this list is made so that you can scroll down and find it
The ability to scroll through the index of the first letter of the title makes it easier to find.
When you click on a cocktail search or a list found in an encyclopedia, a recipe fragment is called.
The recipe shows a screen containing information about the cocktail, ingredients, and casting methods. And I can see English now, but the DB of the API we used is in English
For user convenience, the translated casting method is indicated using Naver Papago translation API.

4. **Menu fragment - search ingredients**

<img width="832" alt="image" src="https://user-images.githubusercontent.com/39113029/173241704-9f910d4d-0fc5-4aa6-8cec-3050b124bfd8.png">
Material-based search uses the check box to select the main base of the cocktail, search for and add ingredients, and find cocktails with the ingredients and sub-materials as cast materials.
If you click on the list, you can call the recipe fragment as before to check the information
You can also add the cocktail to your favorites if you like it.

5. **Community pages**

<img width="827" alt="image" src="https://user-images.githubusercontent.com/39113029/173241778-31098999-50c5-43f2-812a-2d52066ca4c2.png">

Community, one of the main functions, lists and shows the author, content, and writing time as a thumbnail. The list also used the Recycler view, and when you click the list, the post is called. 
At this time, the position of the list clicked with the adapter and the unique key searched with the position are sent to the int using the bundle. 
Posts discovered with unique keys will display what the author has written and comments.
You can type any text you want in the comment box, and the comment is saved in the current post, that is, the child of the post.
If the comments and posts are the same as the unique keys of the currently logged-in user, the Delete button is activated. When deleted, it disappears from the data.
<img width="826" alt="image" src="https://user-images.githubusercontent.com/39113029/173241911-eb71e1c2-0786-4373-bb8c-cb6a19f6ded3.png">

If someone else uploads a post, the datachange listener in the database detects the change and refreshes the view in the post list program so that the data is displayed in real time.
When a comment is uploaded, the listener refreshes the comment recycler view inside the poster.
If there is no content when you create a post, an extension occurs.
## Technologies implementation

We introduce a framework other than Android Studios in developing applications.

### API : TheCocktailDB.com ![image](https://user-images.githubusercontent.com/39113029/173242192-62adc0d2-1d93-49f2-832d-597f6c15c3fc.png)
Building and processing cocktail information databases and implementing search algorithms using them is a challenge.
We found paid API that supports various search functions, including a high-quality database, and decided to use it.
In addition to the search function, there are necessary functions within the application, so we made a list with cocktail names and ingredients separately.(635 cocktail list)
### API : papago![image](https://user-images.githubusercontent.com/39113029/173242218-6fbe7907-198a-4f67-8393-2784ffe2a867.png)

Papago was used to show recipes from TheCocktailDB.com in Korean.
It is expected that users will be able to drink more comfortably and without mistakes.
### Application development platform : Firebase ![image](https://user-images.githubusercontent.com/39113029/173242245-af635e42-3a71-4a07-af4a-61c4954419c3.png)
1. Membership registration: Save email and password entered by user in DB
2. Login: If the information entered by the user is compared with the DB, login
3. Bulletin board [glist]: Get the child of Post in DB as a datasnapshot, save it in Post class, and list it using the method in class
3. Bulletin board [Writing]: Create and save unique keys from the child of the post
4. Bulletin board [Read text]: When you click the text list, the key value of the text is transmitted by activity as a fragment using bundle, and the key is searched and displayed
5. Bulletin board [Comments]: When reading a post, take a snapshot of the child of the comment generated by the child of the post and list it on the bottom of the post   


## Improvements
1. Currently, It improvement is that when you create and upload your own custom recipe to share with the community, someone wants to add it as a favorite and upload it to the DB.
 
2. Currently, I am signing up with nicknames, e-mails, and passwords, but I would like to use the Google login function because it is very insecure. 
However, Google login requires developer registration, so if the improvement is resolved, it is expected to be added before release.

## Contributing



### Contributors

This project exists thanks to all the people who contribute. 
<a href="https://github.com/WiFros/Alcoholic/graphs/contributors"><img width="176" alt="image" src="https://user-images.githubusercontent.com/39113029/173242595-0ecd1a70-6e3e-4581-9c1c-b106bf9a4431.png"></a>


## License

[MIT]([LICENSE](https://github.com/WiFros/Alcoholic/blob/main/License)) © Richard Littauer

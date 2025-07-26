# SH Code Challenge

---

## 1st Commit

Create the application, and figure out what dependencies I'll need to do the challenge.
Although I understand Kotlin and took a few classes on the programming language during my undergraduate studies, I've never implemented any API in it, since all my mobile development projects were designed to be coded in Flutter. So it was necessary to do some global research to understand what I would need. Then, after researching, I realized that I would need some plugins like ksp and dependencies like retrofit, moshi, room.

So, after the research, I started preparing all the code by creating a breed class with all the necessary variables, an interface to get the races and also an object that will make the call to the API.
At the end, I created a view so that I could see if the call was made correctly. Obviously, the main point is to have a way of adding a breed to the favorites later.

### Note: The APY key was public in this commit. The main focus was to learn **how to work with API's in Kotlin**, since I've never worked with it before

---

## 2nd commit  

#### Implemented all the screens required. Created:
- **BreedListScreen.kt**: Composable screen that displays a searchable, scrollable list of cat breeds. Allows marking breeds as favorites and navigating to breed details. Handles error display.

- **BreedDetailScreen.kt**: Composable screen that displays detailed information about a selected cat breed, including image, origin, temperament, life span, and description.


But in order to do this, it was necessary to create what I call a “main” file, which defines the application's activity. I used two different files:
- **MainViewModel.kt**: Implements the app's main ViewModel, managing the list of cat breeds, search/filter logic, error handling, and favorite breeds. Fetches breed data from the API.

- **MainActivity.kt**: Sets up the app's main activity, navigation between breed list and breed detail screens, and initializes the ViewModel.



 #### In addition, I still have the 2 files created in the 1st commit:
 - Breed.kt: Defines the data models for a cat breed and its image, used for JSON parsing from the API.
 - CatApi.kt: Configures Retrofit and OkHttp for network requests to The Cat API, including authentication via API key. Defines the API service interface.


#### Basically, the code was developed to follow the MVVM architecture

Model - The Breed data class and API service (CatApiService) represent the data layer.

View - Composable screens like BreedListScreen and BreedDetailScreen display UI and interact with the ViewModel.

ViewModel - MainViewModel manages UI state, data fetching, and business logic.

---

## 3rd and final commit 

Finally I just had to enable offline functionality for breeds and favorites. It wasn't easy, because I never did this in a kotlin app, or any other app, because all my Android projects during university were mainly online services, which would be impossible to use offline due to the need to use payment APIs, location APIs, etc. 

Because of that, I had to do my own research about integrate the room into the app to implement offline functionality. After some research, I was able to create two entity classes: BreedEntity for storing cat breeds and FavoriteEntity for storing favorite breed IDs. I also made corresponding data access objects interfaces, BreedDao and FavoriteDao, to handle database operations like insert, query, and delete.

I set up the AppDatabase class to include both entities and DAOs, and set the database version to 2. At first, I forgot to update the version number after adding new entities, which caused a error where Room couldn't verify the data integrity After fixing the error, got another error saying migration from 1 to 2 was required but not found.

After spending almost half a day researching, found out this happened because Room detected a schema change but I didn’t know how to migrate the old data. To resolve this during development, I added .fallbackToDestructiveMigration() in the database builder, which tells Room to wipe and recreate the database if the schema changes, avoiding migration errors.

I then updated the MainViewModel to use the DAOs for loading, saving, and toggling favorites and breeds. The logic was changed so the app first loads breeds from the local database, then tries to update from the API, and saves results back to Room. I also updated the UI and MainActivity to use the new ViewModel constructor and reflect the new data flow.

In summary, despite knowing the basics of Kotlin, overall this challenge was a learning curve, as I had never implemented an API in Kotlin, nor implemented online functionality, since I never needed to in my projects during university.



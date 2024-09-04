#Astragram

Astagram is an Android app which uses NASA's API to fetch and display a collection of images. Users can view, mark favourites, set wallpaper and download their favourites.
We are aiming for a space themed UI, with some gamified icons and interactions. Images are stunning and taken from the real NASA database. 

Note: The logo and some asserts are designed by @KoviElango(me) using photoshop; I used a free font for non commercial use called Nasalization(Only for the logo).

##Screens:
###Home Screen:

Displays a list of images fetched from the NASA API.
Allows users to scroll through images.
Images are displayed with titles and descriptions.
Each image can be tapped to view a larger version.

###Favorites Screen:

Displays images marked as favorites by the user.
Allows users to set image as their wallpaper
Allows users to remove images from their favorites.
Images are loaded from local storage.

###Image Viewer(Popup):

Displays a full-screen view of the selected image.
Allows users to share or delete the image.
This is a open ended feature and can be expanded for future upgrades

##Application Flow

- App Launch:
  - Check for required permissions (e.g., reading media).
  - If permissions are not granted, prompt the user to allow access.
- Home Screen:
  - Fetch images from the NASA API (HomeViewModel).
  - Populate the imagesLiveData with the retrieved data.
  - Display the images in a scrolling list.
- Favorites Screen:
  - Load favorite images from local storage (FavoritesViewModel).
  - Display the images in a scrolling list.
  - Allow users to remove images from the favorites list.
  - Allow users to use image as wallpaper
- Image Interaction:
  - When a user selects an image, open the Image Viewer.
  - *In the Image Viewer, provide options to share or delete the image.(*Not in Scope)
  - *Update the UI accordingly after any action (share/delete).(*Not in Scope)

##Architecture: MVVM

##Libraries and Tools:
- Retrofit for API calls.
- Coil for image loading.
- Jetpack Compose for building UI components.
- Mockito and JUnit for testing.


##Requirements and Dependencies

###SDK Version:
Min SDK: 30
Target SDK: 34

###Libraries:
Retrofit, Coil, Mockito, JUnit, Jetpack Compose, AndroidX, etc.

###Permissions:
Internet, Read/Write Storage.

###Font:
Nasalization (Shout out to  Typodermic Fonts: https://www.dafont.com/nasalization.font?text=ASTRAgram&psize=s)

Please feel free to comment your feedbacks!



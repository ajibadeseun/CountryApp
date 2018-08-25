# CountryApp
App description
Country app is an app that shows the name , currency name and first official language of all the countries in the world. 
It fetches it data from https://restcountries.eu/rest/v2/all. 
The user of the app have the capacity to delete or remove any country from the list by simply swiping the selected country left or right.
The app's list of countries is being rendered on a recyclerview which comes with the Android SDKs(Software Development Kits).
To help detect the swipe-to-delete action of the user, an helper class called RecyclerItemTouchHelper was used on the recyclerview.
Also in order not to run network calls on the User Interface thread, an AsyncTaskLoader was used. This will ensure the network request 
does not cause the app to freeze but rather perform the network calls in the background thread. The AsyncTaskLoader also make sure that the app does not 
always begin fresh network request as the screen orientation of the user's device changes.

Recipe App
======

**It’s single activity app with MVVM architecture designed according to Google recommendations, as RestAPI used Edamam
Recipe Search API and implemented in app with Retrofit, used pagination(Paging 3), added caching via Room and Room is
used as local database for bookmarks, filter selected by user is saved in SharedPreferences and deep linking is
implemented via Firebase.**


Screenshots
-----------

|              Categories              |    Search     | Filter Dialog |
|:------------------------------------:|:-------------:|:-------------:|
|                 ![9]                 |     ![10]     |     ![11]     |
| __Detail screen with share options__ | __Bookmarks__ |               |
|                ![12]                 |     ![13]     |               |

Libraries and APIs used in this project
------------------------------

* [Lottie][2]
* [Retrofit][3]
* [Glide][4]
* [Paging 3][5]
* [Room][6]
* [Firebase][7]
* [Edamam Recipe API][8]

[APK][1]
-------

[1]: ./RecipeApp_1.0.0.apk
[2]: https://github.com/airbnb/lottie-android
[3]: https://square.github.io/retrofit/
[4]: https://github.com/bumptech/glide
[5]: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
[6]: https://developer.android.com/training/data-storage/room
[7]: https://firebase.google.com/products/dynamic-links
[8]: https://developer.edamam.com/edamam-docs-recipe-api
[9]: screenshots/category.jpg
[10]: screenshots/search.jpg
[11]: screenshots/filter.jpg
[12]: screenshots/detail.jpg
[13]: screenshots/bookmark.jpg
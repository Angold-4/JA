# School Query

In this project, we implemented many features aiming to provide a better user experience. Here are some of them:

## 1. Formatting
We designed a simple, clean and responsive UI in order to help user have a better experience on querying / looking for the information they want. For this, we design a tiny JSON file handler, which will preload all data in the original JSON file into Java objects upon the initialization. After initializing, the data will be present in the index page in a very clean and pretty form.

### Techniques: Lazy Loading
In our implementation, we discover that after initialization, the machine took too much time to render all school objects (about 10000) to the screen. Since we want a fast, responsive Index page, after initializing, instead of rendering all objects in the index page, we only render the first 100 objects after initialization, and every time the user scrolls to the end of page, we retrieve next 100 objects from our object list and render them.


## 2. Multi-language Switching
The clean index page helps user find their target quickly, when the user click one of these schools in the index page, he will enter the detail page of this specific school, where shows the information in a more vivid and precise way, such as its location in the form of map.

We also take care of different users by developing language-switching feature. The application displays data according to the user’s system language.


## 3. Searching
There are a over 10000 schools in Hong Kong, it is hard for user to find their target by just scrolling. For this, we developed the search bar. Using the filterView in Android. Every time when user types some school name in the search bar, the program will refresh the index page by checking whether each school name contains the user input.


## 4. Filtering
In many cases, it is not enough for the user to find its target school by just specify its name. For instance, sometimes the user do not pretty clear about the school name, but know exactly the school level or the district of that school. Or sometimes they may just want to look up all school in some specific region / level. For this, we developed the filtering feature. 

With the filtering support, the user can find their target in a relatively simple and convenient way – find some specific school by giving the district, school level, and part of the name at the same time.


### Techniques: Filtering
In our implementation, filtering is just some sort of  “searching in some specific area”. Every time the user select some range on the top bar, such as “Aid Primary School”, our application will change the searching data set. And with the help of many condition variables, our program can easily perform many kinds of filtering – such as multiple filtering, filtering based on the last searching results, etc. That will make our application perform its operation in a more natural way.






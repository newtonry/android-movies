## Week 1 Project: Flicks

This is the ToDo app described here: http://courses.codepath.com/snippets/intro_to_android/prework.md

### Features

Hrs spent: 15?

#### Required
- [x] User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.
- [x] Lists should be fully optimized for performance with the ViewHolder pattern.
- [x] Views should be responsive for both landscape/portrait mode.
	[x] In portrait mode, the poster image, title, and movie overview is shown.
	[x] In landscape mode, the rotated layout should use the backdrop image instead and show the title and movie overview to the right of it.


#### Optional

- [x] Add pull-to-refresh for popular stream with SwipeRefreshLayout (1 point)
- [x] Display a nice default placeholder graphic for each image during loading (read more about Picasso) (1 point)
- [x] Improve the user interface through styling and coloring (1 to 5 points depending on the difficulty of UI improvements)
- [x] For popular movies (i.e. a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Use Heterogenous ListViews and use different ViewHolder layout files for popular movies and less popular ones. (2 points)
- [x] Stretch: Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity. (3 points)
- [x] Stretch: Allow video posts to be played in full-screen using the YouTubePlayerView (2 points)
	[ ] When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.
	[ ] Less popular videos rely on the detailed page should show an image preview that can initiate playing a YouTube video.
	[ ] See the trailers API for video information. Here's a sample request.
- [x] Stretch: Add a play icon overlay to popular movies to indicate that the movie can be played (1 point).
- [ ] Stretch: Leverage the data binding support module to bind data into one or more activity layout templates. (1 point)
- [ ] Stretch: Apply the popular ButterKnife annotation library to reduce view boilerplate. (1 point)
- [x] Stretch: Add a rounded corners for the images using the Picasso transformations. (1 point). I removed it b/c it fit my style without it

![Video Walkthrough](flicks-1.gif)
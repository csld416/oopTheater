
# Log

## 04/18 
I established the connection between the main page and the login/register. To do that, i introduced the `session manager` class, in which there is a 'email' section where the classes of this appication can check on it, and to verify if the users has logged in. If not, then whenever the end user tries to use the functions that requires the user database we redirect to login section.

oh yeah i also did many other things.....
this feels good, it is 2:46 am now, and i started to see the light of this project.

## 04/19
I added the Login page and the register page of the admin in this application, and I will update the database table accrodgingly in the recent days.

## 04/21

I added the `dimmed layer` class so as to mimic the visual effect that it dims the current page if the user presses the panel of the `toggle list` in the top bar panel.

Moreover, I also implemented the visual effect when the `toggle list` button is pressed, the page shows up and if the mouse press anywhere else than the popped up page, the list disappears.

## 04/22

I established the login/register for staff in the toggle list. Different from the user register, this register for admin requires lisence code, predefined, given by the supervisor.

I created a public class for storing the public UI constants, and after the constuction of the app, the goal is to be robust to the window resizement.

## 04/23

I seperated the component in the `TopBarPanel` out, the goal here is to make everything reusable. As a package, if one may.

I constructed the database for the Movie, and created the backstage page for the staff to load a movie or make modification with the existing movie. In which I first create another panel as a substrate, and mimic the logic when I was implementing the `Register` Page.

Also, my partner `yichou` provided the logo of this cinema, which is a wombat. Well, cute, if you may.


## 04/24

Added the color gradient for the `TopBarPanel`. Also, I reconfigured the Panel Buttons into pure code implementation, where previously was formed using the Netbeans GUI designing feature.

I realized that using pure code to design brings flexibility, whereas the GUI design gives more like a outline. Therefore, one should first design as primitive and intuitive as possible, later, we use mathematical model or other component that allows computation for accuracy and flexibility.

Note: The next is the register for the movies in the `Backstages` of the staff.

## 04/25

I added the logic to add the movie to the database, also created the movie slot(card) so that when the user press that, the stored accoding value and the properties shows up accordingly to the right panel where the user can make further modifications.

oh yeah and i also added the hover effect when the mouse enter the movie slot.

problem: when modified, the code creates a new data in the data table, this should be prohibited.

## 04/26

Note: problem fixed, now the version is good. Also added a feature to detect if the movie to be inserted has a duplicate in the current database and the bar for evaluation is the title, duration, and the description.

Now that these are done, I am thinking of adding a `display` button in the movie slot as a mini button for enabling or disabling the display in the `StartingPage`.

I added the `StartingPage` from the GUI design into a pure code-based design, mimicing the process I created the MovieFrame class, I first initilized the top bar slot and the down panel for the movie cards container. Later, I updated the `MovieCardPanel` class, resize the movie posters in order to make the resolution higher. By the way I created a ButtonPanel class as a reusable object where the constructor takes in the displaying text and two differnt colors as a default and the hover color. 

Currently, the layout of the application seems decent. However, I would like to further add the arrow buttons so as to traverse between the movie lists. Furthermore, I will add a sorting or filter like the one in `Notion`, to increase flexibility of the user interface and fast searching.

Lastly, I added a python file just to count the lines of code that I have dedicated myself into this.

## 04/27

Many things that I did today, first things first I changed the Login and the Register in the user mode to act just like the toggle list, where the frame appears and disappears it the user click elsewhere from the popping frame. Note that I added auxiliary boolean variable so as to act as a lock to prevent the frame from disposing.

Secondly, I updated the `ToggleList` class in pure code instead of using builtin GUI. With the intention of this, I updated all the panel buttons with the pure code, the proper images and the color and bounding logic.

Big work, i must say, UI is a pretty tough field to get involved.

I started working on the user space scope, where the user are able to modify the data of the personal peripherals. That includes purchasing record, change of password, and change of data, and log out button.

jesus this hurts my brain, pretty much. FUCK.

Oh yeah I also seperated the constants for this user scope, there are even packages under packages, jesus chirst.

yeah I think I've been working on this shit for so long, jesus chirst we arent even in the purchase yet, and the database for the ticket and the revenue? 💀 come on.

But I always figure it out, I always do, gotta have faith.

the next, and probably the final stage: purchase. oh, ho ho ho. this is where things get really interested, isnt it? It all started with the "book online" from the moviecardpanel.
As we presses the very button, we open a gate that leads to the circulation of the assets. This is where the things gets relaly escalated and hard. Hope I survive from this.

## 04/30

Took some days off, (for the OS assignment)

ok, today i created the panel for the big theater.
and the small one.

established the database of the rooms, the showtime, the ticket.
tons of work. FUCK YOU. I need to take more days off, come on.
all of this for what? does this even get appreciated?

started to create the whole interface for the admin part, like the users.

a admin main page and a variant of topbar that directly links back to that, also in the mainpage we have a bunch of buttons that leads to everywhere in the admin pages, so as you can see it becames united, good.


OK, and um, most of the work is at the SHOWTIME establishment, this is god pain in the ass, I have to link between the data tables, then i have to seperate the conditions and create a bunch of instances that calls on forth to one another, fucking jesus chirst.

I give myself today a 2/10, fucking awful, but as I know i always return with a better mental state, stay tuned, I'll come back.

## 05/01

JEEEEEZ, I want this to end, like, really fast.

Anyways, I established the `Showtime` class, and the register for the Showtime, seperate the panel of the show showtime list and the regisration.

FUCK, BoxLayout is the most pure piece of shit I've ever seen in my life.

the logic of the validation needs to be patched, there are more logical flaws that needs correction.

## 05/02

I designed the page for the booking large room.

## 05/03

Designed the booking page for the small room, integrate the big room and the small room to the booking feature.
I created the entry class for storing the Showtime of the movies that it has in the database.

Another 6 hour passes, the FUCK.

## 05/04

Added the page for theater management. and updated the database relationship of the seats and the theaters, as well as the showtimes.

patched the logic of the showtime entry in the movie booking section, fucking SQL. The next should be the patching of the seat chosen to the booking stage. Also verify if the seat is actually booked, the layout would change the color of that.

## 05/06

Since we are working on the food part, we need to implement the food register part first in order for the necessary goods to appear on the client side.

created the food entry, admin page, and the register.

next is the image of that.

## 05/08

Update the images of the food, and the display in the client side. Same rule, make the corner rounded, and the approriate color. 

Other than that, I connect the logic of the action of pressing the increment and the decrement label on the entry to the right panel of the food choosing page, and use the html for displaying.

## 05/09

Added the ConfirmOrderPage, which lists the order of a customer, also added the terms and conditions along with the dim layer effect.

Added the confirm rounded corner Jframe.

Now linking to Paypage, which is the final step.

Major update, I added the Order class to replace the primitive way to pass the movie, showtime, seatList, FoodList, etc.
Next step, we will store the order in the Database, shift to the ticket page, and update the ticket displaying panel.

so i should create a Ticket.java, and a database table for Ticket, which has:
- id
- user id(cascade)
- movie id (cascade)
- showtime (cascade)
- seatList no cascade
- FoodList, no cascade

so work in database, and so on and so forth, we are 96% close, come on, let's go.

## 05/10
- Established the workflow of the data, and patched the final piece of the projet --ticket
- Now a ticket can be stored in a database, I am creating a env for that.
For the ticket page, I am dividing it with two different panels, one for `already used` one for `Not yet used`.
and created the scroll pane and the box layout to contain the bunch of the tickets. as for the unused tickets. Nice.
then i will alter the loading panel for the used tickets, and add red stamp to that. ah, looking good, we are close guys!!! lets go!!

## 05/11

Designed the layout of the `ALready used` ticket, and divide that into the left panel and the right panel (`ChatGPT sucks`) FR, my gut has always been correct, and this shit keep telling me the wrong answer, but I am better.
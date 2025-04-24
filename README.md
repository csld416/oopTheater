
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


## 04/25

Added the color gradient for the `TopBarPanel`. Also, I reconfigured the Panel Buttons into pure code implementation, where previously was formed using the Netbeans GUI designing feature.

I realized that using pure code to design brings flexibility, whereas the GUI design gives more like a outline. Therefore, one should first design as primitive and intuitive as possible, later, we use mathematical model or other component that allows computation for accuracy and flexibility.

Note: The next is the register for the movies in the `Backstages` of the staff.
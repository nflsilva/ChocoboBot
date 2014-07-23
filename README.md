ChocoboBot
==========

A very basic computer program that is capable of identifying Chocobo World in-game events and act in response. 

This program identifies events using screenshots from the game.
After that, it compares the screen image with the frames in folder "images", identifing which event is taking place. 
Finaly, with this, the bot finds the adequate action for the event and acts with it.

Good points:
The program works without problems. It's possible to leave it running when the computer is inactive and wont need supervision.
It fights the mobs as it would be expected from a human. It's very easy to reach maximum level and find alot of items.

Bad points:
The program can't simulate the arrow key pressing as the game requires. As such, the solution is to use the mouse and the windows on screen keyboard.
This isn't very effective. The mouse is used by the program and is not possible to use the computer while running the software.
It's still uncompleted. There are a lot of debug messages on the console and the interface isn't the best.
The coordinates for the on screen keyboard and hard coded in the App class. you need to modify it for your use.


How to run:
On eclipse:

On Console:

Still left to do:
- Automatic acquire the game's window coordinates. At the moment, this is only achievable by trial and error. These coordinates are the base of the program. Having these wrong will lead to wrong screenshots and the program won't identify events. 
- Conceive a way to input the arrow keys without clicking in the screen keyboard. It seems that is not privial to emulate the "push" button. The program uses an instance of class Robot to simulate inputs.However, the methods doesn't achieve the same result as the screen keyboard, which are the correct ones. 
- Optimize the input module, so it can send keys to the window without making it active. Doing so, it would be possible to use the program, and still be able to use the computer.


Extra notes:
It is not intended to ruin or change the spirit of the game. Players can still enjoy Chocobo World as it is. This project is for curiosity, enjoyment and knowledge purposes only. 

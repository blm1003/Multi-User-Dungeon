# Multi-User-Dungeon
Java chatroom with an integrated bots designed for a Multi User Dungeon text-based rpg.

## Directory Information

Folders: Server, Client, Common, Bots, roomFiles

Server has the server threads and info (Main, Login Thread, Choose Room Thread, Room Thread). Client has the client main and a parser / printer thread as well as GUI information (contained in the subdirectory "view"). The Bots folder is just the thread, started by the server that runs any specified bot. The roomFiles folder contains room data (held in text) for the Dungeon Bot. As a sub directory, each room has a folder containing the text files representing each specific room.

## Current Bots

Currently, there are two bots. One Echo Bot, built for test purposes, and the multi-user-dungeon bot. 

## Writing For the Dungeon Bot

I want to give a special should out to a good friend of mine. Going by the name of 'unclear-contributions', they have helped write more room files than I would have been able to myself. To see their writing with the full force of a world behind, you can head to their wordpress site: https://sevengodslegendarium.wordpress.com/

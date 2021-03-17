# Discord-Bot
This is a discord bot! 

Below is a description of what it can do

1. Music Functions
   - can display the songs left to play in the queue
   - will display a list of songs already played
   - can loop a song, or loop entire queue
   - can skip a song, play previous song, or remove a song from the queue
   - has announcements for every command, when queue ends and when a new song starts playing
   - song search function uses spotify's API to queue spotify playlists and songs. It can also web scrape Youtube's searching query when the user does not input a spotify link          (web scraping is slow but Youtube API only allows a set number of searches). It then returns a list of possible song matches.

2. Bot Currency
   - has a daily function for users to collect currecy daily
   - sends a currency update message after every change in currency
   - has a betting function where users can play a coin flip game and bet their bot currency
   - users can send each other bot currency
   
3. Admin Commands
   - these commends can: ban deafen, kick, give roles, mute, remove roles, unban, undeafen, and unmute users
   - can also prune memebers in the server

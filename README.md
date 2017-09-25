# InvinciBagel

	JavaFX 8.0 Game built alongside a basic game engine framework


- [x] !!! Please take your time to read this + the other .md files. I must confess that I AM NOT GOOD AT WRITING DOCUMENTATION - although I have good ideas during development, when coming to "write my post-work journal" I don't get it at expected (my ideas / inspiration stuck ...). In other order of ideas, my write-style is funny, I ascertain, so you will BENEFIT from READING MY DOCS !!!


### (Legal) Notice [...]

This project is based on the one presented along my reading of the book "Beginning Java 8 Games Development" by Wallace Jackson - published by Apress (2014). I HAVE MADE REFINEMENTS, that means engineered the code to be more optimized / original, else the structure remains the same to that of the authors you can find in the Legal Menu of my Application !


### What makes this Repo' different ?

In what equals to 1346 lines of code lies a minimal game engine which can at any time be reused and moreover developed further. Essential is the fact that a game - InvinciBagel- is already built on top of it to demonstrate basic capabilities. This Java 8 "structure" considers memory and processor overhead optimizations wherever possible, more precisely :
* Scene Graph "population" is minimal : I reused Node objects whenever possible, mostly for UI objects - whose only Sprite Objects i re-refferenced to display other images, and changed their Z-index to maintain consistency
* Assets are optimized to minimum memory usage, but optimal quality preserved is considered - equilibrium principle ...
* Various Code-Engineering optimizations are applied where my knowledge (though mostly intuition) allowed me to !


### Who I want to thank ?

* The book, which and also the repository you are viewing can't be described better than with the author's own ending words : 
> I’ve tried to go beyond a basic beginner Java 8 book and show you the work process involved in creating a game engine infrastructure, including the design thought process, how to leverage key classes in Java 8 and JavaFX 8.0, and how to use new media assets and optimization techniques. 

* (The author &)All the personnel that made this book "magically" appear at my disposal - and that merrily condemned my last month of Summer University Holiday [and probably the most part of my life, at least till retirement >D]

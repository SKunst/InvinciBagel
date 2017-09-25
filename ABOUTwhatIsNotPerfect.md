I realize that this game engine & implementation is still unpolished (raw), as it is still my first Game, after all ...
More specificaly, I think these further things could be taken into consideration in my next (CODING related) Projects :

# Even better code MODULARIZATION - inside classes & structure (High-Level-View) also ...
# Use more "has-a" relations inside classes - i.e. : make an Entity object and inside let it "aggregate" Location (int x, int y) and Translation(int speedonX, speedonY) classes, and why not even include Translation inside Location object ... I keep "seeing" better approaches in my mind every day !
# Etc. - really I lost my inspiration right now, but you can see yourself I need some "Design Patterns" courses along with more experience at my belt, haha :D

Last, but not least, what I didn't like at JavaFX API collection is - write a lot of frenzy stuff here - @@@ the Applied/Not Applied Transformations different location coordinates ... also I couldn't obtain bounds at several times [...]. @@@
But that is most certainly because this API is really high-level and a lot of things are implemented "under-the-hood" =>

=> The next time I will build my engine "from the scratch", and that means I will have to choose a more low-level API framework to leverage my own classes (probably DirectX - Windows GURUs, beware!)
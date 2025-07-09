peer to peer connections 
maintaining the peer to peer network

. . #
. . #
# . #

. . #
X . #
# . #


X = 3
# = 2
. = 1
they are arranged in grid and if they are active they are dots(.) if they are inactive '#'
-> few peers went offline 
-> take some peers and offload to another network
-> what all pairs to remove so that the remaining peers are still connected
    -> only remove online nodes
    -> k < no of dots available
    -> up down left right
    -> input will always have connected nodes 

. . #
. . #
# . #
k = 2

1 1 2
1 1 2
2 1 2

. . #
X . #
# . #

# . #
. . #
# . #

BFS algo which will traverse from the first dot we see

3
3
1 1 2
1 1 2
2 1 2

7
7
2 1 1 1 1 1 2
1 1 2 1 2 1 1
1 2 1 1 1 2 1
1 1 1 2 1 1 1
1 2 1 1 1 2 1
1 1 2 1 2 1 1
2 1 1 1 1 1 2

18
# skynet
online network challenge (Level: hard)

Description of original challenge (Level: medium) from codingame.com:

THE PROGRAM:
 
Your virus has caused a backdoor to open on the Skynet network enabling you to send new instructions in real time.

You decide to take action by stopping Skynet from communicating on its own internal network.

Skynet's network is divided into several smaller network, in each sub-network is a Skynet agent tasked with transfering information by moving from node to node along links and accessing gateways leading to other sub-networks.

Your mission is to reprogram the virus so it will sever links in such a way that the Skynet Agent is unable to access another sub-network thus preventing information concerning the presence of our virus to reach Skynet's central hub.
 

Gateway

For each test you are given:
A map of the network.
The position of the exit gateways.
The starting position of the Skynet agent.
>>> Nodes can only be connected to up to a single gatweway. <<<


Each game turn:
First off, you sever one of the given links in the network.
Then the Skynet agent moves from one Node to another accessible Node.
 
 
The game is over when :
The Skynet agent cannot reach an exit gateway: mission successfully completed :)
The Skynet agent has reached a gateway: mission failed :(
 
The program must first read the initialization data from standard input. Then, within an infinite loop, read the data from the standard input related to the current state of the Skynet agent and provide to the standard output the next instruction.

Don’t forget to run all the tests by modifying the value of the “test” variable (1, 2, 3, 4) in the “Test script” window

The tests provided are similar to the validation tests used to compute the final score but remain different.
 
INITIALIZATION INPUT:
Line 1 : 3 integers N L E
N the total number of nodes in the level, including the gateways.
L the number of links in the level.
E the number of exit gateways in the level.
Next L lines: two integers per line (N1, N2), indicating a link between the nodes indexed N1 and N2 in the network.
Next E lines: on each line, an integer EI representing the index of a gateway node.
 
 
INPUT FOR ONE GAME TURN:
Line 1: one integer SI. SI is the index of the node on which the Skynet agent is positioned this turn.
 
OUTPUT FOR ONE GAME TURN:
A single line comprised of two integers C1 and C2 seperated by a space. C1 and C2 are the indices of the nodes you wish to sever the link between.
 
CONSTRAINTS:
2 ≤ N ≤ 500
1 ≤ L ≤ 1000
1 ≤ E ≤ 20
0 ≤ N1, N2 < N
0 ≤ SI < N
0 ≤ C1, C2 < N

Nodes can only be connected to up to a single gatweway.
Response time for one game turn ≤ 150ms
 
EXAMPLE:
Initialization input (out of the infinite loop)	No output expected
4 4 1
0 1
0 2
1 3
2 3
3	(N L E)
(N1 N2)
(N1 N2)
(N1 N2)
(N1 N2)
(EI)	4 nodes, 4 links, 1 gateway	 
Input for turn 1	Output for turn 1
0	(SI)	The Skynet agent starts at node 0
	1 3
Our virus severs the link between nodes 1 and 3
Input for turn 2	Output for turn 2
2	(SI)	The Skynet agent moves to node 2
	2 3
Our virus severs the link between nodes 2 and 3
The Skynet agent has been cut off from the exit, you have won !

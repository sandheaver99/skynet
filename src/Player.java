import java.util.*;
import java.io.*;
import java.math.*;



class Player
{
	private static final int LINK_COST = 1;
	Network skynet;
	Scanner in = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		Player player = new Player();
		player.go();
	}
	
	private void go()
	{
		initialise();
		printNetwork();	
		loop();	
		//System.out.println("1 2");	
	}
	
	private void initialise()
	{		
		int numberNodes = in.nextInt(); // the total number of nodes in the level, including the gateways
        int numberLinks = in.nextInt(); // the number of links
        int numberExits = in.nextInt(); // the number of exit gateways
        
		List<Node> nodes = new ArrayList<Node>();
        for(int i = 0; i < numberNodes; i++)
        {
			Node n = new Node( i+"", "Node " + (i+""));
			nodes.add(n);
		}			
        
        List<Link> links = new ArrayList<Link>();
        
        for (int i = 0; i < numberLinks; i++) 
        {
            int aNode = in.nextInt(); // nodeA and nodeB defines a link between these nodes
            int bNode = in.nextInt();
            
            Link l = new Link(i+"", nodes.get(aNode), nodes.get(bNode), Player.LINK_COST);
            Link lReverse = new Link(i+"", nodes.get(bNode), nodes.get(aNode), Player.LINK_COST);
           
            links.add(l);
            links.add(lReverse);
        }
        
        List<Integer> exits = new ArrayList<Integer>();
        
        for (int i = 0; i < numberExits; i++)
        {
            int exitIndex = in.nextInt(); // the index of a gateway node
            
            exits.add(exitIndex);
        }
        
        skynet = new Network(nodes, links, exits);
        
        
	}
	
	private void loop()
	{
		while (true)
		{
            int si = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            
                        
            LinkedList<Node> bestPath = null;
            LinkedList<Node> path = null;
            
            for(Integer exit: skynet.getExits())
            {
				path = getCurrentPath(si, (int) exit);
				
				
				if(path == null)
				{
				    continue;
				}
				
				if(bestPath == null)
				{
					bestPath = path;
				}
				else if(path.size() < bestPath.size())
				{
					bestPath = path;
				}
			}
			
			int last = bestPath.size()-1;
			String actionString = bestPath.get(last-1).getId();					
			actionString += " " + bestPath.get(last).getId();
			
			List<Link> forRemoval = new ArrayList<Link>();
			for(Link l:skynet.getLinks())
			{
				if(linkToSever(l, bestPath))
				{
					forRemoval.add(l);
				}
			}
			for(Link l:forRemoval)
			{
				skynet.getLinks().remove(l);
			}
					
            
            

            System.out.println(actionString); // Example: 0 1 are the indices of the nodes you wish to sever the link between
			
		}
	}
	
	private boolean linkToSever(Link l, LinkedList<Node> bestPath)
	{
		int last = bestPath.size()-1;
		if(l.getSource().getId() == bestPath.get(last-1).getId() && l.getDestination().getId() == bestPath.get(last).getId())
		{
			return true;
		}
		if(l.getSource().getId() == bestPath.get(last).getId() && l.getDestination().getId() == bestPath.get(last-1).getId())
		{
			return true;
		}
		return false;
	}
		
		
	
	private LinkedList<Node> getCurrentPath(int si, int exit)
	{
		// Lets check from location Loc_si to Loc_exit 
		   
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(skynet);
		dijkstra.execute(skynet.getNodes().get(si));
		LinkedList<Node> path = dijkstra.getPath(skynet.getNodes().get(exit));
    
		//assertNotNull(path);
		//assertTrue(path.size() > 0);
    
		System.err.println("The agent is at Node " + si);
		System.err.println("There is an exit at Node " + exit);
		System.err.println("The path between agent and exit is ");
		
		if(path == null)
		{
		    System.err.println(" null!");
		    return null;
		}
		
		for (Node node : path) 
		{			
			System.err.print(node + " , ");			
		}
		System.err.println();
		
		return path;
	}
	
	private void printNetwork()
	{
		for(Node n: skynet.getNodes())
		{
			System.err.println("Node " + n.getId());				
		}
	}	
}

class Network
{
	private static final int BIG_DISTANCE = 999999;
	
	private final List<Node> nodes;
	private final List<Link> links;
	private final List<Integer> exitIndexes;
	
	public Network(List<Node> nodes, List<Link>links, List<Integer>exits)
	{
		this.nodes = nodes;
		this.links = links;
		this.exitIndexes = exits;
	}
	
	public List<Node> getNodes()
	{
		return nodes;
	}
	
	public List<Link> getLinks()
	{
		return links;
	}
	
	public List<Integer> getExits()
	{
		return exitIndexes;
	}
}		

class Node
{
	private final String id;
	private final String name;
	
	public Node(String id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0: id.hashCode());
		return result; //return either 31 or ?? endless loop??
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)	return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Node other = (Node) obj;
		if(id == null)
		{
			if(other.id != null) return false;
		}
		else if (!id.equals(other.id)) return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}

class Link
{
	private final String id;
	private final Node source;
	private final Node destination;
	private int cost;
	
	
	public Link(String id, Node source, Node destination, int cost)
	{
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.cost = cost;
	}
	
	public String getId()
	{
		return id;
	}
	
	public Node getSource()
	{
		return source;
	}
	
	public Node getDestination()
	{
		return destination;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	@Override
	public String toString()
	{
		return source + "" + destination;
	}
}

class DijkstraAlgorithm
{
	private final List<Node> nodes;
	private final List<Link> links;
	
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	
	private Map<Node, Node> parents;
	private Map<Node, Integer> distance;
	
	public DijkstraAlgorithm(Network network)
	{
		// create a copy of the array so that we can operate on it
		this.nodes = new ArrayList<Node>(network.getNodes());
		this.links = new ArrayList<Link>(network.getLinks());
	}
	
	public void execute(Node source)
	{
		System.err.println("Executing Dijkstra Algorithm using source node " + source.getId());
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		
		distance = new HashMap<Node, Integer>();
		parents = new HashMap<Node, Node>();
		
		distance.put(source, 0);
		unSettledNodes.add(source);
		
		while(unSettledNodes.size() > 0)
		{
			Node node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}
	
	private void findMinimalDistances(Node node)
	{
		List<Node> adjacentNodes = getNeighbours(node);
		for(Node target: adjacentNodes)
		{
			//System.err.println("Examining node " + target.getId() + " which is an adjacent node of node " + node.getId()); 
			if(getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target))
			{
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				parents.put(target, node);
				//System.err.println("Added parent " + node.getId() + " to node " + target.getId());
				unSettledNodes.add(target);
			}
		}
	}
	
	private int getDistance(Node node, Node target)
	{
		for(Link link: links)
		{
			if (link.getSource().equals(node) && link.getDestination().equals(target))
			{
				return link.getCost();
			}
			
		}
		throw new RuntimeException("Should not happen");
	}
	
	private List<Node> getNeighbours(Node node)
	{
		List<Node> neighbours = new ArrayList<Node>();
		for(Link link: links)
		{
			
			if(isNeighbourLink(node, link))
			{
				neighbours.add(link.getDestination());				
			}				
		}
		return neighbours;
	}
	
	private boolean isNeighbourLink(Node node, Link link)
	{
		if(link.getSource().equals(node) && !isSettled(link.getDestination()))
		{
			return true;
		}
		
		return false;
	}
	
	private Node getMinimum(Set<Node> nodes)
	{
		Node minimum = null;
		for(Node node:nodes)
		{
			if (minimum == null)
			{
				minimum = node;
			}
			else
			{
				if(getShortestDistance(node) < getShortestDistance(minimum))
				{
					minimum = node;
				}
			}
		}
		return minimum;
	}
	
	private boolean isSettled(Node node)
	{
		return settledNodes.contains(node);
	}
	
	private int getShortestDistance(Node destination)
	{
		Integer d = distance.get(destination);
		if(d == null)
		{
			return Integer.MAX_VALUE;
		}
		else
		{
			return d;
		}
	}
	
	//The following method returns the path from the 
	// source to the selected target and NULL if no path exist
	public LinkedList<Node> getPath(Node target)
	{
		LinkedList<Node> path = new LinkedList<Node>();
		Node step = target;
		
		///check if path exists
		if(parents.get(step) == null)
		{
			return null;
		}
		
		path.add(step);
		
		while(parents.get(step) != null)
		{
			step = parents.get(step);
			path.add(step);
		}
		
		//put in the correct order
		Collections.reverse(path);
		return path;
	}
}
		
		
		
			

		
		
		
		





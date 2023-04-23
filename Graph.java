// jdh CS224B Spring 2023

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
  List<Node> nodes;

  public Graph() {
    this.nodes = new ArrayList<Node>();
  }

  public void addNode(Node n) {
    this.nodes.add(n);
  }

  public void addEdge(Node n1, Node n2, int capacity) {
    this.addEdge(n1, n2, capacity, 0);
  }

  //----------------------------------------------------------------

  public void addEdge(Node n1, Node n2, int capacity, int flow) {
    Edge e1 = new Edge(n1, n2, capacity, flow);
    assert(flow <= capacity);
    int idx1 = this.nodes.indexOf(n1);
    if (idx1 >= 0) {
      this.nodes.get(idx1).add(e1);
    } else {
      System.out.println("node " + n1.name + " not found in graph");
    }
  }

  //----------------------------------------------------------------

  private void addResidualEdge(Node n1, Node n2, int capacity, boolean isBackward) {
    Edge e1 = new Edge(n1, n2, capacity, isBackward);
    int idx1 = this.nodes.indexOf(n1);
    if (idx1 >= 0) {
      this.nodes.get(idx1).addResidualEdge(e1);
    } else {
      System.out.println("node " + n1.name + " not found in graph");
    }
  }

  //----------------------------------------------------------------

  public void print() {
    for (Node n: this.nodes) {
      System.out.print("Node " + n.name + ":");
      for (Edge edge: n.adjlist) {
        System.out.print(" " + edge.n2.name + " (c=" + edge.capacity);
        System.out.print(", f=" + edge.flow + ")");
      }
      System.out.println();
    }
  }

  //----------------------------------------------------------------

  private void printResidual() {
    for (Node n: this.nodes) {
      System.out.print("Node " + n.name + ":");
      for (Edge edge: n.adjlistResid) {
        System.out.print(" " + edge.n2.name + " (c=" + edge.capacity);
        if (edge.isBackward)
          System.out.print(" <=");
        System.out.print(")");
      }
      System.out.println();
    }
  }

  //----------------------------------------------------------------

  private List<Edge> findPathInResid(Node s, Node t) {
    int i, k, idx;
    boolean done, found;
    Node n1, n2;

    List<Edge> path = new ArrayList<Edge>();

    Stack<Node> stack = new Stack<Node>();
    boolean explored[] = new boolean[1 + this.nodes.size()];
    int parent[] = new int[1+this.nodes.size()];

    for (i=0; i<=this.nodes.size(); ++i)
      explored[i] = false;

    done = false;
    stack.push(s);
    while ( ! done && ! stack.empty() ) {
      n1 = stack.pop();
      if ( ! explored[n1.name] ) {
        explored[n1.name] = true;
        if (parent[n1.name] != 0)
          System.out.println("tree: " + n1.name + " -> " + parent[n1.name]);
        for (Edge edge: n1.adjlistResid) {
          n2 = edge.n2;
          if ( ! explored[n2.name] ) {
            stack.push(n2);
            parent[n2.name] = n1.name;
            if (n2.name == t.name)
              done = true;
          }
        }
      }
    }

    System.out.println("here's the backward path from " + t.name);
    done = false;
    idx = t.name;
    while ( ! done ) {
      if (parent[idx] == 0)
        done = true;
      else {
        System.out.println(parent[idx] + " to " + idx);
        // find the edge from parent[idx] to idx
        found = false;
        k = 0;
        while ( ! found && k < nodes.size()) {
          if (nodes.get(k).name == parent[idx])
            found = true;
          else
            k = k + 1;
        }
        n1 = nodes.get(k);
        found = false;
        for (k=0; ! found && k<n1.adjlistResid.size(); ++k) {
          Edge e = n1.adjlistResid.get(k);
          if (e.n2.name == idx) {
            path.add(e);
            found = true;
          }
        }
        idx = parent[idx];
      }
    }

    System.out.println();
    return path;
  } // findPathInResid()

  //----------------------------------------------------------------

  public boolean checkFlow(Node s, Node t) {
    // check that flow out of s == flow into t
    // check conservation condition at each node

    
    List<Edge> edges = getEdges();
    int s_flow = 0;
    int t_flow = 0;

    for(Edge e : edges)
    {
        if(e.n1 == s)
        {
            s_flow = e.flow;
        }
        if(e.n2 == t)
        {
            t_flow = e.flow;
        }
    }

    if(s_flow != t_flow)
    {
        System.out.println("s flow is " + s_flow);
        System.out.println("t flow is " + t_flow);
        return false;
    }

    return true;

  }

  //----------------------------------------------------------------

  private void constructResidualGraph(int delta) {
    // implement this
    for(Node n : nodes)
        n.adjlistResid.clear();
    
    

    

        


  } // constructResidualGraph()

  //----------------------------------------------------------------

  private int findBottleneck(List<Edge> path) {
    // implement this
    // not really good
    int bottleneck = 100;
    for(Edge e : path)
    {
        if(e.flow < bottleneck)
            bottleneck = e.flow;
    }
    return bottleneck;
  }

  //----------------------------------------------------------------

  private void augment(List<Edge> path) {
    // implement this
    // IMPLEMENTED
    int b = findBottleneck(path);
    for(Edge e : path)
    {
        if(e.isBackward == false)
            e.flow += b;
        else
        {
            e = new Edge(e.n2, e.n2, e.capacity, e.flow);
            e.flow -= b;
        }
    }
  }

  //----------------------------------------------------------------

  public int maxFlow(Node s, Node t) {
    // implement this
    List<Edge> edges = getEdges();

    for(Edge e : edges)
    {
        e.flow = 0;
    }


    // while there is an s t path in the residual graph
    while(findPathInResid(s, t).size() >= 0)
    {
        List<Edge> path = findPathInResid(s, t);
        augment(path);
        constructResidualGraph(1);


    }


    System.out.println("max flow is " + flow);

    return flow;
  } // maxFlow()



  public List<Edge> getEdges(){
    // IMPLEMENTED
    List<Edge> edges = new ArrayList<Edge>();

    for(Node n : nodes)
    {
        for(Edge e : n.adjlist)
            edges.add(e);
    }
    return edges;
  }

}
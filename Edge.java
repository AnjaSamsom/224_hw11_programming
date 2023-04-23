// jdh CS 224B Spring 2023
// DO NOT CHANGE

public class Edge {
  int capacity;
  int flow;
  boolean isBackward;
  Node n1;
  Node n2;

  public Edge(Node n1, Node n2, int capacity, boolean isBackward) {
    this(n1, n2, capacity, 0, isBackward);
  }

  public Edge(Node n1, Node n2, int capacity, int flow) {
    this.n1 = n1;
    this.n2 = n2;
    this.capacity = capacity;
    this.flow = flow;
    this.isBackward = false;
  }

  public Edge(Node n1, Node n2, int capacity, int flow, boolean isBackward) {
    this.n1 = n1;
    this.n2 = n2;
    this.capacity = capacity;
    this.flow = flow;
    this.isBackward = isBackward;
  }

  public String toString() {
    String s = n1.name + " -> " + n2.name + " (c=" + capacity + ", f=" + flow + ")";
    return s;
  }
}
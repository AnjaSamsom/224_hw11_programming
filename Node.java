// jdh CS224B Spring 2023
// DO NOT CHANGE

import java.util.ArrayList;
import java.util.List;

public class Node {
  int name;
  List<Edge> adjlist;
  List<Edge> adjlistResid;

  public Node(int name) {
    this.name = name;
    this.adjlist = new ArrayList<Edge>();
    this.adjlistResid = new ArrayList<Edge>();
  }

  public void add(Edge edge) {
    this.adjlist.add(edge);
  }

  public void addResidualEdge(Edge edge) {
    this.adjlistResid.add(edge);
  }

  @Override
  public String toString() {
    String s = "N" + this.name;
    return s;
  }

}
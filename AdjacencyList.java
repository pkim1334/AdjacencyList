/*
// Patrick Kim
// Lab 06
// December 7, 2019
// *used outside sources for help
//  on implementing Dijkstra's algorithm
*/

import java.util.*;

public class AdjacencyList{
  ArrayList<Node> arr1;
  int size;


  public class Node{
    int current;
    int previous;
    int weight;
    boolean status;
    Node next;

    public Node(int c, int p, int newWeight){
      current = c;
      previous = p;
      next = null;
      status = false;
      weight = newWeight;
    }
  }

  public AdjacencyList(int s){
    size = s;
    arr1 = new ArrayList<Node>(size);

    for(int i = 0; i < size; i++){
      Node empty = new Node(i, i, -99);
      arr1.add(empty);
    }
  }

  public void insert(int index, int pos, int newWeight){
    Node temp = new Node(index, pos, newWeight);
    Node traverse = arr1.get(pos);

    while(traverse.next != null){
      traverse = traverse.next;
    }

    traverse.next = temp;
  }

  public void remove(int index, int newVal){
    for(int i = 0; i <size; i++){
      if(i == index){
        Node traverse = arr1.get(index);
        while(traverse.next.weight != newVal){
          traverse = traverse.next;

        }

      traverse.next = traverse.next.next;
      }
    }
  }

  public String print(){
    String temp = "";

    for(int i = 0; i < size; i++){
      Node traverse = arr1.get(i);

      temp += "Node " + i + " connects to: ";
      while (traverse.next != null) {
        traverse = traverse.next;
        temp = temp + traverse.weight + " ";

      }
      temp = temp + "\n";
    }
    return temp;
  }

  public String search(int index, int newVal){
    for(int i = 0; i <size; i++){
      if(i == index){
        Node traverse = arr1.get(index);
        while(traverse.next != null){
          if(traverse.weight == newVal){ //Checks if data equals the value given when it does return index
              return "In there";
          }
          traverse = traverse.next;
        }
        if(traverse.weight == newVal){ //Checks if data equals the value given when it does return index
          return "In there";
        }
        else{
          return "Not in there";
        }
      }
    }
    return "Invalid index";
  }

  public String DFS(){
    boolean[] visit = new boolean[arr1.size()];
    Stack<Integer> stack = new Stack<Integer>();
    Node traverse = arr1.get(0);
    int current;
    String output = "";

    stack.push(traverse.weight);
    while(!stack.isEmpty()){
      current = stack.pop();


      visit[current] = true;

      for(int i = 0; i < size; i++){
        if(!visit[i]){
          stack.push(i);
        }
      }
      output += current + " ";
    }
    return output;
  }

  public String BFS(){
    boolean[] visit = new boolean[size];
    CircularQueue queue = new CircularQueue(size);
    Node temp = arr1.get(0);
    int current=0;
    String output = "";
    int n = 0;
    queue.enqueue(temp.weight);
    visit[0] = true;
    while(!queue.isEmpty()){
      for(int i = 0; i<size; i++){
        current = queue.getFront();
        queue.dequeue();
        output = output + current +" ";
        Node traverse = arr1.get(i);

        while(traverse.next != null){
          traverse = traverse.next;

          if(traverse.next != null){
            n = traverse.next.weight;
          }

          if(!visit[n]){
            visit[n] = true;
            queue.enqueue(n);
          }

        }
      }
    }
    return output;
  }

  public void dijkstra(int n){
    for(int i = 0; i < size; i++){
      arr1.get(i).status = false;
      arr1.get(i).weight = 0;
      arr1.get(i).previous = -1;
    }

    Node currentNode = arr1.get(n);
    currentNode.previous = n;
    currentNode.weight = 0;

    HeapsDontLie<Node> h1 = new HeapsDontLie<Node>(size * 2);
    Node traverse;
    Node temp;

    String str = "";
    str = str + String.format("(Current|Weight|Previous)\n");

    boolean known = false;

    while(!known){
      traverse = currentNode;

      while(traverse.next != null){
        traverse = traverse.next;
        h1.insert(traverse,traverse.weight);
      }

      currentNode.status = true;
      known = true;

      for(int i = 0; i < size; i++){
        if(!arr1.get(i).status){
          known = false;
        }
      }

      if(!known){
        temp = h1.remove();

        if(temp == null){
          break;
        }

        currentNode = arr1.get(temp.current);
        n = 0;

        while (currentNode.status) {
          temp = h1.remove();
          currentNode = arr1.get(temp.current);
          n++;

          if(temp == null || n == size){
            break;
          }
        }

        if(n == size){
          break;
        }

        currentNode.previous = temp.previous;
        currentNode.weight = currentNode.weight + temp.weight;
        currentNode.weight = currentNode.weight + arr1.get(temp.previous).weight;
      }
    }

      for(int i = 0; i < size; i++){
        currentNode = arr1.get(i);
        str = str + String.format("(" + currentNode.current + ", " + currentNode.weight + ", "+ currentNode.previous + ")\n");
      }
      System.out.println(str);

  }
/*
  public int tConverter(T val){
    String temp2 = val.toString();
    if(temp2.matches("-?(0|[1-9]\\d*)")){
      int result1 = Integer.parseInt(temp2);
      return result1;
    }
    return 0;
  }
*/





}

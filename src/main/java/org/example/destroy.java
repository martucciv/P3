package org.example;
/*Veronica Martucci
* COP3503 Spring 2022
* P3: Destroying Connectivity
*/
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class destroy {

    final public static int MAX = 100000;
    public int[] parent;

    public destroy(int n){

        parent = new int[MAX];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public static void main(String[] args){

        int numComputers, numConnections, numDestroy;

        Scanner stdin = new Scanner(System.in);

        //create the individual sets
        numComputers = stdin.nextInt();
        destroy d = new destroy(numComputers);

        numConnections = stdin.nextInt();
        numDestroy = stdin.nextInt();

        //loop through the number of connections and connect them
        for(int i = 0; i < numConnections; i++){

            int v1 = stdin.nextInt();
            int v2 = stdin.nextInt();
            d.union(v1, v2);
        }

        //get the original connectivity of the network
        int[] connectivity = new int[numDestroy + 1];
        connectivity[0] = d.calculateConnectivity();

        //loop through the numbers to destroy and break the connection
        for(int i = 1; i <= numDestroy; i++){
            int destroy = stdin.nextInt();
            d.unconnect(destroy);
            connectivity[i] = d.calculateConnectivity();
        }

        //print
        d.print(numDestroy, connectivity);
    }

    //calculate the total connectivity and return the result
    private int calculateConnectivity() {

        TreeMap<Integer, Integer> sets = new TreeMap<>();

        int val = 1;

        //loop the number of computers and mark each time a root is used to know how many times a root is used
        for(int i = 0; i < parent.length; i++){

            //if the key doesn't exist, create it, and set the val to 1
            if(!sets.containsKey(parent[i])){

                sets.put(i, val);
            }
            //if key exists, increase the val count by 1
            else{
                sets.replace(parent[i], val++);
            }
        }

        int res = 0;

        //loop through the TreeMap and calculate the result
        for(Map.Entry<Integer, Integer> entry : sets.entrySet()){

           res += Math.pow(entry.getValue(), 2);
        }

        return res;
    }

    //Connect the two roots
    private void union(int v1, int v2) {

        //find the roots
        int root1 = find(v1);
        int root2 = find(v2);

        //attach v2 to t1
        if(root1 != root2){
            parent[root2] = root1;
        }
    }

    //Find the root of the tree
    private int find(int v) {

        //at root of tree
        if(parent[v] == v){
            return v;
        }

        //find parent's root
        int root = find(parent[v]);

        //attach directly to root
        parent[v] = root;
        return root;
    }

    //break the link between a num and the set
    private void unconnect(int v){



    }

    //print the connectivity
    private void print(int numDestroy, int[] connectivity){

        for(int i = 0; i < numDestroy + 1; i++){
            System.out.println(connectivity[i]);
        }
    }
}
